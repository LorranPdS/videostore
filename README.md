# videostore
This is an example project from the classic video store to practice unit testing with mockito

ATALHO PARA EXECUÇÃO DO TESTE: Ctrl + F11 com o cursor em cima do nome do teste
Oriente-se pelos commits exibidos no Github para seguir a sequência de aulas

Aula 43 - Execução dos testes via Maven

A segunda parte da execução em paralelo utiliza um plugin do Maven, e é por isso que seremos apresentados primeiro como executar os testes via Maven. É uma execução bem simples uma vez que nosso ambiente esteja configurado, e nesta aula aprenderemos a configurar esse ambiente.

O Maven está funcionando dentro do Eclipse porque ele possui controle sobre o ambiente. Uma vez que eu coloque dentro do arquivo pom.xml as bibliotecas que eu preciso, automaticamente o Maven vai baixa-las para mim e elas ficarão disponíveis para mim no Maven Dependencies, PORÉM, e se eu quiser executar por fora via linha de comando? É o que faremos.

1) entre no diretório que você está pelo cmd e dê o comando 'mvn'
. o terminal não vai reconhecer o comando porque o maven que temos é só o que veio com o Eclipse, portanto eu tenho que ter um Maven na minha máquina, então vamos fazer o download do Maven, descompactar o arquivo zip e a seguir executar os seguintes comandos, no link https://maven.apache.org/download.cgi?Preferred=https%3A%2F%2Fdlcdn.apache.org%2F com extensão -in.zip

2) abrir o cmd na pasta bin do Maven baixado. Eu já tinha ele no diretório C: então já abri por lá

3) dê o comando mvn novamente
. dará um problema no build porque esse não é o projeto mas sim a própria pasta do Maven, então para eu conseguir executar esse comando na pasta do meu projeto, eu preciso adicionar esses comandos no path do meu Sistema Operacional para que o comando 'mvn' seja um comando reconhecido por ele, e para isso cada SO possui uma particularidade e o professor passou na aula como fazer em cada SO

. basicamente vamos adicionar ao path da minha aplicação o endereço de onde o maven foi descompactado, que seria na pasta bin do Maven

. o que iremos fazer é ir nas Variáveis de Ambiente do windows e adicionar o caminho da pasta bin, que será esse caminho aqui. Aqui temos esse tutorial: https://dicasdejava.com.br/como-instalar-o-maven-no-windows/

4) depois de seguir o tutorial, abra novamente o cmd no projeto (conforme o item 1 descrito, no caminho C:\workspace\videostore e execute novamente o 'mvn' (é o mesmo caminho do README.md do meu projeto).

5) vai dar problema de build porque não fomos específicos com o que queríamos, então agora vamos executar o teste com maven executando o comando 'mvn test', e com esse comando eu estou pedindo que o maven execute os testes do meu projeto. Na primeira vez vai demorar um pouco porque serão baixadas algumas dependências, mas se deu BUILD SUCESS no final e antes foram executados os testes, foi porque deu tudo certo.

Configurado o Maven, agora sim poderemos passar para a próxima aula.