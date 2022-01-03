# videostore
This is an example project from the classic video store to practice unit testing with mockito

ATALHO PARA EXECUÇÃO DO TESTE: Ctrl + F11 com o cursor em cima do nome do teste
Oriente-se pelos commits exibidos no Github para seguir a sequência de aulas

Aula 28 - Gravando expectativas

O que acontece quando o comportamento que esperamos em algum relacionamento externo difere do comportamento padrão de um mock?
Como foi dito na aula anterior, o mockito cria um objeto que responde como se fosse um objeto real PORÉM ele não sabe o que fazer, e nesse caso cabe a nós ensinar o objeto mockado como ele deve reagir a cada pergunta feita a ele para que, durante a execução do teste, ele se comporte exatamente como a classe real se comportaria. Vamos fazer isso implementando a seguinte funcionalidade:

A Locadora não deve permitir aluguel de filmes para caloteiros, ou seja, se o usuário estiver negativado no SPC, nem filme ele vai poder levar para casa.