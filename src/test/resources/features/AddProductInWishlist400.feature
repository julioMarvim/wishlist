# language: pt
Funcionalidade: Verificar que um produto ja existe na Wishlist

  Como um cliente da API
  Quero tentar adicionar um produto ja existente na wishlist na wishlist
  Para ter uma resposta de erro dizendo que o produto ja está na wishlist do usuario

  Cenário: Tentativa de cadastro de um produto ja existente na wishlist
    Dado que existe uma wishlist cadastrada no sistema para o clientId 8:
      | clientId | id | name     | description     |
      | 8        | 1  | Garrafa  | Garrafa de cafe |
      | 8        | 2  | Controle | Controle remoto |
    Quando eu faço uma requisição POST para adicionar o produto a wishlist do cliente 8
    Então a resposta quando o produto for adicionado na wishlist deve ter o status code BAD_REQUEST 400
    E a resposta deve conter um PRODUCT_ALREADY_IN_WISHLIST