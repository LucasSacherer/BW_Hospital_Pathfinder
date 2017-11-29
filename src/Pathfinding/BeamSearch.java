package Pathfinding;

import Database.EdgeManager;
import Entity.Edge;
import Entity.Node;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeMath.min;


public class BeamSearch implements PathFinder {

        static int beamWidth = 3;

        EdgeManager edgeM;
        // The set of nodes already evaluated
        ArrayList<String> closedSet = new ArrayList<String>();
        List<Node> neighbors = new ArrayList<Node>();
        List<Node> connected = new ArrayList<Node>();
        // The set of currently discovered nodes that are not evaluated yet.
        boolean alreadyfound = false;

        private static Comparator<pathNode> weightComparator = new Comparator<pathNode>() {
            @Override
            public int compare(pathNode s1, pathNode s2) {
                return (int) (s1.cost - s2.cost);
            }
        };

        PriorityQueue<pathNode> beamPQ = new PriorityQueue<pathNode>(2, weightComparator);
        ArrayList<pathNode> holder = new ArrayList<pathNode>();


    public BeamSearch(EdgeManager e){this.edgeM = e;}

    public ArrayList<Node> pathFind(Node start, Node end) {
        return beamSearch(start, end);
    }

    private ArrayList<Node> beamSearch(Node loc1, Node loc2){
        //remove best node, grab all of its children
        //if any of them is goal, end and retrace path
        //else, sort list by heuristic
        //delete all but the first <beamWidth> children
        //repeat with the new best one

        int size;
        //define starting edge, just to get weight
        Edge tempEdge = new Edge(loc1,loc2);
        double hCost = (double)(tempEdge.getWeight());
        //initial starNode to store weight and null parent
        pathNode start = new pathNode(loc1, null, hCost);
        // Initially, only the start node is known.
        beamPQ.add(start);

        System.out.println("start: " + start.node.getNodeID());

        pathNode current = null;

        while(!(beamPQ.isEmpty())){
            current = beamPQ.poll();

            System.out.println("current: " + current.node.getNodeID());
            //Check if the goal has been reached yet
            if (current.node.getNodeID().equals(loc2.getNodeID())) {
                System.out.println("you just got beamed!");
                //if so trace back its path
                closedSet.clear();
                beamPQ.clear();
                return reconstruct_path(current);
            }
            //add the current path to the closedSet(Explored)
            closedSet.add(current.node.getNodeID());
            System.out.println("sdding to Closed");
            System.out.println(current.node.getNodeID());
            neighbors.clear();
            //get all the edges connected to the starting node
            neighbors = edgeM.getNeighbors(current.node);
            //add all the nodes from the connected edges to the neighbors list

            //loop through the neighbors
            for (int i = 0; i < neighbors.size(); i++) {
                System.out.println("neighbor #" + i + ": " + neighbors.get(i).getNodeID());
                //check if the node is in the closed set
                for (int j = 0; j < closedSet.size(); j++) {
                    if (neighbors.get(i).getNodeID().equals(closedSet.get(j))) {
                        alreadyfound = true;
                        break;// Ignore the neighbor which is already evaluated.
                    } else{
                        alreadyfound = false;

                    }
                }

                if (!alreadyfound) {
                    //if it is not in the closed set add it the priority queue along with its parent
                    tempEdge = new Edge(neighbors.get(i),loc2);
                    double hC = (double)(tempEdge.getWeight());

                    pathNode tempNode = new pathNode(neighbors.get(i), current, hC);
                    beamPQ.add(tempNode);
                    System.out.println("added node: " + tempNode.node.getNodeID());
                    //System.out.println(astarPQ.size());
                }
            }

            //clear the PQ, except for the top <beamWidth> nodes
            //this seems sloppy, is there a better way?

            System.out.println("holder size: " + min(beamWidth, beamPQ.size()));
            holder.clear();
            size = beamPQ.size();
            for(int i = 0; i < min(beamWidth, size); i++){
                pathNode holderTest = beamPQ.poll();
                System.out.println("Held: " + holderTest.node.getNodeID());
                holder.add(holderTest);
            }
            beamPQ.clear();
            size = holder.size();
            for(int i = 0; i < size; i++){
                System.out.println("Returning to beam: " + holder.get(i).node.getNodeID());
                beamPQ.add(holder.get(i));
            }
            holder.clear();
            System.out.println("Queue size: " + beamPQ.size());

        }

        //if failure, return an empty list
        System.out.println("you friggin moron!");
        return new ArrayList<Node>();
    }

    private ArrayList<Node>  reconstruct_path(pathNode curNode){
        ArrayList<Node> total_path = new ArrayList<Node>();
        total_path.add(curNode.node);
        while (curNode.parent != null) {
            curNode = curNode.parent;
            total_path.add(curNode.node);
        }
        return total_path;
    }
}
