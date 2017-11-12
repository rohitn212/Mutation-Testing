package uk.ac.york.minesweeper;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class PostMutationTests {
    @Test
    public void JSITest() {
        Class c = null;
        Object o = null;
        try {
            c = Class.forName("uk.ac.york.minesweeper.JSIMutation");
            o = c.getDeclaredConstructor(new Class[]{int.class, int.class, int.class})
                    .newInstance(new Object[]{1, 2, 3});
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void JSDTest() {
        Class c = null;
        Object o = null;
        try {
            c = Class.forName("uk.ac.york.minesweeper.JSDMutation");
            o = c.getDeclaredConstructor(new Class[]{int.class, int.class, int.class})
                    .newInstance(new Object[]{1, 2, 3});
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void JDCTest() {
        Class c = null;
        Object o = null;
        try {
            c = Class.forName("uk.ac.york.minesweeper.JDCMutation");
            o = c.getDeclaredConstructor(new Class[]{int.class, int.class, int.class})
                    .newInstance(new Object[]{1, 2, 3});
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void IODTest() {
        Class c = null;
        Object o = null;
        try {
            c = Class.forName("uk.ac.york.minesweeper.IODMutation");
            o = c.getDeclaredConstructor(new Class[]{int.class, int.class, int.class})
                    .newInstance(new Object[]{1, 2, 3});
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void AMCTest() {
        Class c = null;
        Object o = null;
        try {
            c = Class.forName("uk.ac.york.minesweeper.AMCMutation");
            o = c.getDeclaredConstructor(new Class[]{int.class, int.class, int.class})
                    .newInstance(new Object[]{1, 2, 3});
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void IPCTest() {
        Class c = null;
        Object o = null;
        try {
            c = Class.forName("uk.ac.york.minesweeper.IPCMutation");
            o = c.getDeclaredConstructor(new Class[]{int.class, int.class, int.class})
                    .newInstance(new Object[]{1, 2, 3});
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
