# Use uma imagem base com GraalVM e Java 21 (ajuste a tag conforme necessário)
FROM container-registry.oracle.com/graalvm/native-image:21

# copia arquivos
WORKDIR /rinha-backend

# Copie todos os arquivos
COPY . /rinha-backend

RUN microdnf install findutils

RUN ./gradlew build

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/rinha-backend/build/libs/rinha-backend-2024-q1-0.0.1-SNAPSHOT.jar"]