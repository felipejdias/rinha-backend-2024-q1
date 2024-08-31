FROM gradle:jdk21-alpine AS build

RUN mkdir -p /usr/src/rinha-backend

COPY . /usr/src/rinha-backend

WORKDIR /usr/src/rinha-backend

RUN gradle build

# Use uma imagem base com GraalVM e Java 21 (ajuste a tag conforme necessário)
FROM container-registry.oracle.com/graalvm/native-image:21

COPY --from=build /usr/src/rinha-backend/build/libs/*.jar /rinha-backend/app.jar

WORKDIR /rinha-backend

EXPOSE 8080
#TODO ainda nao consegue subir dentro de um container docker  porém essa porcaria nao enxerga o banco
ENTRYPOINT ["java", "-jar", "/rinha-backend/app.jar"]