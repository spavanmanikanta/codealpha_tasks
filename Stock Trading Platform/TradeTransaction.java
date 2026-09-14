import java.time.LocalDateTime;

public class TradeTransaction {
    private final TradeType tradeType;
    private final String stockSymbol;
    private final int tradedQuantity;
    private final double executionPrice;
    private final LocalDateTime tradeTimestamp;

    public TradeTransaction(TradeType tradeType, String stockSymbol, int tradedQuantity, double executionPrice, LocalDateTime tradeTimestamp) {
        this.tradeType = tradeType;
        this.stockSymbol = stockSymbol;
        this.tradedQuantity = tradedQuantity;
        this.executionPrice = executionPrice;
        this.tradeTimestamp = tradeTimestamp;
    }

    public TradeType getTradeType() { return tradeType; }
    public String getStockSymbol() { return stockSymbol; }
    public int getTradedQuantity() { return tradedQuantity; }
    public double getExecutionPrice() { return executionPrice; }
    public double getTotalTradeValue() { return executionPrice * tradedQuantity; }
    public LocalDateTime getTradeTimestamp() { return tradeTimestamp; }

    @Override
    public String toString() {
        return String.format("%-4s %-6d %-10s @ $%-9.2f = $%-10.2f  %s",
                tradeType, tradedQuantity, stockSymbol, executionPrice, getTotalTradeValue(), tradeTimestamp);
    }
}
