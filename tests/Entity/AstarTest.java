package Entity;

import static org.junit.Assert.*;

import Database.EdgeManager;
import Database.NodeManager;
import Pathfinding.Astar;
import org.junit.Test;

import java.util.ArrayList;
public class AstarTest {

    @Test
    public void sameNode() throws Exception{
        NodeManager nodeM = new NodeManager();
        EdgeManager edgeM = new EdgeManager(nodeM);

        Astar star = new Astar(edgeM);
        Node n1 = new Node("1",1,1,"1","Shapiro","type","Stairwell","STAI");
        ArrayList<Node> actual = new ArrayList<>();
        actual.add(n1);
        ArrayList<Node> answer = star.Astar(n1,n1);
        System.out.println(answer);
        assertEquals(actual,answer);


        nodeM.removeNode(n1);

    }


    @Test
    public void twoConnects() throws Exception{
        NodeManager nodeM = new NodeManager();
        EdgeManager edgeM = new EdgeManager(nodeM);

        Node n1 = new Node("1",1,1,"1","Shapiro","type","Stairwell","STAI");
        Node n2 = new Node("2",2,1,"1","Shapiro","type","Stairwell","STAI");
        Node n3 = new Node("3",3000,2000,"1","Shapiro","type","Stairwell","STAI");
        nodeM.addNode(n1);
        nodeM.addNode(n2);
        nodeM.addNode(n3);

        Edge e1 = new Edge(n1,n2);
        Edge e3 = new Edge(n1,n3);
        edgeM.addEdge(e1);
        edgeM.addEdge(e3);

        Astar star = new Astar(edgeM);
        ArrayList<String> actual = new ArrayList<>();
        actual.add(n3.getNodeID());
        actual.add(n1.getNodeID());

         ArrayList<Node> answer = star.Astar(n1,n3);
        ArrayList<String> ansID = new ArrayList<>();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(answer);
        assertEquals(actual,ansID);

        edgeM.removeEdge(e1);
        edgeM.removeEdge(e3);
        nodeM.removeNode(n1);
        nodeM.removeNode(n2);
        nodeM.removeNode(n3);
    }

    @Test
    public void realNodes1Connect() throws Exception{
        NodeManager nodeM = new NodeManager();
        nodeM.updateNodes();
        EdgeManager edgeM = new EdgeManager(nodeM);
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GHALL01601");
        Node n2 = nodeM.getNode("GHALL01501");
        Astar star = new Astar(edgeM);
        ArrayList<Node> answer = star.Astar(n1,n2);
        ArrayList<Node> expected;
        System.out.println(answer.get(0).getNodeID());
        System.out.println(answer.get(1).getNodeID());

    }

    @Test
    public void realNodesSameNode() throws Exception{
        NodeManager nodeM = new NodeManager();
        nodeM.updateNodes();
        EdgeManager edgeM = new EdgeManager(nodeM);
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GHALL01601");
        Node n2 = nodeM.getNode("GHALL01601");
        Astar star = new Astar(edgeM);
        ArrayList<Node> answer = star.Astar(n1,n2);
        ArrayList<Node> expected;
        System.out.println(answer.get(0).getNodeID());
    }

