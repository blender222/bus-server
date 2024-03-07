FROM amazoncorretto:17.0.10-alpine
WORKDIR /app
COPY /build/libs/app.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
