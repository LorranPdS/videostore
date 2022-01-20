# videostore
This is an example project from the classic video store to practice unit testing with mockito

ATALHO PARA EXECUÇÃO DO TESTE: Ctrl + F11 com o cursor em cima do nome do teste
Oriente-se pelos commits exibidos no Github para seguir a sequência de aulas

Aula 37 - Mockando métodos estáticos

Tínhamos conseguido resolver o problema da data, pois a LocacaoService estava obtendo a dataAtual através do 'new Date()', mas e se ele estivesse utilizando o Calendar ao invés do 'new Date()' ?

Se apenas alterarmos o método para obtermos a data atual através de Calendar, você verá que os testes irão deixar de funcionar porque o Mockito estava trocando o construtor do 'new Date', mas agora estamos usando o Calendar e a forma de criação do Calendar não utiliza o construtor pois a instância vem através de um método estático.

Para nossa sorte, o PowerMock permite o trabalho com métodos estáticos.