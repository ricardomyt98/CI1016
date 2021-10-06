# CI1016

Meu gerador de código foi implementado na linguagem Python, versão 3.

A pasta raiz possui três arquivos, este próprio (README), “codeGenerator.py” (código Python gerador de código Java) e “inp.json” (exemplo de entrada).
Escolhi Python por seu uma linguagem híbrida (estrutural e orientada a objetos) e de alto nível (fácil implementação).

Conceito da implementação: 
1.	Abrir o arquivo de entrada.
2.	Percorrer uma vez o arquivo de entrada já aberto, obtendo as informações de interesse (nomes e seus tipos), gerando um dicionário de propriedades, indexado por uma string (nome) com o valor sendo sua tipagem (objeto, vetor, string, número, etc).
3.	Após preenchido o dicionário de propriedades, percorro-o para ir construindo o arquivo Java de saída.

Execução e compilação:
1.	Para rodar o código Python e gerar o arquivo Java: “python3 codeGenerator.py <nomeDoArquivoDeEntrada>.json”.
2.	Após o comando acima, será gerado um arquivo “Programa.java”.
3.	Para compilar e rodar o código Java: “javac *.java && java Programa”.
