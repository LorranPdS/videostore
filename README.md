# videostore
This is an example project from the classic video store to practice unit testing with mockito

ATALHO PARA EXECUÇÃO DO TESTE: Ctrl + F11 com o cursor em cima do nome do teste

Aula 9 - Tratamento de exceções: parte 1

Nesta aula vamos adicionar uma nova regra, que é a seguinte:
	- um usuário não pode alugar filmes sem estoque

Para isso, iremos colocar uma validação no nosso método de locação, e se não tiver o filme no estoque, uma exceção será lançada.