FROM tomcat:10.1.13-jdk11

COPY target/rinhabackend-1.0.war /usr/local/tomcat/webapps/ROOT.war

COPY conf/server.xml /usr/local/tomcat/conf

ENV JAVA_OPTS="-XX:MaxRAMPercentage=70 -XX:InitialRAMPercentage=70"

EXPOSE 8080

CMD ["catalina.sh", "run"]