public class StockAsset {
    private final String stockSymbol;
    private final String companyName;
    private double currentPrice;
    private double previousClosePrice;

    public StockAsset(String stockSymbol, String companyName, double currentPrice) {
        this.stockSymbol = stockSymbol;
        this.companyName = companyName;
        this.currentPrice = currentPrice;
        this.previousClosePrice = currentPrice;
    }

    public String getStockSymbol() { return stockSymbol; }
    public String getCompanyName() { return companyName; }
    public double getCurrentPrice() { return currentPrice; }
    public double getPreviousClosePrice() { return previousClosePrice; }

    public double getChangeAmount() { return currentPrice - previousClosePrice; }
    public double getChangePercent() { 
        return previousClosePrice == 0 ? 0 : (getChangeAmount() / previousClosePrice) * 100; 
    }

    public void updatePrice(double updatedPrice) {
        this.previousClosePrice = this.currentPrice;
        this.currentPrice = Math.max(0.01, updatedPrice);
    }

    @Override
    public String toString() {
        String trendArrow = getChangeAmount() > 0 ? "^" : (getChangeAmount() < 0 ? "v" : "-");
        return String.format("%-10s %-22s $%-10.2f %s %+.2f (%+.2f%%)",
                stockSymbol, companyName, currentPrice, trendArrow, getChangeAmount(), getChangePercent());
    }
}
