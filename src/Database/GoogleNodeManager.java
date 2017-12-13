package Database;

import DatabaseSetup.DatabaseGargoyle;
import Entity.GoogleNode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoogleNodeManager {
    private List<GoogleNode> googleNodes;
    private DatabaseGargoyle databaseGargoyle;

    public GoogleNodeManager(DatabaseGargoyle databaseGargoyle) {
        this.googleNodes = new ArrayList<>();
        this.databaseGargoyle = databaseGargoyle;
    }

    /**
     * Returns all google nodes in the database
     * If they have already been loaded, don't query database
     * @return
     */
    public List<GoogleNode> getGoogleNodes(){
        if (googleNodes.size() != 0){
            return googleNodes;
        }
        else {
            databaseGargoyle.createConnection();
            ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM GOOGLENODE");
            try {
                while (rs.next()){
                    String name = rs.getString("NAME");
                    String url = rs.getString("URL");
                    int xcoord = rs.getInt("XCOORD");
                    int ycoord = rs.getInt("YCOORD");
                    String floor = rs.getString("FLOOR");
                    googleNodes.add(new GoogleNode(name,url,xcoord,ycoord,floor));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return googleNodes;
        }
    }
}
