package Pathfinding;
import Database.EdgeManager;
import Database.NodeManager;
import Entity.Node;
import org.junit.Test;
import Pathfinding.TextualDirections;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class TextualDirectionsTests {
    TextualDirections textualDirections = new TextualDirections();

    @Test
    public void findAngleTest(){
        Node previous = new Node("one", -1, 0, "floor 1", "shapiro", "STAI", "blerg", "blerg" );
        Node current = new Node("two", 0, 0, "floor 1", "shapiro", "STAI", "blerg", "blerg" );
        Node next = new Node("three", 0, 1, "floor 1", "shapiro", "STAI", "blerg", "blerg" );
        assertEquals(270, textualDirections.getAngle(previous, current, next), 0.0);
        assertEquals(90, textualDirections.getAngle(next, current, previous), 0.0);
        previous = new Node("four", 90, 90, "floor 1", "shapiro", "STAI", "blerg", "blerg" );
        current = new Node("five", 90, 125, "floor 1", "shapiro", "STAI", "blerg", "blerg" );
        next = new Node("six", 125, 125, "floor 1", "shapiro", "STAI", "blerg", "blerg" );
        assertEquals(90, textualDirections.getAngle(previous, current, next), 0.0);
        assertEquals(270, textualDirections.getAngle(next, current, previous), 0.0);

    }

    @Test
    public void horizontalFindAngleTest(){
        Node previous = new Node("one", -1, 0, "floor 1", "shapiro", "STAI", "blerg", "blerg" );
        Node current = new Node("two", 0, 0, "floor 1", "shapiro", "STAI", "blerg", "blerg" );
        Node next = new Node("three", 1, 0, "floor 1", "shapiro", "STAI", "blerg", "blerg" );
        assertEquals(180, textualDirections.getAngle(next, current, previous), 0.0);

    }
    //fix this
    @Test
    public void getDirTest(){
        TextualDirections textualDirections = new TextualDirections();

        Node a1 = new Node("1",0,0,"1","A","test","test","test");
        Node a2 = new Node("2",0,10,"1","A","test","test","test");
        Node a3 = new Node("3",30,30,"1","A","ELEV","test","test");
        Node b3 = new Node("b3",30,30,"2","A","ELEV","test","test");
        Node b4 = new Node("b4",40,30,"2","A","STAI","test","test");
        Node a4 = new Node("4",40,30,"1","A","STAI","test","test");
        Node a5 = new Node("5",40,30,"1","A","test","test","test");
        Node a6 = new Node("6",60,0,"1","A","test","test","test");
        Node a7 = new Node("7",40,0,"1","A","test","test","test");

        List<Node> path = new ArrayList<>();
        path.add(a1);
        path.add(a2);
        path.add(a3);
        path.add(b3);
        path.add(b4);
        path.add(a4);
        path.add(a5);
        path.add(a6);
        path.add(a7);

        System.out.println(textualDirections.getDir(path));
    }

}
