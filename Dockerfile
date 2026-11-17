FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY ClickButtonServer.java .
RUN javac ClickButtonServer.java
CMD ["java", "ClickButtonServer"]