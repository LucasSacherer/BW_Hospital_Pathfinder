package Entity;

import java.time.LocalDateTime;

public class AdminLog {
    private User user;
    private String action;
    private LocalDateTime time;

    public AdminLog(User user, String action, LocalDateTime time) {
        this.user = user;
        this.action = action;
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public String getAction() {
        return action;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
