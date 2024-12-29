# language: pt
Funcionalidade: Obter a Wishlist vazia de um cliente

  Como um cliente da API
  Quero obter uma wishlist de um usuario que não cadastrou nenhum produto
  Para visualizar uma wishlist vazia

  Cenário: Retorna uma wishlist vazia
    Dado que não existe uma wishlist cadastrada no sistema para o cliente com clientId "3"
    Quando eu faço uma requisição GET para obter a wishlist do cliente com clientId "3"
    Então a resposta quando o usuario não tem produtos na wishlist deve ter o status code OK 200
    E a resposta deve conter uma wishlist com a lista de produtos vazia
