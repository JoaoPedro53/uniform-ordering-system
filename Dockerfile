FROM eclipse-temurin:21
LABEL maintainer="jota.dev <jpsv32@gmail.com>"
WORKDIR /uos
COPY target/system-order-uniform-0.0.1-SNAPSHOT.jar uos-system.jar
ENTRYPOINT ["java", "-jar", "uos-system.jar"]