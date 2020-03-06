# Rate retriever

This project contains the code that solves the assigned task.

## Endpoints

The project exposes two endpoins:
1. GET **/rates** - with optional parameters *startDate*(Default value is 1970-01-01T00:00:00.000000) and *endDate*(Default value is *LocalDateTime.now*). 
This endpoint returns the a json response containing the list of BTC-USD rates between dates. When the parameters are passed, they must be passed as ISO-8601 datetime values. 

2. GET **/rates/latest** - This endpoint returns the latest price of BTC in USD. 
## Architecture

The project consists of a web layer and an in-memory H2 database. The H2 database was used for the sake of simplicity, and
can be replaced by any other database, such as MySQL or Postgre. To store the latest BTC-USD value, the project uses the class 
*LatestRateHolder*. This acts as an extremely simplified cache. Again, in real-life scenarios, a Redis or Memcached cache is 
a more suitable solution.

The application has a Web Layer, for handling of API requests, consisting of the  *RateController* class, and a Service Layer
consisting of the *RateService* interface and its implementation. The web layer handles response and is responsible for creating
suitable responses for the consumer of the API, while the service layer handles the business logic.

Additionally, it has a scheduled service that retrieves the latest BTC-USD rate. The rate of retrieval can be set in the
**application.properties** file, with the parameter *application.checkperiod*. The value set should be in milliseconds. The default
value is 2000ms.