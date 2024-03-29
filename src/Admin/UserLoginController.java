package Admin;

import Database.UserManager;
import Entity.User;

public class UserLoginController {
    final private UserManager userManager;

    public UserLoginController(UserManager um){
        userManager = um;
    }

    /**
     * Validates the username and password, and then gives them to the userManager to authenticate an STAFF
     * @param username
     * @param password
     * @return
     */
    public Boolean authenticateStaff(String username, String password){
        //no comma, '. " .
        boolean formatted = checkFormat(username,password);
        if(formatted) {
            return userManager.authenticateStaff(username, password);
        }
        else return false;
    }
    /**
     * Validates the username and password, and then gives them to the userManager to authenticate an ADMIN
     * @param username
     * @param password
     * @return
     */
    public Boolean authenticateAdmin(String username, String password){
        boolean formatted = checkFormat(username,password);
        if(formatted) {
            return userManager.authenticateAdmin(username, password);
        }
        else return false;
    }
    /**
     * Passes the new user to the userManager to add it to the database
     * @param newUser
     */
    public void addUser(User newUser){
        userManager.addUser(newUser);
    }
    /**
     * Passes the old user to the userManager to delete it from the database
     * @param oldUser
     */
    public void removeUser(User oldUser){
        userManager.removeUser(oldUser);
    }

    /**
     * Passes an update user to the userManaged to update it in the database
     * @param updatedUser
     */
    public void modifyUser(User updatedUser){
        userManager.modifyUser(updatedUser);
    }

    public boolean checkFormat(String username, String password){
        if ((username.contains(","))|| username.contains(".") || username.contains("'") || username.contains("/")){
            return false;
        }
        else if ((password.contains(","))|| password.contains(".") || password.contains("'") || password.contains("/")) {
            return false;
        }
        else return true;
    }
}
