package Pathfinding;

import Database.AdminLogManager;
import Database.EdgeManager;
import Database.NodeManager;
import DatabaseSetup.DatabaseGargoyle;
import Entity.Edge;
import Entity.Node;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DijkstraTest {

    DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();

    @Test
    public void sameNode() throws Exception{
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager nodeM = new NodeManager(databaseGargoyle, adminLogManager);
        EdgeManager edgeM = new EdgeManager(databaseGargoyle, nodeM, adminLogManager);
        databaseGargoyle.attachManager(nodeM);
        databaseGargoyle.attachManager(edgeM);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        Dijkstra b = new Dijkstra(edgeM);
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
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager nodeM = new NodeManager(databaseGargoyle, adminLogManager);
        EdgeManager edgeM = new EdgeManager(databaseGargoyle, nodeM, adminLogManager);
        databaseGargoyle.attachManager(nodeM);
        databaseGargoyle.attachManager(edgeM);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

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

        Dijkstra Dijkstra = new Dijkstra(edgeM);
        ArrayList<String> actual = new ArrayList<>();
        actual.add(n3.getNodeID());
        actual.add(n1.getNodeID());

        ArrayList<Node> answer = Dijkstra.pathFind(n1,n3);
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
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager nodeM = new NodeManager(databaseGargoyle, adminLogManager);
        EdgeManager edgeM = new EdgeManager(databaseGargoyle, nodeM, adminLogManager);
        databaseGargoyle.attachManager(nodeM);
        databaseGargoyle.attachManager(edgeM);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        Node n1 = nodeM.getNode("GHALL01601");
        Node n2 = nodeM.getNode("GHALL01501");
        Dijkstra Dijkstra = new Dijkstra(edgeM);
        ArrayList<Node> answer = Dijkstra.pathFind(n1,n2);
        assertEquals("GHALL01501", answer.get(0).getNodeID());
        assertEquals("GHALL01601", answer.get(1).getNodeID());
    }

    @Test
    public void realNodesSameNode() throws Exception{
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager nodeM = new NodeManager(databaseGargoyle, adminLogManager);
        EdgeManager edgeM = new EdgeManager(databaseGargoyle, nodeM, adminLogManager);
        databaseGargoyle.attachManager(nodeM);
        databaseGargoyle.attachManager(edgeM);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        Node n1 = nodeM.getNode("GHALL01601");
        Node n2 = nodeM.getNode("GHALL01601");
        Dijkstra Dijkstra = new Dijkstra(edgeM);
        ArrayList<Node> answer = Dijkstra.pathFind(n1,n2);
        ArrayList<Node> expected;
        assertEquals("GHALL01601", answer.get(0).getNodeID());
    }

    @Test
    public void realNodesAcrossFloor1() throws Exception{
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager nodeM = new NodeManager(databaseGargoyle, adminLogManager);
        EdgeManager edgeM = new EdgeManager(databaseGargoyle, nodeM, adminLogManager);
        databaseGargoyle.attachManager(nodeM);
        databaseGargoyle.attachManager(edgeM);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        Node n1 = nodeM.getNode("GHALL01601");
        Node n2 = nodeM.getNode("GELEV00N01");
        Dijkstra Dijkstra = new Dijkstra(edgeM);
        ArrayList<Node> answer = Dijkstra.pathFind(n1,n2);
        //System.out.println(answer);
        ArrayList<String> ansID = new ArrayList<>();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);
    }

    @Test
    public void realNodesBacktoBack() throws Exception{
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager nodeM = new NodeManager(databaseGargoyle, adminLogManager);
        EdgeManager edgeM = new EdgeManager(databaseGargoyle, nodeM, adminLogManager);
        databaseGargoyle.attachManager(nodeM);
        databaseGargoyle.attachManager(edgeM);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        Node n1 = nodeM.getNode("GHALL01602");
        Node n2 = nodeM.getNode("GHALL01002");
        Dijkstra Dijkstra = new Dijkstra(edgeM);
        ArrayList<Node> answer = Dijkstra.pathFind(n1,n2);
        ArrayList<Node> expected;
        //System.out.println(answer);
        ArrayList<String> ansID = new ArrayList<>();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);

        n1 = nodeM.getNode("GHALL01601");
        n2 = nodeM.getNode("GELEV00N01");
        Dijkstra = new Dijkstra(edgeM);
        answer = Dijkstra.pathFind(n1,n2);
        ansID.clear();
        for(int i = 0; i < answer.size(); i++){
            ansID.add(answer.get(i).getNodeID());
        }
        System.out.println(ansID);
    }
}