# videostore
This is an example project from the classic video store to practice unit testing with mockito

ATALHO PARA EXECUÇÃO DO TESTE: Ctrl + F11 com o cursor em cima do nome do teste
Oriente-se pelos commits exibidos no Github para seguir a sequência de aulas

Aula 44 - Testes em paralelo, parte 2

Agora que temos o Maven configurado na máquina (feito na aula anterior), utilizaremos o plugin chamado Surefire.

Você pode usa-lo para várias configurações nos seus testes, mas aqui vamos usa-lo especificamente para o paralelismo. Mais detalhes sobre esse plugin, acesse https://maven.apache.org/surefire/maven-surefire-plugin/examples/fork-options-and-parallel-execution.html

A seguir vamos adicionar no arquivo pom.xml as informações necessárias e dar andamento com o projeto.

Você sempre vai rodar os testes pelo cmd com o comando 'mvn test' para ir vendo o tempo de execução e o paralelismo. Na dúvida, reassistir a aula.

Caso você queira executar os testes via Maven mas a partir do Eclipse, basta seguir estes passos:
. selecionar seu projeto com o botão direito / Run As / Maven test
Daí ao invés de ser impresso no console, será impresso no próprio Eclipse