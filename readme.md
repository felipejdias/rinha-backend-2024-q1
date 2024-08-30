# Commandos uteis para esse projeto

## buildar o projeto
`./gradlew build`

## Gerar imagem docker 
`docker build -t  rinha-backend-2024-q1 .`

## Subir container isolado
`docker run  --name rinha-backend-2024-q1 --network rede-backend  rinha-backend-2024-q1`

## subir o projeto
`docker-compose up -d`

## chamar a aplicação
### criar uma transação
`
    curl --location 'http://localhost:9999/transactions/create/2' \
    --header 'Content-Type: application/json' \
    --data '{
        "valor": 99998,
        "tipo" : "d",
        "descricao" : "descricao"
    }'
`

## TODO


de acordo com a doc do spring https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-with-GraalVM spring data jpa e graalvm ainda não compatíveis 