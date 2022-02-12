FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /application/
ENTRYPOINT ["java","-jar","/application/account-book-0.0.1-SNAPSHOT.jar"]
