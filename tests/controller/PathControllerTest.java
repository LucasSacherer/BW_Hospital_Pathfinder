package controller;

import entity.Astar;
import entity.EdgeManager;
import entity.Node;
import entity.NodeManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PathControllerTest {

    @Test
    public void findPathTest(){
        List<Node> testEmptyList1 = new ArrayList<Node>();
        List<Node> testEmptyList2 = new ArrayList<Node>();


        assertArrayEquals(testEmptyList1.toArray(),testEmptyList2.toArray());
    }

    @Test
    public void validatePathTest(){
        Node nodeA = new Node("1", 1, 1, "1", "BuildingA","Type A","Short Name", "1",true);
        Node nodeB = new Node("1", 1, 1, "1", "BuildingA","Type A","Short Name", "1",true);
        Node nodeC = new Node("3", 1, 1, "2", "BuildingA","Type A","Short Name", "1",true);
        Node nodeD = new Node("4", 1, 1, "1", "BuildingB","Type A","Short Name", "1",true);
        Node nodeE = new Node("5", 1, 1, "1", "BuildingA","Type A","Short Name", "1",false);
        Node nodeF = new Node("6", 1, 1, "1", "BuildingA","Type A","Short Name", "1",true);

        NodeManager nodeManager = new NodeManager();
        EdgeManager edgeManager = new EdgeManager(nodeManager);
        Astar astar = new Astar(edgeManager);
        PathController pathController = new PathController(astar);


        assertEquals(false, pathController.validatePath(nodeA, nodeB));
        assertEquals(false, pathController.validatePath(nodeA, nodeC));
        assertEquals(false, pathController.validatePath(nodeA, nodeD));
        assertEquals(false, pathController.validatePath(nodeA, nodeE));
        assertEquals(true, pathController.validatePath(nodeA, nodeF));





    }



}