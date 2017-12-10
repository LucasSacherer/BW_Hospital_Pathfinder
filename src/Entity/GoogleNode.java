package Entity;

public class GoogleNode {
    private String name;
    private String url;
    private int xcoord;
    private int ycoord;
    private String floor;

    public GoogleNode(String name, String url, int xcoord, int ycoord, String floor) {
        this.name = name;
        this.url = url;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.floor = floor;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
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
}