    @Test
    public void realNodesAcrossFloor1() throws Exception{
        NodeManager nodeM = new NodeManager();
        nodeM.updateNodes();
        EdgeManager edgeM = new EdgeManager(nodeM);
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GHALL01601");
        Node n2 = nodeM.getNode("GELEV00N01");
        Astar star = new Astar(edgeM);
        ArrayList<Node> answer = star.Astar(n1,n2);
        ArrayList<Node> expected;
        //System.out.println(answer);
        ArrayList<String> ansID = new ArrayList<>();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);
    }

    @Test
    public void realNodesAcrossFloor2() throws Exception{
        NodeManager nodeM = new NodeManager();
        nodeM.updateNodes();
        EdgeManager edgeM = new EdgeManager(nodeM);
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GHALL01602");
        Node n2 = nodeM.getNode("GHALL01002");
        Astar star = new Astar(edgeM);
        ArrayList<Node> answer = star.Astar(n1,n2);
        ArrayList<Node> expected;
        //System.out.println(answer);
        ArrayList<String> ansID = new ArrayList<>();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);
    }

    @Test
    public void realNodesBacktoBack() throws Exception{
        NodeManager nodeM = new NodeManager();
        nodeM.updateNodes();
        EdgeManager edgeM = new EdgeManager(nodeM);
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GHALL01602");
        Node n2 = nodeM.getNode("GHALL01002");
        Astar star = new Astar(edgeM);
        ArrayList<Node> answer = star.Astar(n1,n2);
        ArrayList<Node> expected;
        //System.out.println(answer);
        ArrayList<String> ansID = new ArrayList<>();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);

        n1 = nodeM.getNode("GHALL01601");
        n2 = nodeM.getNode("GELEV00N01");
        star = new Astar(edgeM);
        answer = star.Astar(n1,n2);
       ansID.clear();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);
    }

    @Test
    public void realNodesbetweenElevators12() throws Exception{
        NodeManager nodeM = new NodeManager();
        nodeM.updateNodes();
        EdgeManager edgeM = new EdgeManager(nodeM);
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GELEV00N01");
        Node n2 = nodeM.getNode("GELEV00N02");
        Astar star = new Astar(edgeM);
        ArrayList<Node> answer = star.Astar(n1,n2);
        ArrayList<Node> expected;
        //System.out.println(answer);
        ArrayList<String> ansID = new ArrayList<>();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);
    }

    @Test
    public void realNodesbetweenFloors12() throws Exception{
        NodeManager nodeM = new NodeManager();
        nodeM.updateNodes();
        EdgeManager edgeM = new EdgeManager(nodeM);
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GHALL02401");
        Node n2 = nodeM.getNode("GELEV00N02");
        Astar star = new Astar(edgeM);
        ArrayList<Node> answer = star.Astar(n1,n2);
        ArrayList<Node> expected;
        //System.out.println(answer);
        ArrayList<String> ansID = new ArrayList<>();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);
    }

    @Test
    //tests that the bumps are gone and it really does take the most direct path
    public void realNodesFloors2() throws Exception{
        NodeManager nodeM = new NodeManager();
        nodeM.updateNodes();
        EdgeManager edgeM = new EdgeManager(nodeM);
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GHALL01002");
        Node n2 = nodeM.getNode("GELEV00N02");
        Astar star = new Astar(edgeM);
        ArrayList<Node> answer = star.Astar(n1,n2);
        ArrayList<String> expected = new ArrayList<>();
        expected.add("GELEV00N02");
        expected.add("GDEPT02402");
        expected.add("GHALL01702");
        expected.add("GHALL01602");
        expected.add("GHALL01402");
        expected.add("GHALL01202");
        expected.add("GHALL01002");
        //System.out.println(answer);
        ArrayList<String> ansID = new ArrayList<>();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);
        assertEquals(expected,ansID);
    }

    @Test
    public void realNodesbetweenFloors13() throws Exception{
        NodeManager nodeM = new NodeManager();
        nodeM.updateNodes();
        EdgeManager edgeM = new EdgeManager(nodeM);
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GHALL02401");
        Node n2 = nodeM.getNode("GSERV01603");
        Astar star = new Astar(edgeM);
        ArrayList<Node> answer = star.Astar(n1,n2);
        ArrayList<String> expected = new ArrayList<>();
        expected.add("GSERV01603");
        expected.add("GDEPT01403");
        expected.add("GELEV00N03");
        expected.add("GELEV00N01");
        expected.add("GHALL02401");
        ArrayList<String> ansID = new ArrayList<>();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);
        assertEquals(expected,ansID);
    }

}