package MementoPattern;

import org.junit.Test;

import static org.junit.Assert.*;

public class MementoTest {

    @Test
    public void TimerTest() throws Exception {
        Memento testM = new Memento("Brigham and Women's");
        testM.run(5000);
        Thread.sleep(10000);
    }
}