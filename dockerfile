#container have jdk-17
FROM eclipse-temurin:17-jdk 

WORKDIR /app

COPY target/*.jar app.jar

#app runs at 8080 
EXPOSE 8080

#runs jar when container starts
ENTRYPOINT ["java","-jar","app.jar"]