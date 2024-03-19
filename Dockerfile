FROM maven:3 AS builder
ADD . /app/
WORKDIR /app/
RUN mvn clean package

FROM eclipse-temurin:21
COPY --from=builder /app/target/phone-booker-task.jar /app/
EXPOSE 8080
CMD ["java", "-jar", "/app/phone-booker-task.jar"]
