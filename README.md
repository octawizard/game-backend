# game-backend

This is a simple backend for a game; only Java 8 and Maven
have been used, except for some frameworks for unit
testing (Junit, Mockito and EqualsVerifier).

Dependency injection is done in a handmade simplistic way, but
that was not the main goal of this exercise.

This project follows a Clean Architecture approach, with a
multi module maven project, in which communications between
modules is possible via interfaces.

The server has been implemented using
`com.sun.net.httpserver.HttpServer`

The maven modules that compose this repository are:
* injector
* injector-contract
* injector-client
* model
* repository
* repository-contract
* usecase
* usecase-contract
* web
* starter

To generate the runnable jar, type
`mvn clean install`

To run it, type 
`java -jar target/game-backend.jar`