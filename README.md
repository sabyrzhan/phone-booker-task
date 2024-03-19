# Phone Booker Application
### Overview
App lets to book and return phones from predefined list of phones.

### Env variables
Environment variables are defined in .env file. It contains variables for DB. By default, it is using embedded 
H2 database. To use different database change variables to your needs. 

### Run application
Run docker-compose using Docker: `docker-compose up`. This will build the application and run at port `8080`

### Build app
App is built using Maven. To build run: `mvn clean package` 

### Run tests
To run tests: `mvn clean test`. App contains unit and functional tests.