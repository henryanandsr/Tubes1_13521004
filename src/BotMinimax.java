import javafx.scene.control.Button;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
public class BotMinimax extends Bot{
    private int playerNumber;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final long TIMEOUT = 5000;
    private Pair<Integer, Integer>[] pairs;
    /***
     * Move with minimax alpha beta pruning algorithm
     * @param buttons matrix of buttons representing the board
     * @return best coordinate to move to
     */
    @Override
    public int[] move(Button[][] buttons, int pNumber) {
        this.playerNumber = pNumber;
        Callable<int[]> minimaxTask = () -> {
            int c = 0;
            int max = Integer.MIN_VALUE;
            int mi = 0, mj = 0;
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
                            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8 && buttons[newX][newY].getText().equals("")) {
                                uniquePairs.add(new Pair<>(newX, newY));
                            }
                        }
                    }
                }
            }
            for (Pair<Integer, Integer> pair : uniquePairs) {
                int i = pair.getKey();
                int j = pair.getValue();
                if (buttons[i][j].getText().equals("")) {
                    c++;
                    BoardState position = new BoardState(buttons);
                    int minimaxValue;
                    if (this.playerNumber == 1){
                        minimaxValue = minimax(new BoardState(position, i, j, 'X'), 3, Integer.MIN_VALUE, Integer.MAX_VALUE, false, 0);
                    } else {
                        minimaxValue = minimax(new BoardState(position, i, j, 'O'), 3, Integer.MIN_VALUE, Integer.MAX_VALUE, false, 0);
                    }
                    if (max < minimaxValue) {
                        max = minimaxValue;
                        mi = i;
                        mj = j;
                    }
                }
            }
            int[] ret = {mi, mj};
            return ret;
        };
        Future<int[]> future = executorService.submit(minimaxTask);
        try {
            return future.get(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            return getRandomMove(buttons);
        } finally {
            future.cancel(true);
        }
    }

    public int minimax(BoardState position, int depth, int alpha, int beta, boolean maximizingPlayer, int count){
        if (depth == 0 || position.isFull()){
            return position.evaluateBoard(playerNumber);
        }
        if (maximizingPlayer){
            Integer maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (position.Positions[i][j] == '_') {
                        int eval;
                        if (playerNumber == 1) {
                            eval = minimax(new BoardState(position, i, j, 'X'), depth-1, alpha, beta, false, count+1);
                        } else {
                            eval = minimax(new BoardState(position, i, j, 'O'), depth-1, alpha, beta, false, count+1);
                        }
                        maxEval = Math.max(eval, alpha);
                        alpha = Math.max(maxEval,alpha);
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
                        int eval;
                        if (playerNumber == 1) {
                            eval = minimax(new BoardState(position, i, j, 'O'), depth-1, alpha, beta, true, count+1);
                        } else {
                            eval = minimax(new BoardState(position, i, j, 'X'), depth-1, alpha, beta, true, count+1);
                        }
                        minEval = Math.min(eval, beta);
                        beta = Math.max(minEval,beta);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return minEval;
        }
    }
    private int[] getRandomMove(Button[][] buttons) {
        List<int[]> eligibleMoves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (buttons[i][j].getText().equals("")) {
                    eligibleMoves.add(new int[]{i, j});
                }
            }
        }
        Random random = new Random();
        int randomIndex = random.nextInt(eligibleMoves.size());
        return eligibleMoves.get(randomIndex);
    }
}
