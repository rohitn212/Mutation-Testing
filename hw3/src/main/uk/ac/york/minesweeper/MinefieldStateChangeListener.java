package uk.ac.york.minesweeper;

import static uk.ac.york.minesweeper.TemplateClass.instrum;
import javafx.util.Pair;

/**
 * Event fired when the state of the game changes
 */
public interface MinefieldStateChangeListener
{
    /**
     * Called when the game state of the minefield changes
     *
     * @param event the event object from the minefield panel
     */
    public void stateChanged(MinefieldStateChangeEvent event);
}
