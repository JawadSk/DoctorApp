# DoctorApp

## Frameworks and Language used:
* Spring
* Spring boot
* My-Sql
* Java
 
## Data Flow
##Controller
###Stock

* getStocksBasedOnType
* getStocksAbovePriceAndLowerDate
* getAllStocksAboveMarketCap
* insertStocks
* insertStocks
* updateTypeById
* updateStockById
* removeStocksByOwnerCount
* 
## Service
### Stock

* getStocksByType
* addStocks
* getStocksAbovePriceAndLowerDate
* getAllStocksAboveMarketCap
* updateMarketCap
* deleteStocksBasedOnCount
* updateTypeById
* updateStockById
* 
## Model
### Stock

* stockId
* stockName
* stockPrices
*stockOwnerCount
* stockType
* stockMarketCap
* stockBirthTimeStamp

## Repository
### Stock

* findByStockPriceGreaterThanAndStockBirthTimeStampLessThanOrderByStockName
* getAllStocksAboveMarketCap
* updateMarketCapById
* deleteStocksBasedOnCount
* modifyStockTypeById
* updateStockById

## Datastructures
* ArrayList

## Project Summary

* This API is a Spring Boot project that is about managing Stocks. We can create, read, update, and delete Stocks in database. In this project request is sent from the client on HTTP in JSON body or from path variable and stored in object then response is sent back from the server by JSON format to the client.

