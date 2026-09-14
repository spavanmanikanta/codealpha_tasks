import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;

public class DatabaseManager {
    private static final String DATABASE_FILE = "trading_account.db";
    private static final double DEFAULT_STARTING_CASH = 10000.00;

    public UserPortfolio loadPortfolio() {
        Path databasePath = Paths.get(DATABASE_FILE);
        UserPortfolio activePortfolio = new UserPortfolio(DEFAULT_STARTING_CASH);
        if (!Files.exists(databasePath)) return activePortfolio;

        try (BufferedReader fileReader = Files.newBufferedReader(databasePath, StandardCharsets.UTF_8)) {
            String currentLine;
            while ((currentLine = fileReader.readLine()) != null) {
                if (currentLine.isBlank()) continue;
                String[] parsedData = currentLine.split("\\|", -1);
                
                switch (parsedData[0]) {
                    case "CASH" -> activePortfolio.setAvailableCash(Double.parseDouble(parsedData[1]));
                    case "HOLDING" -> activePortfolio.restoreHolding(
                            new PortfolioHolding(parsedData[1], Integer.parseInt(parsedData[2]), Double.parseDouble(parsedData[3])));
                    case "TX" -> activePortfolio.restoreTransaction(new TradeTransaction(
                            TradeType.valueOf(parsedData[1]), parsedData[2], Integer.parseInt(parsedData[3]),
                            Double.parseDouble(parsedData[4]), LocalDateTime.parse(parsedData[5])));
                    default -> { /* ignore unknown/corrupt line */ }
                }
            }
        } catch (IOException error) {
            System.out.println("Warning: could not read " + DATABASE_FILE + " (" + error.getMessage() + ")");
        }
        return activePortfolio;
    }

    public void savePortfolio(UserPortfolio activePortfolio) {
        try (BufferedWriter fileWriter = Files.newBufferedWriter(
                Paths.get(DATABASE_FILE), StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

            fileWriter.write("CASH|" + activePortfolio.getAvailableCash());
            fileWriter.newLine();

            for (PortfolioHolding currentHolding : activePortfolio.getHoldings()) {
                fileWriter.write("HOLDING|" + currentHolding.getStockSymbol() + "|" + currentHolding.getShareCount() + "|" + currentHolding.getAverageCost());
                fileWriter.newLine();
            }
            for (TradeTransaction pastTransaction : activePortfolio.getTradeHistory()) {
                fileWriter.write("TX|" + pastTransaction.getTradeType() + "|" + pastTransaction.getStockSymbol() + "|" + pastTransaction.getTradedQuantity()
                        + "|" + pastTransaction.getExecutionPrice() + "|" + pastTransaction.getTradeTimestamp());
                fileWriter.newLine();
            }
        } catch (IOException error) {
            System.out.println("Warning: could not save " + DATABASE_FILE + " (" + error.getMessage() + ")");
        }
    }
}
