# simple-task-manager-svc
This spring boot 3 project has covered all simple task manager CRUD apis required for [simple task manager frontend](../../web/simple-task-manager/README.md)

## Prerequisites
* Java 17
* Docker

## Tech/frameworks used
* Liquibase: for database migration and versioning
* Modelmapper: for mapping between DTO and Entity classes
* ESAPI: owasp library to deal with common security vulnerabilties
* Micrometer-Tracing: to generate trace
* OpenAPIv3: for REST API end-points documentation and self-service browser interface
* Logback: for logging
* Junit5: for unit testing
* Lombok: It is a java library that automatically plugs into your editor and build tools, spicing up your java
* Testcontainers: for setting up database instance and aws services for integration test
* JaCoCo: It is plugin used to identify code coverage
* Docker: for containerization

## Setting up Dev
1. Clone the repository with SSH - git clone git@github.com:sarveshpadwal/task-manager.git
2. Install Lombok plugin in your IDE

## Unit Testing Guidelines
* All integration tests should extend [AbstractContainerBaseTest.java](src/test/java/com/sp/simpletaskmanager/AbstractContainerBaseTest.java) class
* All regular unit test classes should be active for test profile

## Build

### `./gradlew build`

Builds the backend

## Run
* Run [docker-compose.yaml](docker-compose.yaml) with docker compose
### `docker compose up -d`
Spins up containers for postgres database, zipkin-server, and rabbitmq 

* Run application in any one of two ways given below
  * Run [SimpleTaskManagerSvcApplication.java](src/main/java/com/sp/simpletaskmanager/SimpleTaskManagerSvcApplication.java) class in
    your IDE. Please configure environment variable `spring.profiles.active=local`
  * Run gradle goal/configuration in
    IDE. 
### `./gradlew bootRun --args='--spring.profiles.active=local'`