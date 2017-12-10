package GoogleNodes;

import Database.GoogleNodeManager;
import Entity.GoogleNode;

import java.util.ArrayList;
import java.util.List;

public class GoogleNodeController {
    private GoogleNodeManager googleNodeManager;

    public GoogleNodeController(GoogleNodeManager googleNodeManager) {
        this.googleNodeManager = googleNodeManager;
    }

    /**
     * Returns all google nodes in the database
     * @return
     */
    public List<GoogleNode> getGoogleNodes(){
        return googleNodeManager.getGoogleNodes();
    }

    /**
     * Returns all google nodes in the database that are on the specified floor
     * @param floor
     * @return
     */
    public List<GoogleNode> getGoogleNodeByFloor(String floor){
        ArrayList<GoogleNode> results = new ArrayList<>();
        for (GoogleNode node: googleNodeManager.getGoogleNodes()){
            if (node.getFloor().equalsIgnoreCase(floor)){
                results.add(node);
            }
        }
        return results;
    }
}
