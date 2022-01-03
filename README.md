# videostore
This is an example project from the classic video store to practice unit testing with mockito

ATALHO PARA EXECUÇÃO DO TESTE: Ctrl + F11 com o cursor em cima do nome do teste
Oriente-se pelos commits exibidos no Github para seguir a sequência de aulas

Aula 27 - Mockito

O uso de objetos falsos resolve nosso problema isolando os testes do mundo externo mas dá muito trabalho se você tiver muitas dependências (o que é o comum).

Existem frameworks que nos ajudam a criar dinamicamente esses objetos falsos, e o framework que será apresentado nesse curso para essa criação será o Mockito.

Ele possui esse nome porque ele não gera objetos fake mas sim objetos mock, e nos mocks somos capazes de definir comportamentos dinâmicos e inclusive verificar se ele foi utilizado conforme o esperado.

Vamos refatorar o código para mockar a interface que estava nos dando problemas ao invés de utilizar aquele objeto fake