package uk.ac.york.minesweeper;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class PostMutationTests {

    // Tests the Static Modifier Insertion mutation
    @Test
    public void JSITest() {
        try {
            Class c = Class.forName("JSIMutation");
            Object o1 = c.getDeclaredConstructor(int.class, int.class, int.class)
                    .newInstance(new Object[]{1, 2, 3});
            Object o2 = c.getDeclaredConstructor(int.class, int.class, int.class)
                    .newInstance(new Object[]{1, 2, 3});
            Field f = c.getDeclaredField("tilesLeft"); // gets field to test
            assertNotEquals(f.getInt(o1), f.getInt(o2)); // tests field for both objects
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            assertEquals(false, true); // if the test fails, throw a false
        } catch (NoSuchFieldException e) {
            assertEquals(false, true);
        }
    }

    // Tests the Java-supported Default Constructor mutation
    @Test
    public void JDCTest() {
        try {
            Class c = Class.forName("JDCMutation");
            Object o = c.getDeclaredConstructor(Minefield.class)
                    .newInstance(new Minefield(1, 2, 3));
            Field f = c.getDeclaredField("getWidth");  // gets field to test
            assertEquals(f.getInt(o), 10); // tests field for object
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            assertEquals(false, true); // if the test fails, throw a false
        } catch (NoSuchFieldException e) {
            assertEquals(false, true);
        }
    }

    // Tests the Overriding Method Deletion mutation
    @Test
    public void IODTest() {
        try {
            Class c = Class.forName("IODMutation");
            Object o1 = c.getDeclaredConstructor(Minefield.class)
                    .newInstance(new Minefield(1, 2, 3));
            Object o2 = c.getDeclaredConstructor(Minefield.class)
                    .newInstance(new Minefield(1, 2, 3));
            Field f = c.getDeclaredField("preferredSize"); // gets field to test
            assertEquals(f.getInt(o1), f.getInt(o2)); // tests field for object
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            assertEquals(false, true); // if the test fails, throw a false
        } catch (NoSuchFieldException e) {
            assertEquals(false, true);
        }
    }

    // Tests the Access Modifier Change mutation
    @Test
    public void AMCTest() {
        try {
            Class c = Class.forName("AMCMutation");
            Object o = c.getDeclaredConstructor(Minefield.class)
                    .newInstance(new Minefield(1, 2, 3));
            Field f = c.getDeclaredField("tilesLeft"); // gets field to test
            assertTrue(f.getInt(o) != 1); // tests field for object
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            assertEquals(false, true); // if the test fails, throw a false
        } catch (NoSuchFieldException e) {
            assertEquals(false, true);
        }
    }

    // Tests the Overloading Method Deletion mutation
    @Test
    public void OMDTest() {
        try {
            Class c = Class.forName("OMDMutation");
            Object o = c.getDeclaredConstructor(Minefield.class)
                    .newInstance(new Minefield(1, 2, 3));
            Field f = c.getDeclaredField("uncoverMinesAtEnd"); // gets field to test
            assertEquals(f.getBoolean(o), true);  // tests field for object
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            assertEquals(false, true); // if the test fails, throw a false
        } catch (NoSuchFieldException e) {
            assertEquals(false, true);
        }
    }

    // Tests the Member Variable Declaration With Parent Class type mutation
    @Test
    public void PMDTest() {
        try {
            Class c = Class.forName("PMDMutation");
            Object o = c.getDeclaredConstructor(Minefield.class)
                    .newInstance(new Minefield(1, 2, 3));
            Field f = c.getDeclaredField("serialVersionID"); // gets field to test
            assertEquals(f.getName(), "java.lang.String"); // tests field for object
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            assertEquals(false, true); // if the test fails, throw a false
        } catch (NoSuchFieldException e) {
            assertEquals(false, true);
        }
    }

    // Tests the Overridden Method Rename mutation
    @Test
    public void IORTest() {
        try {
            Class c = Class.forName("IORMutation");
            Object o = c.getDeclaredConstructor(Minefield.class)
                    .newInstance(new Minefield(1, 2, 3));
            Field f = c.getDeclaredField("width"); // gets field to test
            assertEquals(f.getInt(o), 4); // tests field for object
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            assertEquals(false, true); // if the test fails, throw a false
        } catch (NoSuchFieldException e) {
            assertEquals(false, true);
        }
    }

    // Tests the Overloading Method Contents Change mutation
    @Test
    public void OMRTest() {
        try {
            Class c = Class.forName("OMRMutation");
            Object o = c.getDeclaredConstructor(Minefield.class)
                    .newInstance(new Minefield(1, 2, 3));
            Field f = c.getDeclaredField("uncoverMinesAtEnd"); // gets field to test
            assertEquals(f.getBoolean(o), true);  // tests field for object
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            assertEquals(false, true); // if the test fails, throw a false
        } catch (NoSuchFieldException e) {
            assertEquals(false, true);
        }
    }
}
