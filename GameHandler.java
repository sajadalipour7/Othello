import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is a class for handling the game and core
 * This class controls and handle the core of the game
 */
public class GameHandler {
    private Player playerWhite;
    private Player playerBlack;
    private Board board;
    private Cell[][] map;
    private Scanner sc = new Scanner(System.in);

    public GameHandler(Board board) {
        this.map = board.getMap();
        this.board = board;
    }

    /**
     * This is a method to show the menu in a proper way
     */
    public void showMenu() {
        System.out.println("******************************");
        System.out.println("---------Othello Game---------\n");
        System.out.println("1) SinglePlayer Mode");
        System.out.println("2) MultiPlayer Mode");
        System.out.println("3) Exit");

    }

    /**
     * This is a method to initialize the players in multi player mode
     */
    public void initializePlayers() {
        String nameOfBlackPlayer, nameOfWhitePlayer;
        System.out.println("player white please enter your name :");
        nameOfWhitePlayer=sc.next();
        System.out.println("player black please enter your name :");
        nameOfBlackPlayer=sc.next();

        //nameOfWhitePlayer = "Player White";
        //nameOfBlackPlayer = "Player Black";
        playerWhite = new Player(nameOfWhitePlayer, "white");
        playerBlack = new Player(nameOfBlackPlayer, "black");

    }

    /**
     * This is a method to initialize the player in single player mode
     */
    public void initializeOnePlayer() {
        String nameOfBlackPlayer;
        System.out.println("player black please enter your name :");
        //nameOfBlackPlayer = "Sajad";
        nameOfBlackPlayer=sc.next();
        playerWhite = new BotPlayer("AI", "white");
        playerBlack = new Player(nameOfBlackPlayer, "black");
    }

    /**
     * This is a method to initialize the first four taws of the game
     */
    public void initializeFirstFourTaw() {
        changeColor(3, 3, 1);
        changeColor(3, 4, -1);
        changeColor(4, 3, -1);
        changeColor(4, 4, 1);
    }

    /**
     * This is the play method of single player mode
     * it controls the loop of the game
     *
     * @param modeAI
     */
    public void playSinglePlayer(int modeAI) {
        //The white player is our AI bot
        BotPlayer botPlayer = (BotPlayer) playerWhite;
        botPlayer.setModeAI(modeAI);
        //The loop of the game until the game finishes
        do {
            //clearing available moves because every round it changes
            playerWhite.clearAvailableMoves();
            playerBlack.clearAvailableMoves();
            //Handling player's turns
            playerTurn(playerBlack, -1);
            botPlayerTurn(playerWhite, 1);
        } while (!endGame());
        System.out.println();
        System.out.println(playerWhite.getName()+" : "+playerWhite.getSizeOfTaws());
        System.out.println(playerBlack.getName()+" : "+playerBlack.getSizeOfTaws());
        //if the game mode is normal
        if (botPlayer.getModeAI() == 1) {
            if (playerWhite.getSizeOfTaws() > playerBlack.getSizeOfTaws()) {
                System.out.println(playerWhite.getName() + " Won !");
            } else if (playerBlack.getSizeOfTaws() > playerWhite.getSizeOfTaws()) {
                System.out.println(playerBlack.getName() + " Won !");
            } else {
                System.out.println("draws !");
            }
            //if the game mode is reverse mode
        } else if (botPlayer.getModeAI() == 2) {
            if (playerWhite.getSizeOfTaws() < playerBlack.getSizeOfTaws()) {
                System.out.println(playerWhite.getName() + " Won !");
            } else if (playerBlack.getSizeOfTaws() < playerWhite.getSizeOfTaws()) {
                System.out.println(playerBlack.getName() + " Won !");
            } else {
                System.out.println("draws !");
            }
        }
    }

