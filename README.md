# game-backend

This is a simple backend for a game; only Java 8 and Maven
are used in this project, except for some frameworks for unit
testing (Junit, Mockito and EqualsVerifier).

Dependency injection is done in a simplistic way, but that was
not the main goal of this exercise.

This project follows a Clean Architecture approach, with a
multi module maven project, in which communications between
modules is possible via interfaces.

The modules are:
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

To generate the runnable jar type
`mvn clean install
`

To run this type 
`java -jar backend-1.0-SNAPSHOT-jar-with-dependencies.jar`