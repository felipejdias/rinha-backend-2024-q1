# Etapa de build
FROM gradle:jdk21-alpine AS build

# Configuração para cache de dependências
COPY build.gradle.kts settings.gradle.kts /usr/src/rinha-backend/

WORKDIR /usr/src/rinha-backend

# Baixar dependências. Isso será cacheado se apenas os arquivos de configuração de dependência mudarem.
RUN gradle dependencies --no-daemon

# Copiar o restante do código do projeto
COPY . /usr/src/rinha-backend

# Realizar o build da aplicação
RUN gradle build

# Etapa de execução
FROM container-registry.oracle.com/graalvm/native-image:21

# Copiar o JAR gerado na etapa de build
COPY --from=build /usr/src/rinha-backend/build/libs/*.jar /rinha-backend/app.jar

WORKDIR /rinha-backend

# Expor a porta necessária
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "/rinha-backend/app.jar"]