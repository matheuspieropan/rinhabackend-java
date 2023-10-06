FROM tomcat:10.1.13-jdk11

COPY target/rinhabackend-1.0.war /usr/local/tomcat/webapps/ROOT.war

COPY conf /usr/local/tomcat/conf

ENV CATALINA_OPTS="-XX:+UseParallelGC -XX:MaxRAMPercentage=75.0"

EXPOSE 8080

CMD ["catalina.sh", "run"]