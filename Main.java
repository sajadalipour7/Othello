import java.util.Scanner;

/**
 * In the name of god
 * <p>
 * --------------------------
 * The Othello Game
 * --------------------------
 * This Project is the othello board game in computer
 * It includes Main,GameHandler,Board,Cell,Player,BotPlayer Classes
 * <p>
 * This class is the driver of project
 *
 * @author MohammadSajad Alipour
 * @version 1.0    2020
 */
public class Main {
    /**
     * Driver or main Function
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //A Menu infinity loop
        do {
            //Creating the board of game
            Board board = new Board();
            //Creating a gameHandler to handle the game
            GameHandler gameHandler = new GameHandler(board);
            gameHandler.showMenu();
            int mode = sc.nextInt();
            switch (mode) {
                //SinglePlayer mode
                case 1:
                    System.out.println("1) Normal Mode");
                    System.out.println("2) Reverse Mode (the player with less taws will win)");
                    int modeAI = sc.nextInt();
                    if (modeAI != 1 && modeAI != 2) {
                        System.out.println("Invalid Input!");
                        break;
                    }
                    gameHandler.initializeOnePlayer();
                    gameHandler.initializeFirstFourTaw();
                    gameHandler.playSinglePlayer(modeAI);

                    //gameHandler.playMultiPlayer();
                    break;
                //MultiPlayer mode
                case 2:
                    gameHandler.initializePlayers();
                    gameHandler.initializeFirstFourTaw();
                    gameHandler.playMultiPlayer();
                    break;
                //Exit mode
                case 3:
                    return;
                //handling Wrong or invalid input
                default:
                    System.out.println("Invalid Input!");
                    break;
            }
        } while (true);
    }
}