    /**
     * This is the play method of multi player mode
     * it controls the loop of the game
     */
    public void playMultiPlayer() {
        //The loop of the game until the game finishes
        do {
            //clearing available moves because every round it changes
            playerWhite.clearAvailableMoves();
            playerBlack.clearAvailableMoves();
            playerTurn(playerBlack, -1);
            playerTurn(playerWhite, 1);
        } while (!endGame());
        System.out.println();
        System.out.println(playerWhite.getName()+" : "+playerWhite.getSizeOfTaws());
        System.out.println(playerBlack.getName()+" : "+playerBlack.getSizeOfTaws());
        //printing who is the winner
        if (playerWhite.getSizeOfTaws() > playerBlack.getSizeOfTaws()) {
            System.out.println(playerWhite.getName() + " Won !");
        } else if (playerBlack.getSizeOfTaws() > playerWhite.getSizeOfTaws()) {
            System.out.println(playerBlack.getName() + " Won !");
        } else {
            System.out.println("draws !");
        }
    }

    /**
     * This is a method to handle the player's movements for the player which is it's round
     *
     * @param player
     * @param color
     */
    public void playerTurn(Player player, int color) {
        //showing map into terminal
        board.showMap();
        //announcing who is it's round
        System.out.println(player.getName() + " it's your turn!");
        System.out.println("Available places : ");
        //showing available places
        showAvailablePlaces(color);
        System.out.printf("\n");
        //checking if there is no available place
        if (player.availableLength() == 0) {
            System.out.println("pass");
            return;
        }
        //converting the inputs into a suitable way
        int positionX = sc.nextInt() - 1;
        int positionY = sc.next().charAt(0) - 'A';
        //checking for wrong or unavailable inputs
        while (!isItCorrectMove(positionX, positionY, player)) {
            System.out.println("Not a legal place to put your taw!");
            positionX = sc.nextInt() - 1;
            positionY = sc.next().charAt(0) - 'A';
        }
        //filling and changing the map after putting the taw
        fillAndChange(positionX, positionY, color);
        //an optional function to show players taw lists
        //showPlayersTawList();

    }

    /**
     * This is a method to handle the bot's movements
     *
     * @param player
     * @param color
     */
    public void botPlayerTurn(Player player, int color) {
        //casting the player into bot because of compilation things
        BotPlayer botPlayer = (BotPlayer) player;
        botPlayer.setMainMap(map);
        board.showMap();
        System.out.println("AI turn !");
        System.out.println("AI available places :");
        showAvailablePlaces(color);
        System.out.printf("\n");
        //checking if there is no available place
        if (player.availableLength() == 0) {
            System.out.println("pass");
            return;
        }
        //finding and setting the best place to put the taw for the AI bot
        botPlayer.findAndSetBestPlaceToPutTaw();
        //showing the choice of AI bot player
        System.out.println(xConvertor(botPlayer.getProperX()) + " " + yConvertor(botPlayer.getProperY()));
        //filling and changing the map after putting the taw
        fillAndChange(botPlayer.getProperX(), botPlayer.getProperY(), color);
        //an optional function to show players taw lists
        //showPlayersTawList();
    }

