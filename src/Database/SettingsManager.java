package Database;

import DatabaseSetup.DatabaseGargoyle;
import Editor.NodeEditController;
import Entity.Node;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SettingsManager {

    private HashMap<String, String> settings;
    private DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();


    public SettingsManager(HashMap<String, String> settings){
        this.settings = settings;
    }



    public void setSetting(String setting, String nodeID){
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("UPDATE SETTINGS SET STRING2 = '"+nodeID+"' WHERE STRING1 = '"+
                setting+"'", databaseGargoyle.getStatement());
        databaseGargoyle.destroyConnection();

        updateSettings();
    }

    public void updateSettings(){
        settings.clear();

        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM SETTINGS", databaseGargoyle.getStatement());
        try {
            while(rs.next()){
                settings.put(rs.getString("STRING1"), rs.getString("STRING2"));
            }
        } catch (SQLException e) {
            System.out.println("Failed to update the settings manager!");
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
    }

    public String getSetting(String string){
        return settings.get(string);
    }
}
