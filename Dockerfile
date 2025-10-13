FROM openjdk:21-jdk-slim

WORKDIR /todo

COPY target/todo-0.0.1-SNAPSHOT.jar todo.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "todo.jar"]