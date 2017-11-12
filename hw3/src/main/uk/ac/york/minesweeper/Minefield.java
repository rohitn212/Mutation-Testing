package uk.ac.york.minesweeper;

import static uk.ac.york.minesweeper.TemplateClass.instrum;
import java.util.Arrays;
import java.util.Random;
import javafx.util.Pair;

/**
 * Class containing the game data for the minesweeper game
 */
public class Minefield
{
    // Array containing tile values (-1 = mine)
    public final byte[][] valuesArray;

    // Array containing tile states
    private final TileState[][] stateArray;

    // Number of mines
    private final int mines;

    // Number of extra tiles which need to uncovered to win
    public int tilesLeft;

    // If true, uncovers mines when the game finishes
    private boolean uncoverMinesAtEnd = true;

    // State of the game
    private GameState gameState = GameState.NOT_STARTED;

    /**
     * Initializes a new Minefield class with the given properties
     *
     * The mine locations are not allocated until the first click is made
     *
     * @param width width of the minefield in tiles
     * @param height height of the minefield in tiles
     * @param mines number of mines
     */
    public Minefield(int width, int height, int mines)
    {
        int tilesLeft = (width * height) - mines;

        instrum(46,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.Minefield.width",width),new Pair<>("uk.ac.york.minesweeper.Minefield.Minefield.height",height),new Pair<>("uk.ac.york.minesweeper.Minefield.Minefield.mines",mines));
		// Validate arguments
        if (width < 1 || height < 1 || mines < 0)
            throw new IllegalArgumentException("invalid minefield dimensions");

        instrum(50,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.Minefield.tilesLeft",tilesLeft));
		if (tilesLeft <= 0)
            throw new IllegalArgumentException("too many mines");

        // Save initial properties
        this.mines = mines;
		instrum(54,"Assign",new Pair<>("uk.ac.york.minesweeper.Minefield.mines",this.mines),new Pair<>("uk.ac.york.minesweeper.Minefield.Minefield.mines",mines));
        this.tilesLeft = tilesLeft;
		instrum(56,"Assign",new Pair<>("uk.ac.york.minesweeper.Minefield.tilesLeft",this.tilesLeft),new Pair<>("uk.ac.york.minesweeper.Minefield.Minefield.tilesLeft",tilesLeft));

        // Create arrays (empty + covered)
        TileState[][] stateArray = new TileState[width][height];

        for (int x = 0; x < width; x++) {Arrays.fill(stateArray[x],TileState.COVERED);instrum(62,"method call",new Pair<>("uk.ac.york.minesweeper.Minefield.Minefield.stateArray",stateArray),new Pair<>("uk.ac.york.minesweeper.Minefield.Minefield.x",x));}

        this.stateArray = stateArray;
		instrum(64,"Assign",new Pair<>("uk.ac.york.minesweeper.Minefield.stateArray",this.stateArray),new Pair<>("uk.ac.york.minesweeper.Minefield.Minefield.stateArray",stateArray));
        this.valuesArray = new byte[width][height];
		instrum(66,"Assign",new Pair<>("uk.ac.york.minesweeper.Minefield.valuesArray",this.valuesArray),new Pair<>("uk.ac.york.minesweeper.Minefield.Minefield.width",width),new Pair<>("uk.ac.york.minesweeper.Minefield.Minefield.height",height));
    }

    /**
     * Gets the width of the minefield in tiles
     *
     * @return width of the minefield
     */
    public int getWidth()
    {
        instrum(78,"return",new Pair<>("uk.ac.york.minesweeper.Minefield.valuesArray",valuesArray));
		return valuesArray.length;
    }

    /**
     * Gets the height of the minefield in tiles
     *
     * @return height of the minefield
     */
    public int getHeight()
    {
        instrum(89,"return",new Pair<>("uk.ac.york.minesweeper.Minefield.valuesArray",this.valuesArray));
		return valuesArray[0].length;
    }

    /**
     * Gets the total number of mines in the minefield
     *
     * @return total number of mines
     */
    public int getMines()
    {
        instrum(100,"return",new Pair<>("uk.ac.york.minesweeper.Minefield.mines",mines));
		return mines;
    }

    /**
     * Gets a value which is true if all mines are uncovered at the end of the game
     *
     * @return true if mines are uncovered at the end
     */
    public boolean isUncoveringMinesAtEnd()
    {
        instrum(111,"return",new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverMinesAtEnd",uncoverMinesAtEnd));
		return uncoverMinesAtEnd;
    }

    /**
     * Sets a value determining whether mines are uncovered at the end of the game
     *
     * @param uncoverMinesAtEnd true if mines are uncovered at the end
     */
    public void setUncoverMinesAtEnd(boolean uncoverMinesAtEnd)
    {
        this.uncoverMinesAtEnd = uncoverMinesAtEnd;
		instrum(121,"Assign",new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverMinesAtEnd",this.uncoverMinesAtEnd),new Pair<>("uk.ac.york.minesweeper.Minefield.setUncoverMinesAtEnd.uncoverMinesAtEnd",uncoverMinesAtEnd));
    }

    /**
     * Gets the current state of the game
     *
     * @return the state of the game
     */
    public GameState getGameState()
    {
        instrum(133,"return",new Pair<>("uk.ac.york.minesweeper.Minefield.gameState",gameState));
		return gameState;
    }

    /**
     * Returns true if the game has finished
     *
     * @return true if the game has finished
     */
    public boolean isFinished()
    {
        instrum(144,"return",new Pair<>("uk.ac.york.minesweeper.Minefield.gameState",gameState));
		return gameState != GameState.RUNNING && gameState != GameState.NOT_STARTED;
    }

    /**
     * Gets the value of the given tile (mine / surrounding mines)
     *
     * This should only be called AFTER the first tile is clicked (or when a tile is uncovered)
     *
     * @param x x position of tile
     * @param y y position of tile
     * @return value of that tile (-1 = mine)
     */
    public int getTileValue(int x, int y)
    {
        instrum(159,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.gameState",gameState));
		if (gameState == GameState.NOT_STARTED)
            throw new IllegalStateException("you must call uncover at least once before using getTileValue");

        instrum(163,"return",new Pair<>("uk.ac.york.minesweeper.Minefield.valuesArray",valuesArray),new Pair<>("uk.ac.york.minesweeper.Minefield.getTileValue.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.getTileValue.y",y));
		return valuesArray[x][y];
    }

    /**
     * Gets the state of the given tile
     *
     * @param x x position of tile
     * @param y y position of tile
     * @return state of that tile
     */
    public TileState getTileState(int x, int y)
    {
        instrum(176,"return",new Pair<>("uk.ac.york.minesweeper.Minefield.stateArray",stateArray),new Pair<>("uk.ac.york.minesweeper.Minefield.getTileState.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.getTileState.y",y));
		return stateArray[x][y];
    }

    /**
     * Updates the state of the given tile
     *
     * Can be used to add flags and uncover tiles.
     * You cannot cover a tile that has already been uncovered.
     * If a tile is uncovered, other tile states and the game state may be updated.
     *
     * @param x x position of tile
     * @param y y position of tile
     * @param newState the tile's new state
     */
    public void setTileState(int x, int y, TileState newState)
    {
        if (isFinished())
            throw new IllegalStateException("the game has finished");

        instrum(196,"switch",new Pair<>("uk.ac.york.minesweeper.Minefield.setTileState.newState",newState));
		switch (newState)
        {
            case COVERED:
            case FLAGGED:
            case QUESTION:
			{instrum(202,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.stateArray",stateArray),new Pair<>("uk.ac.york.minesweeper.Minefield.setTileState.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.setTileState.y",y));if (stateArray[x][y] == TileState.UNCOVERED)throw new UnsupportedOperationException("you cannot cover a tile once uncovered");}

			{stateArray[x][y]=newState;instrum(203,"Assign",new Pair<>("uk.ac.york.minesweeper.Minefield.stateArray",stateArray),new Pair<>("uk.ac.york.minesweeper.Minefield.setTileState.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.setTileState.y",y),new Pair<>("uk.ac.york.minesweeper.Minefield.setTileState.newState",newState));}
                break;

            case UNCOVERED:
                // Forward to uncover
                uncover(x, y);
                break;

            default:
                throw new IllegalArgumentException("newState is not a valid tile state");
        }
    }

    /**
     * Uncovers the tile at the given location
     *
     * This method is equivalent to calling {@code setTileState(x, y, TileState.UNCOVERED)}
     *
     * @param x x position of tile
     * @param y y position of tile
     */
    public void uncover(int x, int y)
    {
        if (isFinished())
            throw new IllegalStateException("the game has finished");

        instrum(231,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.gameState",gameState));
		// New game?
        if (gameState == GameState.NOT_STARTED)
        {
            initValues(x, y);
            gameState = GameState.RUNNING;
			instrum(234,"Assign",new Pair<>("uk.ac.york.minesweeper.Minefield.gameState",gameState));
        }

        // Perform any uncovering
        uncoverNoChecks(x, y);
    }

    /** Tile processor for uncoverNoChecks which uncovers a tile immediately */
    private final SurroundingTilesProcessor PROCESSOR_UNCOVER = new SurroundingTilesProcessor()
    {
        @Override
        public void process(int x, int y)
        {
            uncoverNoChecks(x, y);
        }
    };

    /**
     * Uncovers the given tile and surrounding tiles without performing state checks
     *
     * @param x x position of tile
     * @param y y position of tile
     */
    private void uncoverNoChecks(int x, int y)
    {
        int width = getWidth();
        int height = getHeight();

        instrum(265,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverNoChecks.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverNoChecks.y",y),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverNoChecks.width",width),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverNoChecks.height",height));
		// Ignore if the tile does not exist / is already uncovered
        if (x < 0 || y < 0 || x >= width || y >= height)
            return;

        instrum(269,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.stateArray",stateArray),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverNoChecks.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverNoChecks.y",y));
		if (stateArray[x][y] == TileState.UNCOVERED)
            return;

        // Uncover this tile
        stateArray[x][y] = TileState.UNCOVERED;
		instrum(273,"Assign",new Pair<>("uk.ac.york.minesweeper.Minefield.stateArray",stateArray),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverNoChecks.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverNoChecks.y",y));
        tilesLeft--;
		instrum(275,"PostFix",new Pair<>("uk.ac.york.minesweeper.Minefield.tilesLeft",tilesLeft));

        instrum(280,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.valuesArray",valuesArray),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverNoChecks.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverNoChecks.y",y));
		// Check for special tiles (0 and mines)
        if (valuesArray[x][y] == 0)
        {
            // Uncover all surrounding tiles
            processSurrounding(x, y, PROCESSOR_UNCOVER);
        }else {instrum(285,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.valuesArray",valuesArray),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverNoChecks.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverNoChecks.y",y));if (valuesArray[x][y] < 0){gameState=GameState.LOST;instrum(287,"Assign",new Pair<>("uk.ac.york.minesweeper.Minefield.gameState",gameState));uncoverAllMines();} else {instrum(291,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.tilesLeft",tilesLeft),new Pair<>("uk.ac.york.minesweeper.Minefield.gameState",gameState));if (tilesLeft <= 0 && gameState == GameState.RUNNING){gameState=GameState.WON;instrum(295,"Assign",new Pair<>("uk.ac.york.minesweeper.Minefield.gameState",gameState));uncoverAllMines();}}}
    }

    /**
     * Uncovers all mines if uncoverMinesAtEnd is set
     *
     * This does not uncover correctly flagged mines, but sets incorrectly
     * flagged mines to questions.
     */
    private void uncoverAllMines()
    {
        instrum(310,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverMinesAtEnd",uncoverMinesAtEnd));
		if (uncoverMinesAtEnd)
        {
            int width = getWidth();
            int height = getHeight();

            // Set state of all mines to uncovered
            for (int x = 0; x < width; x++)
            {
                instrum(316,"for",new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverAllMines.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverAllMines.width",width));
				for (int y = 0; y < height; y++)
                {
                    instrum(319,"for",new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverAllMines.y",y),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverAllMines.height",height));
					instrum(323,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.valuesArray",valuesArray),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverAllMines.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverAllMines.y",y));
					if (valuesArray[x][y] < 0)
                    {
                        instrum(327,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.stateArray",stateArray),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverAllMines.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverAllMines.y",y));
						// Uncover if not flagged
                        if (stateArray[x][y] != TileState.FLAGGED){stateArray[x][y]=TileState.UNCOVERED;instrum(327,"Assign",new Pair<>("uk.ac.york.minesweeper.Minefield.stateArray",stateArray),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverAllMines.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverAllMines.y",y));}
                    }
                    else
                    {
                        instrum(333,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.stateArray",stateArray),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverAllMines.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverAllMines.y",y));
						// Set flags to questions
                        if (stateArray[x][y] == TileState.FLAGGED){stateArray[x][y]=TileState.QUESTION;instrum(333,"Assign",new Pair<>("uk.ac.york.minesweeper.Minefield.stateArray",stateArray),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverAllMines.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.uncoverAllMines.y",y));}
                    }
                }
            }
        }
    }

    /** Tile processor for chord which uncovers a tile if it is not flagged */
    private final SurroundingTilesProcessor PROCESSOR_CHORD = new SurroundingTilesProcessor()
    {
        @Override
        public void process(int x, int y)
        {
            instrum(348,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.stateArray",stateArray),new Pair<>("uk.ac.york.minesweeper.Minefield.process.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.process.y",y));
			// Uncover non-flagged tiles
            if (stateArray[x][y] != TileState.FLAGGED)
                uncoverNoChecks(x, y);
        }
    };

    /**
     * Attempts to chord using the given central position
     *
     * Chording causes all the surrounding tiles to be uncovered if the number of
     * surrounding flags is equal to the value on the central tile.
     *
     * This method does nothing if the central tile is still covered of the number of
     * surrounding flags in incorrect.
     *
     * @param x x position of central tile
     * @param y y position of central tile
     */
    public void chord(int x, int y)
    {
        if (isFinished())
            throw new IllegalStateException("the game has finished");

        instrum(372,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.stateArray",stateArray),new Pair<>("uk.ac.york.minesweeper.Minefield.chord.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.chord.y",y));
		// Ensure the tile is uncovered
        if (stateArray[x][y] != TileState.UNCOVERED)
            return;

        instrum(377,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.valuesArray",valuesArray),new Pair<>("uk.ac.york.minesweeper.Minefield.chord.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.chord.y",y));
		// Check number of surrounding flags
        if (valuesArray[x][y] == countSurroundingFlags(x, y))
        {
            // Uncover all surrounding tiles which are not flagged
            processSurrounding(x, y, PROCESSOR_CHORD);
        }
    }

    /** Tile processor for initValues which increments a tile's value if it is not a mine */
    private final SurroundingTilesProcessor PROCESSOR_INIT_VALUES = new SurroundingTilesProcessor()
    {
        @Override
        public void process(int x, int y)
        {
            instrum(392,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.valuesArray",valuesArray),new Pair<>("uk.ac.york.minesweeper.Minefield.process.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.process.y",y));
			// Increment values which are not mines
            if (valuesArray[x][y] >= 0){valuesArray[x][y]++;instrum(392,"PostFix",new Pair<>("uk.ac.york.minesweeper.Minefield.valuesArray",valuesArray),new Pair<>("uk.ac.york.minesweeper.Minefield.process.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.process.y",y));}
        }
    };

    /**
     * Initializes the values grid for a new game
     *
     * startX and startY are used to prevent mines from appearing at the start location
     *
     * @param startX x position to prevent mines for
     * @param startY y position to prevent mines for
     */
    private void initValues(int startX, int startY)
    {
        int width = getWidth();
        int height = getHeight();

        // Randomly place all the mines
        Random rnd = new Random();

        for (int i = 0; i < mines; i++)
        {
            instrum(412,"for",new Pair<>("uk.ac.york.minesweeper.Minefield.initValues.i",i),new Pair<>("uk.ac.york.minesweeper.Minefield.mines",mines));
			int x, y;

            // Keep trying random positions until we've found an acceptable one
            do
            {
                x = rnd.nextInt(width);
				instrum(421,"Assign",new Pair<>("uk.ac.york.minesweeper.Minefield.initValues.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.initValues.rnd",rnd),new Pair<>("uk.ac.york.minesweeper.Minefield.initValues.width",width));
                y = rnd.nextInt(height);
				instrum(423,"Assign",new Pair<>("uk.ac.york.minesweeper.Minefield.initValues.y",y),new Pair<>("uk.ac.york.minesweeper.Minefield.initValues.rnd",rnd),new Pair<>("uk.ac.york.minesweeper.Minefield.initValues.height",height));
				instrum(424,"do",new Pair<>("uk.ac.york.minesweeper.Minefield.valuesArray",valuesArray),new Pair<>("uk.ac.york.minesweeper.Minefield.initValues.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.initValues.y",y),new Pair<>("uk.ac.york.minesweeper.Minefield.initValues.startX",startX),new Pair<>("uk.ac.york.minesweeper.Minefield.initValues.startY",startY));
            }
            while(valuesArray[x][y] < 0 || (x == startX && y == startY));

            // Set as a mine
            valuesArray[x][y] = -1;
			instrum(429,"Assign",new Pair<>("uk.ac.york.minesweeper.Minefield.valuesArray",valuesArray),new Pair<>("uk.ac.york.minesweeper.Minefield.initValues.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.initValues.y",y));

            // Increment number of mines in all surrounding tiles
            processSurrounding(x, y, PROCESSOR_INIT_VALUES);
        }
    }

    /**
     * Counts the number of flags surrounding a position
     *
     * @param x x position of central tile
     * @param y y position of central tile
     * @return number of surrounding flags
     */
    private int countSurroundingFlags(int x, int y)
    {
        int count = 0;
        int width = getWidth();
        int height = getHeight();

        instrum(451,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.y",y));
		if (y > 0)
        {
            instrum(454,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.x",x));
			if (x > 0){instrum(454,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.stateArray",stateArray),new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.y",y));if (stateArray[x - 1][y - 1] == TileState.FLAGGED){count++;instrum(452,"PostFix",new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.count",count));}}
                                instrum(454,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.stateArray",stateArray),new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.y",y));
								if (stateArray[x    ][y - 1] == TileState.FLAGGED){count++;instrum(453,"PostFix",new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.count",count));}
            instrum(455,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.width",width));
			if (x < width - 1){instrum(455,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.stateArray",stateArray),new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.y",y));if (stateArray[x + 1][y - 1] == TileState.FLAGGED){count++;instrum(453,"PostFix",new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.count",count));}}
        }

        instrum(457,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.x",x));
		if (x > 0){instrum(457,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.stateArray",stateArray),new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.y",y));if (stateArray[x - 1][y] == TileState.FLAGGED){count++;instrum(455,"PostFix",new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.count",count));}}
        instrum(457,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.width",width));
		if (x < width - 1){instrum(457,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.stateArray",stateArray),new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.y",y));if (stateArray[x + 1][y] == TileState.FLAGGED){count++;instrum(455,"PostFix",new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.count",count));}}

        instrum(458,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.y",y),new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.height",height));
		if (y < height - 1)
        {
            instrum(461,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.x",x));
			if (x > 0){instrum(461,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.stateArray",stateArray),new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.y",y));if (stateArray[x - 1][y + 1] == TileState.FLAGGED){count++;instrum(459,"PostFix",new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.count",count));}}
                                instrum(461,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.stateArray",stateArray),new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.y",y));
								if (stateArray[x    ][y + 1] == TileState.FLAGGED){count++;instrum(460,"PostFix",new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.count",count));}
            instrum(462,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.width",width));
			if (x < width - 1){instrum(462,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.stateArray",stateArray),new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.y",y));if (stateArray[x + 1][y + 1] == TileState.FLAGGED){count++;instrum(460,"PostFix",new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.count",count));}}
        }

        instrum(464,"return",new Pair<>("uk.ac.york.minesweeper.Minefield.countSurroundingFlags.count",count));
		return count;
    }

    /**
     * Calls a function on all the surrounding tiles which exist
     *
     * @param x x position of central tile
     * @param y y position of central tile
     */
    private void processSurrounding(int x, int y, SurroundingTilesProcessor processor)
    {
        int width = getWidth();
        int height = getHeight();

        instrum(479,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.y",y));
		if (y > 0)
        {
            instrum(482,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.x",x));
			if (x > 0){processor.process(x - 1,y - 1);instrum(481,"method call",new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.processor",processor),new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.y",y));}
                                processor.process(x    , y - 1);
								instrum(482,"method call",new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.processor",processor),new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.y",y));
            instrum(485,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.width",width));
			if (x < width - 1){processor.process(x + 1,y - 1);instrum(484,"method call",new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.processor",processor),new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.y",y));}
        }

        instrum(488,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.x",x));
		if (x > 0){processor.process(x - 1,y);instrum(487,"method call",new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.processor",processor),new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.y",y));}
        instrum(489,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.width",width));
		if (x < width - 1){processor.process(x + 1,y);instrum(488,"method call",new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.processor",processor),new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.y",y));}

        instrum(491,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.y",y),new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.height",height));
		if (y < height - 1)
        {
            instrum(494,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.x",x));
			if (x > 0){processor.process(x - 1,y + 1);instrum(493,"method call",new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.processor",processor),new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.y",y));}
                                processor.process(x    , y + 1);
								instrum(494,"method call",new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.processor",processor),new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.y",y));
            instrum(497,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.width",width));
			if (x < width - 1){processor.process(x + 1,y + 1);instrum(496,"method call",new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.processor",processor),new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.processSurrounding.y",y));}
        }
    }

    /**
     * Gets a string representing the minefield's current visible state
     */
    @Override
    public String toString()
    {
        int width = getWidth();
        int height = getHeight();

        StringBuilder builder = new StringBuilder();

        // Write top line
        builder.append('+');
		instrum(512,"method call",new Pair<>("uk.ac.york.minesweeper.Minefield.toString.builder",builder));
        for (int x = 0; x < width; x++) {builder.append('-');instrum(514,"method call",new Pair<>("uk.ac.york.minesweeper.Minefield.toString.builder",builder));}
        builder.append("+\n");
		instrum(515,"method call",new Pair<>("uk.ac.york.minesweeper.Minefield.toString.builder",builder));

        // Write each line of the minefield
        for (int y = 0; y < height; y++)
        {
            instrum(519,"for",new Pair<>("uk.ac.york.minesweeper.Minefield.toString.y",y),new Pair<>("uk.ac.york.minesweeper.Minefield.toString.height",height));
			builder.append('|');
			instrum(522,"method call",new Pair<>("uk.ac.york.minesweeper.Minefield.toString.builder",builder));

            for (int x = 0; x < width; x++)
            {
                instrum(525,"for",new Pair<>("uk.ac.york.minesweeper.Minefield.toString.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.toString.width",width));
				char c;

                instrum(532,"switch",new Pair<>("uk.ac.york.minesweeper.Minefield.toString.x",x),new Pair<>("uk.ac.york.minesweeper.Minefield.toString.y",y));
				// Handle each tile state
                switch (getTileState(x, y))
                {
                    case COVERED:
					{c='#';instrum(534,"Assign",new Pair<>("uk.ac.york.minesweeper.Minefield.toString.c",c));}
                        break;

                    case FLAGGED:
					{c='f';instrum(537,"Assign",new Pair<>("uk.ac.york.minesweeper.Minefield.toString.c",c));}
                        break;

                    case QUESTION:
					{c='?';instrum(540,"Assign",new Pair<>("uk.ac.york.minesweeper.Minefield.toString.c",c));}
                        break;

                    default:
                        // Show tile's value
                        int tileValue = getTileValue(x, y);

					{instrum(547,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.toString.tileValue",tileValue));if (tileValue < 0){c='!';instrum(546,"Assign",new Pair<>("uk.ac.york.minesweeper.Minefield.toString.c",c));} else {instrum(547,"if",new Pair<>("uk.ac.york.minesweeper.Minefield.toString.tileValue",tileValue));if (tileValue == 0){c=' ';instrum(546,"Assign",new Pair<>("uk.ac.york.minesweeper.Minefield.toString.c",c));} else {c=(char)('0' + tileValue);instrum(547,"Assign",new Pair<>("uk.ac.york.minesweeper.Minefield.toString.c",c),new Pair<>("uk.ac.york.minesweeper.Minefield.toString.tileValue",tileValue));}}}
                }

                builder.append(c);
				instrum(550,"method call",new Pair<>("uk.ac.york.minesweeper.Minefield.toString.builder",builder),new Pair<>("uk.ac.york.minesweeper.Minefield.toString.c",c));
            }

            builder.append("|\n");
			instrum(554,"method call",new Pair<>("uk.ac.york.minesweeper.Minefield.toString.builder",builder));
        }

        // Write bottom line
        builder.append('+');
		instrum(559,"method call",new Pair<>("uk.ac.york.minesweeper.Minefield.toString.builder",builder));
        for (int x = 0; x < width; x++) {builder.append('-');instrum(561,"method call",new Pair<>("uk.ac.york.minesweeper.Minefield.toString.builder",builder));}
        builder.append("+\n");
		instrum(562,"method call",new Pair<>("uk.ac.york.minesweeper.Minefield.toString.builder",builder));

        instrum(566,"return",new Pair<>("uk.ac.york.minesweeper.Minefield.toString.builder",builder));
		return builder.toString();
    }

    /**
     * Interface used for processing surrounding tiles
     */
    private interface SurroundingTilesProcessor
    {
        /**
         * Processes the given tile (which is guaranteed to exist)
         *
         * @param x x position of tile
         * @param y y position of tile
         */
        public void process(int x, int y);
    }
}
