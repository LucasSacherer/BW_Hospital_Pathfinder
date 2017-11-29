package Entity;

import java.time.LocalDateTime;

public class CleanUpRequest implements Request{
    final private String name;
    final private LocalDateTime timeCreated;
    final private LocalDateTime timeCompleted;
    final private String type;
    final private String description;
    final private Node node;
    final private User user;


    public CleanUpRequest (String name, LocalDateTime timeCreated, LocalDateTime timeCompleted, String type,
                           String description, Node node, User user){
        this.name = name;
        this.timeCreated = timeCreated;
        this.timeCompleted = timeCompleted;
        this.type = type;
        this.description = description;
        this.node = node;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public LocalDateTime getTimeCompleted() {
        return timeCompleted;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Node getNode() {
        return node;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Clean Up:    " + name + "    " + timeCreated.getMonth() + " " + timeCreated.getDayOfMonth() + " " + timeCreated.getHour() + ":" + timeCreated.getMinute() + ":" + timeCreated.getSecond();
    }

    public String getRequestReport(){
        return "Name: " + name + "\n\nDescription: " + description;
    }
}
