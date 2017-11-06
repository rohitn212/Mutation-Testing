package uk.ac.york.minesweeper;

import org.junit.Test;

import java.awt.event.ActionEvent;

import static org.junit.Assert.*;

/**
 * Created by kobebryant1624 on 26/09/17.
 */
public class MinesweeperFrameTest {
    @Test
    public void actionPerformed() throws Exception {
        ActionEvent ae = new ActionEvent(new Object(), 1, "test");
        MinesweeperFrame msf = new MinesweeperFrame();
        msf.actionPerformed(ae);
    }

    @Test
    public void main() throws Exception {
        ActionEvent ae = new ActionEvent(new Object(), 3, "test");
        MinesweeperFrame msf = new MinesweeperFrame();
        msf.actionPerformed(ae);
    }

}