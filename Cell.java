import java.util.Objects;

/**
 * This is class for cell blocks of the game board
 * <p>
 * if the mode is 0 ---> empty cell
 * if the mode is 1 ---> white Taw
 * if the mode is -1 ---> black Taw
 */
public class Cell {
    private int x;
    private int y;
    private int mode;

    public Cell(int x, int y) {
        this.mode = 0;
        this.x = x;
        this.y = y;
    }

    public Cell(int x, int y, int mode) {
        this.mode = mode;
        this.x = x;
        this.y = y;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                ", mode=" + mode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x &&
                y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
