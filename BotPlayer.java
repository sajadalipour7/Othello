import java.util.ArrayList;

/**
 * This is a class for the AI bot Player and calculations for Artificial Intelligence.
 * Also this class extends from Player class.
 */
public class BotPlayer extends Player {
    private int modeAI;
    private int properX;
    private int properY;
    //This is the main map
    private Cell[][] mainMap;
    //This is a map for the next situations
    private Cell[][] nextMoveMap = new Cell[8][8];

    public BotPlayer(String name, String color) {
        super(name, color);
    }

    /**
     * This is a method to find the best place which is better to put taw
     * The best place in here means a place that will increase the most player's taw
     * My Algorithm is that I simulate all the next possible moves and pick the best one .
     */
    public void findAndSetBestPlaceToPutTaw() {
        ArrayList<String> availableBotMoves = getAvailableMoves();
        //This is an array to keep the number of bot's taws after next move
        int[] tawSizesAfterNextMoves = new int[availableBotMoves.size()];
        int nextMoveIterator = 0;
        for (String availableMove : availableBotMoves) {
            int x = availableMove.charAt(0) - '0';
            int y = availableMove.charAt(1) - '0';
            //I copy the main map because I don't want change the main map
            copyMainMapToNextMoveMap();
            fillAndChange(x, y, 1);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (nextMoveMap[i][j].getMode() == 1) {
                        tawSizesAfterNextMoves[nextMoveIterator]++;
                    }
                }
            }
            nextMoveIterator++;
        }
        int maximumTawSizeIndex = 0;
        int minimumTawSizeIndex = 0;
        for (int i = 0; i < availableBotMoves.size(); i++) {
            if (modeAI == 1) {
                if (tawSizesAfterNextMoves[i] > tawSizesAfterNextMoves[maximumTawSizeIndex]) {
                    maximumTawSizeIndex = i;
                }
            } else if (modeAI == 2) {
                if (tawSizesAfterNextMoves[i] < tawSizesAfterNextMoves[minimumTawSizeIndex]) {
                    minimumTawSizeIndex = i;
                }
            }
        }
        //Now if the game mode is normal I should set the maximum and if the game mode is reverse I should set the minimum
        if (modeAI == 1) {
            properX = availableBotMoves.get(maximumTawSizeIndex).charAt(0) - '0';
            properY = availableBotMoves.get(maximumTawSizeIndex).charAt(1) - '0';
        } else if (modeAI == 2) {
            properX = availableBotMoves.get(minimumTawSizeIndex).charAt(0) - '0';
            properY = availableBotMoves.get(minimumTawSizeIndex).charAt(1) - '0';
        }

    }

    /**
     * This is a method to copy main map into nextMoveMap
     */
    public void copyMainMapToNextMoveMap() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Cell tmp = new Cell(mainMap[i][j].getX(), mainMap[i][j].getY(), mainMap[i][j].getMode());
                nextMoveMap[i][j] = tmp;
            }
        }
    }

    /**
     * This is a method to change and fill the taw's after the player's move
     *
     * @param positionX
     * @param positionY
     * @param color
     */
    public void fillAndChange(int positionX, int positionY, int color) {
        fillDirection(positionX, positionY, color, 1, 1);
        fillDirection(positionX, positionY, color, 1, 0);
        fillDirection(positionX, positionY, color, 1, -1);
        fillDirection(positionX, positionY, color, 0, 1);
        fillDirection(positionX, positionY, color, 0, -1);
        fillDirection(positionX, positionY, color, -1, 1);
        fillDirection(positionX, positionY, color, -1, 0);
        fillDirection(positionX, positionY, color, -1, -1);
        changeColor(positionX, positionY, color);
    }

    /**
     * This is a method to fill and change in a direction way
     *
     * @param positionX
     * @param positionY
     * @param color
     * @param xDirection
     * @param yDirection
     */
    public void fillDirection(int positionX, int positionY, int color, int xDirection, int yDirection) {
        if (searchDirection(positionX, positionY, xDirection, yDirection, color)) {
            positionX += xDirection;
            positionY += yDirection;
            while (positionX >= 0 && positionX < 8 && positionY >= 0 && positionY < 8) {
                if (nextMoveMap[positionX][positionY].getMode() == color * -1) {
                    changeColor(positionX, positionY, color);
                    positionX += xDirection;
                    positionY += yDirection;
                } else {
                    break;
                }
            }
        }
    }

    /**
     * This is a method to change the color of a cell block
     *
     * @param x
     * @param y
     * @param color
     */
    public void changeColor(int x, int y, int color) {
        nextMoveMap[x][y].setMode(color);
    }

    /**
     * This is a method to check a place to understand is suitable to put taw in a specific direction
     *
     * @param positionX
     * @param positionY
     * @param xDirection
     * @param yDirection
     * @param color
     * @return
     */
    public boolean searchDirection(int positionX, int positionY, int xDirection, int yDirection, int color) {
        int startX = positionX;
        int startY = positionY;
        positionX += xDirection;
        positionY += yDirection;
        if (nextMoveMap[startX][startY].getMode() == color || nextMoveMap[startX][startY].getMode() == -1 * color) {
            return false;
        }
        if (positionX < 0 || positionX > 7 || positionY < 0 || positionY > 7) {
            return false;
        }
        if (nextMoveMap[positionX][positionY].getMode() == color) {
            return false;
        } else if (nextMoveMap[positionX][positionY].getMode() == 0) {
            return false;
        }
        while (positionX >= 0 && positionX < 8 && positionY >= 0 && positionY < 8) {
            if (nextMoveMap[positionX][positionY].getMode() == -1 * color) {
                positionX += xDirection;
                positionY += yDirection;
            } else {
                break;
            }
        }
        if (positionX < 0 || positionX > 7 || positionY < 0 || positionY > 7) {
            return false;
        }
        if (nextMoveMap[positionX][positionY].getMode() == color) {
            return true;
        } else {
            return false;
        }
    }

    public void setModeAI(int modeAI) {
        this.modeAI = modeAI;
    }

    public void setMainMap(Cell[][] mainMap) {
        this.mainMap = mainMap;
    }

    public int getModeAI() {
        return modeAI;
    }

    public int getProperX() {
        return properX;
    }

    public int getProperY() {
        return properY;
    }
}
