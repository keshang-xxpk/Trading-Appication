# Trading Application
## Introduction
- This project is an online stock trading simulation REST API which can handle options for user to manage their trading account,buy and sell stock,get the latest data from the stock market,and ect.
- Front-end developer,mobile developer,traders and use it easliy.
- It is a MicroService which implemented with SpringBoot framework and build JDBC through Spring.Iex market data as our source data and PSQL is the database I use in this project.
- Quick Link:<br>
  [SpringBoot](https://spring.io/projects/spring-boot)<br>
  [PSQL](https://www.postgresql.org)<br>
  [IEX Market data](https://iexcloud.io)<br>
  [Swagger](https://swagger.io)
  


## Quick Start
 - Prequiresites:
      \-JRD virtual machine       
      \- centOS 7        
      \- Java 8   
      \- Docker(17.05 or higher which support multi-stage build)
            
- PSQL init
- git clone and mvn build
- Strating Springboot app using a shell script
- Sign up an account on IEX.
-IEX token for getting market data (https://iexcloud.io/docs/api/)

## REST API Usage
### Swagger
- Swagger is a powerful yet easy-to-use suite of API developer tools for teams and individuals, enabling development across the entire API lifecycle, from design and documentation, to test and deployment.
- Swagger provides a set of tools that help programmers generate client or server code and install self-generated documentation for web services.
### Trading App controller

#### app-controller
- **GET /health**
<pre>/health to make sure SpringBoot app is up and running</pre>
#### dashboard-controller
- **GET /dashboard/portfolio/traderId/{traderId}**
<pre>Show portfolio by trader ID</pre>
- **GET /dashboard/profile/traderId/{traderId}**
<pre>Show trader profile by trader ID</pre>
#### order-controller
- <pre>Order controller implements OrderService.</pre>
- **POST /order/marketOrder**
<pre>Submit a market order with DTO</pre>
#### quote-controller
-  <pre>Quote controller implements QuoteService.It can allow users to sell and buy stock.It can get data through MarketDataDao from IEX.Update the user's account in database.</pre>
- **GET /quote/dailyList**
<pre>list all securities that are available to trading in this trading system</pre>
- **GET /quote/iex/ticker/{ticker}**
<pre>Show iexQuote for a given ticker/symbol from IEX which is an external market data source</pre>
- **POST /quote/tickerId/{tickerId}**
<pre>Add a new ticker/symbol to the quote table, so trader can trade this security.</pre>
- **PUT /quote/**
<pre>Manually update a quote in the quote table</pre>
- **PUT /quote/iexMarketData**
<pre>Update all quotes from IEX</pre>
#### trader-controller
- <pre>Trader controller implements FundTransferService and RegisterService. It could manage trader and account information such as create a new account and delete an exist account. Also, it could deposit and withdraw fund from a given account.</pre>
- **DELETE /trader/traderId/{traderId}**
<pre>Delete a trader</pre>
- **POST /trader/**
<pre>Create a trader and an account with DTO</pre>
- **POST /trader/firstname/{firstname}/lastname/{lastname}/dob/{dob}/country/{country}/email/{email}**
<pre>Create a trader and an account with url</pre>
- **PUT /trader/deposit/traderId/{traderId}/amount/{amount}**
<pre>Deposit a fund</pre>
- **PUT /trader/withdraw/traderId/{traderId}/amount/{amount}**
<pre>Withdraw a fund</pre>

## Architechture
![](https://github.com/keshang-xxpk/Trading-Appication/blob/master/Trading-app-architicture.png)
### Data Access Object(DAO):<pre>DAO stands for data access object. It provides a CRUD interface for a single entity.</pre>
- TraderDao
- AccountDao
- QuoteDao
- SecurityOdreDao
- PositionDao
- MarketDataDao
### Service Layer(Bussiness Logic)<pre>The service layer stands on DAO to handle business requirements.Inside the service, we could design different business logic such as implementing validations, constraints and so on.</pre>
- DashboardService
- FundTransferService
- OrderService
- RegisterService
- QuoteService
### Controller(PREST API)<pre>Controllers receive input, and generate output. They would handle the navigation between the different views</pre>
- DashboardController
- TraderController
- OrderController
- QuoteController

