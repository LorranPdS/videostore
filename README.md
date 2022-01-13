# videostore
This is an example project from the classic video store to practice unit testing with mockito

ATALHO PARA EXECUÇÃO DO TESTE: Ctrl + F11 com o cursor em cima do nome do teste
Oriente-se pelos commits exibidos no Github para seguir a sequência de aulas

Aula 32 - Lançando exceções

E quando o esperado é que o Mock lance uma exceção? Também podemos fazer isso, e é o que faremos

Vamos supor que o serviço de consulta ao SPC é instável e dá erro às vezes... então eu gostaria de verificar se a minha aplicação estará lançando uma mensagem amigável nesses casos, e para isso criaremos um teste

Obs.: existem 2 tipos de exceções, que são as checadas (checked) e as não checadas (unchecked), em que:

. checked - são das que dependem da Exception, com exceção do ramo que vem da RuntimeException
. unchecked - são da família da RuntimeException. Ela dá esse problema por fugir do tempo de execução