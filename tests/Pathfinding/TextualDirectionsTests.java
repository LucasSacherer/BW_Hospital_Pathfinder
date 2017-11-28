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
    public void horizontalFindAngleTest(){
        Node previous = new Node("one", -1, 0, "floor 1", "shapiro", "STAI", "blerg", "blerg" );
        Node current = new Node("two", 0, 0, "floor 1", "shapiro", "STAI", "blerg", "blerg" );
        Node next = new Node("three", 0, 1, "floor 1", "shapiro", "STAI", "blerg", "blerg" );
        assertEquals(270, textualDirections.getAngle(previous, current, next), 0.0);
        assertEquals(90, textualDirections.getAngle(next, current, previous), 0.0);
    }


}
