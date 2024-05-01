package src.objects;
public class Node {
    public String word;
    public Node parent;
    public int cost;
    public int heuristic;

    public Node(String word, Node parent, int cost) {
        this.word = word;
        this.parent = parent;
        this.cost = cost;
    }

    public Node(String word, Node parent, int cost, int heuristic) {
        this.word = word;
        this.parent = parent;
        this.cost = cost;
        this.heuristic = heuristic;
    }
}
