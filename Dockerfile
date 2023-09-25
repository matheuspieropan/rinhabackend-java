FROM tomcat:10.1.13-jdk11

COPY ROOT.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]