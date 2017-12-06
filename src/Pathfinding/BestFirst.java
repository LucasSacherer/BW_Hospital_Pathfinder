
package Pathfinding;

import Database.EdgeManager;
import Entity.Edge;
import Entity.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import static java.lang.Math.min;

public class BestFirst implements PathFinder{

    //Interpreted BestFirst as Greedy BestFirst
    //without a huge beam width, that is
    int beamWidth = 1;

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
            //return (int) (s1.cost - s2.cost);
            return (int) (s1.cost - s2.cost);
        }
    };

    PriorityQueue<pathNode> beamPQ = new PriorityQueue<pathNode>(2, weightComparator);
    ArrayList<pathNode> holder = new ArrayList<pathNode>();


    public BestFirst(EdgeManager e){this.edgeM = e;}

    public ArrayList<Node> pathFind(Node start, Node end) {
        return bestFirst(start, end);
    }

    private ArrayList<Node> bestFirst(Node loc1, Node loc2){
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

        pathNode current = null;

        while(!(beamPQ.isEmpty())){
            current = beamPQ.poll();

            //Check if the goal has been reached yet
            if (current.node.getNodeID().equals(loc2.getNodeID())) {
                System.out.println("Goal Reached!");
                //if so trace back its path
                closedSet.clear();
                beamPQ.clear();
                return reconstruct_path(current);
            }
            //add the current path to the closedSet(Explored)
            closedSet.add(current.node.getNodeID());
            neighbors.clear();
            //get all the edges connected to the starting node
            neighbors = edgeM.getNeighbors(current.node);
            //add all the nodes from the connected edges to the neighbors list

            //loop through the neighbors

            for (int i = 0; i < neighbors.size(); i++) {
                //check if the node is in the closed set
                for (int j = 0; j < closedSet.size(); j++) {
                    if (neighbors.get(i).getNodeID().equals(closedSet.get(j))) {
                        alreadyfound = true;
                        break;// Ignore the neighbor which is already evaluated.
                    } else{
                        alreadyfound = false;

                    }
                }

                //check to see if it works only on the same level?
                if (!alreadyfound){
                    //if it is not in the closed set add it the priority queue along with its parent
                    tempEdge = new Edge(neighbors.get(i),loc2);
                    double hC = (double)(tempEdge.getWeight());

                    // similar floor weighting to AStar, unsure how much it helps
                    if(((loc2.getBuilding().equals("BTM") || loc2.getBuilding().equals("Shapiro")))||((neighbors.get(i).getBuilding().equals("BTM") || neighbors.get(i).getBuilding().equals("Shapiro")))){
                        if (!(loc2.getBuilding().equals(neighbors.get(i)))){
                            //if one is the tower and one is francis ignore
                            if (!(neighbors.get(i).getFloor().equals("2"))) {
                                hC += 400;
                            }
                        }
                    }
                    if (!(loc2.getBuilding().equals(neighbors.get(i)))){
                        //if one is the tower and one is francis ignore
                        hC += 400;
                    }



                    pathNode tempNode = new pathNode(neighbors.get(i), current, hC);
                    beamPQ.add(tempNode);
                }
            }

            //clear the PQ, except for the top <beamWidth> nodes
            //this seems sloppy, is there a better way?

            //System.out.println("holder size: " + min(beamWidth, beamPQ.size()));
            holder.clear();
            size = beamPQ.size();
            for(int i = 0; i < min(beamWidth, size); i++){
                pathNode holderTest = beamPQ.poll();
                //System.out.println("Held: " + holderTest.node.getNodeID());
                holder.add(holderTest);
            }
            beamPQ.clear();
            size = holder.size();
            for(int i = 0; i < size; i++){
                //System.out.println("Returning to beam: " + holder.get(i).node.getNodeID());
                beamPQ.add(holder.get(i));
            }
            holder.clear();
            //System.out.println("Queue size: " + beamPQ.size());
        }

        //if failure, return an empty list
        closedSet.clear();
        beamPQ.clear();

        System.out.println("Path Not Found");
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

