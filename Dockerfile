FROM tomcat:8.5.47-jdk8-openjdk
ARG WAR_FILE=target/personal_saving-0.0.1-SNAPSHOT.war
COPY ${WAR_FILE} /usr/local/tomcat/webapps/
CMD ["catalina.sh","run"]
