package MapNavigation;

import Database.AdminLogManager;
import Database.NodeManager;
import Database.SettingsManager;
import DatabaseSetup.DatabaseGargoyle;
import Entity.AdminLog;
import Entity.Node;
import org.junit.Test;


import static org.junit.Assert.*;

public class MapNavigationFacadeTest {

    @Test
    public void getNearestNode() throws Exception {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager manager = new NodeManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(manager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        ClickController clickController = new ClickController(manager);
        MapDisplayController mapDisplayController = new MapDisplayController();
        DirectoryController directoryController = new DirectoryController(manager);
        NearestPOIController nearestPOIController = new NearestPOIController(manager);
        MapNavigationFacade mapNavigationFacade = new MapNavigationFacade(clickController,nearestPOIController,mapDisplayController,directoryController);

        Node test = new Node("1",50,0,"1","building","type","lName","sName");
        Node test2 = new Node("2",99, 99,"1","building","type","lName","sName");
        Node test3 = new Node("3",2,2,"1","building","type","lName","sName");
        Node test4 = new Node("4",0,0,"1","building","type","lName","sName");
        manager.addNode(test);
        manager.addNode(test2);
        manager.addNode(test3);
        manager.addNode(test4);
        String nodeID = mapNavigationFacade.getNearestNode(3, 3, "1").getNodeID();

        manager.removeNode(test);
        manager.removeNode(test2);
        manager.removeNode(test3);
        manager.removeNode(test4);

        assertEquals(test3.getNodeID(),nodeID);
    }

    @Test
    public void getNearestPOI() throws Exception {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager manager = new NodeManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(manager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        ClickController clickController = new ClickController(manager);
        MapDisplayController mapDisplayController = new MapDisplayController();
        DirectoryController directoryController = new DirectoryController(manager);
        NearestPOIController nearestPOIController = new NearestPOIController(manager);
        MapNavigationFacade mapNavigationFacade = new MapNavigationFacade(clickController,nearestPOIController,mapDisplayController,directoryController);

        Node test = new Node("1",10,10,"1","building","Rest","lName","sName");
        Node test2 = new Node("2",15, 15,"2","building","Rest","lName","sName");
        Node test3 = new Node("3",9,9,"1","building","Elev","lName","sName");
        Node test4 = new Node("4",0,0,"1","building","type","lName","sName");
        manager.addNode(test);
        manager.addNode(test2);
        manager.addNode(test3);
        manager.addNode(test4);
        String nodeID1 = mapNavigationFacade.getNearestPOI(8, 8, "Rest").getNodeID();
        String nodeID2 = mapNavigationFacade.getNearestPOI(14, 14, "Rest").getNodeID();
        String nodeID3 = mapNavigationFacade.getNearestPOI(15, 15, "Elev").getNodeID();
        String nodeID4 = mapNavigationFacade.getNearestPOI(11, 11,"Rest").getNodeID();

        manager.removeNode(test);
        manager.removeNode(test2);
        manager.removeNode(test3);
        manager.removeNode(test4);

        assertEquals(test.getNodeID(),nodeID1);
        assertEquals(test2.getNodeID(),nodeID2);
        assertEquals(test3.getNodeID(),nodeID3);
        assertEquals(test.getNodeID(),nodeID4);
    }
/*
    @Test
    public void getFloorMap() throws Exception {
            MapDisplayController newMap = new MapDisplayController();
            File file1 = new File("\\boundary\\images\\DefaultMaps\\00_thegroundfloor.png");
            Image imageG = new Image(new FileInputStream(file1));
            assertEquals(newMap.getMap(), 2);

    }
*/
    @Test
    public void getDirectory() throws Exception {
    }

    @Test
    public void getDefaultNode() throws Exception {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager manager = new NodeManager(databaseGargoyle, adminLogManager);
        SettingsManager settingsManager = SettingsManager.getInstance();
        databaseGargoyle.attachManager(manager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        ClickController clickController = new ClickController(manager);
        MapDisplayController mapDisplayController = new MapDisplayController();
        DirectoryController directoryController = new DirectoryController(manager);
        NearestPOIController nearestPOIController = new NearestPOIController(manager);
        MapNavigationFacade mapNavigationFacade = new MapNavigationFacade(clickController,nearestPOIController,mapDisplayController,directoryController);

        System.out.println(settingsManager.getSetting("Default Node"));
        System.out.println(manager.getNode(settingsManager.getSetting("Default Node")));

        assertEquals("GHALL03802", mapNavigationFacade.getDefaultNode().getNodeID());
    }

}