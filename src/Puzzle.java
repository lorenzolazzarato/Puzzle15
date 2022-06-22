import java.util.Date;
import java.util.Random;

public class Puzzle{
    private final int SHUFFLES_NUMBER = 10000;

    public int[][] board;
    public int[] blankPosition = new int[2]; // x and y of the empty cell
    public int size;

    public enum DIRECTION {UP, DOWN, LEFT, RIGHT};
    private Random rnd = new Random(new Date().getTime());

    public Puzzle(int size) {
        board = new int[size][size];
        this.size = size;
        // initialize in the final position
        int k = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = k;
                k++;
            }
        }
        // the 0 cell represents the empty cell. Now the board is in a completed state.
        board[size - 1][size - 1] = 0;
        blankPosition[0] = blankPosition[1] = size - 1;
        // shuffle the board to make it random
        shuffle(SHUFFLES_NUMBER);
    }

    public Puzzle(int[][] board) {
        this.board = new int[board.length][board[0].length];
        this.size = board.length;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = board[i][j];
                if(this.board[i][j] == 0) {
                    blankPosition[0] = i;
                    blankPosition[1] = j;
                }
            }
        }
    }

    public void shuffle(int shuffles) {
        for (int i = 0; i < shuffles; i++) {
            int k = rnd.nextInt(4);
            move(DIRECTION.values()[k]);
        }
    }

    // a move is swapping the 0 tile with an adjacent numbered tile
    public boolean move(DIRECTION direction) {
        int[] newPosition = new int[2];
        switch (direction) {
            case UP -> {
                newPosition[0] = blankPosition[0] - 1;
                newPosition[1] = blankPosition[1];
            }
            case DOWN -> {
                newPosition[0] = blankPosition[0] + 1;
                newPosition[1] = blankPosition[1];
            }
            case LEFT -> {
                newPosition[0] = blankPosition[0];
                newPosition[1] = blankPosition[1] - 1;
            }
            case RIGHT -> {
                newPosition[0] = blankPosition[0];
                newPosition[1] = blankPosition[1] + 1;
            }
        }

        // check if new position is out of bounds
        if(newPosition[0] < 0 || newPosition[0] >= size || newPosition[1] < 0 || newPosition[1] >= size) {
            return false;
        }
        // if it's not swap the 2 tiles and update the blank pos
        board[blankPosition[0]][blankPosition[1]] = board[newPosition[0]][newPosition[1]];
        board[newPosition[0]][newPosition[1]] = 0;
        blankPosition = newPosition;

        return true;
    }

    public boolean isSolved() {
        int k = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(board[i][j] != k && board[i][j] != 0) return false;
                k++;
            }
        }
        return true;
    }

    public static DIRECTION oppositeMove(DIRECTION move) {
        DIRECTION opposite = null;
        switch(move) {
            case UP -> {opposite = DIRECTION.DOWN; }
            case DOWN -> {opposite = DIRECTION.UP;}
            case LEFT -> {opposite = DIRECTION.RIGHT;}
            case RIGHT -> {opposite = DIRECTION.LEFT;}
        }
        return opposite;
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j <  size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

}
