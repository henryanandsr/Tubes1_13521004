import javafx.scene.control.Button;

public class BotHillClimb extends Bot{
    /***
     * Move with local search hill climbing algorithm
     * @param buttons matrix of buttons representing the board
     * @return best coordinate to move to
     */

    public int[] move(Button[][] buttons) {
        BoardState bs = new BoardState(buttons);
        return hillclimb(bs);
    }
    /***
     * Function to find the best valued step for O in a turn
     * @param input BoardState Object, the state of the game
     * @return BoardState local maximum for O
     */
    public int[] hillclimb(BoardState input) {
        int[] Pos = {-1, -1}; //index 0 and 1 is position values, index 2 is value of quantification
        int Val = Integer.MIN_VALUE;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (input.Positions[i][j] == '_'){
                    BoardState itr = new BoardState(input, i, j, 'O');
                    if (itr.evaluateBoard() > Val) {
                        Pos[0] = i;
                        Pos[1] = j;
                        Val = itr.evaluateBoard();
                    }
                }
            }
        }
        return Pos;
    }
}
