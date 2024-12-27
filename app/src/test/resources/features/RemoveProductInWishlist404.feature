# language: pt
Funcionalidade: Remover um produto que não esxiste na wishlist de um cliente

  Como um cliente da API
  Quero Remover um produto que não esxiste na wishlist de um usuario
  Para ter uma resposta de um erro dizendo que o produto não existe na wishlist

  Cenário: Retorna uma resposta de erro ao tentar remover um produto inexistente da wishlist
    Dado que existe uma wishlist cadastrada no sistema para o clientId 6:
      | clientId | products                                                                                                                                |
      | 6        | [{"id":"1","name":"Garrafa","description":"Garrafa de cafe"}] |
    Quando eu faço uma requisição DELETE para remover o produto de id "2" da wishlist do clientId 6
    Então a resposta quando o produto não existir na wishlist deve ter o status code NOT_FOUND 404
    E a ao remover o produto a resposta deve conter o erro PRODUCT_NOT_FOUND_ERROR