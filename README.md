# Wishlist API

Esta API tem como objetivo oferecer um serviço HTTP para expor as funcionalidades de uma Wishlist (lista de desejos) para um e-comrece. Ela permite realizar as seguintes operações:

- **Adicionar um produto**: O cliente pode adicionar um produto à sua lista de desejos.
- **Remover um produto**: O cliente pode remover um produto da sua lista de desejos.
- **Consultar todos os produtos**: O cliente pode consultar todos os produtos atualmente presentes em sua lista de desejos.
- **Verificar a presença de um produto na Wishlist**: O cliente pode verificar se um determinado produto está presente em sua lista de desejos.

## Decisões de Projeto

Durante o desenvolvimento deta API de Wishlist, algumas decisões importantes foram tomadas para garantir simplicidade, funcionalidade e maior alinhamento com os requisitos do projeto. Vou listar as principais escolhas bem como suas respectivas justificativas:

### 1. Criação Automática de Wishlist
- **Decisão**: Sempre criar uma wishlist caso ela não exista para o cliente.
- **Justificativa**: A API não realiza validação ou consulta para verificar a existência de um cliente. Pressupõe-se que, se o cliente é válido, ele já pode ter uma wishlist associada, pois todos os clientes têm direito a uma lista de desejos. Essa abordagem simplifica a lógica e evita verificações desnecessárias.

### 2. Exceções em Contexto Global
- **Decisão**: Todas as exceções foram centralizadas em um único contexto global.
- **Justificativa**: Esta é uma API é pequena, então não houve necessidade de separar as exceções por camadas. Fiz isso para reduzir a complexidade e facilitar o rastreamento de erros, garantindo que todas as exceções sejam tratados de forma centralizada.

### 3. Validações no Repositório
- **Decisão**: As validações de dados e regras de negócio foram implementadas na camada de repositório.
- **Justificativa**: Para manter a simplicidade e a responsabilidade única nos casos de uso (use cases), as validações foram movidas para o repositório. Fiz isso na tentativa de garantir que a lógica de negócios seja mantida próxima à camada de persistência de dados, para otimizar o fluxo de dados.

---

> **Nota:** Esta é apenas uma versão inicial do projeto. Melhorias e ajustes poderão ser feitos no futuro.



**Github Actions**: Criação de workflows  executar e build, testes unitários e de testes de integração do projeto.
<p>
    <a href="https://github.com/julioMarvim/wishlist/actions">
        <img alt="Build" src="https://github.com/julioMarvim/wishlist/actions/workflows/build-test.yml/badge.svg" />
    </a>
</p>

## Execução da API
## Pré requisitos: 
### Docker

