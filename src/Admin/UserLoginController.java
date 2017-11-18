package Admin;

import Database.UserManager;
import Entity.User;

public class UserLoginController {
    UserManager userManager = new UserManager();

    /**
     * Validates the username and password, and then gives them to the userManager to authenticate an STAFF
     * @param username
     * @param password
     * @return
     */
    public Boolean authenticateStaff(String username, String password){
        //Check that the username and password are formatted correctly
        //TODO

        //If correct, authenticate them with the UserManager
        return userManager.authenticateStaff(username, password);
    }
    /**
     * Validates the username and password, and then gives them to the userManager to authenticate an ADMIN
     * @param username
     * @param password
     * @return
     */
    public Boolean authenticateAdmin(String username, String password){
        //Check that the username and password are formatted correctly
        //TODO

        //If correct, authenticate them with the UserManager
        return userManager.authenticateAdmin(username, password);
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
}
