
## Running

Execute
```
sbt web/run
```

And then go to http://localhost:9000 to see the running web application.


### Controllers

- [HomeController.scala](controllers/HomeController.scala) - Shows how to handle simple HTTP requests.

- [AsyncController.scala](controllers/AsyncController.scala) - Shows how to do asynchronous programming when handling a request.


### Services

- [WebGameServiceImpl.scala](services/WebGameServiceImpl.scala) - Component that handles receiving requests from user and returns responses from our AI.

- [ApplicationTimer.scala](services/ApplicationTimer.scala) - Component that starts when the application starts and stops when the application stops.

### Filters

- [CorrelationIdRequestFilter.scala](filters/CorrelationIdRequestFilter.scala) - Will output contextual information in the error log message so that you don’t have to scan through all relevant messages (by correlation ID) to piece them together.

