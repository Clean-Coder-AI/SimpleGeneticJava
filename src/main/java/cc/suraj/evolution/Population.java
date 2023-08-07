package cc.suraj.evolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Population {
    List<Individual> individuals = new ArrayList<>();
    public Population(int populationSize, int numberOfGenes) {
        for(int i = 0; i < populationSize; i++) {
            individuals.add(new Individual(numberOfGenes));
        }
    }

    public Population(List<Individual> individuals) {
        this.individuals = individuals;
    }

    public List<Individual> getIndividuals() {
        return individuals;
    }


    public Individual getFittest(Climate climate) {
        Individual fittest = individuals.get(0);
        for(int i = 0; i < individuals.size(); i++) {
            if(individuals.get(i).getFitness(climate) > fittest.getFitness(climate)) {
                fittest = individuals.get(i);
            }
        }
        return fittest;
    }

    public Individual tournamentSelection(Climate climate, int tournamentSize) {
        Population tournament = new Population(tournamentSize, individuals.get(0).getGenes().size());
        for(int i = 0; i < tournamentSize; i++) {
            int randomIndex = (int) (Math.random() * individuals.size());
            tournament.getIndividuals().set(i, individuals.get(randomIndex));
        }
        return tournament.getFittest(climate);
    }

    public Individual getFittestAmongGroup(Climate climate, int groupSize) {
        Population group = new Population(groupSize, individuals.get(0).getGenes().size());
        for(int i = 0; i < groupSize; i++) {
            int randomIndex = (int) (Math.random() * individuals.size());
            group.getIndividuals().set(i, individuals.get(randomIndex));
        }
        return group.getFittest(climate);
    }

    public Population evolve_v2(Climate climate, double mutationRate, double randomSelectionPercentage) {


        List<Individual> newIndividuals = new ArrayList<>();
        List<Individual> sortedIndividuals = sortedIndividualsByFitness(climate);
        // return empty list if the top individual has fitness less than 20% of gene size
        Individual topIndividual = sortedIndividuals.get(0);

        if(topIndividual.getFitness(climate) < 0.2 * topIndividual.getGenes().size()) {
//            System.out.println("Population Collapsed Wait Another Billion Years For Maybe Life");
            return new Population(0, 0);
        }

        // Select the top 20% of the population
        List<Individual> topIndividuals = sortedIndividuals.subList(0, (int) (0.2 * sortedIndividuals.size()));
        List<Individual> bottomIndividuals = sortedIndividuals.subList((int) (0.2 * sortedIndividuals.size()), sortedIndividuals.size());
        // For each individual in the top 20% choose a random individual from the top 20% and perform crossover 4 times
        List<Individual> topIndividualBabies = getTopBabies(mutationRate, topIndividuals);
//        System.out.println("average fitness of top " + getAverageFitnessOfGroup(topIndividuals, climate));
        newIndividuals.addAll(topIndividualBabies);
        // For randomSelectionPercentage get random individual from current population and crossover with
        // another random individual
        if (randomSelectionPercentage > 0) {
            List<Individual> randomBabies = mateRandomIndividuals(mutationRate, randomSelectionPercentage, sortedIndividuals);
//            System.out.println("average fitness of random: " + getAverageFitnessOfGroup(randomBabies, climate));
            newIndividuals.addAll(randomBabies);
        }

        if(newIndividuals.size() < sortedIndividuals.size()) {
            List<Individual> bottomBabies = getBottomBabies(mutationRate, newIndividuals, sortedIndividuals, bottomIndividuals);
//            System.out.println("average fitness of bottom: " + getAverageFitnessOfGroup(bottomBabies, climate));
            newIndividuals.addAll(bottomBabies);
        }

        return new Population(newIndividuals);
    }

    public double getAverageFitness(Climate climate) {
        double totalFitness = 0;
        for(int i = 0; i < individuals.size(); i++) {
            totalFitness += individuals.get(i).getFitness(climate);
        }
        return totalFitness / individuals.size();
    }

    public double getAverageFitnessOfGroup(List<Individual> individuals, Climate climate) {
        double totalFitness = 0;
        for(int i = 0; i < individuals.size(); i++) {
            totalFitness += individuals.get(i).getFitness(climate);
        }
        return totalFitness / individuals.size();
    }
    private static List<Individual> getBottomBabies(double mutationRate, List<Individual> newIndividuals, List<Individual> sortedIndividuals, List<Individual> bottomIndividuals) {
        List<Individual> bottomBabies = new ArrayList<>();
        // Mate bottom 80% of population until population size is reached
        for (int i = 0; i < sortedIndividuals.size() - newIndividuals.size(); i++) {
            Individual individual1 = bottomIndividuals.get(i);
            // Get random index from bottom 80% of population
            // Normally discard the last one unless
            int randomIndex = (int) (Math.random() * bottomIndividuals.size() - 1);
            if (randomIndex == i) {
                randomIndex++;
            }
            Individual individual2 = bottomIndividuals.get(randomIndex);
            Individual individual = EvolutionTools.crossover(individual1.mutate(mutationRate),
                    individual2.mutate(mutationRate));
            bottomBabies.add(individual);
        }
        return bottomBabies;
    }

    private static List<Individual> getTopBabies(double mutationRate, List<Individual> topIndividuals) {
        List<Individual> topIndividualBabies = new ArrayList<>();
        for(int i = 0; i < topIndividuals.size(); i++) {
            for(int j = 0; j < 3; j++) {
                Individual individual1 = topIndividuals.get(i);
                Individual individual2 = topIndividuals.get((int) (Math.random() * topIndividuals.size()));
                Individual individual = EvolutionTools.crossover(individual1.mutate(mutationRate),
                        individual2.mutate(mutationRate));
                topIndividualBabies.add(individual);
            }
        }
        return topIndividualBabies;
    }

    private List<Individual> mateRandomIndividuals(double mutationRate, double randomSelectionPercentage, List<Individual> sortedIndividuals) {
        List<Individual> newIndividuals = new ArrayList<>();
        for(int i = 0; i < (int) (randomSelectionPercentage * 0.01 * sortedIndividuals.size() * 0.4); i++) {
            // generate unique random numbers between 0 and sortedIndividuals.size()
            List<Integer> randomNumbers = new ArrayList<>();
            while(randomNumbers.size() < 2) {
                int randomNumber = (int) (Math.random() * sortedIndividuals.size());
                if(!randomNumbers.contains(randomNumber)) {
                    randomNumbers.add(randomNumber);
                }
            }
            Individual individual1 = individuals.get((int) (randomNumbers.get(0)));
            Individual individual2 = individuals.get((int) (randomNumbers.get(1)));
            Individual individual = EvolutionTools.crossover(individual1.mutate(mutationRate),
                    individual2.mutate(mutationRate));
            newIndividuals.add(individual);
        }
        return newIndividuals;
    }

    public Population evolve(Climate climate, double mutationRate, int tournamentSize, int randomSelectionSize) {
        Population newPopulation = new Population(individuals.size(), individuals.get(0).getGenes().size());
        for(int i = 0; i < randomSelectionSize; i++) {
            // get random individual from current population
            int randomIndex = (int) (Math.random() * individuals.size());
            newPopulation.getIndividuals().set(i, individuals.get(randomIndex));
        }
        for(int i = randomSelectionSize; i < individuals.size(); i++) {
            Individual individual1 = tournamentSelection(climate, tournamentSize);
            Individual individual2 = tournamentSelection(climate, tournamentSize);
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

    public List<Individual> sortedIndividualsByFitness(Climate climate) {
        return individuals.stream().sorted((individual1, individual2) -> {
            if(individual1.getFitness(climate) > individual2.getFitness(climate)) {
                return -1;
            } else if(individual1.getFitness(climate) < individual2.getFitness(climate)) {
                return 1;
            } else {
                return 0;
            }
        }).collect(Collectors.toList());
    }

}
