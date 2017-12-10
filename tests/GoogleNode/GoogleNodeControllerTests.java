package GoogleNode;

import Database.GoogleNodeManager;
import DatabaseSetup.DatabaseGargoyle;
import GoogleNodes.GoogleNodeController;
import org.junit.Test;
import static org.junit.Assert.*;

public class GoogleNodeControllerTests {

    @Test
    public void testGetAllNodes() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        GoogleNodeManager googleNodeManager = new GoogleNodeManager(databaseGargoyle);
        GoogleNodeController googleNodeController = new GoogleNodeController(googleNodeManager);

        int originalSize = googleNodeManager.getGoogleNodes().size();

        assertTrue(googleNodeController.getGoogleNodes().size() != 0);
        assertEquals(originalSize, googleNodeController.getGoogleNodes().size());
    }

    @Test
    public void testGetNodesByFloor() {
        DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();
        GoogleNodeManager googleNodeManager = new GoogleNodeManager(databaseGargoyle);
        GoogleNodeController googleNodeController = new GoogleNodeController(googleNodeManager);

        assertTrue(googleNodeController.getGoogleNodeByFloor("1").size() != 0);
    }
}
