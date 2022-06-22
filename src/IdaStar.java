import java.util.ArrayList;
import java.util.Stack;

public class IdaStar {

    public static ArrayList<Puzzle.DIRECTION> solve(Puzzle puzzle) {
        int bound = heuristic(puzzle);
        Stack<Puzzle> path = new Stack<>();
        path.push(puzzle);
        ArrayList<Puzzle.DIRECTION> moves = new ArrayList<>();

        while (true) {
            int t = search(path, 0, bound, moves);
            if(t < 0) {
                System.out.println("Found a solution in " + moves.size() + " moves.");
                System.out.println("Moves: " + moves);
                break;
            } else if(t == Integer.MAX_VALUE) {
                System.out.println("Couldn't find a solution. Check that the scheme is solvable.");
                break;
            }
            bound = t;
        }
        return moves;
    }

    // performs a DFS. Returns the special value -1 if the solution is found, else the next threshold
    private static int search(Stack<Puzzle> path, int g, int bound, ArrayList<Puzzle.DIRECTION> moves) {
        Puzzle node = path.peek();
        int f = g + heuristic(node);
        if(f > bound) return f;
        if(node.isSolved()) return -1;
        int min = Integer.MAX_VALUE;

        // push all directions in stack
        for (Puzzle.DIRECTION dir: Puzzle.DIRECTION.values()) {
            // if the last move done is the opposite of the current move skip this move
            if(!moves.isEmpty() && moves.get(moves.size() - 1) == Puzzle.oppositeMove(dir)) {
                continue;
            }

            Puzzle copy = new Puzzle(node.board);
            if(copy.move(dir) && !path.contains(copy)) {
                path.add(copy);
                moves.add(dir);
                int t = search(path, g + 1, bound, moves);

                if(t < 0) return t;
                else if (t < min) min = t;

                path.pop();
                moves.remove(moves.size() - 1);
            }
        }

        return min;

    }

    // sum of manhattan distance of every tile from his correct position
    public static int heuristic(Puzzle puzzle) {
        int sum = 0;
        for (int i = 0; i < puzzle.size; i++) {
            for (int j = 0; j < puzzle.size; j++) {
                int tile = puzzle.board[i][j];
                if(tile != 0) {
                    int row = (tile - 1) / 4;
                    int col = (tile - 1) % 4;
                    sum += Math.abs(i - row) + Math.abs(j - col);
                }
            }
        }
        return sum;
    }
}
