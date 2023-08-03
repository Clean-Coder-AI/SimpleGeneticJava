package cc.suraj.evolution;

import java.util.ArrayList;
import java.util.List;

public class Individual {
    List<Integer> genes = new ArrayList<>();
    public Individual(int numberOfGenes) {
        for(int i = 0; i < numberOfGenes; i++) {
            genes.add((int) Math.round(Math.random()));
        }
    }

    public List<Integer> getGenes() {
        return genes;
    }

    int getFitness(boolean hot) {
        int fitness = 0;
        for (int i = 0; i < genes.size(); i++) {
            if (hot) {
                if (genes.get(i) == 1) {
                    fitness++;
                }
            } else {
                if (genes.get(i) == 0) {
                    fitness++;
                }
            }
        }
        return fitness;
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

}
