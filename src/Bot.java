import javafx.scene.control.Button;
import javafx.util.Pair;
import java.util.HashSet;

public class Bot {
    /***
     * Move with random decision
     * @return decided coordinate
     */
    public int[] move() {
        // create random move
        return new int[]{(int) (Math.random() * 8), (int) (Math.random() * 8)};
    }
}