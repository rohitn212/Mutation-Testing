package uk.ac.york.minesweeper;

import org.junit.Test;

import static org.junit.Assert.*;


public class MinefieldTest {

    @Test
    public void isUncoveringMinesAtEnd() throws Exception {
        Minefield mf = new Minefield(16, 16, 8);
        assertEquals(mf.isUncoveringMinesAtEnd(), true);
    }

    @Test
    public void setUncoverMinesAtEnd() throws Exception {
        Minefield mf = new Minefield(16, 16, 8);
        mf.setUncoverMinesAtEnd(true);
        assertEquals(mf.isUncoveringMinesAtEnd(), true);
    }

    @Test
    public void getGameState() throws Exception {
        Minefield mf = new Minefield(16, 16, 8);
        GameState gs = mf.getGameState();
        assertEquals(mf.getGameState(), gs);
    }

    @Test
    public void isFinished() throws Exception {
        Minefield mf = new Minefield(16, 16, 8);
        boolean isFinished = mf.isFinished();
        assertEquals(mf.isFinished(), isFinished);
    }

    @Test
    public void getTileState() throws Exception {
        Minefield mf = new Minefield(16, 16, 8);
        TileState ts = mf.getTileState(2, 3);
        assertEquals(mf.getTileState(2, 3), ts);
    }

    @Test
    public void uncover() throws Exception {
        Minefield mf = new Minefield(16, 16, 8);
        mf.uncover(2, 3);
    }

    @Test
    public void chord() throws Exception {
        Minefield mf = new Minefield(16, 16, 8);
        mf.chord(8, 8);
    }

    @Test
    public void toStringTest() throws Exception {
        Minefield mf = new Minefield(16, 16, 8);
        mf.toString();
    }

    @org.junit.Test
    public void getWidth() throws Exception {
        Minefield mf = new Minefield(16, 16, 8);
        assertEquals(mf.getWidth(), 16);
        System.out.println("Testcase Passed");
    }

    @org.junit.Test
    public void getHeight() throws Exception {
        Minefield mf = new Minefield(16, 16, 8);
        assertEquals(mf.getHeight(), 16);
        System.out.println("Testcase Passed");
    }

    @org.junit.Test
    public void getMines() throws Exception {
        Minefield mf = new Minefield(16, 16, 8);
        assertEquals(mf.getMines(), 8);
        System.out.println("Testcase Passed");
    }

    @Test
    public void uncoverAllMines() throws Exception {
        Minefield mf = new Minefield(16, 16, 8);
        mf.setUncoverMinesAtEnd(true);

    }
}