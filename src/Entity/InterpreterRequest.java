package Entity;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.time.LocalDateTime;

public class InterpreterRequest extends RecursiveTreeObject<InterpreterRequest> implements Request {
    final private String name;
    final private LocalDateTime timeCreated;
    final private LocalDateTime timeCompleted;
    final private String type;
    final private String description;
    final private Node node;
    final private User user;

    final private String language;

    public InterpreterRequest (String name, LocalDateTime timeCreated, LocalDateTime timeCompleted, String type,
                               String description, Node node, User user, String language){
        this.name = name;
        this.timeCreated = timeCreated;
        this.timeCompleted = timeCompleted;
        this.type = type;
        this.description = description;
        this.node = node;
        this.user = user;

        this.language = language;
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

    public String getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return "Interpreter:    " + name + "    " + timeCreated.getMonth() + " " + timeCreated.getDayOfMonth() + " " + timeCreated.getHour() + ":" + timeCreated.getMinute() + ":" + timeCreated.getSecond();
    }

    public String getRequestReport(){
        return "Name:\t" + name + "\n\nDescription: "+ description + "\n\nLanguage: " + language;
    }
}
