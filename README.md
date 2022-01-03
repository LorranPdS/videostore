# videostore
This is an example project from the classic video store to practice unit testing with mockito

ATALHO PARA EXECUÇÃO DO TESTE: Ctrl + F11 com o cursor em cima do nome do teste
Oriente-se pelos commits exibidos no Github para seguir a sequência de aulas

Aula 26 - Objetos falsos

Com a adição da interface do DAO, nossos testes deixaram de funcionar porque o nosso DAO precisa ser instanciado. O problema é que não temos ainda uma implementação desse DAO e mesmo que tivéssemos, não poderíamos utiliza-la por estarmos no escopo de testes unitários, ou seja, os nossos testes precisam estar isolados do mundo externo.

A estratégia que usamos aqui é chamada Fake Object, mas o problema dela é que precisamos implementar uma classe sempre que precisamos isolar os testes de uma entidade externa.

Na próxima aula iremos ver uma ferramenta usada para substituir classes externas de uma forma mais dinâmica.