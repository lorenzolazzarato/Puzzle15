import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Trying to solve the following scheme:");
        // new int[][]{{0, 12, 9, 13},{15, 11, 10, 14},{3, 7, 2, 5}, {4, 8, 6, 1}} combinazione piu difficile
        double somma = 0;
        double nmoves = 0;
        for (int i = 0; i < 10; i++) {
            System.out.println("Puzzle numero " + i);
            Puzzle puzzle = new Puzzle(4);
            puzzle.print();
            Instant start = Instant.now();
            ArrayList<Puzzle.DIRECTION> moves = IdaStar.solve(puzzle);
            Instant end = Instant.now();
            nmoves += moves.size();
            somma += Duration.between(start, end).getSeconds();
        }

        System.out.println("-------------------------------------");
        System.out.println("Media secondi: " + somma / 10);
        System.out.println("Media mosse: " + nmoves / 10);


        //puzzle.print();
    }
}
