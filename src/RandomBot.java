import javafx.scene.control.Button;
import javafx.util.Pair;
import java.util.HashSet;

public class RandomBot extends Bot{
    /***
     * Move with random decision
     * @return decided coordinate
     */
    public int[] move() {
        // create random move
        return new int[]{(int) (Math.random() * 8), (int) (Math.random() * 8)};
    }

    @Override
    public int[] move(Button[][] buttons, int pNumber) {
        return new int[0];
    }
}
