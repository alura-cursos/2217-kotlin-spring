FROM adoptopenjdk/openjdk11
EXPOSE 8080
ADD /target/forum-0.0.1-SNAPSHOT.jar forum.jar
ENTRYPOINT ["java", ""$JAVA_OPTS -XX:+UseContainerSupport", "-Xmx300m -Xss512k -XX:CICompilerCount=2", "-Dserver.port=$PORT", "-jar", "forum.jar"]