FROM eclipse-temurin:21.0.2_13-jdk-alpine

RUN mkdir /app

COPY . /app

WORKDIR /app

RUN chmod +x mvnw

RUN ./mvnw clean package

RUN cp target/springboot-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
