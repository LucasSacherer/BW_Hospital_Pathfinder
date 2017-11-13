package entity;

import org.junit.Test;

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

        Node n1 = new Node("1", 1, 1, "1", "test", "type","lName", "sName",true);
        Node n2 = new Node("2", 1, 1, "1", "test","type", "lName", "sName",true);
        Node n3 = new Node("3", 1, 1, "1", "test","type", "lName", "sName", true);
        Node n4 = new Node("4", 1, 1, "1", "test", "type", "lName", "sName", true);
        Edge e1 = new Edge(n2, n4);
        Edge e2 = new Edge(n1, n3);

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

        List<Node> result = test.getNeighbors(n1);

        assertEquals(result.get(0).getNodeID(), n3.getNodeID());
        test.removeEdge(e1);
        test.removeEdge(e2);
        manager.removeNode(n1);
        manager.removeNode(n2);
        manager.removeNode(n3);
        manager.removeNode(n4);

        assertEquals(result.get(0).getStartNode().getNodeID(), e2.getStartNode().getNodeID());
        assertEquals(result.get(0).getEndNode().getNodeID(), e2.getEndNode().getNodeID());
    }

    @Test
    public void testEdgeWeight() throws Exception {

        Node n1 = new Node("1", 1, 1, "1", "test","type", "lName", "sName", true);
        Node n2 = new Node("2", 1, 1, "1", "test","type", "lName", "sName", true);
        Node n3 = new Node("3", 1, 4, "1", "test","type", "lName", "sName", true);
        Node n4 = new Node("4", 1, 3, "1", "test","type", "lName", "sName", true);
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
}