package Admin;

public class UserLoginController {
    UserManager userManager = new UserManager();

    public Boolean authenticateStaff(String username, String password){
        //Check that the username and password are formatted correctly
        //TODO

        //If correct, authenticate them with the UserManager
        return userManager.authenticateStaff(username, password);
    }
    public Boolean authenticateAdmin(String username, String password){
        //Check that the username and password are formatted correctly
        //TODO

        //If correct, authenticate them with the UserManager
        return userManager.authenticateAdmin(username, password);
    }
}
