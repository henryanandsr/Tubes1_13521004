import javafx.scene.control.Button;
import javafx.util.Pair;
import java.util.HashSet;

public class BotMinimax extends Bot{
    /***
     * Move with minimax alpha beta pruning algorithm
     * @param buttons matrix of buttons representing the board
     * @return best coordinate to move to
     */
    public int[] move(Button[][] buttons) {
        int c = 0;
        int max = Integer.MIN_VALUE;
        int mi = 0,mj = 0;
        HashSet<Pair<Integer, Integer>> uniquePairs = new HashSet<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (buttons[i][j].getText().equals("X") || buttons[i][j].getText().equals("O")) {
                    int[][] directions = {
                            {0, 1},
                            {0, -1},
                            {1, 0},
                            {-1, 0},
                    };
                    for (int[] dir : directions) {
                        int newX = i + dir[0];
                        int newY = j + dir[1];
                        if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                            uniquePairs.add(new Pair<>(newX, newY));
                        }
                    }
                }
            }
        }
        Pair<Integer, Integer>[] pairs = uniquePairs.toArray(new Pair[0]);
        for (Pair<Integer, Integer> pair : uniquePairs) {
            int i = pair.getKey();
            int j = pair.getValue();
            if (buttons[i][j].getText().equals("")) {
                c++;
                BoardState position = new BoardState(buttons);
                int minimaxValue = minimax(new BoardState(position, i, j, 'O'), 4, Integer.MIN_VALUE, Integer.MAX_VALUE, true, 0);
                if (max <= minimaxValue) {
                    max = minimaxValue;
                    mi = i;
                    mj = j;
                }
            }
        }
        int[] ret = {mi, mj};
        return ret;
    }

    public int minimax(BoardState position, int depth, int alpha, int beta, boolean maximizingPlayer, int count){
        if (depth == 0 || position.isFull()){
            return position.evaluateBoard();
        }
        if (maximizingPlayer){
            Integer maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (position.Positions[i][j] == '_') {
                        BoardState a = new BoardState(position,i,j,'O');
                        int eval = minimax(new BoardState(position, i, j, 'O'), depth-1, alpha, beta, false, count+1);
                        maxEval = Math.max(eval, alpha);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return maxEval;
        } else{
            Integer minEval = Integer.MAX_VALUE;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (position.Positions[i][j] == '_') {
                        int eval = minimax(new BoardState(position, i, j, 'X'), depth-1, alpha, beta, true, count+1);
                        minEval = Math.min(eval, beta);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return minEval;
        }
    }
}
