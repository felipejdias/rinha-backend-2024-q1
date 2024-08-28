Commandos uteis para esse projeto

./gradlew nativeCompile

docker build -t  rinha-backend-2024-q1 .

docker run  --name rinha-backend-2024-q1 --network rede-backend --entrypoint /rinha-backend/build/native/nativeCompile/rinha-backend-2024-q1 rinha-backend-2024-q1

de acordo com a doc do spring https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-with-GraalVM spring data jpa e graalvm ainda não compatíveis 