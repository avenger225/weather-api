# Worldwide Windsurfer’s Weather Service

Project created with usage of Spring Boot framework (Java 11) - REST API available for surfing conditions diagnostics

##Tech

Used technologies:

- Java 11
- Spring Boot/Spring/Spring Security/Spring Data JPA
- JWT Authorization
- Maven
- EhCache
- H2 database
- Log4j
- Spring Data
- Swagger 2
- Lombok
- Intellij IDEA
    
  
##Installation and running the application


###With usage of Intellij IDEA

- Import project from repository
- Use Maven to build project: ```install -f pom.xml```
- Run project with: ```spring-boot:run -f pom.xml```


###Usage with Command Line

Attention: JAVA_HOME as system variable required

- Open command line in project's main directory
- Use Maven to build project: ```./mvnw install```
- Use commends: 
``` 
cd target

java -jar weather-0.0.1.jar
```

  
##Usage of project with Swagger UI

1. Open link http://localhost:8080/swagger-ui.html

Correct authorization with JWT token is required.

More info about JTW: https://jwt.io/introduction

First step is to use POST method login in LoginController with predefined user data in request body:
``` 
{
  "login": "test",
  "password": "test"
} 
```

In the response we get:
``` 
{
  "Authorization": "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNjE0OTc2ODEwfQ.CARSk0S8NW5CIW_KkCHA4S_j1gLyldKVAXshoCb918E"
}
```

To be able to use all methods now we need to paste the value in quotation marks (Bearer XYZ) for key "Authorization" from that response body to Swagger's authorizations pop'up (green button "Authorize" with lock icon).

If the authorization is successful, we can verify that fact by using the method http://localhost:8080/login/test.

In response, we should see response body with a word 'test';

##Usage of project with Swagger UI

Methods from Weather Controller with usage of the Weatherbit Forecast API as the source of weather forecast:

- /weather/best-surfing-location/{date}
    
    Getting surfing location with the best surfer coefficient's value for given date. 
    If the date is within 16 days of today - method should return a city name.

- /weather/{city}
    
    Getting basic weather's forecast data for given city.
    
- /weather/map
    
    Getting forecast data (including surferCoefficient parameter) map for next 16 days for cities embedded in H2 database.
    
 - /weather/surfing
    
    Getting full weather's forecast list for cities embedded in H2 database.
   