# rest-api-tests


### Tools

1. Java 11
2. Maven
3. RestAssured
4. Hamcrest


### How to run tests

Tests are run with Maven

1. Clone the project 
````
git clone https://github.com/mohcine1789/rest-api-tests.git
````
2. Rename `.env.example` to `.env` and set up your api key and base url (you can also keep the values as they are)

3. Run the tests
````
mvn clean test
````

### Test cases

Endpoint :   Get https://www.alphavantage.co/query?function=TIME_SERIES_DAIL

**Pre-conditions:**
1. Base url : https://www.alphavantage.co
2. APi key is generated

**Positive test case :** Get raw daily time series successfully

Steps:

1. Get stock marketGET https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=IBM&apikey=demo
2. Then validate :
    . Status code is equal to 200
    . Validate the json schema
    . Validate the response time is less than 1 second

**Negative test case:** Get raw daily time series without api key

1. GET https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=IBM 
2. Then validate :
      . Status code is equal to 200
      . Validate the error message of missing api key
      . Validate the response time is less than 1 second


