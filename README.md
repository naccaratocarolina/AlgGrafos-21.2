# Algoritmos e Grafos 2021.2
Por definição, um grafo é denominado P4-Esparso se nenhum conjunto de cinco vértices induzir mais de um P4. O algoritmo desenvolvido explora essa principal propriedade dos grafos p4-esparsos, buscando, por força bruta, se algum conjunto de 5 vértices do grafo dado induz mais de um P4.

Sabemos que, para que um conjunto de n vértices forme um caminho simples de tamanho n, temos que verificar se o conjunto de vértices não formam um ciclo; se nenhum vértice do conjunto possuir grau maior que 2 (garantindo que é um caminho simples); e, claro, se o conjunto de vértices é conexo. Portanto, a ideia central do algoritmo é iterar pelos vértices do grafo e formar todos os possíveis conjuntos de 5 vértices. Para cada conjunto de 5 vértices, verifica se é possível induzir mais de um P4. Se sim, o grafo não é p4-esparso. Caso contrário, é p4-esparso.

Para rodar o programa basta popular a pasta ```/myfiles``` dentro do diretório do arquivo principal AlgGrafos.java com informações dos grafos que se deseja avaliar se é p4-esparso ou não. A estrutura do arquivo deve ser ```"id = vértices adjacentes"```, por exemplo: 
```
0 = 2 7
1 = 2 7
2 = 0 1 3 5 6 7
3 = 2 4 5
4 = 3 5 6
5 = 2 3 4
6 = 2 4
7 = 0 1 2
```