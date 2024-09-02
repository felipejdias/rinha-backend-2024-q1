# Usando uma imagem base mais leve para build
FROM gradle:jdk21-alpine AS build

# Diretório de trabalho para o build
WORKDIR /usr/src/rinha-backend

# Copiar gradle cache e adicionar arquivos gradle para cache eficiente
COPY gradle /usr/src/rinha-backend/gradle
COPY build.gradle.kts settings.gradle.kts /usr/src/rinha-backend/

RUN gradle build -i --stacktrace || return 0

# Copiar o resto do código
COPY . /usr/src/rinha-backend

RUN gradle build --no-daemon

# Usando uma imagem base otimizada para produção
FROM azul/zulu-openjdk-alpine:21

# Copiar o jar gerado
COPY --from=build /usr/src/rinha-backend/build/libs/*.jar /rinha-backend/app.jar

WORKDIR /rinha-backend

# Subindo para porta 8080
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/rinha-backend/app.jar"]