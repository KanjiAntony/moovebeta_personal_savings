#FROM openjdk:8-jdk-alpine
FROM tomcat:8.5.47-jdk8-openjdk
#RUN addgroup -S springMoovebetaGroup && adduser -S springMoovebetaSavings -G springMoovebetaGroup
#USER springMoovebetaSavings:springMoovebetaGroup
ARG WAR_FILE=target/personal_saving-0.0.1-SNAPSHOT.war
COPY ${WAR_FILE} /usr/local/tomcat/webapps/
#ENTRYPOINT ["java","-jar","/app.war"]
CMD ["catalina.sh","run"]
