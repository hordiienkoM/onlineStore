FROM openjdk:17
WORKDIR /app
COPY . .
LABEL maintainer = "hordiienko"
ADD target/OnlineStore-0.0.1-SNAPSHOT.jar online_store.jar
ENTRYPOINT ["java", "-jar", "online_store.jar"]