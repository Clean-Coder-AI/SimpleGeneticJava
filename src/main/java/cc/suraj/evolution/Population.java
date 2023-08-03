package cc.suraj.evolution;

import java.util.ArrayList;
import java.util.List;

public class Population {
    List<Individual> individuals = new ArrayList<>();
    public Population(int populationSize, int numberOfGenes) {
        for(int i = 0; i < populationSize; i++) {
            individuals.add(new Individual(numberOfGenes));
        }
    }

    public List<Individual> getIndividuals() {
        return individuals;
    }


    public Individual getFittest(boolean hot) {
        Individual fittest = individuals.get(0);
        for(int i = 0; i < individuals.size(); i++) {
            if(hot) {
                if(individuals.get(i).getFitness(true) > fittest.getFitness(true)) {
                    fittest = individuals.get(i);
                }
            } else {
                if(individuals.get(i).getFitness(false) > fittest.getFitness(false)) {
                    fittest = individuals.get(i);
                }
            }
        }
        return fittest;
    }

    public Individual tournamentSelection(boolean hot, int tournamentSize) {
        Population tournament = new Population(tournamentSize, individuals.get(0).getGenes().size());
        for(int i = 0; i < tournamentSize; i++) {
            int randomIndex = (int) (Math.random() * individuals.size());
            tournament.getIndividuals().set(i, individuals.get(randomIndex));
        }
        return tournament.getFittest(hot);
    }

    public Individual getFittestAmongGroup(boolean hot, int groupSize) {
        Population group = new Population(groupSize, individuals.get(0).getGenes().size());
        for(int i = 0; i < groupSize; i++) {
            int randomIndex = (int) (Math.random() * individuals.size());
            group.getIndividuals().set(i, individuals.get(randomIndex));
        }
        return group.getFittest(hot);
    }

    public Population evolve(boolean hot, double mutationRate, int tournamentSize, int randomSelectionSize) {
        Population newPopulation = new Population(individuals.size(), individuals.get(0).getGenes().size());
        for(int i = 0; i < randomSelectionSize; i++) {
            // get random individual from current population
            int randomIndex = (int) (Math.random() * individuals.size());
            newPopulation.getIndividuals().set(i, individuals.get(randomIndex));
        }
        for(int i = randomSelectionSize; i < individuals.size(); i++) {
            Individual individual1 = tournamentSelection(hot, tournamentSize);
            Individual individual2 = tournamentSelection(hot, tournamentSize);
            Individual individual = EvolutionTools.crossover(individual1, individual2);
            newPopulation.getIndividuals().set(i, individual.mutate(mutationRate));
        }
        return newPopulation;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < this.getIndividuals().size(); i++) {
            stringBuilder.append(this.getIndividuals().get(i).toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
