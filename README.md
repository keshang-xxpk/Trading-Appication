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
- **GET**
<pre>/health to make sure SpringBoot app is up and running</pre>
#### dashboard-controller
- **GET**
#### order-controller
- **POST**
#### quote-controller
- **GET**
- **GET**
- **POST**
- **PUT**
- **PUT**
#### trader-controller
- **DELETE**
- **POST**
- **POST**
- **PUT**
- **PUT**
