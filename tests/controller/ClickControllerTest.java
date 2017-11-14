package controller;

import entity.Node;
import entity.NodeManager;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClickControllerTest {
    @Test
    public void getNearestNode(){
        NodeManager manager = new NodeManager();
        ClickController clickController = new ClickController(manager);
        manager.updateNodes();
        Node test = new Node("1",50,0,"1","building","type","lName","sName",true);
        Node test2 = new Node("2",99, 99,"1","building","type","lName","sName",true);
        Node test3 = new Node("3",2,2,"1","building","type","lName","sName",true);
        Node test4 = new Node("4",5,5,"1","building","type","lName","sName",true);
        manager.addNode(test);
        manager.addNode(test2);
        manager.addNode(test3);
        manager.addNode(test4);
        String nodeID = clickController.getNearestNode(0, 0).getNodeID();

        manager.removeNode(test);
        manager.removeNode(test2);
        manager.removeNode(test3);
        manager.removeNode(test4);

        assertEquals(test3.getNodeID(),nodeID);
    }

}