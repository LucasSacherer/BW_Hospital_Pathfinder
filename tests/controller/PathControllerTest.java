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
        Node nodeB = new Node("1", 1, 1, "2", "BuildingB","Type A","Short Name", "1",true);
        Node nodeC = new Node("2", 1, 1, "3", "BuildingA","Type A","Short Name", "1",true);
        Node nodeD = new Node("3", 1, 1, "1", "BuildingC","Type A","Short Name", "1",true);
        Node nodeE = new Node("4", 1, 1, "4", "BuildingD","Type A","Short Name", "1",true);

        assertEquals(PathController.validatePath(nodeA, nodeE),true);
        assertEquals(PathController.validatePath(nodeA, nodeB), false);
        assertEquals(PathController.validatePath(nodeA, nodeC), false);
        assertEquals(PathController.validatePath(nodeA, nodeD), false);




    }



}