package entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EdgeTest {
    @Test
    public void testSetWeight () {
        Node n1 = new Node("1",1,1,"1","Shapiro","type","Stairwell","STAI",true);
        Node n2 = new Node("2",2,1,"1","Shapiro","type","Stairwell","STAI",true);
        Edge e = new Edge(n1,n2);
        assertEquals(e.getWeight(),1);
    }

}
