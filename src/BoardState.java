import javafx.scene.control.Button;

public class BoardState {
    public char[][] Positions = {
            {'_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_'},
            {'_','_','_','_','_','_','_','_'}};
    public int evaluateBoard(int pNumber) {
        int sum = 0;
        for (char[] row :
                Positions) {
            for (char mark :
                    row) {
                if (pNumber==1){
                    if (mark == 'O') {
                        sum--;
                    } else if (mark == 'X') {
                        sum++;
                    }
                }
                else{
                    if (mark == 'O') {
                        sum++;
                    } else if (mark == 'X') {
                        sum--;
                    }
                }
            }
        }
        return sum;
    }
    protected void mark(int x, int y, char placeholder){
        Positions[x][y] = placeholder;
    }
    public BoardState(Button[][] buttons) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String buttonText = buttons[i][j].getText();
                if (buttonText.equals("X")) {
                    mark(i, j, 'X');
                } else if (buttonText.equals(("O"))){
                    mark(i,j, 'O');
                }
            }
        }
    }
    public BoardState(BoardState src, int x, int y, char mark) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.Positions[i][j] = src.Positions[i][j];
            }
        }
        this.Positions[x][y] = mark;
        if (x-1 >= 0 && this.Positions[x-1][y]!=mark && this.Positions[x-1][y] != '_'){
            this.Positions[x-1][y] = mark;
        }
        if (x+1<=7 && this.Positions[x+1][y]!=mark && this.Positions[x+1][y] != '_'){
            this.Positions[x+1][y] = mark;
        }
        if (y+1<=7 && this.Positions[x][y+1]!=mark && this.Positions[x][y+1] != '_'){
            this.Positions[x][y+1] = mark;
        }
        if (y-1>=0 && this.Positions[x][y-1]!=mark && this.Positions[x][y-1] != '_'){
            this.Positions[x][y-1] = mark;
        }
    }

    public boolean isFull() {
        for (char[] row :
                Positions) {
            for (char i :
                    row) {
                if (i == '_') {
                    return false;
                }
            }
        }
        return true;
    }
}