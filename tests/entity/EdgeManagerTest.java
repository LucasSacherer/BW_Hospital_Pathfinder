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

        EdgeManager test = new EdgeManager();
        test.addEdge(e1);
        test.addEdge(e2);

        List<Edge> result = test.getNeighbors(n1);

        assertEquals(result.get(0), e2);
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

        EdgeManager test = new EdgeManager();
        test.addEdge(e1);
        test.addEdge(e2);
        test.addEdge(e3);

        assertEquals(test.edgeWeight(n2, n4), 2.0, .1);
    }
}