# language: pt
Funcionalidade: Verificar que um produto não está na wishlist de um cliente

  Como um cliente da API
  Quero verificar se um produto não está na wishlist de um usuario
  Para ter uma resposta de erro dizendo que o produto não esta na wishlist do usuario

  Cenário: Retorna uma resposta de erro ao verificar se um produto existe na wishlist
    Dado que existe uma wishlist cadastrada no sistema para o clientId 4:
      | clientId | products                                                      |
      | 4        | [{"id":"1","name":"Garrafa","description":"Garrafa de cafe"}] |
    Quando eu faço uma requisição GET para verificar se o produto de id "2" está na wishlist do clientId 4
    Então a resposta quando o produto não está na wishlist deve ter o status code NOT_FOUND 404
    E a resposta deve conter um PRODUCT_NOT_FOUND_ERROR