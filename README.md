# videostore
This is an example project from the classic video store to practice unit testing with mockito

ATALHO PARA EXECUÇÃO DO TESTE: Ctrl + F11 com o cursor em cima do nome do teste
Oriente-se pelos commits exibidos no Github para seguir a sequência de aulas

Aula 35 - PowerMock

Finalizamos Mockito mas vamos levantar algumas restrições que ele possui.
Há algumas operações que o Mockito não consegue trabalhar, por exemplo:

1) mockar o construtor de um objeto
2) alterar o comportamento de algum método estático/privados

Caso precisarmos realizar uma dessas atividades, vamos precisar de uma ferramenta mais poderosa, e essa ferramenta é o PowerMock.

PowerMock é um framework que estende as funcionalidades de outros frameworks de mock.
Ele possui integração com EasyMock e com o Mockito, então vamos adicionar essa dependência ao nosso projeto.

Na aula seguinte, começaremos a estender as funcionalidades do Mockito.