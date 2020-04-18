/**
 * This is a class for the board of the game
 */
public class Board {
    private final int row = 8;
    private final int column = 8;
    private Cell[][] map = new Cell[row][column];

    public Board() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                map[i][j] = new Cell(i, j);
            }
        }

    }


    /**
     * This is a method to show the board in a proper way into terminal
     */
    public void showMap() {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";
        System.out.println("    A   B   C  D   E   F  G  H");
        for (int i = 0; i < row; i++) {
            System.out.printf(" %d ", i + 1);
            for (int j = 0; j < column; j++) {
                if (map[i][j].getMode() == -1) {
                    System.out.printf(" ⚪ ");
                } else if (map[i][j].getMode() == 1) {
                    System.out.printf(" ⚫ ");
                } else if (map[i][j].getMode() == 0) {
                    System.out.printf(ANSI_GREEN+" ◈ "+ANSI_RESET);
                }
            }
            System.out.printf("\n");
        }

    }

    public Cell[][] getMap() {
        return map;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}

