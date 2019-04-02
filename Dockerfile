FROM maven as build
WORKDIR /build
COPY pom.xml .
RUN mvn verify --fail-never
COPY . .
RUN mvn clean package

FROM openjdk:8
COPY --from=build /build/target/Gateway-0.0.1-SNAPSHOT.jar Gateway.jar
ENTRYPOINT ["java","-jar","Gateway.jar"]
