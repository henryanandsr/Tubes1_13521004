import javafx.scene.control.Button;

import java.util.ArrayList;

public class BotGenetic extends Bot{
    private ArrayList<Specimen> Specimens;
    @Override
    public int[] move(Button[][] buttons) {
        return new int[0];
    }
    public void generateSpecimens(BoardState boardState, int amount, int depth) {
        for (int i = 0; i < amount; i++) {
            Specimens.add(new Specimen(boardState, depth));
        }
    }

}
