# language: pt
Funcionalidade: Verificar que um produto está na wishlist de um cliente

  Como um cliente da API
  Quero verificar se um produto está na wishlist de um usuario
  Para ter uma resposta positiva de que o produto esta na wishlist do usuario

  Cenário: Retorna uma resposta positiva ao verificar se um produto existe na wishlist
    Dado que existe uma wishlist cadastrada no sistema para o clientId 2:
      | clientId | products                                                      |
      | 2        | [{"id":"1","name":"Garrafa","description":"Garrafa de cafe"}] |
    Quando eu faço uma requisição GET para verificar se o produto de id "1" está na wishlist
    Então a resposta quando o produto está na wishlist deve ter o status code OK 200
    E a resposta deve estar vazia