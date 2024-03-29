package Entity;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.time.LocalDateTime;
import java.util.List;

public class FoodRequest extends RecursiveTreeObject<FoodRequest> implements Request{
    final private String name;
    final private LocalDateTime timeCreated;
    final private LocalDateTime timeCompleted;
    final private String type;
    final private String description;
    final private Node node;
    final private User user;
    final private List<String> order;

    public FoodRequest(String name, LocalDateTime timeCreated, LocalDateTime timeCompleted, String type,
                       String description, Node node, User user, List<String> order) {
        this.name = name;
        this.timeCreated = timeCreated;
        this.timeCompleted = timeCompleted;
        this.type = type;
        this.description = description;
        this.node = node;
        this.user = user;
        this.order = order;
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

    public List<String> getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "Food:    " + name + "    " + timeCreated.getMonth() + " " + timeCreated.getDayOfMonth() + " " + timeCreated.getHour() + ":" + timeCreated.getMinute() + ":" + timeCreated.getSecond();
    }

    public String getRequestReport(){
        String result = "Name: " + name + "\n\nDescription: " + description + "\n\nOrder: ";
        int length = order.size();
        int i = 1;
        for (String order: order){
            result += order;
            if (i < length){
                result += ", ";
            }
        }
        return result;
    }
}
