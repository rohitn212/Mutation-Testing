package uk.ac.york.minesweeper;

import static uk.ac.york.minesweeper.TemplateClass.instrum;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javafx.util.Pair;

/**
 * A component which can display a minefield graphically and handle various events
 */
public class MinefieldPanel extends JComponent
{
    private static final long serialVersionUID = 1L;

    /** Size of all the tiles */
    private static final int TILE_SIZE = 32;

    /** Width of the bevel */
    private static final int BEVEL_WIDTH = 2;

    /** Font vertical offset (from top to BASELINE) */
    private static final int FONT_VOFFSET = 24;

    /** The font to draw numbers with */
    private static final Font FONT = new Font(Font.MONOSPACED, Font.BOLD, 24);


    /** Default background colour */
    private static final Color COLOUR_BACKGROUND = new Color(0xC0, 0xC0, 0xC0);

    /** Light grey for bevels */
    private static final Color COLOUR_LIGHT = new Color(0xE0, 0xE0, 0xE0);

    /** Dark grey for bevels */
    private static final Color COLOUR_DARK = new Color(0x80, 0x80, 0x80);

    /** Colour of question marks */
    private static final Color COLOUR_QUESTION = Color.WHITE;

    /** The colours of the numbers (0 is unused) */
    private static final Color[] COLOUR_NUMBERS = new Color[]
    {
        null,                           // 0 = Unused
        new Color(0x00, 0x00, 0xFF),    // 1 = Blue
        new Color(0x00, 0x7F, 0x00),    // 2 = Green
        new Color(0xFF, 0x00, 0x00),    // 3 = Red
        new Color(0x2F, 0x2F, 0x9F),    // 4 = Dark Blue
        new Color(0x7F, 0x00, 0x00),    // 5 = Maroon
        new Color(0x9F, 0x9F, 0x2F),    // 6 = Turquoise
        new Color(0x00, 0x00, 0x00),    // 7 = Black
        new Color(0x7F, 0x7F, 0x7F),    // 8 = Grey
    };

    /** Current minefield */
    private Minefield minefield;

    /** Currently selected tile (null most of the time) */
    private Point selectedTile;

    /** List of state change listeners */
    private ArrayList<MinefieldStateChangeListener> listeners = new ArrayList<MinefieldStateChangeListener>();

    /**
     * Initializes a new MinefieldPanel with the given Minefield
     *
     * There must always be a minefield to display (you cannot pass null)
     *
     * @param minefield minefield to display
     */
    public MinefieldPanel(Minefield minefield)
    {
        this.addMouseListener(new MouseEventListener());
        this.setBackground(COLOUR_BACKGROUND);
		instrum(85,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.COLOUR_BACKGROUND",COLOUR_BACKGROUND));
        this.setOpaque(true);
        this.setFont(FONT);
		instrum(88,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.FONT",FONT));
        this.setMinefield(minefield);
		instrum(90,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.MinefieldPanel.minefield",minefield));
    }

    /**
     * Adds a listener to which received game state change events
     *
     * @param listener listener to add
     */
    public void addStateChangeListener(MinefieldStateChangeListener listener)
    {
        instrum(102,"if",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.listeners",listeners),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.addStateChangeListener.listener",listener));
		if (!listeners.contains(listener)){listeners.add(listener);instrum(102,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.listeners",listeners),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.addStateChangeListener.listener",listener));}
    }

    /**
     * Removes a listener which received game state change events
     *
     * @param listener listener to remove
     */
    public void removeStateChangeListener(MinefieldStateChangeListener listener)
    {
        listeners.remove(listener);
		instrum(112,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.listeners",listeners),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.removeStateChangeListener.listener",listener));
    }

