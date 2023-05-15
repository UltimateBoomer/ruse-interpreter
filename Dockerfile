FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
COPY target/ruse-interpreter-0.0.1-SNAPSHOT.jar ruseinterpreter.jar
EXPOSE 8080
# ENTRYPOINT exec java $JAVA_OPTS -jar ruseinterpreter.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
ENTRYPOINT exec java -Djava.security.egd=file:/dev/./urandom -jar ruseinterpreter.jar
