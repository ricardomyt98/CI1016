# CI1016

Para este trabalho, foi utilizado a ferramenta de auxílio "Picocog" e, por dependência, o Maven também.

[INSTALAÇÃO DE DEPENDÊNCIAS]
O arquivo "pom.xml" possui as dependências para a correta execução do projeto. Para executar este arquivo e instalar as dependências, execute:
    mvn install

[ARQUIVO DE ENTRADA]
O arquivo "inp.json" é um exemplo de arquivo de entrada (exemplo da especificação do trabalho).

[CÓDIGO GERADOR]
A pasta "src" possui tres niveis: "app", "code" e "json".
    A pasta "app":  Contém o código Java (App.java) principal.
    A pasta "code": Contém o código Java (CodeGenerator.java) que escreve/ formata o arquivo Java de saída, usando o Picocog.
    A pasta "json": Contém os códigos Java (JsonParser.java, JsonProperty.java e JsonPropertyType.java) para parsear o arquivo Json de entrada.

[EXECUCAO]
Para compilar o programa, passar o arquivo Json de entrada, compilar o código Java gerado e o executar, existem duas maneiras para a passagem do arquivo de entrada: "xxx < inp.json" ou "xxx inp.json"

Com o "<":
   mvn exec:java -Dexec.mainClass="com.trab2.app.App" < inp.json && javac Programa.java && java Programa

Sem o "<"
   mvn exec:java -Dexec.mainClass="com.trab2.app.App" -Dexec.args="inp.json" && javac Programa.java && java Programa

Após a execução de um dos comandos acima, o programa deverá criar os arquivos, na raiz, referente às classes lidas no arquivo de entrada e a saída padrão irá mostrar a mensagem "Hi, the Java code was generated correctly!!!".