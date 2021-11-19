# videostore
This is an example project from the classic video store to practice unit testing with mockito

ATALHO PARA EXECUÇÃO DO TESTE: Ctrl + F11 com o cursor em cima do nome do teste
Oriente-se pelos commits exibidos no Github para seguir a sequência de aulas

Aula 16 - TDD, parte 3

Já aconteceu de você corrigir um problema e depois aparecerem mais 3 novos erros que não existiam antes? Ou você ter resolvido algum problema e depois de 3 ou 4 semanas depois ele volta a surgir?
A impressão é que você está andando em círculos por ter que voltar nos testes pra limpar alguma sujeira.

Nesta aula veremos como evitar esses problemas através da seguinte situação:
. não deveria gerar um aluguel devolvendo filmes no domingo (se eu alugar no sábado ele vai mandar para o domingo mas no domingo a locadora é fechada).

POR FIM, você vai ver que foi um teste criado que só funciona se for Sábado, e atualmente não há um tipo de solução pra evitar que, ao ser sábado não estoure outro dia.

Na próxima aula veremos como contornar isso