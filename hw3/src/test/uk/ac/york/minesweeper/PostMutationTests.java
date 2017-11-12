package uk.ac.york.minesweeper;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class PostMutationTests {
    @Test
    public void JSITest() {
        try {
            Class c = Class.forName("uk.ac.york.minesweeper.JSIMutation");
            Object o = c.getDeclaredConstructor(int.class, int.class, int.class)
                    .newInstance(new Object[]{1, 2, 3});
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void JSDTest() {
        try {
            Class c = Class.forName("uk.ac.york.minesweeper.JSDMutation");
            Object o = c.getDeclaredConstructor(int.class, int.class, int.class)
                    .newInstance(new Object[]{1, 2, 3});
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void JDCTest() {
        try {
            Class c = Class.forName("uk.ac.york.minesweeper.JDCMutation");
            Object o = c.getDeclaredConstructor(Minefield.class)
                    .newInstance(new Object[]{new Minefield(1, 2, 3)});
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void IODTest() {
        try {
            Class c = Class.forName("uk.ac.york.minesweeper.IODMutation");
            Object o = c.getDeclaredConstructor(int.class, int.class, int.class)
                    .newInstance(new Object[]{1, 2, 3});
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void AMCTest() {
        try {
            Class c = Class.forName("uk.ac.york.minesweeper.AMCMutation");
            Object o = c.getDeclaredConstructor(int.class, int.class, int.class)
                    .newInstance(new Object[]{1, 2, 3});
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void IPCTest() {
        try {
            Class c = Class.forName("uk.ac.york.minesweeper.IPCMutation");
            Object o = c.getDeclaredConstructor(Object.class)
                    .newInstance(new Object[]{"jaja"});
        } catch (ClassNotFoundException | InvocationTargetException |
                InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
