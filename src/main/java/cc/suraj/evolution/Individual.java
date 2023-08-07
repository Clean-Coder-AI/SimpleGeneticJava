package cc.suraj.evolution;

import java.util.ArrayList;
import java.util.List;

public class Individual {
    List<Integer> genes;
    public Individual(int numberOfGenes) {
        genes = generateRandomGene(numberOfGenes);
    }

    public List<Integer> generateRandomGene(int numberOfGenes) {
        int numberOfOnes = (int) (Math.random() * numberOfGenes);

        List<Integer> indices = generateRandomIndices(numberOfGenes, numberOfOnes);
        List<Integer> genes = new ArrayList<>();
        for(int i = 0; i < numberOfGenes; i++) {
            genes.add(0);
        }
        for(int i = 0; i < numberOfOnes; i++) {
            genes.set(indices.get(i), 1);
        }
        return genes;
    }

    public List<Integer> generateRandomIndices(int numberOfGenes, int numberOfOnes) {
        List<Integer> indices = new ArrayList<>();
        for(int i = 0; i < numberOfGenes; i++) {
            indices.add(i);
        }
        for(int i = 0; i < numberOfGenes - numberOfOnes; i++) {
            int randomIndex = (int) (Math.random() * indices.size());
            indices.remove(randomIndex);
        }
        return indices;
    }

    public Individual(List<Integer> genes) {
        this.genes = genes;
    }

    public List<Integer> getGenes() {
        return genes;
    }

    int getFitness(Climate climate) {
        return (climate == Climate.HOT) ?
                genes.stream().mapToInt(Integer::intValue).sum() :
                genes.size() - genes.stream().mapToInt(Integer::intValue).sum();
    }

    Individual cloneIndividual() {
        Individual individual = new Individual(this.genes.size());
        for (int i = 0; i < this.genes.size(); i++) {
            individual.getGenes().set(i, this.genes.get(i));
        }
        return individual;
    }

    Individual mutate(double mutationRate) {
        Individual individual = this.cloneIndividual();
        for (int i = 0; i < individual.getGenes().size(); i++) {
            if (Math.random() < mutationRate) {
                if (individual.getGenes().get(i) == 0) {
                    individual.getGenes().set(i, 1);
                } else {
                    individual.getGenes().set(i, 0);
                }
            }
        }
        return individual;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.getGenes().size(); i++) {
            stringBuilder.append(this.getGenes().get(i));
        }
        return stringBuilder.toString();
    }

}

enum Climate {
    HOT, COLD
}
