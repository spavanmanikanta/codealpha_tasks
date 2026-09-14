public class PortfolioHolding {
    private final String stockSymbol;
    private int shareCount;
    private double averageCost;

    public PortfolioHolding(String stockSymbol, int shareCount, double averageCost) {
        this.stockSymbol = stockSymbol;
        this.shareCount = shareCount;
        this.averageCost = averageCost;
    }

    public String getStockSymbol() { return stockSymbol; }
    public int getShareCount() { return shareCount; }
    public double getAverageCost() { return averageCost; }

    public void addShares(int addedQuantity, double executionPrice) {
        double calculatedTotalCost = (averageCost * shareCount) + (executionPrice * addedQuantity);
        shareCount += addedQuantity;
        averageCost = shareCount == 0 ? 0 : calculatedTotalCost / shareCount;
    }

    public boolean removeShares(int removedQuantity) {
        if (removedQuantity > shareCount) return false;
        shareCount -= removedQuantity;
        if (shareCount == 0) averageCost = 0;
        return true;
    }

    public double getCostBasis() { return averageCost * shareCount; }
    public double getMarketValue(double livePrice) { return livePrice * shareCount; }
    public double getProfitLoss(double livePrice) { return getMarketValue(livePrice) - getCostBasis(); }
}
