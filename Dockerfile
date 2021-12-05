FROM openjdk:11

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} phonebook-step-app-0.0.1.jar

ENTRYPOINT ["java", "-jar", "/phonebook-step-app-0.0.1.jar"]