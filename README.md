#Description:

This service is intended for the display of certain gifs depending on the exchange rate compared with the previous day value.
The exchange rate it takes here: https://docs.openexchangerates.org/

According to course change:
- If the rate is higher the service shows a random gif from https://giphy.com/search/rich
- If the rate is lower the service shows a random gif from https://giphy.com/search/broke
- If the rate doesn't change the service shows a random gif from https://giphy.com/search/equal
- In case of server-side errors the service shows a random gif from https://giphy.com/search/error
Exchange rates update at 11:00 a.m. (UTC/GMT +3)

The service is written in Spring Boot 2.7.0 + Java.

For interaction with external services, Feign is used.

The service is built with Gradle.

#Settings:
The server port and the currency code to which exchange rate is compared, addresses, and keys of external services are in the configuration file. 
You can change the keyword for searching the relevant gif. 

The configuration file is here:
/src/main/resourses/application.properties




#Directions for starting:
 
 - use the .jar: 
java -jar build\libs\spring_boot_exchange_rate_comparison-0.0.1-SNAPSHOT.jar
*jdk:17 should be installed

 - use the Docker:
-- create an image from Dockerfile
-- launch in cmd: docker run -d {ID}
