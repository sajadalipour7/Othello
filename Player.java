import java.util.ArrayList;
import java.util.Iterator;

/**
 * This is a class for the players and their details and properties
 */
public class Player {
    private String name;
    private String color;
    //An ArrayList that includes all possible places in two character x and y
    private ArrayList<String> availableMoves = new ArrayList<>();
    //An ArrayList that includes all of the player's taws
    private ArrayList<Cell> tawList = new ArrayList<>();


    public Player(String name, String color) {
        this.name = name;
        this.color = color;
    }

    /**
     * This is a method to add a taw to the tawList
     *
     * @param tawToAdd
     */
    public void addCell(Cell tawToAdd) {
        tawList.add(tawToAdd);
    }

    /**
     * This is a method to remove a taw from tawList
     *
     * @param tawToRemove
     */
    public void removeCell(Cell tawToRemove) {
        Iterator<Cell> it = tawList.iterator();
        while (it.hasNext()) {
            Cell taw = it.next();
            if (taw.equals(tawToRemove)) {
                it.remove();
            }
        }
    }

    /**
     * This is a method to add an available place into availableMoves ArrayList.
     *
     * @param move
     */
    public void addAvailableMoves(String move) {
        availableMoves.add(move);
    }

    /**
     * This a method to print all the available places .
     */
    public void printAvailableMoves() {
        for (String s : availableMoves) {
            System.out.printf("%s ", s);
        }
    }

    /**
     * This is a method to clear available places list.
     */
    public void clearAvailableMoves() {
        availableMoves.clear();
    }

    /**
     * This is a method to print all of the player's taws
     */
    public void print() {
        for (Cell cell : tawList) {
            System.out.println(cell);
        }
    }

    public ArrayList<String> getAvailableMoves() {
        return availableMoves;
    }

    /**
     * This is a method to get the number of player's taws.
     *
     * @return
     */
    public int getSizeOfTaws() {
        return tawList.size();
    }

    /**
     * This is a method to get the number of available places.
     *
     * @return
     */
    public int availableLength() {
        return availableMoves.size();
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
