package uk.ac.york.minesweeper;

import org.junit.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.Assert.*;

public class PreMutationTests {
    @Test
    public void JSITest() throws Exception {
        Minefield m1 = new Minefield(10,10,1);
        Minefield m2 = new Minefield(20,20,2);
        m1.tilesLeft = 1;
        m2.tilesLeft = 2;
        assertNotEquals(m1.tilesLeft, m2.tilesLeft);
    }

    @Test
    public void JSDTest() {
        Minefield m = new Minefield(10,10,1);
        MinefieldPanel mp1 = new MinefieldPanel(m);
        MinefieldPanel mp2 = new MinefieldPanel(m);
        assertEquals(mp1.FONT, mp2.FONT);
    }

    @Test
    public void JDCTest() {
        Minefield m = new Minefield(10, 10, 1);
        assertEquals(m.getWidth(), 10);
    }

    @Test
    public void IODTest() {
        Minefield m = new Minefield(10, 10, 1);
        JComponent j = new JComponent() {
            @Override
            public Dimension getPreferredSize() {
                return super.getPreferredSize();
            }
        };

        MinefieldPanel mp = new MinefieldPanel(m);
        Dimension d1 = mp.getPreferredSize();
        Dimension d2 = j.getPreferredSize();
        assertNotEquals(d1, d2);
    }

    @Test
    public void AMCTest() {
        Minefield m1 = new Minefield(10,10,1);
        m1.tilesLeft = 1;
        assertTrue(m1.tilesLeft == 1);
    }

    @Test
    public void OMDTest() {
        Minefield m = new Minefield(10, 10, 1);
        m.setUncoverMinesAtEnd(true);
        assertEquals(m.isUncoveringMinesAtEnd(), true);
    }

    @Test
    public void PMDTest() {
        MinefieldStateChangeEvent mce = new MinefieldStateChangeEvent(new Object());
        assertEquals(mce.serialVersionID.getClass().getName(), "java.lang.String");
    }

    @Test
    public void IORTest() {
        MinefieldPane mp = new MinefieldPane(10,10,1);
        assertEquals(mp.getWidth(), 4);
    }

    @Test
    public void OMRTest() {
        Minefield m = new Minefield(10, 10, 1);
        m.setUncoverMinesAtEnd(true);
        assertEquals(m.isUncoveringMinesAtEnd(), true);
    }
}
