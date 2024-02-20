FROM tomcat:10.1.13-jdk11

COPY target/rinhabackend-1.0.war /usr/local/tomcat/webapps/ROOT.war

ENV JAVA_OPTS="-XX:+UseG1GC -XX:MinRAMPercentage=80.0"

EXPOSE 8080

CMD ["catalina.sh", "run"]