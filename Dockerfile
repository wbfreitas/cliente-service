FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src
ENV MONGOUSER=${MONGOUSER} MONGOPWD=${MONGOPWD} SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}

CMD ["./mvnw", "spring-boot:run"]
