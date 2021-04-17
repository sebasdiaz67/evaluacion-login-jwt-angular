EvaluacionLoginJwt Backend
This project was generated with Spring Boot version 2.4.5.
Para levanta el sistema se debe ejecutar la siguiente linea de comando, esto generará una imagen con el nombre spring-jwt
docker build -t spring-jwt .
Para levantar la imagen generada ejecute el siguiente comando
docker run  -e "POSTGRES_DATABASE_CONN=jdbc:postgresql://host.docker.internal:5432/db_evaluacion_login" -p 8080:8080 spring-jwt
Variable de entorno de url postgres
POSTGRES_DATABASE_CONN