package Database;

import Entity.User;
import java.util.List;

public class UserManager {
    private List<User> users;

    /**
     * Returns true if there exists an ADMIN with the given credentials, false if there is no user.
     * @param username
     * @param password
     * @return
     */
    public Boolean authenticateAdmin(String username, String password){
        updateUsers();
        for (User user: users){
            if (user.getAdminFlag()==true){
                if (user.getUsername()==username && user.getPassword()==password){
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
            if (user.getAdminFlag()==true){
                if (user.getUsername()==username && user.getPassword()==password){
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
        //TODO reload all users in List<User> users from the database
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
