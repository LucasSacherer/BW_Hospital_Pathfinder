package entity;

public class Edge {
    Node start;
    Node end;
    int weight;

    Edge(Node s, Node e, int w){
        start = s;
        end = e;
        weight = w;
    }
}

