📈 Stock Trading Platform

A dynamic, console-based stock market simulation built in Java. This platform allows users to view live (simulated) stock prices, execute buy and sell orders, track their portfolio's profit and loss (P/L), and maintain a complete transaction history.
This project was built to demonstrate object-oriented design, state management, and flat-file data persistence.

🚀 Features

1. Dynamic Market Simulation: Stock prices automatically fluctuate up or down (by up to +/- 3%) every time the user interacts with the dashboard, simulating real market volatility.

2. Portfolio Management: Users start with a default $10,000 cash balance. The system automatically calculates Average Cost, Cost Basis, Current Value, and Total Profit/Loss (P/L) for all holdings.

3. Trade Execution: Robust buy and sell logic prevents users from spending more cash than they have or selling shares they do not own.

4. Transaction Logging: Every successful trade is permanently recorded with a timestamp, action type (BUY/SELL), execution price, and total trade value.

5. Data Persistence: The application features a custom DatabaseManager that automatically saves the user's cash balance, active holdings, and trade history to a local .db flat-file (trading_account.db) upon logging out, ensuring no data is lost between sessions.

🛠️ Project Architecture

  The project follows a clean, modular Object-Oriented design, broken down into the following 8 classes:

1. TradingDashboard: The main entry point. Handles the user interface, console input/output, and the main application loop.

2. StockMarket: Acts as the central exchange. Initializes the available stocks and handles the simulateMarketTick() logic to randomize prices.

3. StockAsset: A data model representing an individual company (e.g., Apple, Microsoft) containing its symbol, name, and current/previous prices.

4. UserPortfolio: The core engine for user data. Manages the available cash, tracks PortfolioHoldings, logs TradeTransactions, and calculates net worth.

5. PortfolioHolding: Represents a user's open position in a specific stock, calculating the blended average cost when new shares are added.

6. TradeTransaction: A historical record of a completed trade, logging the exact time, price, and quantity.

7. TradeType: A simple Enum defining whether a transaction is a BUY or SELL.

8. DatabaseManager: Handles standard I/O operations, writing the current state to a text file and parsing it back into objects upon startup.

💻 How to Run
Prerequisites

Java Development Kit (JDK) 14 or higher installed on your machine (the code utilizes modern Java switch expressions).

A terminal or command prompt.

Installation & Execution

1. Clone or Download the repository and extract the files into a single folder.

2. Open your terminal and navigate to the project directory:

example:  cd path/to/stock_platform

3. Complie all java files.

🎮 How to use 
Upon launching the application, you will be greeted by the main dashboard:


=========================================
   STOCK TRADING PLATFORM   
=========================================
    -- avilable Stosk are didplayed here --

(Prices are simulated - each action dynamically updates the market)

==================== Dashboard
====================
 1. View Market
 2. Buy Stock
 3. Sell Stock
 4. View Portfolio
 5. Transaction History
 6. Leave the Market
 Select our choose:

==================================================

Option 1 (View Market): Displays the current ticker symbols, company names, real-time prices, and how much they have changed since the previous tick.

Option 2 (Buy Stock): Prompts you for a ticker symbol (e.g., NVIDIA) and a quantity. Deducts the total cost from your available cash.

Option 3 (Sell Stock): Prompts you to select a currently owned stock and a quantity to sell, adding the proceeds back to your cash balance.

Option 4 (View Portfolio): Shows your total net worth, uninvested cash, and a detailed breakdown of your open positions with your profit/loss.

Option 5 (Transaction History): Prints a timestamped ledger of every trade you have ever made.

Option 6 (Leave the Market): Saves your progress to trading_account.db and safely exits the application.

⚠️ Don't Force quit while using. Always use option (6. Leave the Market) to exit the application  or else you lost your most recent trades and info not saved into file 

