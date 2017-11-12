package uk.ac.york.minesweeper;

import org.junit.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.Assert.*;

public class PreMutationTests {
    // sets up the test for the Static Modifier Insertion mutation
    @Test
    public void JSITest() throws Exception {
        Minefield m1 = new Minefield(10,10,1);
        Minefield m2 = new Minefield(20,20,2);
        m1.tilesLeft = 1; // object before inserting static
        m2.tilesLeft = 2; // object after inserting static
        assertNotEquals(m1.tilesLeft, m2.tilesLeft);
    }

    // sets up the test for the Java-supported Default Constructor mutation
    @Test
    public void JDCTest() {
        Minefield m = new Minefield(10, 10, 1); // object whose constructor will be changed
        assertEquals(m.getWidth(), 10);
    }

    // sets up the test for the Overriding Method Deletion mutation
    @Test
    public void IODTest() {
        Minefield m = new Minefield(10, 10, 1);
        // bypass the method by going into the super
        JComponent j = new JComponent() {
            @Override
            public Dimension getPreferredSize() {
                return super.getPreferredSize(); // gets the supers size
            }
        };

        MinefieldPanel mp = new MinefieldPanel(m);
        Dimension d1 = mp.getPreferredSize(); // gets first dimension to compare
        Dimension d2 = j.getPreferredSize(); // gets second dimension to compare
        assertNotEquals(d1, d2);
    }

    // sets up the test for the Access Modifier Change mutation
    @Test
    public void AMCTest() {
        Minefield m1 = new Minefield(10,10,1);
        m1.tilesLeft = 1; // sets up the field for access modifier change
        assertTrue(m1.tilesLeft == 1);
    }

    // sets up the test for the Overloading Method Deletion mutation
    @Test
    public void OMDTest() {
        Minefield m = new Minefield(10, 10, 1);
        m.setUncoverMinesAtEnd(true); // set up the method to be deleted
        assertEquals(m.isUncoveringMinesAtEnd(), true);
    }

    // sets up the test for the Member Variable Declaration With Parent Class type mutation
    @Test
    public void PMDTest() {
        MinefieldStateChangeEvent mce = new MinefieldStateChangeEvent(new Object());
        assertEquals(mce.serialVersionID.getClass().getName(), "java.lang.String");
    }

    // sets up the test for the Overridden Method Rename mutation
    @Test
    public void IORTest() {
        MinefieldPane mp = new MinefieldPane(10,10,1);
        assertEquals(mp.getWidth(), 4); // sets up overridden method
    }

    // sets up the test for the Overloading Method Contents Change mutation
    @Test
    public void OMRTest() {
        Minefield m = new Minefield(10, 10, 1);
        m.setUncoverMinesAtEnd(true); // sets up overloaded method
        assertEquals(m.isUncoveringMinesAtEnd(), true);
    }
}
