package Database;

import DatabaseSetup.DatabaseGargoyle;
import Entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserManager {
    private ArrayList<User> users = new ArrayList<>();
    private DatabaseGargoyle databaseGargoyle = new DatabaseGargoyle();

    /**
     * Returns true if there exists an ADMIN with the given credentials, false if there is no user.
     * @param username
     * @param password
     * @return
     */
    public Boolean authenticateAdmin(String username, String password){
        updateUsers();
        for (User user: users){
            if (user.getUsername()==username && user.getPassword()==password){
                if (user.getAdminFlag()==true){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there exists a STAFF with the given credentials, false if there s no user
     * @param username
     * @param password
     * @return
     */
    public Boolean authenticateStaff(String username, String password){
        updateUsers();
        for (User user: users){
            if (user.getUsername()==username && user.getPassword()==password){
                if (user.getAdminFlag()==false){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Updates all users in the UserManager's list to be up to date with the database
     */
    public void updateUsers(){
        String userID, userName, password, department;
        Boolean adminFlag;
        users.clear();

        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM KIOSKUSER", databaseGargoyle.getStatement());
        try {
            while (rs.next()){
                userID = rs.getString("USERID");
                userName = rs.getString("USERNAME");
                password = rs.getString("PASSWORD");
                department = rs.getString("DEPARTMENT");
                if (rs.getString("ADMINFLAG") == "true"){
                    adminFlag = true;
                } else adminFlag = false;
                users.add(new User(userID, userName, password, adminFlag, department));
            }
        } catch (SQLException e) {
            System.out.println("Failed to get users from database!");
            e.printStackTrace();
        }
        databaseGargoyle.destroyConnection();
    }

    /**
     * Changes the user in the database with changedUser.userID to contain the new information
     * @param updatedUser
     */
    public void modifyUser(User updatedUser){
        //TODO edit the user in the database with updatedUser's ID to contain updatedUser's new info
    }

    /**
     * Adds the new user to the User table in the database
     * @param newUser
     */
    public void addUser(User newUser){
        //TODO add the newUser to the User table in the database
    }

    /**
     * Removes the oldUser from the User table in the database
     * @param oldUser
     */
    public void removeUser(User oldUser){
        //TODO remove the oldUser from the User table in the database
    }
}
