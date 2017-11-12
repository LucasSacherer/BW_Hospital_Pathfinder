package controller;

import entity.Node;
import org.junit.Test;

import static org.junit.Assert.*;

public class PathControllerTest {

    //@Test
    //public void findPathTest(){

    //}

    @Test
    public void validatePathTest(){
        Node nodeA = new Node("1", 1, 1, "1", "BuildingA","Type A","Short Name", "1",true);
        Node nodeB = new Node("1", 1, 1, "1", "BuildingA","Type A","Short Name", "1",true);
        Node nodeC = new Node("3", 1, 1, "2", "BuildingA","Type A","Short Name", "1",true);
        Node nodeD = new Node("4", 1, 1, "1", "BuildingB","Type A","Short Name", "1",true);
        Node nodeE = new Node("5", 1, 1, "1", "BuildingA","Type A","Short Name", "1",false);
        Node nodeF = new Node("6", 1, 1, "1", "BuildingA","Type B","Short Name", "1",true);
        Node nodeG = new Node("6", 1, 1, "1", "BuildingA","Type A","Short Name", "1",true);



        assertEquals(false, PathController.validatePath(nodeA, nodeB));
        assertEquals(false, PathController.validatePath(nodeA, nodeC));
        assertEquals(false, PathController.validatePath(nodeA, nodeD));
        assertEquals(false, PathController.validatePath(nodeA, nodeE));
        assertEquals(false, PathController.validatePath(nodeA, nodeF));
        assertEquals(true, PathController.validatePath(nodeA, nodeG));





    }



}