# language: pt
Funcionalidade: Tratamento de argumentos inválidos na API

  Como um cliente da API
  Quero receber um erro apropriado ao enviar argumentos inválidos
  Para entender que minha requisição está incorreta

  Cenário: Tentativa de adicionar um produto com argumentos inválidos
    Quando eu faço uma requisição POST para adicionar um produto a wishlist com os seguintes dados inválidos:
      | id    | name       | description |
      |       | Produto X  |             |
    Então a resposta deve ter o status code BAD_REQUEST 400
    E a resposta deve conter um VALIDATION_FAILED
