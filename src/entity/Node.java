package entity;

public class Node {
    final private int nodeID;
    final private int xcoord;
    final private int ycoord;
    final private int floor;
    final private String building;
    final private String longName;
    final private String shortName;
    private int weight;

    public Node(int nodeID, int xcoord, int ycoord, int floor, String building, String longName, String shortName) {
        this.nodeID = nodeID;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.floor = floor;
        this.building = building;
        this.longName = longName;
        this.shortName = shortName;
    }

    public void setWeight(Node dest) {
        this.weight = (int) Math.sqrt ((this.getXcoord() - dest.getXcoord()) * (this.getXcoord() - dest.getXcoord())
                +
                (this.getYcoord() - dest.getYcoord()) *  (this.getYcoord() - dest.getYcoord()));

    }

    public int getNodeID() {
        return nodeID;
    }

    public int getXcoord() {
        return xcoord;
    }

    public int getYcoord() {
        return ycoord;
    }

    public int getFloor() {
        return floor;
    }

    public String getBuilding() {
        return building;
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
}
