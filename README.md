# videostore
This is an example project from the classic video store to practice unit testing with mockito

ATALHO PARA EXECUÇÃO DO TESTE: Ctrl + F11 com o cursor em cima do nome do teste
Oriente-se pelos commits exibidos no Github para seguir a sequência de aulas

Aula 21 - Suíte de testes

Agora que nossos testes estão em mais de uma classe, seria interessante ter uma forma de executar todos eles em uma bateria apenas. A Suíte de Testes é a última característica dos frameworks XUnit que não tinha sido mencionado, e ela serve exatamente para isso.

Para criarmos a Suíte de Testes, vamos criar inicialmente um pacote de suites e vou desenvolver esse trabalho nesse pacote.

. A utilização do BeforeClass e AfterClass na Suíte de testes é muito útil na bateria de testes que precisam de alguma configuração inicial. Por exemplo, em testes funcionais, eu poderia preparar um banco para a execução dos testes nesse ponto.

. Para executar todos os métodos de testes que criamos, basta clicar com o botão direito no source folder de teste (src/test/java) e mandar executar todos os testes dele, da mesma forma como fazemos em uma classe comum, e assim ele vai executar todos os testes daquele pacote.

. O professor costuma evitar o uso de suítes nesse caso porque sempre que criamos uma nova classe, temos que lembrar de adicionar na declaração do SuiteClasses (e estamos todos sujeitos a esquecer)

. Outro problema em relação a Suítes, é que em uma ferramenta de integração contínua, geralmente serão executados todos os testes que ele encontrar, então após serem feitos os testes, os testes que estiverem declarados dentro da Suite de testes será feito novamente.

. Qualquer coisa, se você não quiser que as coisas sejam interpretadas como uma Suíte, basta comentar o @RunWith e rodar normalmente que apenas os testes serão executados.