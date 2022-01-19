# videostore
This is an example project from the classic video store to practice unit testing with mockito

ATALHO PARA EXECUÇÃO DO TESTE: Ctrl + F11 com o cursor em cima do nome do teste
Oriente-se pelos commits exibidos no Github para seguir a sequência de aulas

Aula 36 - Mockando construtores

Lá no LocacaoService, sempre que eu preciso de uma nova data, eu instancio o 'new Date', mas que tal se nós mockarmos o Date? Ou seja, se eu puder alterar o comportamento desse construtor do Date, com o PowerMock nós podemos fazer isso.

Então vamos fazer os nossos métodos de teste.