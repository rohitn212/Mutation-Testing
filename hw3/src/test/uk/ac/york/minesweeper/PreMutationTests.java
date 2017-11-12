package uk.ac.york.minesweeper;

import org.junit.Test;
import static org.junit.Assert.*;

public class PreMutationTests {
    @Test
    public void JSITest() throws Exception {
        Minefield m1 = new Minefield(10,10,1);
        Minefield m2 = new Minefield(20,20,2);
        m1.tilesLeft = 1;
        m2.tilesLeft = 2;
        assertEquals(m1.tilesLeft, m2.tilesLeft);
    }

    @Test
    public void JSDTest() {
        Minefield m1 = new Minefield(10,10,1);
        Minefield m2 = new Minefield(20,20,2);
        m1.tilesLeft = 1;
        m2.tilesLeft = 2;
        assertNotEquals(m1.tilesLeft, m2.tilesLeft);
    }

    @Test
    public void JDCTest() {
        Minefield m = new Minefield(10, 10, 1);
    }

    @Test
    public void IODTest() {

    }

    @Test
    public void AMCTest() {

    }

    @Test
    public void IPCTest() {

    }
}
