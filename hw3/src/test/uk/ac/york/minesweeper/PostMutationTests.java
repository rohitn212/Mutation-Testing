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
            Field f = c.getDeclaredField("tilesLeft");
            assertNotEquals(f.getInt(o1), f.getInt(o2));
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            assertEquals(false, true);
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
            Field f = c.getDeclaredField("tilesLeft");
            assertEquals(f.getInt(o1), f.getInt(o2));
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            assertEquals(false, true);
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
            Field f = c.getDeclaredField("getWidth");
            assertEquals(f.getInt(o), 10);
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            assertEquals(false, true);
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
            assertEquals(false, true);
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
            assertEquals(false, true);
        } catch (NoSuchFieldException e) {
            assertEquals(false, true);
        }
    }

}
