package Pathfinding;

import Database.EdgeManager;
import Database.NodeManager;
import DatabaseSetup.DatabaseGargoyle;
import Entity.Edge;
import Entity.Node;
import org.junit.Test;

import java.util.ArrayList;

import static java.lang.Math.min;
import static org.junit.Assert.*;

public class BeamSearchTest {
    DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
    @Test
    public void sameNode() throws Exception{
        NodeManager nodeM = new NodeManager(databaseGargoyle);
        EdgeManager edgeM = new EdgeManager(databaseGargoyle, nodeM);

        BeamSearch b = new BeamSearch(edgeM);
        Node n1 = new Node("1",1,1,"1","Shapiro","type","Stairwell","STAI");
        ArrayList<Node> actual = new ArrayList<>();
        actual.add(n1);
        ArrayList<Node> answer = b.pathFind(n1,n1);
        System.out.println(answer);
        assertEquals(actual,answer);

        nodeM.removeNode(n1);

    }

    @Test
    public void twoConnects() throws Exception{
        NodeManager nodeM = new NodeManager(databaseGargoyle);
        EdgeManager edgeM = new EdgeManager(databaseGargoyle, nodeM);

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

        BeamSearch beamSearch = new BeamSearch(edgeM);
        ArrayList<String> actual = new ArrayList<>();
        actual.add(n3.getNodeID());
        actual.add(n1.getNodeID());

        ArrayList<Node> answer = beamSearch.pathFind(n1,n3);
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
        NodeManager nodeM = new NodeManager(databaseGargoyle);
        nodeM.updateNodes();
        EdgeManager edgeM = new EdgeManager(databaseGargoyle, nodeM);
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GHALL01601");
        Node n2 = nodeM.getNode("GHALL01501");
        BeamSearch beamSearch = new BeamSearch(edgeM);
        ArrayList<Node> answer = beamSearch.pathFind(n1,n2);
        assertEquals("GHALL01501", answer.get(0).getNodeID());
        assertEquals("GHALL01601", answer.get(1).getNodeID());
    }

    @Test
    public void realNodesSameNode() throws Exception{
        NodeManager nodeM = new NodeManager(databaseGargoyle);
        nodeM.updateNodes();
        EdgeManager edgeM = new EdgeManager(databaseGargoyle, nodeM);
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GHALL01601");
        Node n2 = nodeM.getNode("GHALL01601");
        BeamSearch beamSearch = new BeamSearch(edgeM);
        ArrayList<Node> answer = beamSearch.pathFind(n1,n2);
        ArrayList<Node> expected;
        assertEquals("GHALL01601", answer.get(0).getNodeID());
    }

    @Test
    public void realNodesAcrossFloor1() throws Exception{
        NodeManager nodeM = new NodeManager(databaseGargoyle);
        nodeM.updateNodes();
        EdgeManager edgeM = new EdgeManager(databaseGargoyle, nodeM);
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GHALL01601");
        Node n2 = nodeM.getNode("GELEV00N01");
        BeamSearch beamSearch = new BeamSearch(edgeM);
        ArrayList<Node> answer = beamSearch.pathFind(n1,n2);
        //System.out.println(answer);
        ArrayList<String> ansID = new ArrayList<>();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);
    }

    @Test
    public void realNodesBacktoBack() throws Exception{
        NodeManager nodeM = new NodeManager(databaseGargoyle);
        nodeM.updateNodes();
        EdgeManager edgeM = new EdgeManager(databaseGargoyle, nodeM);
        edgeM.updateEdges();
        Node n1 = nodeM.getNode("GHALL01602");
        Node n2 = nodeM.getNode("GHALL01002");
        BeamSearch beamSearch = new BeamSearch(edgeM);
        ArrayList<Node> answer = beamSearch.pathFind(n1,n2);
        ArrayList<Node> expected;
        //System.out.println(answer);
        ArrayList<String> ansID = new ArrayList<>();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);

        n1 = nodeM.getNode("GHALL01601");
        n2 = nodeM.getNode("GELEV00N01");
        beamSearch = new BeamSearch(edgeM);
        answer = beamSearch.pathFind(n1,n2);
        ansID.clear();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);
    }
}