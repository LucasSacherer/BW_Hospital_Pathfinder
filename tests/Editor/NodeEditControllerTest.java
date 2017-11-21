package Editor;

import Database.NodeManager;
import Database.SettingsManager;
import Entity.Node;
import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class NodeEditControllerTest {

    @Test
    public void AddRemoveEditNode() throws Exception {
        NodeManager nManager = new NodeManager();
        SettingsManager sManager = new SettingsManager();
        NodeEditController editor = new NodeEditController(nManager, sManager);

        nManager.updateNodes();
        Node test = new Node("1",1,1,"1","building","type","lName","sName");
        Node test2 = new Node("2",2, 2,"1","building","type","lName","sName");
        Node test3 = new Node("3",5,5,"1","building","bathroom","lName","sName");
        Node test4 = new Node("4",3,3,"1","building","bathroom","lName","sName");
        editor.addNode(test);
        editor.addNode(test2);
        editor.addNode(test3);
        editor.addNode(test4);

        assertEquals(nManager.getNode("1").getNodeID(), test.getNodeID());
        assertEquals(nManager.getNode("2").getNodeID(), test2.getNodeID());
        assertEquals(nManager.getNode("3").getNodeID(), test3.getNodeID());
        assertEquals(nManager.getNode("4").getNodeID(), test4.getNodeID());

        Node testEdit = new Node("1",1,1,"1","monkey","type","lName","sName");


        editor.editNode(testEdit);

        assertEquals(nManager.getNode("1").getBuilding(), "monkey");

        editor.deleteNode(test);
        editor.deleteNode(test2);
        editor.deleteNode(test3);
        editor.deleteNode(test4);
        editor.deleteNode(testEdit);
    }

    @Test
    public void setKioskTest() throws Exception {
        Node test = new Node("1",1,1,"1","building","type","lName","sName");

        NodeManager nManager = new NodeManager();
        nManager.updateNodes();
        SettingsManager sManager = new SettingsManager();
        NodeEditController editor = new NodeEditController(nManager, sManager);

        editor.setKioskLocation(test);

        assertEquals(editor.settingsManager.getSetting("Default Node"), test.getNodeID());
        editor.setKioskLocation(nManager.getNode("GHALL03802"));
    }
}