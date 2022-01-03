# videostore
This is an example project from the classic video store to practice unit testing with mockito

ATALHO PARA EXECUÇÃO DO TESTE: Ctrl + F11 com o cursor em cima do nome do teste
Oriente-se pelos commits exibidos no Github para seguir a sequência de aulas

Aula 24 - Análise de Cobertura

Aqui iremos falar sobre métricas que podemos extrair a partir dos testes unitários.

As duas métricas mais comuns são: percentual de aceitação dos testes e o percentual de cobertura de código

- percentual de aceitação do código: é a quantidade de testes executados com sucesso dividido pela quantidade de testes executados no geral
	Se você seguir a ideia de sempre deixar a barra verde, não precisa se preocupar em calcular se está 100% porque a barra verde executando para todos os testes significa que ela está 100%
	
- percentual de código: para ela precisamos de uma ferramenta extra. No Eclipse, podemos instalar um plugin para realizar essa análise chamado EclEmma
	Teremos 3 cores, que serão o verde, amarelo e vermelho.
	. verde - significa que a linha foi executada completamente
	. amarela - linha foi executada parcialmente. Só acontece com linhas que contém lógica, ou seja, linhas que podem tomar caminhos distintos. Essas linhas também são conhecidas como branches e que acabamos de executar um dos branches (caso ela esteja amarela)
	. vermelho - linha não foi executada
	1) O que importa de coverage é o índice dos pacotes de serviços do que vai para a produção e que vai estar bem alto porque estamos evoluindo nosso código usando TDD e só não está com 100% porque temos aquele código que só executa aos sábados
	2) Nos 3 pontos do coverage, você consegue também verificar esse cálculo pelas linhas
	3) O ideal é sempre chegarmos aos 100% mas até 85% é algo muito alto e questionável.
	4) Uma ferramenta de análise de código também é a chamada Sonar
	5) Busque principalmente possíveis erros que podem aparecer, não se preocupe mais com cobertura do que com possíveis erros
	6) Confira por cobertura de branches e por cobertura de linhas
	7) PORÉM, note o seguinte: se esse método for chamado passando zero como denominador nós teremos um erro, se passar letras ao invés de números nós teremos outro erro, OU SEJA, 100% de cobertura não indica que um código está 100% testado, muito menos que o código está 100% livre de erros
	MUITO IMPORTANTE: cobertura de testes não deve ser utilizada para medir a qualidade do código. A finalidade da execução de uma análise de cobertura não é para garantir que o código está bem testado mas para ver se existe algum ponto que não está sendo coberto por algum teste, ou seja: para mim, a parte mais importante é ver se existem linhas vermelhas