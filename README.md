# Wishlist API

A Wishlist API permite aos usuários criar e gerenciar listas de desejos, com funcionalidades para adicionar, remover e
visualizar itens nas listas.

## Instalação

Para executar o projeto, execute o seguinte comando na pasta raiz do projeto:

```bash
docker-compose up --build -d
```

Para finalizar a execução, utilize o comando:

```bash
docker-compose down
```

## Exemplos de requisições

Aqui estão alguns exemplos de requisições para interagir com a API:

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
a especificação Open Api da aplicação:

<http://localhost:8080/swagger-ui/index.html>

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

