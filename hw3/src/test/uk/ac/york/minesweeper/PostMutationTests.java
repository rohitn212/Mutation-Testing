package uk.ac.york.minesweeper;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class PostMutationTests {
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

    @Test
    public void JSDTest() {
        try {
            Class c = Class.forName("JSDMutation");
            Object o1 = c.getDeclaredConstructor(int.class, int.class, int.class)
                    .newInstance(new Object[]{1, 2, 3});
            Object o2 = c.getDeclaredConstructor(int.class, int.class, int.class)
                    .newInstance(new Object[]{1, 2, 3});
            Field f = c.getDeclaredField("tilesLeft"); // gets field to test
            assertEquals(f.getInt(o1), f.getInt(o2)); // tests field for both objects
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            assertEquals(false, true); // if the test fails, throw a false
        } catch (NoSuchFieldException e) {
            assertEquals(false, true);
        }
    }

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

    @Test
    public void IODTest() {
        try {
            Class c = Class.forName("IODMutation");
            Object o = c.getDeclaredConstructor(Minefield.class)
                    .newInstance(new Minefield(1, 2, 3));
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void AMCTest() {
        try {
            Class c = Class.forName("AMCMutation");
            Object o = c.getDeclaredConstructor(Minefield.class)
                    .newInstance(new Minefield(1, 2, 3));
            Field f = c.getDeclaredField("tilesLeft");
            assertTrue(f.getInt(o) != 1);
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            assertEquals(false, true); // if the test fails, throw a false
        } catch (NoSuchFieldException e) {
            assertEquals(false, true);
        }
    }

    @Test
    public void OMDTest() {
        try {
            Class c = Class.forName("OMDMutation");
            Object o = c.getDeclaredConstructor(Minefield.class)
                    .newInstance(new Minefield(1, 2, 3));
            Field f = c.getDeclaredField("uncoverMinesAtEnd");
            assertEquals(f.getBoolean(o), true);
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            assertEquals(false, true); // if the test fails, throw a false
        } catch (NoSuchFieldException e) {
            assertEquals(false, true);
        }
    }


    @Test
    public void PMDTest() {
        try {
            Class c = Class.forName("PMDMutation");
            Object o = c.getDeclaredConstructor(Minefield.class)
                    .newInstance(new Minefield(1, 2, 3));
            Field f = c.getDeclaredField("serialVersionID");
            assertEquals(f.getName(), "java.lang.String");
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            assertEquals(false, true); // if the test fails, throw a false
        } catch (NoSuchFieldException e) {
            assertEquals(false, true);
        }
    }

    @Test
    public void IORTest() {
        try {
            Class c = Class.forName("IORMutation");
            Object o = c.getDeclaredConstructor(Minefield.class)
                    .newInstance(new Minefield(1, 2, 3));
            Field f = c.getDeclaredField("width");
            assertEquals(f.getInt(o), 4);
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            assertEquals(false, true); // if the test fails, throw a false
        } catch (NoSuchFieldException e) {
            assertEquals(false, true);
        }
    }
}
