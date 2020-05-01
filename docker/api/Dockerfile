FROM maven:latest
WORKDIR app
COPY c-application ./c-application
COPY c-common ./c-common
COPY c-mysql ./c-mysql
COPY pom.xml ./
RUN mvn clean install -DskipTests -P prod
RUN mv c-application/target/*.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","app.jar"]
