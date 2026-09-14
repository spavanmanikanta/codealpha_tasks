import java.time.LocalDateTime;
import java.util.*;

public class UserPortfolio {
    private double availableCash;
    private final Map<String, PortfolioHolding> currentHoldings = new LinkedHashMap<>();
    private final List<TradeTransaction> tradeHistory = new ArrayList<>();

    public UserPortfolio(double initialCash) {
        this.availableCash = initialCash;
    }

    public double getAvailableCash() { return availableCash; }
    public Collection<PortfolioHolding> getHoldings() { return currentHoldings.values(); }
    public List<TradeTransaction> getTradeHistory() { return tradeHistory; }

    public Optional<PortfolioHolding> getHolding(String stockSymbol) {
        return Optional.ofNullable(currentHoldings.get(stockSymbol.toUpperCase()));
    }

    public static class TradeResult {
        public final boolean isSuccessful;
        public final String errorMessage;
        private TradeResult(boolean isSuccessful, String errorMessage) { 
            this.isSuccessful = isSuccessful; 
            this.errorMessage = errorMessage; 
        }
        static TradeResult success() { return new TradeResult(true, null); }
        static TradeResult fail(String message) { return new TradeResult(false, message); }
    }

    public TradeResult buyAsset(StockAsset targetAsset, int tradeQuantity) {
        if (tradeQuantity <= 0) return TradeResult.fail("Quantity must be positive.");
        double totalCost = targetAsset.getCurrentPrice() * tradeQuantity;
        if (totalCost > availableCash) {
            return TradeResult.fail(String.format("Insufficient cash. Need $%.2f, have $%.2f.", totalCost, availableCash));
        }

        availableCash -= totalCost;
        currentHoldings.computeIfAbsent(targetAsset.getStockSymbol(), ticker -> new PortfolioHolding(targetAsset.getStockSymbol(), 0, 0))
                .addShares(tradeQuantity, targetAsset.getCurrentPrice());
        tradeHistory.add(new TradeTransaction(TradeType.BUY, targetAsset.getStockSymbol(), tradeQuantity, targetAsset.getCurrentPrice(), LocalDateTime.now()));
        return TradeResult.success();
    }

    public TradeResult sellAsset(StockAsset targetAsset, int tradeQuantity) {
        if (tradeQuantity <= 0) return TradeResult.fail("Quantity must be positive.");
        PortfolioHolding targetHolding = currentHoldings.get(targetAsset.getStockSymbol());
        if (targetHolding == null || targetHolding.getShareCount() < tradeQuantity) {
            return TradeResult.fail("You don't own enough shares of " + targetAsset.getStockSymbol() + " to sell " + tradeQuantity + ".");
        }

        targetHolding.removeShares(tradeQuantity);
        if (targetHolding.getShareCount() == 0) currentHoldings.remove(targetAsset.getStockSymbol());
        
        double tradeProceeds = targetAsset.getCurrentPrice() * tradeQuantity;
        availableCash += tradeProceeds;
        tradeHistory.add(new TradeTransaction(TradeType.SELL, targetAsset.getStockSymbol(), tradeQuantity, targetAsset.getCurrentPrice(), LocalDateTime.now()));
        return TradeResult.success();
    }

    public double getHoldingsValue(StockMarket activeMarket) {
        double totalValue = 0;
        for (PortfolioHolding activeHolding : currentHoldings.values()) {
            double livePrice = activeMarket.getAsset(activeHolding.getStockSymbol()).map(StockAsset::getCurrentPrice).orElse(activeHolding.getAverageCost());
            totalValue += activeHolding.getMarketValue(livePrice);
        }
        return totalValue;
    }

    public double getNetWorth(StockMarket activeMarket) {
        return availableCash + getHoldingsValue(activeMarket);
    }

    public double getTotalProfitLoss(StockMarket activeMarket) {
        double totalProfitLoss = 0;
        for (PortfolioHolding activeHolding : currentHoldings.values()) {
            double livePrice = activeMarket.getAsset(activeHolding.getStockSymbol()).map(StockAsset::getCurrentPrice).orElse(activeHolding.getAverageCost());
            totalProfitLoss += activeHolding.getProfitLoss(livePrice);
        }
        return totalProfitLoss;
    }

    void setAvailableCash(double updatedCash) { this.availableCash = updatedCash; }
    void restoreHolding(PortfolioHolding restoredHolding) { currentHoldings.put(restoredHolding.getStockSymbol(), restoredHolding); }
    void restoreTransaction(TradeTransaction restoredTransaction) { tradeHistory.add(restoredTransaction); }
}
