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
    
  
##Installation and running the aplication

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

