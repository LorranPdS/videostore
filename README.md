# videostore
This is an example project from the classic video store to practice unit testing with mockito

ATALHO PARA EXECUÇÃO DO TESTE: Ctrl + F11 com o cursor em cima do nome do teste
Oriente-se pelos commits exibidos no Github para seguir a sequência de aulas

Aula 25 - Dependências externas

Nessa aula em diante, vamos adicionar uma chamada na classe de serviço que tínhamos omitido desde o início para facilitar a nossa vida. Será uma alteração pequena mas vai mudar bastante da forma como trabalhamos com os testes a partir de agora.

Há uma regra importante sobre o que é teste unitário: um testes unitário não deve ter dependência externa, como acesso a banco de dados, rede, arquivos ou qualquer outra entidade externa ao código que desejamos testar.