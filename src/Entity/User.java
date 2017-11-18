package Entity;

public class User {
    public String username;
    public String password;
    public Boolean adminFlag;
    public String department;

    public User(String username, String password, Boolean adminFlag, String department) {
        this.username = username;
        this.password = password;
        this.adminFlag = adminFlag;
        this.department = department;
    }
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
