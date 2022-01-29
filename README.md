# videostore
This is an example project from the classic video store to practice unit testing with mockito

ATALHO PARA EXECUÇÃO DO TESTE: Ctrl + F11 com o cursor em cima do nome do teste
Oriente-se pelos commits exibidos no Github para seguir a sequência de aulas

Aula 41 - Refatorações para substituir o PowerMock

Nesta aula será mostrado como é possível realizar os testes sem o uso do PowerMock. Apenas o Mockito e a estruturação do código serão o suficiente para resolver o nosso problema.

Inicialmente, será duplicada a classe LocalServiceTest e deixar nela apenas os métodos do PowerMock, e na classe oficial iremos tirar todos os testes que utilizam o PowerMock.

Portanto, no fim da aula, o professor recomenda que utilizemos o PowerMock "apenas em último caso" como por exemplo naquele projeto legado de anos atrás que ninguém tem nem coragem de alterar uma única vírgula sequer sem necessidade real porque os testes são escassos ou inexistentes. Nesses casos, o PowerMock seria a melhor forma para criar os testes porque a partir dele você poderá criar testes da forma menos intrusiva possível sem refatorações.