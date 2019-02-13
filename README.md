# car-api-spring-boot
REST API with Spring Boot and H2 database

Running car API locally
This is a Spring Boot application built using Maven. You can build a jar file and run it from the command line:

git clone https://github.com/tvermila/car-api-spring-boot.git<br/>
cd car-api-spring-boot<br/>
./mvnw package<br/>
java -jar target/*.jar<br/>

You can then make requests to http://localhost:8080/cars

You can filter cars by make, model, minYear and maxYear parameters. For example:<br/>
GET REQUEST to http://localhost:8080/cars?minYear=2000&maxYear=2015<br/>
would return JSON array which constains cars manufactured between years 2000 and 2015.
