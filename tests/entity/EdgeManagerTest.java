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

        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();
        Node n4 = new Node();
        Edge e1 = new Edge(n2, n4, 1);
        Edge e2 = new Edge(n1, n3, 1);

        EdgeManager test = new EdgeManager();
        test.addEdge(e1);
        test.addEdge(e2);

        List<Edge> result = test.getNeighbors(n1);

        assertEquals(result.get(0), e2);
    }

    @Test
    public void testEdgeWeight() throws Exception {

        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();
        Node n4 = new Node();
        Edge e1 = new Edge(n2, n3, 1);
        Edge e2 = new Edge(n1, n3, 3);
        Edge e3 = new Edge(n2, n4, 2);
        Edge eDummy = new Edge(n2, n4, 99);

        EdgeManager test = new EdgeManager();
        test.addEdge(eDummy);
        test.addEdge(e1);
        test.addEdge(e2);
        test.addEdge(e3);

        test.removeEdge(eDummy);

        assertEquals(test.edgeWeight(n2, n4), 2.0, .1);
    }
}