# Market Loan Quoter

##Description
CLI Application to provide best available compound loan out of available market offers.

## Building the application
To build the application in the root directory run:  
`mvn clean install`

## Running the application
After the app has been build, in the root directory run:  
`java -jar target\market-quoter-0.0.1-SNAPSHOT-jar-with-dependencies.jar ${location of market data file} ${size of loan}`

#### Example
`java -jar target\market-quoter-0.0.1-SNAPSHOT-jar-with-dependencies.jar src\test\resources\test-market-data.csv 1500`

## Input requirements
Market data file should contain at least one or more offers. 
Example market data file provided here: `src\test\resources\test-market-data.csv`

Loan size should be between £1000 and £15'000. It should be provided at increments of a £100 e.g. 1100 or 14600.