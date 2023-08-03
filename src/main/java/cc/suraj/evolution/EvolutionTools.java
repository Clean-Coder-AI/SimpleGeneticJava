package cc.suraj.evolution;

public class EvolutionTools {
    // pass random number generator lambda as a parameter
    public static Individual crossover(Individual individual1, Individual individual2) {
        Individual individual = new Individual(individual1.getGenes().size());
        for (int i = 0; i < individual1.getGenes().size(); i++) {
            if (Math.random() < 0.5) {
                individual.getGenes().set(i, individual1.getGenes().get(i));
            } else {
                individual.getGenes().set(i, individual2.getGenes().get(i));
            }
        }
        return individual;
    }
}
