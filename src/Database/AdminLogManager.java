package Database;

import DatabaseSetup.DatabaseGargoyle;
import Entity.AdminLog;
import Entity.User;

import java.util.ArrayList;
import java.util.List;

public class AdminLogManager {

    private final List<AdminLog> adminlogs;
    private final DatabaseGargoyle databaseGargoyle;
    private final UserManager userManager;

    public AdminLogManager(UserManager userManager) {
        this.adminlogs = new ArrayList<>();
        this.databaseGargoyle = new DatabaseGargoyle();
        this.userManager = userManager;
    }



    public void logAction(String aciton, User user){

    }
}
