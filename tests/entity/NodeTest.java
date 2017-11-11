package entity;

import org.junit.Test;
import static org.junit.Assert.*;

public class NodeTest {
    @Test
    public void testSetWeight () {
        Node n1 = new Node(1,1,1,1,"Shapiro","Stairwell","STAI");
        Node n2 = new Node(2,2,1,1,"Shapiro","Stairwell","STAI");
        n1.setWeight(n2);
        assertEquals(n1.getWeight(),1);
        assertTrue(true);
    }

}
