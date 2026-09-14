import java.util.*;

public class StockMarket {
    private final Map<String, StockAsset> availableAssets = new LinkedHashMap<>();
    private final Random priceSimulator = new Random();

    public StockMarket() {
        seedMarketData();
    }

    private void seedMarketData() {
        addStockAsset("NVIDIA", "Nvidia", 125.80);
        addStockAsset("MICROSOFT", "MicroSoft", 430.00);
        addStockAsset("RELIENCE", "Relience ind", 310.25);
        addStockAsset("APPLE", "Apple", 225.50);
        addStockAsset("TATA", "TATA", 420.00);
        addStockAsset("AMAZON", "Amazon", 190.50);
        addStockAsset("GOOGLE", "Google", 165.20);
        addStockAsset("MAHANDRA", "Mahandra", 290.50);
    }

    private void addStockAsset(String stockSymbol, String companyName, double initialPrice) {
        availableAssets.put(stockSymbol, new StockAsset(stockSymbol, companyName, initialPrice));
    }

    public Collection<StockAsset> getAvailableAssets() { return availableAssets.values(); }

    public Optional<StockAsset> getAsset(String stockSymbol) {
        return Optional.ofNullable(availableAssets.get(stockSymbol.toUpperCase()));
    }

    public void simulateMarketTick() {
        for (StockAsset currentAsset : availableAssets.values()) {
            double percentageChange = (priceSimulator.nextDouble() * 6.0) - 3.0; 
            double updatedPrice = currentAsset.getCurrentPrice() * (1 + percentageChange / 100.0);
            currentAsset.updatePrice(updatedPrice);
        }
    }
}
