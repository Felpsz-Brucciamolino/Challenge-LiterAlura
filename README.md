# 📚 LiterAlura

Projeto desenvolvido como desafio do programa ONE - Oracle Next Education (Alura).

O objetivo é consumir a API Gutendex e persistir livros e autores em um banco de dados PostgreSQL utilizando Spring Boot e Spring Data JPA.

## 🚀 Tecnologias

Java 17+

Spring Boot

Spring Data JPA

PostgreSQL

Maven

API Gutendex

# 📌 Funcionalidades

🔎 Buscar livro por título (consome API e salva no banco)

📖 Listar livros registrados

👤 Listar autores registrados

📅 Listar autores vivos em determinado ano

🌎 Listar livros por idioma

📊 Top 10 livros mais baixados

Após salvar, todas as consultas são feitas apenas no banco de dados.

# 🗄 Banco de Dados

O projeto utiliza PostgreSQL.

É necessário criar o banco:

CREATE DATABASE literalura;
# ⚙️ Configuração

O projeto utiliza variáveis de ambiente para conexão com o banco.

No application.properties:
```
spring.datasource.url=jdbc:postgresql://${DB_HOST:localhost}/${DB_NOME:literalura}
spring.datasource.username=${DB_USER:postgres}
spring.datasource.password=${DB_PASSWORD:postgres}

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```
#🔹 Como funciona

Se as variáveis de ambiente não estiverem definidas, o projeto usará os valores padrão:

Host → localhost

Banco → literalura

Usuário → postgres

Senha → postgres

# ▶️ Executando o projeto

Clonar o repositório:

git clone <url-do-repositorio>

Executar:

mvn spring-boot:run

ou rodar pela IDE.

📡 API Utilizada

https://gutendex.com/
