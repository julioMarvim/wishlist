# language: pt
Funcionalidade: Obter a Wishlist de um cliente

  Como um cliente da API
  Quero obter uma wishlist de um usuario
  Para visualizar todos os produtos cadastrados nessa wishlist

  Cenário: Retorna wishlist do usuario com sucesso
    Dado que existe uma wishlist cadastrada no sistema para o clientId 1:
      | clientId | products                                                                                                                                |
      | 1        | [{"id":"1","name":"Garrafa","description":"Garrafa de cafe"}, {"id":"2","name":"Porta Retrato","description":"Porta Retrato colorido"}] |
    Quando eu faço uma requisição GET para obter a wishlist
    Então a resposta quando o usuario tem produtos na wishlist deve ter o status code OK 200
    E a resposta deve conter os dados que foram cadastrados previamente