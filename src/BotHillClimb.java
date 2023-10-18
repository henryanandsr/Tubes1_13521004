import javafx.scene.control.Button;

public class BotHillClimb extends Bot{
    private int playerNumber;
    /***
     * Move with local search hill climbing algorithm
     * @param buttons matrix of buttons representing the board
     * @return best coordinate to move to
     */
    @Override
    public int[] move(Button[][] buttons, int pNumber) {
        this.playerNumber = pNumber;
        BoardState bs = new BoardState(buttons);
        return hillclimb(bs);
    }
    /***
     * Function to find the best valued step for O in a turn
     * @param input BoardState Object, the state of the game
     * @return BoardState local maximum for O
     */
    public int[] hillclimb(BoardState input) {
        int[] Pos = {-1, -1};
        int Val = Integer.MIN_VALUE;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (input.Positions[i][j] == '_'){
                    BoardState itr;
                    if (this.playerNumber == 1) {
                        itr = new BoardState(input, i, j, 'X');
                    } else {
                        itr = new BoardState(input, i, j, 'O');
                    }
                    if (itr.evaluateBoard(playerNumber) > Val) {
                        Pos[0] = i;
                        Pos[1] = j;
                        Val = itr.evaluateBoard(playerNumber);
                    }
                }
            }
        }
        return Pos;
    }
}
