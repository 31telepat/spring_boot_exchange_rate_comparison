#Use the OpenJDK 17 image as a base image
FROM openjdk:17

#Create a new app directory
RUN mkdir /app

#Copy the app files from host machine to image filesystem
COPY build/libs/spring_boot_exchange_rate_comparison-0.0.1-SNAPSHOT.jar/ /app

#Set the directory for executing future commands
WORKDIR /app

#Run application
CMD java -jar /app/spring_boot_exchange_rate_comparison-0.0.1-SNAPSHOT.jar


