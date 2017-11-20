package Entity;

public class User {
    public String userID;
    public String username;
    public String password;
    public Boolean adminFlag;
    public String department;

    public User(String userID, String username, String password, Boolean adminFlag, String department) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.adminFlag = adminFlag;
        this.department = department;
    }
    public String getUserID() { return userID; }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public Boolean getAdminFlag() {
        return adminFlag;
    }
    public String getDepartment() {
        return department;
    }
}
