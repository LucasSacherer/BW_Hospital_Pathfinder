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
    public void updateUsers(){

    }
}
