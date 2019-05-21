package br.insper.robot19;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Comparator;

public class BuscaAestrelinha {
    private Block start = null;
    private Block end = null;
    private GridMap map = null;

    private Queue<Node> border;
    private boolean[][] visited;

    private LinkedList<RobotAction> list = new LinkedList<>();

    public BuscaAestrelinha(Block start, Block end, GridMap map){
        this.start = start;
        this.end = end;
        this.map = map;
        visited = new boolean[map.getHeight()][map.getWidth()];
        for (int i=0; i<map.getHeight();i++){
            for (int j=0; j<map.getWidth();j++){
                visited[i][j] = false;

            }
        }


    }

    private Node searchNode(){

        Node root = new Node(start, null, null, 0);

        //Limpa a fronteira e insere o nó raiz
        border = new LinkedList<Node>();
        border.add(root);

        while(!border.isEmpty()) {

            Node node = border.remove();
            Block actual = node.getValue();

            visited[actual.row][actual.col] = true;

            int counter = 0;

            double min = 9999999;

            boolean moved = false;

            double[] nextHeuristica = new double[4];

            if(actual.row == end.row && actual.col == end.col) {
                return node;

            } else for(RobotAction acao : RobotAction.values()) {

                Block next = map.nextBlock(actual, acao);
                if (next != null && next.type != BlockType.WALL && !visited[next.row][next.col]){
                    Node aa = new Node(next, node, acao, next.type.cost);
                    border.add(aa);
                    nextHeuristica[counter] = next.getEstrelinha(this.end, aa.getPathCost());

                    if (nextHeuristica[counter]<min){
                        min = nextHeuristica[counter];
                    }
                }
                counter++;
            }

            counter = 0;
            for(double i: nextHeuristica){
                RobotAction acao = RobotAction.values()[counter];
                Block next = map.nextBlock(actual, acao);

                if(!moved && min==i){
                    next.visit();
                    moved = true;
                    Node newNode = new Node(next, node, acao, next.type.cost);
                    border.add(newNode);
                    list.add(acao);
                }
                counter++;
            }
        }
        return null;
    }


    public RobotAction[] resolver() {

        // Encontra a solução através da busca
        Node destino = searchNode();
        if(destino == null) {
            return null;
        }

        //Faz o backtracking para recuperar o caminho percorrido
        Node atual = destino;
        Deque<RobotAction> caminho = new LinkedList<RobotAction>();
        while(atual.getAction() != null) {
            caminho.addFirst(atual.getAction());
            atual = atual.getParent();
        }
        return caminho.toArray(new RobotAction[caminho.size()]);
    }
}
