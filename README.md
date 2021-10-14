# videostore
This is an example project from the classic video store to practice unit testing with mockito

ATALHO PARA EXECUÇÃO DO TESTE: Ctrl + F11 com o cursor em cima do nome do teste

Aula 14 - TDD, parte 1
Nossa nova sequência de mudança será o teste, depois código (com o mínimo necessário de mudança) e depois refatoração

Vamos criar uma calculadora usando o TDD

Muito interessante treinar como desenvolver TDD usando o projeto da criação de uma calculadora por ser bem simples de se fazer e qualquer pessoa conhece a lógica da calculadora

Portanto, se surgir uma nova funcionalidade para a Calculadora, você vai seguir o seguinte roteiro:
1° criamos o método de teste especificando como deve ser o comportamento da calculadora para essa funcionalidade
2° desenvolver no serviço de calculadora o mínimo necessário para que aquele cenário seja atendido

Importante que seu teste sempre falhe no início e depois de pronto, erre de propósito para ver se você não está criando falsos positivos.

RED -> GREEN -> REFACTOR
Baby Steps