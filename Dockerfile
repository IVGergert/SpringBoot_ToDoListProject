FROM eclipse-temurin:21-jdk

WORKDIR /todo

COPY target/todo-0.0.1-SNAPSHOT.jar todo.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "todo.jar"]