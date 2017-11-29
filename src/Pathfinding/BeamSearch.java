package Pathfinding;

import Database.EdgeManager;
import Entity.Edge;
import Entity.Node;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.List;


public class BeamSearch implements PathFinder {

    public ArrayList<Node> pathFind(Node start, Node end){
        int beamWidth = 3;

        EdgeManager edgeM;
        // The set of nodes already evaluated
        ArrayList<String> closedSet = new ArrayList<String>();
        List<Node> neighbors = new ArrayList<Node>();
        List<Node> connected = new ArrayList<Node>();
        // The set of currently discovered nodes that are not evaluated yet.
        boolean alreadfound = false;




        return null;

    }
}
