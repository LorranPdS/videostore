# videostore
This is an example project from the classic video store to practice unit testing with mockito

ATALHO PARA EXECUÇÃO DO TESTE: Ctrl + F11 com o cursor em cima do nome do teste

Aula 11 - Before e After

@Before: anotação usada para que o método com essa anotação seja executado antes de cada teste. Se tivermos 10 testes, o método que estiver a anotação @Before será executado 10 vezes também

@After: anotação usada para que o método com essa anotação seja executado após cada teste. Se tivermos 10 testes, o método que estiver a anotação @After será executado 10 vezes também

@BeforeClass: anotação usada para que o método com essa anotação executado antes da classe ser instanciada. Se tivermos 10 testes, o método que estiver a anotação @BeforeClass será executado 1 vez antes de começarmos os testes

@AfterClass: anotação usada para que o método com essa anotação executado após a classe ser finalizada. Se tivermos 10 testes, o método que estiver a anotação @AfterClass será executado 1 vez após finalizarmos todos os testes

Execute o teste e veja no console como ficou a impressão em tela para que você entenda melhor quando cada uma dessas anotações funcionaram

Após isso, foi feito um exercício em que é criado um contador

Observação: ao colocar o static na variável "contador", ela deixou de ficar na instância do teste e, por ser static, ela passou para o escopo da classe, e esse tipo de variável o JUnit não vai reinicializar
