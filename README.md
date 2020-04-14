<h1 align="center">
    Cash House
</h1>

<h2 align="center">
  Servidor Java
</h2>

<p align="center">
  <img alt="License" src="https://img.shields.io/github/license/marcelobojikian/Cash-House-Backend-Java" />
  <img alt="Travis Building" src="https://travis-ci.org/marcelobojikian/Cash-House-Backend-Java.svg?branch=master" />
</p>

<p align="center">
  <img alt="Quality Gate" src="https://sonarcloud.io/api/project_badges/measure?project=marcelobojikian_Cash-House-Backend-Java&metric=alert_status" />
  <img alt="Security Rating" src="https://sonarcloud.io/api/project_badges/measure?project=marcelobojikian_Cash-House-Backend-Java&metric=security_rating" />
  <img alt="Coverage" src="https://sonarcloud.io/api/project_badges/measure?project=marcelobojikian_Cash-House-Backend-Java&metric=coverage" />
</p>

# About
Cash House has the purpose of managing one or more savings cashiers, very common at shared home where all the members of the house must contribute money for specific accounts such as energy, water, electricity, food and others.

<p align="center">
  <a href="#dependencies">Dependencies</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#how-to-run">How to Run</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#how-to-use">How to Use</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#tools-and-technologies-to-be-used">Tools and Technologies</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#issues">Issues</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#license">License</a>
</p>

## Dependencies
    1. Download and Install [Java](https://www.oracle.com/java/) 1.8 or above ( recommended )
    2. Download and Install [Maven](https://maven.apache.org/) 3.6+ ( recommended )
    3. Download and Install [Docker](https://www.docker.com/) 19.03.6 ( recommended )
    4. Download and Install [Docker Compose](https://docs.docker.com/compose/) 1.25.0 ( recommended )
    
## How to Run
    1. git clone https://github.com/marcelobojikian/Cash-House-Backend-Java.git
    2. cd backend

### Database
    3. docker-compose up

### System
    4. mvn package
    5. java -jar server/target/server-1.0-SNAPSHOT.jar

## How to Use
When executing the project it is possible to view the documentation of the resources that can be consumed by the link http://localhost:8080/swagger-ui.html, see an example:

## Tools and Technologies to be used
* Use Maven for dependency management.
* Spring Oauth2, Spring Security. 
* Flyway, QueryDsl.
* PostgreSql and H2database for test.

## Issues

If face an issue, please notify it [here](https://github.com/marcelobojikian/cash-house/issues) as a new issue.

## License

Cash House is licensed under The MIT License (MIT). Which means that you can use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software. But you always need to state that Cash House is the original author of this template.

Project is developed and maintained by Marcelo Nogueira Bojikian