    /**
     * This is a method to check if the game is finished
     *
     * @return
     */
    public boolean endGame() {
        if (playerWhite.availableLength() == 0 && playerBlack.availableLength() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This is a method to check if a place is correct and available for a player
     *
     * @param x
     * @param y
     * @param player
     * @return
     */
    public boolean isItCorrectMove(int x, int y, Player player) {
        String thePlaceToPutTaw = "" + x + "" + y;
        ArrayList<String> availablePlaces = player.getAvailableMoves();
        for (String place : availablePlaces) {
            if (place.equals(thePlaceToPutTaw)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This is a method to change and fill the taw's after the player's move
     *
     * @param positionX
     * @param positionY
     * @param color
     */
    public void fillAndChange(int positionX, int positionY, int color) {
        //Calling the fillDirection method to all the eight ways
        //such as north-east-west-south-northEast-northWest-southEast-southWest
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
                if (map[positionX][positionY].getMode() == color * -1) {
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
        if (color == 1) {
            playerWhite.addCell(map[x][y]);
            playerBlack.removeCell(map[x][y]);
        } else if (color == -1) {
            playerBlack.addCell(map[x][y]);
            playerWhite.removeCell(map[x][y]);
        }
        map[x][y].setMode(color);
    }

    /**
     * This is a method to print all the available places for a player and adding them into availableMoves of player
     *
     * @param color
     */
    public void showAvailablePlaces(int color) {
        Player player = null;
        if (color == 1) {
            player = playerWhite;
        } else if (color == -1) {
            player = playerBlack;
        }
        //checking whole the board to find available places
        for (int i = 0; i < board.getRow(); i++) {
            for (int j = 0; j < board.getColumn(); j++) {
                boolean isSuitableForAddingToAvailableMoves = false;
                int positionX = i;
                int positionY = j;
                if (searchDirection(positionX, positionY, 1, 1, color)) {
                    isSuitableForAddingToAvailableMoves = true;

                }
                if (searchDirection(positionX, positionY, 1, 0, color)) {
                    isSuitableForAddingToAvailableMoves = true;

                }
                if (searchDirection(positionX, positionY, 1, -1, color)) {
                    isSuitableForAddingToAvailableMoves = true;

                }
                if (searchDirection(positionX, positionY, 0, 1, color)) {
                    isSuitableForAddingToAvailableMoves = true;

                }
                if (searchDirection(positionX, positionY, 0, -1, color)) {
                    isSuitableForAddingToAvailableMoves = true;

                }
                if (searchDirection(positionX, positionY, -1, 1, color)) {
                    isSuitableForAddingToAvailableMoves = true;

                }
                if (searchDirection(positionX, positionY, -1, 0, color)) {
                    isSuitableForAddingToAvailableMoves = true;

                }
                if (searchDirection(positionX, positionY, -1, -1, color)) {
                    isSuitableForAddingToAvailableMoves = true;

                }
                if (isSuitableForAddingToAvailableMoves) {
                    System.out.printf("%s %s , ", xConvertor(positionX), yConvertor(positionY));
                    //adding the place to availableMove list
                    player.addAvailableMoves("" + positionX + "" + positionY);
                }
            }
        }
    }

    /**
     * This is a method to print players tawsList
     */
    public void showPlayersTawList() {
        System.out.println("Player white:");
        playerWhite.print();
        System.out.println("Player black:");
        playerBlack.print();
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
        //checking if it is not empty
        if (map[startX][startY].getMode() == color || map[startX][startY].getMode() == -1 * color) {
            return false;
        }
        //checking if it is out of range
        if (positionX < 0 || positionX > 7 || positionY < 0 || positionY > 7) {
            return false;
        }
        //checking if it is the same color
        if (map[positionX][positionY].getMode() == color) {
            return false;
            //checking if it is empty
        } else if (map[positionX][positionY].getMode() == 0) {
            return false;
        }
        //going through the direction until arriving into the same color
        while (positionX >= 0 && positionX < 8 && positionY >= 0 && positionY < 8) {
            if (map[positionX][positionY].getMode() == -1 * color) {
                positionX += xDirection;
                positionY += yDirection;
            } else {
                break;
            }
        }
        //checking if it is out of range
        if (positionX < 0 || positionX > 7 || positionY < 0 || positionY > 7) {
            return false;
        }
        //checking if it has arrived into a same color
        if (map[positionX][positionY].getMode() == color) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This is a method to convert the X coordinate into a user friendly way
     *
     * @param x
     * @return
     */
    public String xConvertor(int x) {
        int tmp = x + 1;
        return "" + tmp;
    }

    /**
     * This is a method to convert the Y coordinate into a user friendly way
     *
     * @param y
     * @return
     */
    public String yConvertor(int y) {
        int t = y + 'A';
        char tmp = (char) t;
        return "" + tmp;
    }

}
