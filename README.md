# videostore
This is an example project from the classic video store to practice unit testing with mockito

ATALHO PARA EXECUÇÃO DO TESTE: Ctrl + F11 com o cursor em cima do nome do teste

Aula 10 - Tratamento de exceções: parte 2

Nesta aula adicionaremos ainda mais duas validações: usuário e filme são obrigatórios

Vamos novamente usar para cada uma os tratamentos de exceção da forma elegante, robusta e nova.

Forma Elegante: funciona bem quando apenas a exceção importa para você, ou seja, são os casos em que você consegue garantir o motivo pelo qual a exceção foi lançada. Caso precise de alguma mensagem, você vai precisar a forma Robusta ou Nova

Forma Nova: atende na maioria dos casos porém existirão pontos em que somente a forma Robusta vai ajudar

Forma Robusta: caso não queira ficar decorando as três formas e prefere usar apenas uma, a mais recomendada é esta forma por ela ser a mais completa