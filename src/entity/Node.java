package entity;

public class Node {
    final private String nodeID;
    final private int xcoord;
    final private int ycoord;
    final private String floor;
    final private String building;
    final private String nodeType;
    final private String longName;
    final private String shortName;
    final private boolean visitable;
    private int weight;

    public Node(String nodeID, int xcoord, int ycoord, String floor, String building, String nodeType, String longName, String shortName, boolean visitable) {
        this.nodeID = nodeID;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.floor = floor;
        this.building = building;
        this.nodeType = nodeType;
        this.longName = longName;
        this.shortName = shortName;
        this.visitable = visitable;
    }

    public void setWeight(Node dest) {
        this.weight = (int) Math.sqrt ((this.getXcoord() - dest.getXcoord()) * (this.getXcoord() - dest.getXcoord())
                +
                (this.getYcoord() - dest.getYcoord()) *  (this.getYcoord() - dest.getYcoord()));

    }

    public String getNodeID() {
        return nodeID;
    }

    public int getXcoord() {
        return xcoord;
    }

    public int getYcoord() {
        return ycoord;
    }

    public String getFloor() {
        return floor;
    }

    public String getBuilding() {
        return building;
    }

    public String getNodeType() {
        return nodeType;
    }

    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isVisitable() {
        return visitable;
    }

    @Override
    public String toString() {
        return this.getShortName();
    }
}
