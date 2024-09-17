# EasyDesk API

EasyDesk é uma API desenvolvida para gerenciar operações de restaurantes, como cardápios, comandas, mesas e autenticação de usuários. A API utiliza uma arquitetura segura baseada em JWT (JSON Web Tokens) para autenticação e autorização.

## Funcionalidades

- **Autenticação e Autorização**: Registro e login de usuários utilizando JWT.
- **Gestão de Cardápio**: Criação, edição e consulta de cardápios.
- **Gestão de Comandas**: Criação e fechamento de comandas.
- **Gestão de Mesas**: Definição de disponibilidade e controle de mesas do restaurante.
- **Documentação OpenAPI**: A API é documentada utilizando Swagger para facilitar o consumo.

## Tecnologias Utilizadas

- **Java 21**: Linguagem de programação utilizada no backend.
- **Spring Boot 3.3.2**: Framework para simplificação do desenvolvimento backend.
    - **Spring Security**: Gerenciamento de autenticação e autorização.
    - **Spring Data JPA**: Integração com o banco de dados PostgreSQL.
    - **Spring Validation**: Validação dos dados nas requisições.
- **PostgreSQL**: Banco de dados relacional utilizado para armazenar os dados.
- **JWT (Java JWT - Auth0)**: Implementação de segurança com tokens JWT para autenticação.
- **Swagger**: Documentação da API com a biblioteca SpringDoc OpenAPI.
- **Lombok**: Redução de código boilerplate com geração automática de getters, setters e construtores.

## Dependências

As principais dependências da API, conforme definidas no arquivo `pom.xml`, incluem:

- **Spring Boot Starters**: Web, Data JPA, Security.
- **PostgreSQL Driver**: Para conectar ao banco de dados.
- **Java JWT (Auth0)**: Para geração e validação de tokens JWT.
- **Lombok**: Para simplificação de código.
- **SpringDoc OpenAPI**: Para geração da documentação Swagger.

## Estrutura de Testes

Para garantir a qualidade do código, foram implementados testes unitários e de integração com as seguintes tecnologias:

- **JUnit 5**: Utilizado para a criação e execução dos testes unitários.
- **Mockito**: Mock de dependências e simulação de comportamentos em serviços.
- **Spring Boot Test**: Para integração com o contexto da aplicação e execução de testes.
- **Spring Security Test**: Utilizado para testar cenários de autenticação e autorização.

## Como Rodar o Projeto

### Pré-requisitos

- **Java 21**: Certifique-se de que a versão 21 do Java está instalada.
- **PostgreSQL**: O banco de dados deve estar configurado e rodando na porta padrão ou na que você definir.

### Configurações

No arquivo `application.yml`, configure as seguintes propriedades para o banco de dados:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/easydesk_db
    username: postgres
    password: postgres
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

Adicione a chave secreta para JWT no arquivo `application.yml`:

```yaml
jwt:
  secret: sua-chave-secreta-aqui
```

### Passos para Execução

1. **Clonar o Repositório**: Clone o projeto do repositório Git.
2. **Configurar o Banco de Dados**: Certifique-se de que o PostgreSQL está rodando e as credenciais estão corretas.
3. **Rodar o Projeto**: Utilize o Maven para construir e rodar o projeto.

   Execute o comando abaixo para rodar o projeto:

   ```bash
   ./mvnw spring-boot:run
   ```

4. **Acessar a Documentação da API**: Uma vez que o projeto esteja rodando, acesse a documentação Swagger na seguinte URL:

   ```
   http://localhost:8080/swagger-ui.html
   ```

### Executando os Testes

Para executar os testes, utilize o Maven:

```bash
./mvnw test
```

Os testes cobrem os principais serviços e controladores da aplicação, incluindo a lógica de autenticação, manipulação de cardápios e mesas, além de validações de acesso.


Este é o repositório da API EasyDesk, desenvolvido para na disciplina de Engenharia de Software