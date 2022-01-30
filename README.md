# videostore
This is an example project from the classic video store to practice unit testing with mockito

ATALHO PARA EXECUÇÃO DO TESTE: Ctrl + F11 com o cursor em cima do nome do teste
Oriente-se pelos commits exibidos no Github para seguir a sequência de aulas

Aula 42 - Testes em paralelo, parte 1

Agora que temos uma Suíte pronta, vamos tentar otimizar o seu tempo de execução.
Durante o curso, o professor falou bastante sobre o FIRST, onde o F remete justamente à rapidez na execução.
Como já otimizamos o projeto a nível de código, agora vamos otimiza-lo a nível de execução e o professor explica na aula como a nossa Suíte está sendo executada e como pretendemos fazer para que o tempo de execução diminua mais ainda.

Antes das nossas modificações desta aula, os testes estavam sendo executados um após o outro de forma serializada até que o último teste fosse executado e o tempo total de execução é mostrado.

O professor mostra uma segunda possibilidade em que um computador é potente e consegue executar dois testes ao mesmo tempo, e assim o tempo total de execução já será menor que o anterior pela execução de testes em paralelo, e quanto mais testes eu conseguir executar em paralelo, melhor ainda.

Um terceiro cenário seria a situação em que os testes possuem durações distintas (que é o que vai acontecer em uma situação real) e de início, cada linha de execução pegará um dos testes da lista e, quando algum termina, já vai procurar o próximo a ser executado (ao invés de ficar esperando o outro terminar), ou seja, os testes não são divididos igualmente, e dessa forma conseguiremos otimizar melhor o tempo de execução da nossa bateria de testes.

Vamos então fazer uma execução paralela dos nossos testes. Infelizmente o JUnit não tem suporte nativa ao paralelismo, então iremos criar um novo Runner que irá nos dar esse poder.