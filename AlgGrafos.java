import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class AlgGrafos {
    public static final String diretorio = System.getProperty("user.dir") + "/myfiles/";
    private static int arestasCont = 0;

    public static class LeitorDeArquivo {
        private final String arquivo;
        private List<String> linhas;

        /**
         * Construtor de LeitorDeArquivo
         * @param arquivo nome do arquivo a ser lido
         */
        public LeitorDeArquivo (String arquivo) {
            this.arquivo = arquivo;
            this.linhas = new ArrayList<String>();
        }

        /**
         * Funcao que le todas as linhas de determinado arquivo e
         * armazena em uma variavel.
         * @return todas as linhas do arquivo
         */
        public List<String> abreArquivo () {
            Path caminho = Paths.get(diretorio + this.arquivo);
            Charset charset = StandardCharsets.UTF_8;

            try {
                this.linhas = Files.readAllLines(caminho, charset);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return linhas;
        }
    }

    public static class Grafo {
        private final Map<Integer, ArrayList<Integer>> verticesAdj;
        private int verticesCont;
        private int arestasCont;

        /**
         * Construtor de Grafo
         */
        public Grafo() {
            this.verticesAdj = new HashMap<Integer, ArrayList<Integer>>();
            this.verticesCont = 0;
            this.arestasCont = 0;
        }

        /**
         * Getter de verticesAdj.
         * @return hashmap da relacao de vertices (key) e seus vertices adjacentes (value)
         */
        public Map<Integer, ArrayList<Integer>> getVerticesAdj () {
            return this.verticesAdj;
        }

        /**
         * Adiciona um novo vertice ao conjunto de vertices de um grafo
         * @param id identificador do vertice
         */
        void addVertice (int id) {
            this.verticesAdj.putIfAbsent(id, new ArrayList<Integer>());
        }

        /**
         * Adiciona uma nova aresta, adicionando id2 a lista
         * de vertices adjacentes a id1
         * @param id1 vertice raiz
         * @param id2 vertice a ser adicionado a lista de vertices adj
         */
        void addAresta (int id1, int id2) {
            this.verticesAdj.get(id1).add(id2);
        }

        /**
         * Getter de verticesCont
         * @return quantidade de vertices
         */
        public int getVerticesCont() {
            return verticesCont;
        }

        /**
         * Setter de verticesCont
         * @param verticesCont quantidade de vertices
         */
        public void setVerticesCont (int verticesCont) {
            this.verticesCont = verticesCont;
        }

        /**
         * Getter de arestasCont
         * @return quantidade de arestas
         */
        public int getArestasCont () {
            return arestasCont;
        }

        /**
         * Setter de arestasCont
         * @param arestasCont quantidade de arestas
         */
        public void setArestasCont (int arestasCont) {
            this.arestasCont = arestasCont;
        }

        /**
         * Dado um conjunto de 4 vertices, verifica se eh possivel formar
         * um caminho simples. Para que 4 vertices formem um P4, temos que
         * as seguintes afirmacoes sao verdadeiras: (i) Se o conjunto de
         * vertices nao formam um ciclo; (ii) Se nenhum vertice do conjunto
         * de vertices possuir grau maior que 2; (iii) Se o conjunto de
         * vertices eh conexo.
         *
         * Uso: formaP4(a, b, c, d). Verifica se o conjunto de vertices
         * a, b, c, d (em qualquer ordem) induz um P4. Para isso, mantemos
         * controle do grau dos vertices fornecidos e da quantidade de
         * arestas entre eles.
         *
         * @param v conjunto de vertices a, b, c, d
         * @return true se o conjunto de vertices forma um P4; false, c.c.
         */
        private boolean formaP4 (Integer... v) {
            if (v.length != 4) return false;

            int[] grau = new int[4]; // sequencia de graus dos vertices
            int arestasCont = 0;

            for (int i = 0; i < 3; i++) {
                for (int j = i + 1; j <= 3; j++) {
                    if (this.verticesAdj.get(v[i]).contains(v[j])) {
                       grau[i]++;
                       grau[j]++;
                       arestasCont++;
                    }
                }
            }

            if (arestasCont != 3) return false;

            // Possui 3 arestas. Garante que nao eh um K3 + v
            for (int i = 0; i < 4; i++) if (grau[i] == 0) return false;

            return true;
        }

        /**
         * Por definicao, um grafo eh P4-Esparso se nenhum conjunto de
         * cinco vertices induzir mais de um P4. Portanto, esta funcao
         * conta quantos P4s sao induzidos a partir de todos os conjuntos
         * de 5 vertices de determinado grafo. Se algum conjunto induzir
         * mais de um P4, o grafo nao eh P4-Esparso. Se tais conjuntos
         * induzirem no maximo um P4, entao o grafo eh P4-Esparso.
         *
         * @return true, se o grafo for P4-Esparso; false, c.c.
         */
        public boolean ehP4Esparso () {
            ArrayList<Integer> v = new ArrayList<>(this.verticesAdj.keySet());
            int n = v.size();

            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    for (int k = j + 1; k < n; k++) {
                        for (int m = k + 1; m < n; m++) {
                            int count = 0;
                            if (formaP4(v.get(i), v.get(j), v.get(k), v.get(m))) count ++;
                            for (int p = m + 1; p < n; p++) {
                                if (formaP4(v.get(i), v.get(j), v.get(k), v.get(p))) count++;
                                if (count > 1) {
                                    System.err.println("Não é P4-Esparso porque  " + v.get(i) + " " + v.get(j) + " " + v.get(k) + " " + v.get(m) + " " + v.get(p) + " induz muitos P4s");
                                    return false;
                                }

                                if (formaP4(v.get(i), v.get(j), v.get(p), v.get(m))) count++;
                                if (count > 1) {
                                    System.err.println("Não é P4-Esparso porque  " + v.get(i) + " " + v.get(j) + " " + v.get(k) + " " + v.get(m) + " " + v.get(p) + " induz muitos P4s");
                                    return false;
                                }

                                if (formaP4(v.get(i), v.get(p), v.get(k), v.get(m))) count++;
                                if (count > 1) {
                                    System.err.println("Não é P4-Esparso porque " + v.get(i) + " " + v.get(j)+ " " + v.get(k) + " " + v.get(m) + " " + v.get(p) + " induz muitos P4s");
                                    return false;
                                }

                                if (formaP4(v.get(p), v.get(j), v.get(k), v.get(m))) count++;
                                if (count > 1) {
                                    System.err.println("Não é P4-Esparso porque  " + v.get(i) + " " + v.get(j) + " " + v.get(k) + " " + v.get(m) + " " + v.get(p) + " induz muitos P4s");
                                    return false;
                                }
                            }
                        }
                    }
                }
            }

            return true;
        }
    }

    /**
     * Cria um vertice a partir da linha do arquivo.
     * @param linha linha do arquivo ("id do vertice = vertices adjacentes")
     * @param grafo grafo do qual o vertice sera adicionado
     * @param comecaComZero booleano para saber se sera necessario decrementar os ids
     */
    public static void criaVertice (String linha, Grafo grafo, boolean comecaComZero) {
        // Processa as informacoes da linha
        String[] verticeInfo = linha.split(" = "); // [id, vizinhos]

        // Processa o identificador do vertice
        int id = Integer.parseInt(verticeInfo[0]);
        if (!comecaComZero) id--;
        grafo.addVertice(id);

        // Processa vertices adjacentes
        int[] adj = Arrays.stream(verticeInfo[1].split(" ")).mapToInt(Integer::parseInt).toArray();
        for (int verticeId : adj) {
            if (!comecaComZero) verticeId--;
            grafo.addAresta(id, verticeId);
            grafo.setArestasCont(++arestasCont);
        }
    }

    public static void main (String[] args) {
        LeitorDeArquivo leitorDeArquivo = new LeitorDeArquivo("grafo.txt");
        Grafo grafo = new Grafo();
        int quantVertices = 0;

        int primeiroId = Integer.parseInt(leitorDeArquivo.abreArquivo().get(0).split(" = ")[0]);
        boolean comecaComZero = (primeiroId == 0);

        // Cria o grafo a partir das linhas do arquivo
        for (String linha : leitorDeArquivo.abreArquivo()) {
            criaVertice(linha, grafo, comecaComZero);
            grafo.setVerticesCont(++quantVertices);
        }

        // Imprime grafo
        System.out.println("==========================================================\n" +
                "   Grafo: " + grafo.getVerticesCont() + " vertices e " + grafo.getArestasCont() + " arestas\n" +
                "==========================================================");
        for (Map.Entry<Integer, ArrayList<Integer>> entry : grafo.getVerticesAdj().entrySet())
            System.out.println("Vertice = " + entry.getKey() + ". Vertices Adjacentes = " + entry.getValue().toString());
        System.out.println("");

        // Verifica se eh P4-Esparso
        if (grafo.ehP4Esparso()) System.out.println("O grafo é P4-Esparso");
    }
}
