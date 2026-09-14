import java.util.Scanner;

public class TradingDashboard {
    private static final Scanner inputScanner = new Scanner(System.in);
    private static final StockMarket globalMarket = new StockMarket();
    private static final DatabaseManager dbManager = new DatabaseManager();
    private static UserPortfolio activePortfolio;

    public static void main(String[] args) {
        activePortfolio = dbManager.loadPortfolio();

        System.out.println("=========================================");
        System.out.println("         STOCK TRADING PLATFORM ");
        System.out.println("=========================================");
        System.out.println("(Prices are simulated - each action dynamically updates the market)");

        boolean isRunning = true;
        while (isRunning) {
            displayDashboard();
            String userChoice = inputScanner.nextLine().trim();

            globalMarket.simulateMarketTick();

            switch (userChoice) {
                case "1" -> viewMarket();
                case "2" -> buyStock();
                case "3" -> sellStock();
                case "4" -> viewPortfolio();
                case "5" -> viewTransactionHistory();
                case "6" -> isRunning = false;
                default -> System.out.println("Invalid Input. Please select a valid option (1-6).");
            }
        }
        dbManager.savePortfolio(activePortfolio);
        System.out.println("You Loged out , Thank you.");
    }

    private static void displayDashboard() {
        System.out.println("\n==================== Dashboard ====================");
        System.out.println("1. View Market");
        System.out.println("2. Buy Stock");
        System.out.println("3. Sell Stock");
        System.out.println("4. View Portfolio");
        System.out.println("5. Transaction History");
        System.out.println("6. Leave the Market");
        System.out.print("Select our choose: ");
    }

    private static void viewMarket() {
        System.out.println("----------------------- Live Market ---------------------------");
        System.out.printf("%-10s %-22s %-12s %s%n", "Company", "NAME", "PRICE", "CHANGE");
        System.out.println("---------------------------------------------------------------");
        for (StockAsset currentAsset : globalMarket.getAvailableAssets()) {
            System.out.println(currentAsset);
        }
        System.out.println("---------------------------------------------------------------");
    }

    private static void buyStock() {
        System.out.println("--------------- Bought Stock ---------------");
        System.out.println("--------------------------------------------");
        System.out.printf("Cash available: $%.2f%n", activePortfolio.getAvailableCash());
        System.out.print("Enter name of Stocks you need to buy: ");
        String enteredSymbol = inputScanner.nextLine().trim().toUpperCase();

        var assetOpt = globalMarket.getAsset(enteredSymbol);
        if (assetOpt.isEmpty()) {
            System.out.println("invalid input. Use 'View Market' to see available options.");
            return;
        }

        StockAsset targetAsset = assetOpt.get();
        System.out.printf("%s is currently trading at  $%.2f%n", targetAsset.getStockSymbol(),
                targetAsset.getCurrentPrice());
        int tradeQuantity = readInt("Enter quantity of stocks buy: ");

        UserPortfolio.TradeResult result = activePortfolio.buyAsset(targetAsset, tradeQuantity);
        if (result.isSuccessful) {
            System.out.printf("Success : Bought %d share(s) of %s for $%.2f.%n", tradeQuantity, enteredSymbol,
                    targetAsset.getCurrentPrice() * tradeQuantity);
            System.out.println("--------------------------------------------");
        } else {
            System.out.println("FAILED: " + result.errorMessage);
            System.out.println("--------------------------------------------");
        }
    }

    private static void sellStock() {
        System.out.println("-------------- Sold Stock --------------");
        System.out.println("----------------------------------------");
        System.out.print("Enter name of Stock you need to Sell: ");
        String enteredSymbol = inputScanner.nextLine().trim().toUpperCase();

        var assetOpt = globalMarket.getAsset(enteredSymbol);
        if (assetOpt.isEmpty()) {
            System.out.println(" Unknown symbol, Enter Vaild Input");
            return;
        }

        StockAsset targetAsset = assetOpt.get();
        var holdingOpt = activePortfolio.getHolding(enteredSymbol);
        int sharesOwned = holdingOpt.map(PortfolioHolding::getShareCount).orElse(0);

        System.out.printf("You own %d share(s) of %s. Current price: $%.2f%n", sharesOwned, enteredSymbol,
                targetAsset.getCurrentPrice());
        if (sharesOwned == 0)
            return;

        int tradeQuantity = readInt("Enter quantity to sell: ");
        UserPortfolio.TradeResult result = activePortfolio.sellAsset(targetAsset, tradeQuantity);
        if (result.isSuccessful) {
            System.out.printf("SUCCESS: Sold %d share(s) of %s for $%.2f.%n", tradeQuantity, enteredSymbol,
                    targetAsset.getCurrentPrice() * tradeQuantity);
            System.out.println("--------------------------------------------");
        } else {
            System.out.println(">>> FAILED: " + result.errorMessage);
            System.out.println("--------------------------------------------");
        }
    }

    private static void viewPortfolio() {
        System.out.println("\n-------------- Portfolio --------------");
        System.out.printf("Cash balance   : $%.2f%n", activePortfolio.getAvailableCash());
        System.out.printf("Holdings value : $%.2f%n", activePortfolio.getHoldingsValue(globalMarket));
        System.out.printf("Net worth      : $%.2f%n", activePortfolio.getNetWorth(globalMarket));
        System.out.printf("Total P/L      : $%.2f%n", activePortfolio.getTotalProfitLoss(globalMarket));
        System.out.println("----------------------------------------------");

        if (activePortfolio.getHoldings().isEmpty()) {
            System.out.println("No Holdings Found  - Start Investing Today");
            return;
        }

        System.out.printf("%-10s %-6s %-10s %-10s %-12s %s%n", "SYM", "QTY", "AVG COST", "PRICE", "VALUE", "P/L");
        for (PortfolioHolding activeHolding : activePortfolio.getHoldings()) {
            double currentPrice = globalMarket.getAsset(activeHolding.getStockSymbol()).map(StockAsset::getCurrentPrice)
                    .orElse(activeHolding.getAverageCost());
            System.out.printf("%-10s %-6d $%-9.2f $%-9.2f $%-11.2f $%.2f%n",
                    activeHolding.getStockSymbol(), activeHolding.getShareCount(), activeHolding.getAverageCost(),
                    currentPrice,
                    activeHolding.getMarketValue(currentPrice), activeHolding.getProfitLoss(currentPrice));
        }
    }

    private static void viewTransactionHistory() {
        System.out.println("\n--------------- Transaction History ---------------");
        if (activePortfolio.getTradeHistory().isEmpty()) {
            System.out.println("No transactions recorded Found.");
            return;
        }
        for (TradeTransaction pastTransaction : activePortfolio.getTradeHistory()) {
            System.out.println(pastTransaction);
        }
        System.out.println("---------------------------------------");
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = inputScanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println(" Invalid Input. Please enter a valid Input.");
            }
        }
    }
}