- **Links com a documentação para instalação**:
  - [Instalação no Windows](https://docs.docker.com/desktop/setup/install/windows-install/)
  - [Instalação no Linux](https://docs.docker.com/desktop/setup/install/linux/)
  - [Instalação no macOS](https://docs.docker.com/desktop/setup/install/mac-install/)

- **Versões utilizadas**:
  - Docker (`versão 27.4.1`)
  - Docker Compose (`versão 1.29.2`)

Essas versões foram usadas para criar containers para o ambiente de desenvolvimento e o banco de dados, garantindo consistência entre os ambientes de desenvolvimento e produção.

### JDK
- **Java 17**: (`versão utilizada openjdk 17.0.13`): A linguagem de programação utilizada neste projeto.
  - [Download para Windows](https://adoptium.net/?variant=openjdk17)
  - [Download para Linux](https://adoptium.net/?variant=openjdk17)
  - [Download para macOS](https://adoptium.net/?variant=openjdk17)

### Spring Boot
- **Spring Boot**: (`versão 3.4.1`): O framework utilizado para construir a API.
  - [Documentação do Spring Boot](https://spring.io/projects/spring-boot)

### Apache Maven
- **Apache Maven**: (`versão 3.8.7`): Ferramenta de automação de build utilizada no projeto.
  - [Download para Windows](https://maven.apache.org/download.cgi)
  - [Download para Linux](https://maven.apache.org/download.cgi)
  - [Download para macOS](https://maven.apache.org/download.cgi)

## Detalhes Importantes:

Certifique-se de seguir os passos abaixo para garantir que o projeto funcione corretamente:

### 1. Clonando o Repositório
Para obter o código fonte do projeto, execute o seguinte comando no terminal:

```bash
git clone https://github.com/julioMarvim/wishlist.git
```

### 2. Verifique a Disponibilidade da Porta
  Certifique-se de que a porta `8080` está disponível no seu ambiente antes de iniciar os containers.

### 3. Rodando a Aplicação com Docker
   Com todos os pré-requisitos configurados navegue até a pasta raiz do projeto (`wishlist`), e execute os seguintes comandos:

Para **inicia os containers em segundo plano** execute:
```bash
docker-compose down
docker-compose up --build -d
```

Para **iniciar os containers em primeiro plano** execute:
```bash
docker-compose down
docker-compose up --build
```

Para **finalizar a execução da API**, execute:
```bash
docker-compose down
```

## Exemplos de requisições

Aqui estão alguns exemplos de requisições para interagir com a API:
Deixei uma **collection** do insomnia disponível com todos os endpoints para testes locais: [Collection Insomia](wishlist-collection.json) 

### Adicionar um produto à wishlist

```bash
curl --request POST \
  --url http://localhost:8080/api/v1/wishlist/6 \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/10.3.0' \
  --data '{
    "id": "productId",
    "name": "Product Name",
    "description": "Product Description"
}
```

### Obter a wishlist de um cliente

```bash
curl --request GET \
  --url http://localhost:8080/api/v1/wishlist/{clientId} \
  --header 'User-Agent: insomnia/10.3.0'
  ```

### Verificar se um produto está na wishlist

```bash
curl --request GET \
  --url http://localhost:8080/api/v1/wishlist/{clientId}/{productId}/exists \
  --header 'User-Agent: insomnia/10.3.0'
  ```

### Remover um produto da wishlist

```bash
curl --request DELETE \
  --url http://localhost:8080/api/v1/wishlist/{clientId}/{productId} \
  --header 'User-Agent: insomnia/10.3.0'
  ```

## Documentação da API

Para acessar a documentação completa da API, inicie o projeto e acesse o seguinte endpoint no navegador, para visualizar
a especificação Open Api da aplicação 

**Certifique-se de ter seguido todos os passos em: [Detalhes importantes](#detalhes-importantes) para que o link funcione corretamente**.

- **Link para o Swagger**: [Swagger-ui](http://localhost:8080/swagger-ui/index.html#/)

### Documentação das Rotas da API

A tabela a seguir descreve as rotas disponíveis na API, seus métodos HTTP, parâmetros esperados e códigos de status de
resposta.

| Endpoint                                         | Método | Parâmetros                              | Códigos de Status  | Descrição                                   |
|--------------------------------------------------|--------|-----------------------------------------|--------------------|---------------------------------------------|
| `/api/v1/wishlist/{clientId}`                    | GET    | `clientId` (path, string, obrigatório)  | 200, 500           | Recuperar a wishlist de um cliente.         |
| `/api/v1/wishlist/{clientId}`                    | POST   | `clientId` (path, string, obrigatório)  | 201, 400, 409, 500 | Adicionar um produto à wishlist do cliente. |
| `/api/v1/wishlist/{clientId}/{productId}`        | DELETE | `clientId` (path, string, obrigatório), | 204, 404, 500      | Remover um produto da wishlist do cliente.  |
| `/api/v1/wishlist/{clientId}/{productId}/exists` | GET    | `clientId` (path, string, obrigatório), | 200, 404, 500      | Verificar se um produto existe na wishlist. |

### Detalhes dos Códigos de Status

- **200**: Sucesso na operação.
- **201**: Recurso criado com sucesso.
- **204**: Operação concluída com sucesso, sem conteúdo retornado.
- **400**: Erros de validação nos dados enviados.
- **404**: Recurso não encontrado.
- **409**: Conflito, como um produto já existente na wishlist.
- **500**: Erro interno no servidor.


## Como Rodar os Testes da API

Este projeto está configurado para rodar testes automatizados utilizando os seguintes frameworks e ferramentas:

- **JUnit 5**: Para testes unitários e de integração.
- **Cucumber**: Para testes com Desenvolvimento Orientado por Comportamento (BDD), integrado ao JUnit para execução.
- **Flapdoodle Embedded MongoDB**: Para testes com uma instância embutida do MongoDB, simulando o banco de dados em testes de integração.
- 
### Para rodar os testes, use o seguinte comando Maven:

Primeiramente certifique-se navegar até a pasta (`wishlist`) do projeto antes de rodar a aplicação.
```bash
    mvn clean test integration-test
```
Esse comando executará todos os testes configurados no projeto, incluindo testes unitários, e testes BDD utilizando o Cucumber.

### Dependências
Aqui estão as dependências principais do projeto, relevantes para os testes e funcionalidade da API:

- **JUnit 5** (`org.junit:junit-bom:5.10.3`): O framework de testes utilizado no projeto.
- **Cucumber** (`io.cucumber:cucumber-java:7.14.0`, `io.cucumber:cucumber-spring:7.14.0`): Ferramentas de BDD integradas ao Spring.
- **Spring Boot Starter Test** (`org.springframework.boot:spring-boot-starter-test`): Um conjunto de ferramentas para testes unitários e de integração.
- **Flapdoodle Embedded MongoDB** (`de.flapdoodle.embed:de.flapdoodle.embed.mongo.spring30x:4.5.2`): Para rodar uma instância embutida do MongoDB para os testes.
- **Spring Boot Starter Web** (`org.springframework.boot:spring-boot-starter-web`): Para habilitar funcionalidades web na aplicação Spring Boot.
- **Spring Boot Starter Validation** (`org.springframework.boot:spring-boot-starter-validation`): Para validação de dados com Jakarta Validation e Hibernate Validator.

## Tecnologias e Ferramentas
### Linguagens e Frameworks
Java (versão 17): A linguagem de programação utilizada neste projeto.
Spring Boot (versão 3.4.1): O framework utilizado para construir a API.

## Banco de Dados
- **MongoDB**: Um banco de dados NoSQL utilizado neste projeto, integrado ao Spring Boot via Spring Boot Starter Data MongoDB.

## Ferramentas de Teste
- **JUnit** (`versão 5.10.3`): O framework principal para os testes no projeto.
- **Cucumber** (`versão 7.14.0`): Para testes de BDD.
- **Spring Boot Starter Test**: Um conjunto de ferramentas para facilitar os testes em aplicações Spring Boot.
- **Flapdoodle Embedded MongoDB**: Para rodar MongoDB em memória durante os testes.

