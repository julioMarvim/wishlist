# language: pt
Funcionalidade: Adicionar um produto na Wishlist

  Como um cliente da API
  Quero adicionar um produto na wishlist
  Para visualizar que a wishlist foi criada

  Cenário: Cria uma wishlist com os produtos cadastrados
    Dado que existe uma wishlist cadastrada no sistema para o clientId 7:
      | clientId | id | name    | description     |
      | 7        | 1  | Garrafa | Garrafa de cafe |
    Quando eu faço uma requisição POST para adicionar o produto a wishlist do cliente 7
    Então a resposta quando o produto for adicionado na wishlist deve ter o status code OK 201
    E a resposta deve conter os dados de produto que foram cadastrados