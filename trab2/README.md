# CI1016

Para este trabalho, foi utilizado a ferramenta de auxílio "Picocog" e, por dependência, o Maven também.

[INSTALAÇÃO DE DEPENDÊNCIAS]
O arquivo "pom.xml" possui as dependências para a correta execução do projeto. Para executar este arquivo e instalar as dependências, execute:
    mvn install

[ARQUIVO DE ENTRADA]
O arquivo "inp.json" é um exemplo de arquivo de entrada (exemplo da especificação do trabalho).

[CÓDIGO GERADOR]
A pasta "src" possui tres níveis: "app", "code" e "json". A metodologia para ler e parsear o arquivo Json de entrada, seguiu exatamente a mesma lógica do primeiro projeto, somente a geração do código Java final que é diferente, pois se utiliza o Picocog.

    A pasta "app":  Contém o código Java (App.java) principal.
    A pasta "code": Contém o código Java (CodeGenerator.java) que escreve/ formata o arquivo Java de saída, usando o Picocog.
    A pasta "json": Contém os códigos Java (JsonParser.java, JsonProperty.java e JsonPropertyType.java) para parsear o arquivo Json de entrada.

[EXECUCAO]
Para compilar o programa, passar o arquivo Json de entrada, compilar o código Java gerado e o executar, existem duas maneiras para a passagem do arquivo de entrada: "xxx < inp.json" ou "xxx inp.json"

Com o "<":
   mvn exec:java -Dexec.mainClass="com.trab2.app.App" < inp.json && javac Programa.java && java Programa

Sem o "<"
   mvn exec:java -Dexec.mainClass="com.trab2.app.App" -Dexec.args="inp.json" && javac Programa.java && java Programa

Após a execução de um dos comandos acima, o programa deverá criar o arquivo ".java", programa principal (Programa.java) e os arquivos ".class" (Aluno.class, Programa.class e Turma.class, referindo-se às classes do arquivo de entrada dado na especificação do trabalho) e na saída padrão irá ser mostrado a mensagem "Hi, the Java code was generated correctly!!!", indicando que o programa rodou corretamente.
