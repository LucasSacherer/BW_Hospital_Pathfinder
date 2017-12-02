package Database;

import DatabaseSetup.DatabaseGargoyle;
import Entity.User;
import Request.GenericRequestController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserManager {
    private ArrayList<User> users;
    private DatabaseGargoyle databaseGargoyle;

    public UserManager(DatabaseGargoyle dbG) {
        this.databaseGargoyle = dbG;
        this.users = new ArrayList<>();
    }

    /**
     * Returns true if there exists an ADMIN with the given credentials, false if there is no user.
     * @param username
     * @param password
     * @return
     */
    public Boolean authenticateAdmin(String username, String password){
        updateUsers();
        for (User user: users){
            if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                if (user.getAdminFlag()){
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
            if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                if (!user.getAdminFlag()){
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
        String userID, userName, password, department, tempAdminFlag;
        Boolean adminFlag = null;

        users.clear();
        databaseGargoyle.createConnection();
        ResultSet rs = databaseGargoyle.executeQueryOnDatabase("SELECT * FROM KIOSKUSER", databaseGargoyle.getStatement());
        try {
            while (rs.next()){
                userID = rs.getString("USERID");
                userName = rs.getString("USERNAME");
                password = rs.getString("PASSWORD");
                tempAdminFlag = rs.getString("ADMINFLAG");
                if (tempAdminFlag.equalsIgnoreCase("true")){
                    adminFlag = true;
                } else if (tempAdminFlag.equalsIgnoreCase("false")){
                    adminFlag = false;
                }
                department = rs.getString("DEPARTMENT");
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
        String adminFlag;
        if (updatedUser.getAdminFlag()){
            adminFlag = "true";
        } else adminFlag = "false";
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("UPDATE KIOSKUSER SET " +
                "USERNAME = '" + updatedUser.getUsername() + "'," +
                "PASSWORD = '" + updatedUser.getPassword() + "'," +
                "ADMINFLAG = '" + adminFlag + "'," +
                "DEPARTMENT = '" + updatedUser.getDepartment() + "' WHERE USERID = '" + updatedUser.getUserID() + "'", databaseGargoyle.getStatement());
        databaseGargoyle.destroyConnection();
        updateUsers();
    }

    /**
     * Adds the new user to the User table in the database
     * @param newUser
     */
    public void addUser(User newUser){
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("INSERT INTO KIOSKUSER VALUES ('"+ newUser.getUserID()+"','"+newUser.getUsername()+"','"+
                newUser.getPassword()+"','"+newUser.getAdminFlag().toString()+"','"+newUser.getDepartment()+"')", databaseGargoyle.getStatement());
        databaseGargoyle.destroyConnection();
        updateUsers();
    }

    /**
     * Removes the oldUser from the User table in the database
     * @param oldUser
     */
    public void removeUser(User oldUser){
        databaseGargoyle.createConnection();
        databaseGargoyle.executeUpdateOnDatabase("DELETE FROM KIOSKUSER WHERE userID = '" + oldUser.getUserID() + "'", databaseGargoyle.getStatement());
        databaseGargoyle.destroyConnection();
        updateUsers();
    }

    /**
     * Returns the user from the given userID
     * @param userID
     * @return
     */
    public User getUser(String userID){
        for (User user: this.users){
            if (user.getUserID().equals(userID)){
                return user;
            }
        }
        return null;
    }

    /**
     * Returns the user from the given userName
     * @param userName
     * @return
     */
    public User getUserByName(String userName){
        for (User user: this.users){
            if (user.getUsername().equals(userName)){
                return user;
            }
        }
        return null;
    }

    /**
     * Returns the user list as an ObservableList
     * @return
     */
    public ObservableList getUsers() {
        ObservableList fxUsers = FXCollections.observableArrayList();
        fxUsers.addAll(this.users);
        updateUsers();
        return fxUsers;
    }
}