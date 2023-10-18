import javafx.scene.control.Button;
import javafx.util.Pair;

import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BotGenetic extends Bot{
    protected ArrayList<Specimen> specimens;

    public void generateSpecimens(BoardState boardState, int amount, int depth, int pNumber) {
        specimens = new ArrayList<Specimen>();
        for (int i = 0; i < amount; i++) {
            specimens.add(new Specimen(boardState, depth, pNumber));
        }
    }
    
    public class Specimen{
        private ArrayList<Pair<Integer, Integer>> chromosome = new ArrayList<>();
        private int depth;
        private BoardState boardState;
        private int pNumber;
        private int value;
        private int prob;
        
        
        public int getProb() {
            return prob;
        }

        public void setProb(int prob) {
            this.prob = prob;
        }

        private Pair<Integer,Integer> generateMovement() {
            return new Pair<>((int)Math.floor(Math.random()*8), (int)Math.floor(Math.random()*8));
        }
        
        public Specimen(BoardState bs, int depth, int pNumber){
            this.boardState = new BoardState(bs);
            this.depth = depth;
            this.pNumber = pNumber;
            this.generateChromosome();
        }

        public Specimen(Specimen copy){
            this.boardState = new BoardState(copy.boardState);
            this.depth = copy.depth;
            this.pNumber = copy.pNumber;
            this.generateChromosome();
        }

        public Pair<Integer, Integer> generateValidMove() {
            Pair<Integer, Integer> candidate;
            do {
                candidate = generateMovement();
            } while (!boardState.isValidMove(candidate));
            return candidate;
        }

        public void generateChromosome() {
            for (int i = 0; i < depth; i++) {
                chromosome.add(generateValidMove());
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
            for (int i = 0; i < chromosome.size(); i++) {
                if (i > crossPoint) {
                    offspring1.chromosome.set(i, other.chromosome.get(i));
                    offspring2.chromosome.set(i, this.chromosome.get(i));
                }
            }
            offsprings.add(offspring1);
            offsprings.add(offspring2);
            offsprings.get(0).duplicateMutation();
            offsprings.get(1).duplicateMutation();
            return offsprings;
        }

        public void mutate(int mutatePoint) {
            this.chromosome.set(mutatePoint, generateValidMove());
        }

        public void duplicateMutation() {
            Map<Pair<Integer,Integer>, Integer> frequencyMap = new HashMap<>();
            for (Pair<Integer, Integer> c :
                    this.chromosome) {
                frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
            }
            // if there are duplicates, change last indexes of duplicates into something valid
            for (Pair<Integer, Integer> c : frequencyMap.keySet()) {
                while (frequencyMap.get(c) != 1) {
                    this.mutate(this.chromosome.lastIndexOf(c));
                    // assuming the set operation will take care of duplicate, frequency would be subtracted by 1
                    frequencyMap.replace(c, frequencyMap.get(c)-1);
                }
            }
        }
        @Override
        public int hashCode() {
            return Objects.hash(this.chromosome, this.boardState, this.depth);
        }
        /**
         * @return total value
         */
        public int getLeafValue(){
            BoardState bs = new BoardState(this.boardState);
            for (int i = 0; i < this.chromosome.size(); i++) {
                if (this.pNumber == 1){
                    bs = new BoardState(bs, this.chromosome.get(i).getKey(), this.chromosome.get(i).getValue(), 'X');
                } else {
                    bs = new BoardState(bs, this.chromosome.get(i).getKey(), this.chromosome.get(i).getValue(), 'O');
                }
            }
            return bs.evaluateBoard(this.pNumber);
        }

        public int getValue(){
            return this.value;
        }

        public void setValue(int value){
            this.value = value;
        }

    }

    public void fitnessFunction(Button[][] buttons, int pNumber){
        BoardState bs = new BoardState(buttons);
        int p = pNumber;
        for (int i = 0; i < 4; i++) {
            if (p == 1){
                specimens.add(new Specimen(bs, 8, p));
                specimens.get(i).setValue(specimens.get(i).getLeafValue());
                p = 2;
            } else {
                specimens.add(new Specimen(bs, 8, p));
                specimens.get(i).setValue(specimens.get(i).getLeafValue());
                p = 1;
            }
        }
    }

    /**
     * calculate probability of the specimen's fitness
     */
    public void calculateProbs(){
        int total = 1;
        for (Specimen specimen : specimens) {
            total += specimen.getValue();
            if (specimen.getValue() == 0) {
                specimen.setProb(25);
            }
            if (total == 0){
                total = 25;
            }
            specimen.setProb((int) Math.ceil(specimen.getValue() / total));
        }
    }


    /**
     * 
     */

    @Override
    public int[] move(Button[][] buttons, int pNumber) {
        ArrayList<Specimen> parent = new ArrayList<>();
        ArrayList<Specimen> crossOverSpecimen = new ArrayList<>();
        generateSpecimens(new BoardState(buttons), 4, 8, pNumber);
        int val = 0;
        fitnessFunction(buttons, pNumber);
        calculateProbs();
        for (int i = 0; i < specimens.size(); i++) {
            val = (int) Math.ceil(Math.random()*100);
            if (val <= specimens.get(0).getProb()){
                parent.add(specimens.get(0));
            } else if (val <= specimens.get(0).getProb() + specimens.get(1).getProb() &&
                       val >  specimens.get(0).getProb()) {
                parent.add(specimens.get(1));
            } else if (val <= specimens.get(1).getProb() + specimens.get(2).getProb() &&
                       val >  specimens.get(0).getProb() + specimens.get(1).getProb()) {
                parent.add(specimens.get(2));
            } else {
                parent.add(specimens.get(3));
            }
        }

        crossOverSpecimen.addAll(parent.get(0).crossoverWith(parent.get(1)));
        crossOverSpecimen.addAll(parent.get(2).crossoverWith(parent.get(3)));
        
        int maxVal = Integer.MIN_VALUE;
        int index = -1;

        for (int i = 0; i < crossOverSpecimen.size(); i++) {
            crossOverSpecimen.get(i).mutate((int)Math.floor(Math.random()*8.0));
            crossOverSpecimen.get(i).duplicateMutation();
            if (maxVal < crossOverSpecimen.get(i).getLeafValue()){
                maxVal = crossOverSpecimen.get(i).getLeafValue();
                index = i;
            }
        }


        return new int[] {crossOverSpecimen.get(index).chromosome.get(0).getKey(), crossOverSpecimen.get(index).chromosome.get(0).getValue()};
    }

    public ArrayList<Specimen> getSpecimens() {
        return specimens;
    }

    public void setSpecimens(ArrayList<Specimen> specimens) {
        this.specimens = specimens;
    }
}
