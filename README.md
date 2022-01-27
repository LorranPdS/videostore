# videostore
This is an example project from the classic video store to practice unit testing with mockito

ATALHO PARA EXECUÇÃO DO TESTE: Ctrl + F11 com o cursor em cima do nome do teste
Oriente-se pelos commits exibidos no Github para seguir a sequência de aulas

Aula 40 - The dark side of PowerMock

O PowerMock é realmente uma ferramenta poderosa e nos permite ir além das capacidades de uma ferramenta de Mock comum. Segundo o professor, mesmo tendo todo esse poder, ele não é algo tão necessário porque para ele, o PowerMock acha que a forma como resolvemos alguns dos nossos problemas é muito brusca.

Essa flexibilidade toda que o PowerMock nos dá acaba trazendo alguns problemas.

1) aquela anotação PrepareForTest é tão pesada que, se fizermos uma análise de cobertura de todo o pacote, você vai ver que a cobertura vai diminuir demais (no exemplo do professor, a classe da LocacaoService deixou de ser 100% pra ser 62% porque o Eclema começa a ter problemas com essa classe, assim ele não contabiliza a cobertura completa

2) com relação a Mockar métodos privados, existe muita discussão sobre esse aspecto. Métodos privados são considerados detalhes de implementação que retiramos de algum método público, seja para reduzir a complexidade, seja para que o mesmo possa ser reutilizado por outros métodos, dessa forma, métodos privados podem ser perfeitamente acessados através dos métodos públicos que o utilizam; se não tiver nenhum método público utilizando, verifique se realmente esse método é necessário para a sua classe, e provavelmente você não vai precisar testa-lo e sim deleta-lo [algumas fontes, inclusive, recomendam alterar a visibilidade dos métodos privados para PROTECTED, aí sim poderemos acessa-los nos testes sem o uso de PowerMock]

3) métodos privados muito complexos são o reflexo da falta de foco na qualidade e legibilidade do código. A utilização do TDD desde o início do projeto já inibe muitos problemas, afinal de contas você não iria se deparar repentinamente com um cenário complexo porque esse comportamento já seria dividido em pequenas porções logo no início (por isso foi dito no início que os projetos mais difíceis de serem testados são os sistemas legados; essas qualidades são esquecidas até que alguém pede para testar o código, mas com o código já pronto, o timely já foi pro espaço e problemas com métodos muito complexos começam a surgir)

4) a maioria dos problemas que o PowerMock resolveu poderia ser resolvido se o código tivesse uma qualidade melhor, então na próxima aula, iremos refatorar o nosso projeto para deixar de usar o PowerMock