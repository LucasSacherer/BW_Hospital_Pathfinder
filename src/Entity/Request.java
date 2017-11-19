package Entity;

import java.time.LocalDateTime;

public interface Request {

    public String getName();
    public String getType();
    public String getDescription();
    public Node getNode();
    public LocalDateTime getTimeStamp();
}
