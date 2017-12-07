package Entity;

public class User {
    private String userID;
    private String username;
    private String password;
    private Boolean adminFlag;
    private String department;

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

    @Override
    public String toString() {
        return "UserID: " + userID + ", Username: " + username  + ", Department: "+ department + (adminFlag ? " [Administrator]" : "");
    }
}
