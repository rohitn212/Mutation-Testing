package uk.ac.york.minesweeper;

public class MinefieldPane extends Minefield {

    public MinefieldPane(int width, int height, int mines) {
        super(width, height, mines);
    }

    @Override
    public int getWidth() {
        return 4;
    }
}
