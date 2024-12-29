# language: pt
Funcionalidade: Remover um produto da wishlist de um cliente

  Como um cliente da API
  Quero Remover um produto da wishlist de um usuario
  Para ter uma resposta positiva de que o produto foi removido

  Cenário: Retorna uma resposta positiva ao tentar remover um produto da wishlist
    Dado que existe uma wishlist cadastrada no sistema para o clientId 5:
      | clientId | products                                                                                                                                |
      | 5        | [{"id":"1","name":"Garrafa","description":"Garrafa de cafe"}, {"id":"2","name":"Porta Retrato","description":"Porta Retrato colorido"}] |
    Quando eu faço uma requisição DELETE para remover o produto de id "1" da wishlist do clientId 5
    Então a resposta quando o produto for removido da  wishlist deve ter o status code OK 204
    E a ao remover o produto a resposta deve estar vazia