package Entity;

import Database.EdgeManager;
import Database.NodeManager;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EdgeManagerTest {

    @Test
    public void testValidity() throws Exception {
        assertTrue(true);
        assertFalse(false);
    }

    @Test
    public void testGetNeighbors() throws Exception {

        Node n1 = new Node("1", 1, 1, "1", "test", "type","lName", "sName");
        Node n2 = new Node("2", 1, 1, "1", "test","type", "lName", "sName");
        Node n3 = new Node("3", 1, 1, "1", "test","type", "lName", "sName");
        Node n4 = new Node("4", 1, 1, "1", "test", "type", "lName", "sName");
        Edge e1 = new Edge(n2, n4);
        Edge e2 = new Edge(n1, n3);

        NodeManager manager = new NodeManager();
        manager.updateNodes();

        Node n0 = manager.getNode("GHALL00402");

        EdgeManager test = new EdgeManager(manager);
        test.updateEdges();

        manager.addNode(n1);
        manager.addNode(n2);
        manager.addNode(n3);
        manager.addNode(n4);
        test.addEdge(e1);
        test.addEdge(e2);

        List<Node> result = test.getNeighbors(n1);
        List<Node> result2 = test.getNeighbors(n3);

        List<Node> result0 = test.getNeighbors(n0);

        test.removeEdge(e1);
        test.removeEdge(e2);
        manager.removeNode(n1);
        manager.removeNode(n2);
        manager.removeNode(n3);
        manager.removeNode(n4);

        assertEquals(result.get(0).getNodeID(), n3.getNodeID());
        assertEquals(result2.get(0).getNodeID(), n1.getNodeID());
        System.out.println(result0);
        System.out.println(result0.size());
    }

    @Test
    public void testGetEdge(){
        NodeManager nodeManager = new NodeManager();
        EdgeManager edgeManager = new EdgeManager(nodeManager);
        nodeManager.updateNodes();
        edgeManager.updateEdges();

        Node testnode1 = nodeManager.getNode("GHALL001L2");
        Node testnode2 = nodeManager.getNode("GLABS003L2");
        Edge testEdge1 = edgeManager.getEdge(testnode1,testnode2);
        Edge testEdge2 = edgeManager.getEdge(testnode2,testnode1);

        assertEquals(testEdge1.getStartNode().getNodeID().equals("GHALL001L2") && testEdge1.getEndNode().getNodeID().equals("GLABS003L2"), true);
        assertEquals(testEdge2.getStartNode().getNodeID().equals("GHALL001L2") && testEdge2.getEndNode().getNodeID().equals("GLABS003L2"), true);
        assertEquals(testEdge1.getStartNode().getNodeID().equals("GLABS003L2") && testEdge1.getEndNode().getNodeID().equals("GHALL001L2"),false);
        assertEquals(testEdge1.getStartNode().getNodeID().equals("Wrong") && testEdge1.getEndNode().getNodeID().equals("GLABS003L2"), false);
        assertEquals(testEdge1.getStartNode().getNodeID().equals("GHALL001L2") && testEdge1.getEndNode().getNodeID().equals("Wrong"), false);

    }

    @Test
    public void testEdgeWeight() throws Exception {

        Node n1 = new Node("1", 1, 1, "1", "test","type", "lName", "sName");
        Node n2 = new Node("2", 1, 1, "1", "test","type", "lName", "sName");
        Node n3 = new Node("3", 1, 4, "1", "test","type", "lName", "sName");
        Node n4 = new Node("4", 1, 3, "1", "test","type", "lName", "sName");
        Edge e1 = new Edge(n2, n3);
        Edge e2 = new Edge(n1, n3);
        Edge e3 = new Edge(n2, n4);

        NodeManager manager = new NodeManager();
        manager.updateNodes();
        EdgeManager test = new EdgeManager(manager);
        test.updateEdges();

        manager.addNode(n1);
        manager.addNode(n2);
        manager.addNode(n3);
        manager.addNode(n4);
        test.addEdge(e1);
        test.addEdge(e2);
        test.addEdge(e3);

        double weight = test.edgeWeight(n2, n4);
        double weight2 = test.edgeWeight(n4, n2);

        test.removeEdge(e1);
        test.removeEdge(e2);
        test.removeEdge(e3);
        manager.removeNode(n1);
        manager.removeNode(n2);
        manager.removeNode(n3);
        manager.removeNode(n4);

        assertEquals(weight, 2.0, .1);
        assertEquals(weight2, 2.0, .1);
    }
    @Test
    public void testRemoveNeighborEdges() throws Exception {

        Node n1 = new Node("1", 1, 1, "1", "test", "type","lName", "sName");
        Node n2 = new Node("2", 2, 1, "1", "test","type", "lName", "sName");
        Node n3 = new Node("3", 1, 2, "1", "test","type", "lName", "sName");
        Edge e1 = new Edge(n1, n2);
        Edge e2 = new Edge(n1, n3);

        NodeManager manager = new NodeManager();
        manager.updateNodes();
        EdgeManager edgeManager = new EdgeManager(manager);
        edgeManager.updateEdges();

        manager.addNode(n1);
        manager.addNode(n2);
        manager.addNode(n3);
        edgeManager.addEdge(e1);
        edgeManager.addEdge(e2);

        List<Node> n1NeighborsBefore = edgeManager.getNeighbors(n1);

        edgeManager.removeNeighborEdges(n1);

        List<Node> n1Neighbors = edgeManager.getNeighbors(n1);

        assertEquals(n1Neighbors.isEmpty(),true);

        edgeManager.removeEdge(e1);
        edgeManager.removeEdge(e2);
        manager.removeNode(n1);
        manager.removeNode(n2);
        manager.removeNode(n3);


    }
}