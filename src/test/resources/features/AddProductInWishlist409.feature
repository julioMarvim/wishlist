# language: pt
Funcionalidade: Verificar o limite máximo de produtos na Wishlist

  Como um cliente da API
  Quero tentar adicionar mais de 20 produtos em uma wishlist
  Para receber uma resposta de erro indicando que o limite máximo foi excedido

  Cenário: Tentativa de adicionar um produto em uma wishlist com 20 produtos
    Dado que existe uma wishlist cadastrada no sistema para o clientId 9 com 20 produtos:
      | clientId | id | name        | description       |
      | 9        | 1  | Produto 1   | Descricao 1       |
      | 9        | 2  | Produto 2   | Descricao 2       |
      | 9        | 3  | Produto 3   | Descricao 3       |
      | 9        | 4  | Produto 4   | Descricao 4       |
      | 9        | 5  | Produto 5   | Descricao 5       |
      | 9        | 6  | Produto 6   | Descricao 6       |
      | 9        | 7  | Produto 7   | Descricao 7       |
      | 9        | 8  | Produto 8   | Descricao 8       |
      | 9        | 9  | Produto 9   | Descricao 9       |
      | 9        | 10 | Produto 10  | Descricao 10      |
      | 9        | 11 | Produto 11  | Descricao 11      |
      | 9        | 12 | Produto 12  | Descricao 12      |
      | 9        | 13 | Produto 13  | Descricao 13      |
      | 9        | 14 | Produto 14  | Descricao 14      |
      | 9        | 15 | Produto 15  | Descricao 15      |
      | 9        | 16 | Produto 16  | Descricao 16      |
      | 9        | 17 | Produto 17  | Descricao 17      |
      | 9        | 18 | Produto 18  | Descricao 18      |
      | 9        | 19 | Produto 19  | Descricao 19      |
      | 9        | 20 | Produto 20  | Descricao 20      |
    Quando eu faço uma requisição POST para adicionar o produto a wishlist do cliente 9
    Então a resposta deve ter o status code CONFLICT 409
    E a resposta deve conter um WISHLIST_LIMIT_EXCEEDED
