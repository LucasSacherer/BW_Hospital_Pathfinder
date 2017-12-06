package Database;

import DatabaseSetup.DatabaseGargoyle;
import Entity.Node;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PathfindingLogManagerTest {
    @Test
    public void testAddRemovePathfindingLog(){
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        PathfindingLogManager pathfindingLogManager = new PathfindingLogManager();
        AdminLogManager adminLogManager = new AdminLogManager(databaseGargoyle);
        NodeManager nodeManager = new NodeManager(databaseGargoyle, adminLogManager);
        databaseGargoyle.attachManager(nodeManager);
        databaseGargoyle.attachManager(adminLogManager);
        databaseGargoyle.notifyManagers();

        int originalSize = pathfindingLogManager.getPathfindingLogSize();

        List<Node> path = new ArrayList<>();
        path.add(nodeManager.getNode("GELEV00N02"));
        path.add(nodeManager.getNode("GDEPT02402"));
        path.add(nodeManager.getNode("GHALL01702"));
        path.add(nodeManager.getNode("GHALL01602"));
        path.add(nodeManager.getNode("GHALL01402"));
        path.add(nodeManager.getNode("GHALL01202"));
        path.add(nodeManager.getNode("GHALL01002"));

        pathfindingLogManager.addPathToLog(path);

        //Ensure that the database changed
        assertEquals(originalSize + 7, pathfindingLogManager.getPathfindingLogSize());
    }
}
