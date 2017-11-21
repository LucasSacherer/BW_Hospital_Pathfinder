package entity;

import entity.Node;

import java.time.LocalDateTime;

public class FoodRequest implements Request{
    final private String type;
    final private String name;
    final private String description;
    final private Node node;
    final private LocalDateTime timeStamp;


    public FoodRequest (String type, String name, String description, Node node, LocalDateTime timeStamp){
        this.type = type;
        this.name = name;
        this.description = description;
        this.node = node;
        this.timeStamp = timeStamp;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Node getNode() {
        return node;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return name + "    " + timeStamp.getMonth() + " " + timeStamp.getDayOfMonth() + " " + timeStamp.getHour() + ":" + timeStamp.getMinute() + ":" + timeStamp.getSecond();
    }
}
