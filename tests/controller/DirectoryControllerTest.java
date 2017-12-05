package controller;

import Database.SettingsManager;
import DatabaseSetup.DatabaseGargoyle;
import Entity.Node;
import MapNavigation.DirectoryController;
import Database.NodeManager;
import MapNavigation.DirectoryController;
import org.junit.Test;
import MapNavigation.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DirectoryControllerTest {
    DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
    @Test
    public void testFormatNodeList() throws Exception {
        List<Node> nodes = new ArrayList<>();
        NodeManager nm = new NodeManager(databaseGargoyle);
        DirectoryController dc = new DirectoryController(nm);

        Node n1 = new Node("elev1", 1, 1, "1", "1", "ELEV", "1", "1");
        Node n2 = new Node("elev2", 1, 1, "1", "1", "ELEV", "1", "1");
        Node n3 = new Node("elev3", 1, 1, "1", "1", "ELEV", "1", "1");
        ArrayList<Node> elev = new ArrayList<>();
        elev.add(n1);
        elev.add(n2);
        elev.add(n3);
        nodes.add(n1);
        nodes.add(n2);
        nodes.add(n3);

        Node n4 = new Node("stai1", 1, 1, "1", "1", "STAI", "1", "1");
        Node n5 = new Node("stai2", 1, 1, "1", "1", "STAI", "1", "1");
        Node n6 = new Node("stai3", 1, 1, "1", "1", "STAI", "1", "1");
        ArrayList<Node> stai = new ArrayList<>();
        stai.add(n4);
        stai.add(n5);
        stai.add(n6);
        nodes.add(n4);
        nodes.add(n5);
        nodes.add(n6);

        Node n7 = new Node("exit1", 1, 1, "1", "1", "EXIT", "1", "1");
        Node n8 = new Node("exit2", 1, 1, "1", "1", "EXIT", "1", "1");
        Node n9 = new Node("exit3", 1, 1, "1", "1", "EXIT", "1", "1");
        Node n10 = new Node("exit4", 1, 1, "1", "1", "EXIT", "1", "1");
        ArrayList<Node> exit = new ArrayList<>();
        exit.add(n7);
        exit.add(n8);
        exit.add(n9);
        exit.add(n10);
        nodes.add(n7);
        nodes.add(n8);
        nodes.add(n9);
        nodes.add(n10);

        assertEquals(dc.formatNodeListTester(nodes).get("Elevators"), elev);

        assertEquals(dc.formatNodeListTester(nodes).get("Stairs"), stai);

        assertEquals(dc.formatNodeListTester(nodes).get("Exits/Entrances"), exit);
    }
}
