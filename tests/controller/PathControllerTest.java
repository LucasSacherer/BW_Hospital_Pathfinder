package controller;

import Pathfinding.Astar;
import Database.EdgeManager;
import entity.Node;
import Database.NodeManager;
import Pathfinding.PathController;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PathControllerTest {

    @Test
    public void findPathTest(){
        NodeManager nodeM = new NodeManager();
        EdgeManager edgeM = new EdgeManager(nodeM);
        Astar star = new Astar(edgeM);
        PathController pathController = new PathController(star);

        Node n1 = new Node("1",1,1,"1","Shapiro","type","Stairwell","STAI");
        ArrayList<Node> actual = new ArrayList<>();
        List<Node> answer = pathController.findPath(n1,n1);
        System.out.println(answer);
        assertEquals(actual,answer);
        nodeM.removeNode(n1);
    }

    @Test
    public void validatePathTest(){
        Node nodeA = new Node("1", 1, 1, "1", "BuildingA","Type A","Short Name", "1");
        Node nodeB = new Node("1", 1, 1, "1", "BuildingA","Type A","Short Name", "1");
        Node nodeC = new Node("3", 1, 1, "2", "BuildingA","Type A","Short Name", "1");
        Node nodeD = new Node("4", 1, 1, "1", "BuildingB","Type A","Short Name", "1");
        Node nodeE = new Node("5", 1, 1, "1", "BuildingA","Type A","Short Name", "1");
        Node nodeF = new Node("6", 1, 1, "1", "BuildingA","Type A","Short Name", "1");

        NodeManager nodeManager = new NodeManager();
        EdgeManager edgeManager = new EdgeManager(nodeManager);
        Astar astar = new Astar(edgeManager);
        PathController pathController = new PathController(astar);

        /*
        assertEquals(false, pathController.validatePath(nodeA, nodeB));
        assertEquals(false, pathController.validatePath(nodeA, nodeC));
        assertEquals(false, pathController.validatePath(nodeA, nodeD));
        assertEquals(true, pathController.validatePath(nodeA, nodeE));
        assertEquals(true, pathController.validatePath(nodeA, nodeF));
        */




    }



}