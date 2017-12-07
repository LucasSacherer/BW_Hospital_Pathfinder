package Pathfinding;

import Database.EdgeManager;
import Entity.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public abstract class BadAlgorithms implements PathFinder{

    EdgeManager edgeManager;

    abstract void init(Node startNode, Node endNode);
    abstract void body(Node startNode, Node endNode);
    abstract void clean();
    ArrayList<String> closedSet = new ArrayList<String>();
    List<Node> neighbors = new ArrayList<Node>();
    List<Node> connected = new ArrayList<Node>();
    boolean alreadyfound;

    PriorityQueue<pathNode> beamPQ = new PriorityQueue<pathNode>(2, weightComparator);
    ArrayList<pathNode> holder = new ArrayList<pathNode>();
    List<Node> result = new ArrayList<Node>();

    private static Comparator<pathNode> weightComparator = new Comparator<pathNode>() {
        @Override
        public int compare(pathNode s1, pathNode s2) {
            //return (int) (s1.cost - s2.cost);
            return (int) (s1.cost - s2.cost);
        }
    };


    List<Node> findPath(Node startNode, Node endNode){
        System.out.println("succ");
        init(startNode, endNode);
        while(!beamPQ.isEmpty()){
            body(startNode, endNode);
            clean();
        }
        return result;
    }
}

