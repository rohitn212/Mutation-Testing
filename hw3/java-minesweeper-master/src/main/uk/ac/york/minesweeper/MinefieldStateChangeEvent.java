package uk.ac.york.minesweeper;

import static uk.ac.york.minesweeper.TemplateClass.instrum;
import java.util.EventObject;
import javafx.util.Pair;

/**
 * Event fired when the minefield changes game state
 */
public class MinefieldStateChangeEvent extends EventObject
{
    private static final long serialVersionUID = 1L;

    /**
     * Initializes a new MinefieldStateChangeEvent object
     *
     * @param source event source
     */
    public MinefieldStateChangeEvent(Object source)
    {
        super(source);
		instrum(21,"super constructor",new Pair<>("uk.ac.york.minesweeper.MinefieldStateChangeEvent.MinefieldStateChangeEvent.source",source));
    }
}