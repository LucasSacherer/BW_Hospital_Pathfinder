package boundary.sceneControllers;

import Entity.User;

public class StaffRequestHubController {
    private User user;

    public void serviceHubtoAPITest() { }

    public void serviceHubtoFoodAPI() { }

    public String getUserName() {
        return user.username;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