    /**
     * Fires the state changed event
     */
    private void fireStateChangeEvent()
    {
        MinefieldStateChangeEvent event = new MinefieldStateChangeEvent(this);

        for (MinefieldStateChangeListener listener : listeners) {listener.stateChanged(event);instrum(123,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.fireStateChangeEvent.listener",listener),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.fireStateChangeEvent.event",event));}
    }

    /**
     * Gets the current minefield
     *
     * @return current minefield
     */
    public Minefield getMinefield()
    {
        instrum(134,"return",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.minefield",minefield));
		return minefield;
    }

    /**
     * Sets a new minefield for the component
     *
     * @param newMinefield the new minefield
     */
    public void setMinefield(Minefield newMinefield)
    {
        instrum(145,"if",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.setMinefield.newMinefield",newMinefield));
		if (newMinefield == null)
            throw new IllegalArgumentException("newMinefield cannot be null");

        this.minefield = newMinefield;
		instrum(148,"Assign",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.minefield",this.minefield),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.setMinefield.newMinefield",newMinefield));

        // Reset selected tile
        this.selectedTile = null;
		instrum(152,"Assign",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.selectedTile",this.selectedTile));

        // Update all visuals
        this.setSize(getPreferredSize());
        this.repaint();

        // Fire event
        this.fireStateChangeEvent();
    }

    /**
     * Draws a character on a tile
     *
     * @param g graphics object
     * @param x x position of top-left of tile
     * @param y y position of top-left of tile
     * @param c character to draw
     */
    private static void drawCharacter(Graphics g, int x, int y, char c)
    {
        // Get coordinates to draw at
        int drawX = x + (TILE_SIZE - g.getFontMetrics().charWidth(c)) / 2;
        int drawY = y + FONT_VOFFSET;

        // Draw the character
        g.drawChars(new char[] { c }, 0, 1, drawX, drawY);
		instrum(178,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.drawCharacter.g",g),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.drawCharacter.c",c),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.drawCharacter.drawX",drawX),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.drawCharacter.drawY",drawY));
    }

    /**
     * Draws an image at the given tile location
     *
     * @param g graphics object
     * @param tileX x position of top-left of tile
     * @param tileY y position of top-left of tile
     * @param img image to draw
     */
    private static void drawImage(Graphics g, int tileX, int tileY, BufferedImage img)
    {
        int xOff = tileX + (TILE_SIZE - img.getWidth()) / 2;
        int yOff = tileY + (TILE_SIZE - img.getHeight()) / 2;

        g.drawImage(img, xOff, yOff, null);
		instrum(195,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.drawImage.g",g),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.drawImage.img",img),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.drawImage.xOff",xOff),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.drawImage.yOff",yOff));
    }

    @Override
    public void paintComponent(Graphics gOld)
    {
        Graphics2D g = (Graphics2D) gOld;

        // Get selected tile position
        int selectedX = (selectedTile == null ? -1 : selectedTile.x);
        int selectedY = (selectedTile == null ? -1 : selectedTile.y);

        // Make the numbers look a little nicer
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		instrum(209,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.g",g));

        // Draw background
        if (isOpaque())
        {
            g.setColor(getBackground());
			instrum(215,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.g",g));
            g.fillRect(0, 0, getWidth(), getHeight());
			instrum(217,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.g",g));
        }

        // Draw all the tiles
        for (int x = 0; x < minefield.getWidth(); x++)
        {
            instrum(222,"for",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.x",x),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.minefield",minefield));
			for (int y = 0; y < minefield.getHeight(); y++)
            {
                instrum(225,"for",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.y",y),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.minefield",minefield));
				int graphicsX1 = x * TILE_SIZE;
                int graphicsY1 = y * TILE_SIZE;

                // Draw standard background
                g.setColor(COLOUR_DARK);
				instrum(232,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.g",g),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.COLOUR_DARK",COLOUR_DARK));
                g.drawLine(graphicsX1, graphicsY1, graphicsX1 + TILE_SIZE, graphicsY1);
				instrum(234,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.g",g),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.graphicsX1",graphicsX1),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.graphicsY1",graphicsY1),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.TILE_SIZE",TILE_SIZE));
                g.drawLine(graphicsX1, graphicsY1, graphicsX1, graphicsY1 + TILE_SIZE);
				instrum(236,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.g",g),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.graphicsX1",graphicsX1),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.graphicsY1",graphicsY1),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.TILE_SIZE",TILE_SIZE));

                instrum(241,"if",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.minefield",minefield),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.x",x),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.y",y));
				// Covered or uncovered?
                if (minefield.getTileState(x, y) == TileState.UNCOVERED)
                {
                    // Draw the correct symbol
                    int tileValue = minefield.getTileValue(x, y);

                    instrum(247,"if",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.tileValue",tileValue));
					if (tileValue < 0)
                    {
                        drawImage(g, graphicsX1, graphicsY1, Images.MINE);
                    }else {instrum(251,"if",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.tileValue",tileValue));if (tileValue > 0){g.setColor(COLOUR_NUMBERS[tileValue]);instrum(252,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.g",g),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.COLOUR_NUMBERS",COLOUR_NUMBERS),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.tileValue",tileValue));drawCharacter(g,graphicsX1,graphicsY1,(char)('0' + tileValue));}}
                }
                else
                {
                    instrum(261,"if",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.x",x),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.selectedX",selectedX),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.y",y),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.selectedY",selectedY));
					// Only draw the bevel background if this is NOT the selected tile
                    if (x != selectedX || y != selectedY)
                    {
                        int bevelX2 = graphicsX1 + TILE_SIZE - BEVEL_WIDTH;
                        int bevelY2 = graphicsY1 + TILE_SIZE - BEVEL_WIDTH;

                        g.setColor(COLOUR_LIGHT);
						instrum(266,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.g",g),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.COLOUR_LIGHT",COLOUR_LIGHT));
                        g.fillRect(graphicsX1, graphicsY1, TILE_SIZE, BEVEL_WIDTH);
						instrum(268,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.g",g),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.graphicsX1",graphicsX1),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.graphicsY1",graphicsY1),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.TILE_SIZE",TILE_SIZE),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.BEVEL_WIDTH",BEVEL_WIDTH));
                        g.fillRect(graphicsX1, graphicsY1, BEVEL_WIDTH, TILE_SIZE);
						instrum(270,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.g",g),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.graphicsX1",graphicsX1),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.graphicsY1",graphicsY1),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.BEVEL_WIDTH",BEVEL_WIDTH),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.TILE_SIZE",TILE_SIZE));
                        g.setColor(COLOUR_DARK);
						instrum(272,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.g",g),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.COLOUR_DARK",COLOUR_DARK));
                        g.fillRect(graphicsX1, bevelY2,    TILE_SIZE, BEVEL_WIDTH);
						instrum(274,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.g",g),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.graphicsX1",graphicsX1),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.bevelY2",bevelY2),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.TILE_SIZE",TILE_SIZE),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.BEVEL_WIDTH",BEVEL_WIDTH));
                        g.fillRect(bevelX2,    graphicsY1, BEVEL_WIDTH, TILE_SIZE);
						instrum(276,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.g",g),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.bevelX2",bevelX2),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.graphicsY1",graphicsY1),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.BEVEL_WIDTH",BEVEL_WIDTH),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.TILE_SIZE",TILE_SIZE));
                    }

                    instrum(282,"if",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.minefield",minefield),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.x",x),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.y",y));
					// Draw flag or question mark if needed
                    if (minefield.getTileState(x, y) == TileState.FLAGGED)
                    {
                        drawImage(g, graphicsX1, graphicsY1, Images.FLAG);
                    }else {instrum(286,"if",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.minefield",minefield),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.x",x),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.y",y));if (minefield.getTileState(x,y) == TileState.QUESTION){g.setColor(COLOUR_QUESTION);instrum(287,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.paintComponent.g",g),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.COLOUR_QUESTION",COLOUR_QUESTION));drawCharacter(g,graphicsX1,graphicsY1,'?');}}
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize()
    {
        instrum(300,"return",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.TILE_SIZE",TILE_SIZE),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.minefield",minefield));
		return new Dimension(TILE_SIZE * minefield.getWidth(),
                             TILE_SIZE * minefield.getHeight());
    }

    @Override
    public Dimension getMaximumSize()
    {
        return getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize()
    {
        return getPreferredSize();
    }

    /**
     * Handles all mouse events within the game area
     */
    private class MouseEventListener extends MouseAdapter
    {
        /**
         * Calculates the selected tile from a mouse event
         */
        private Point getTileFromEvent(MouseEvent e)
        {
            instrum(327,"return",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.getTileFromEvent.e",e),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.TILE_SIZE",TILE_SIZE));
			return new Point(e.getX() / TILE_SIZE, e.getY() / TILE_SIZE);
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
            instrum(335,"if",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.selectedTile",selectedTile));
			// Clear selected tile
            if (selectedTile != null)
            {
                selectedTile = null;
				instrum(337,"Assign",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.selectedTile",selectedTile));
                repaint();
            }
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            instrum(348,"if",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.minefield",minefield));
			// Ignore if finished
            if (minefield.isFinished())
                return;

            // Get tile position
            Point tile = getTileFromEvent(e);

            instrum(356,"if",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.mousePressed.e",e));
			// Right or left click?
            if (SwingUtilities.isLeftMouseButton(e))
            {
                instrum(360,"if",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.minefield",minefield),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.mousePressed.tile",tile));
				// Do not select tiles with flags on
                if (minefield.getTileState(tile.x, tile.y) == TileState.FLAGGED)
                    return;

                // Set new selected tile
                selectedTile = tile;
				instrum(364,"Assign",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.selectedTile",selectedTile),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.mousePressed.tile",tile));
            }else {instrum(367,"if",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.mousePressed.e",e));if (SwingUtilities.isRightMouseButton(e)){TileState newState;instrum(372,"switch",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.minefield",minefield),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.mousePressed.tile",tile));switch (minefield.getTileState(tile.x,tile.y)){case COVERED:{newState=TileState.FLAGGED;instrum(373,"Assign",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.mousePressed.newState",newState));}break;case FLAGGED:{newState=TileState.QUESTION;instrum(373,"Assign",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.mousePressed.newState",newState));}break;default:{newState=TileState.COVERED;instrum(373,"Assign",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.mousePressed.newState",newState));}break;case UNCOVERED:{newState=TileState.UNCOVERED;instrum(374,"Assign",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.mousePressed.newState",newState));}break;}minefield.setTileState(tile.x,tile.y,newState);instrum(377,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.minefield",minefield),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.mousePressed.tile",tile),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.mousePressed.newState",newState));}}

            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            instrum(389,"if",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.minefield",minefield));
			// Ignore if finished
            if (minefield.isFinished())
                return;

            instrum(394,"if",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.selectedTile",selectedTile));
			// Ensure there was a tile selected
            if (selectedTile != null)
            {
                instrum(398,"if",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.selectedTile",selectedTile),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.mouseReleased.e",e));
				// Ensure the tile was the same as the one clicked on
                if (selectedTile.equals(getTileFromEvent(e)))
                {
                    // Either chord or uncover depending on the number of clicks
                    GameState state = minefield.getGameState();

                    instrum(404,"if",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.mouseReleased.e",e));
					if (e.getClickCount() == 2){minefield.chord(selectedTile.x,selectedTile.y);instrum(404,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.minefield",minefield),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.selectedTile",selectedTile));}else {instrum(405,"if",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.mouseReleased.e",e));if (e.getClickCount() == 1){minefield.uncover(selectedTile.x,selectedTile.y);instrum(404,"method call",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.minefield",minefield),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.selectedTile",selectedTile));}}

                    instrum(408,"if",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.minefield",minefield),new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.mouseReleased.state",state));
					// Fire state changed event if needed
                    if (minefield.getGameState() != state)
                        fireStateChangeEvent();
                }

                // Clear selected tile
                selectedTile = null;
				instrum(413,"Assign",new Pair<>("uk.ac.york.minesweeper.MinefieldPanel.selectedTile",selectedTile));
                repaint();
            }
        }
    }
}
