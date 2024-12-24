# Wishlist Service

Este é um serviço de gerenciamento de lista de desejos (wishlist) para clientes. A aplicação permite adicionar, remover, verificar a presença de produtos na lista de desejos de um cliente e recuperar a lista de produtos.

## Tecnologias Utilizadas

- **Java 17**: Para desenvolvimento da aplicação.
- **Spring Boot**: Framework para criar a aplicação de backend.
- **Jakarta Persistence (JPA)**: Para persistência de dados.
- **Lombok**: Para reduzir o código boilerplate.
- **SLF4J**: Framework de logging.
- **Docker**: Para containerização da aplicação.
- **JUnit / Mockito**: Para testes unitários e mocks.
- **Mock MVC**: Para testes da controller da APIs REST.

## Executando a Aplicação

### Pré-requisitos

Docker e o Docker Compose instalados na sua máquina.

### Subindo a Aplicação com Docker Compose

1. Clone o repositório da aplicação para sua máquina.
2. Navegue até o diretório do projeto.
3. Execute o comando a seguir para construir e iniciar os containers:

```bash
docker-compose up -d --build
