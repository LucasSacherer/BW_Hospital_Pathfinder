package entity;

public class Edge {
    final private Node startNode;
    final private Node endNode;
    final int weight;


    public Edge(Node startNode, Node endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.weight = setWeight(startNode, endNode);
    }

    public int getWeight() {
        return weight;
    }

    private int setWeight(Node start, Node end) {
        return (int) Math.sqrt ((start.getXcoord() - end.getXcoord()) * (start.getXcoord() - end.getXcoord())
                +
                (start.getYcoord() - end.getYcoord()) *  (start.getYcoord() - end.getYcoord()));
    }
}
