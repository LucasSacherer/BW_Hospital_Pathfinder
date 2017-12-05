package Database;

import DatabaseSetup.DatabaseGargoyle;
import Editor.NodeEditController;
import Entity.Node;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SettingsManager {

    private HashMap<String, String> settings;
    private DatabaseGargoyle databaseGargoyle;


    public SettingsManager(DatabaseGargoyle dbG){
        databaseGargoyle = dbG;
        settings = new HashMap<>();
    }



    public void setSetting(String setting, String subject){
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("UPDATE SETTINGS SET STRING2 = '"+subject+"' WHERE STRING1 = '"+
                setting+"'");
        databaseGargoyle.destroyConnection();

        updateSettings();
    }

    public void updateSettings(){
        settings.clear();

        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM SETTINGS");
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
