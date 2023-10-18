import javafx.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Specimen {
    private ArrayList<Pair<Integer, Integer>> Chromosome = new ArrayList<>();
    private BoardState boardState;
    private int depth;

    //TODO: can be optimized because towards the end of game there are lesser squares to random from
    private Pair<Integer,Integer> generateMove() {
        return new Pair<>((int)Math.floor(Math.random()*8), (int)Math.floor(Math.random()*8));
    }

    public Specimen(BoardState boardState, int depth) {
        this.boardState = new BoardState(boardState);
        this.depth = depth;
        this.generateChromosome();
    }

    public Specimen(Specimen src) {
        this.boardState = new BoardState(src.boardState);
        this.depth = src.depth;
        this.Chromosome = new ArrayList<>(src.Chromosome);
    }

    public Pair<Integer, Integer> generateValidMove() {
        Pair<Integer, Integer> candidate;
        do {
            candidate = generateMove();
        } while (!boardState.isValidMove(candidate));
        return candidate;
    }

    public void generateChromosome() {
        for (int i = 0; i < depth; i++) {
            Chromosome.add(generateValidMove());
        }
    }

    /**
     * One-point crossover genetic operation
     * @param other Specimen to cross over with
     * @return Offspring specimens
     */
    public ArrayList<Specimen> crossoverWith(Specimen other) {
        ArrayList<Specimen> offsprings = new ArrayList<>();
        int crossPoint = (int) Math.floor(Math.random()*(this.depth-1));
        Specimen offspring1 = new Specimen(this);
        Specimen offspring2 = new Specimen(other);
        for (int i = 0; i < Chromosome.size(); i++) {
            if (i > crossPoint) {
                offspring1.Chromosome.set(i, other.Chromosome.get(i));
                offspring2.Chromosome.set(i, this.Chromosome.get(i));
            }
        }
        offsprings.add(offspring1);
        offsprings.add(offspring2);
        return offsprings;
    }

    /**
     * Mutation operations to take care of duplicate values
     * @param mutatePoint index to do operation
     */
    public void mutate(int mutatePoint) {
        this.Chromosome.set(mutatePoint, generateValidMove());
    }

    public void duplicateMutation() {
        Map<Pair<Integer,Integer>, Integer> frequencyMap = new HashMap<>();
        for (Pair<Integer, Integer> c :
                this.Chromosome) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        // if there are duplicates, change last indexes of duplicates into something valid
        for (Pair<Integer, Integer> c :
            frequencyMap.keySet()) {
            while (frequencyMap.get(c) != 1) {
                this.mutate(this.Chromosome.lastIndexOf(c));
                // assuming the set operation will take care of duplicate, frequency would be subtracted by 1
                frequencyMap.replace(c, frequencyMap.get(c)-1);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof Specimen)){
            return false;
        }
        Specimen s = (Specimen) o;
        return Objects.equals(this.Chromosome, s.Chromosome) &&
                Objects.equals(this.boardState, s.boardState) &&
                Objects.equals(this.depth, s.depth);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.Chromosome, this.boardState, this.depth);
    }

    // TODO: getting leaf value
//    public int getLeafValue(){
//        // Leaf Value is calculated like minimax
//        // and then calculated from boardState.evaluateBoard()
//        return 0;
//    };
    // TODO: getting specimen value
//     public int getSpecimenValue() {
//        // Specimen value is calculated by depth - getLeafValue() + 1
//         return 0;
//     }
}
