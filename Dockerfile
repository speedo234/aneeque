FROM tomcat:9.0-alpine

COPY ./target/demo-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/


EXPOSE 8089

CMD java -version
CMD ["catalina.sh", "run"]