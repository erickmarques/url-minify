# Minify Url

[![Java](https://img.shields.io/badge/Java-17-brightgreen.svg)](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Postgres](https://img.shields.io/badge/Postgres-14-blue.svg)](https://www.postgresql.org/)

Minify Url é um projeto desenvolvido para encurtar e redirecionar URLs.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.3.2**
- **JUnit**
- **Mockito**
- **PostgreSQL**
- **Docker**
- **Gradle**
- **Swagger**

## Funcionalidades

1. Encurtamento de URL (POST /url-minify)
Essa funcionalidade permite que o usuário envie uma URL completa e receba uma versão encurtada dela. O endpoint recebe uma URL via requisição POST no formato JSON e retorna uma URL encurtada que pode ser usada para redirecionar para o destino original.

2. Redirecionamento de URL (GET /{id})
Essa funcionalidade permite que o usuário seja redirecionado para a URL original usando a versão encurtada gerada. Quando o usuário acessa a URL encurtada, ele é automaticamente redirecionado para a URL completa cadastrada anteriormente.


🔍 Baixe o projeto e teste você mesmo na prática.

## Contribuição

Se você deseja contribuir com este projeto, sinta-se à vontade para fazer um fork do repositório e enviar pull requests. Todas as contribuições são bem-vindas!
