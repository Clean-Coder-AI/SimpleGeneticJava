package cc.suraj.evolution;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PopulationTest {

    @Test
    void new_population_should_create_population_with_correct_number_of_individuals() {
        Population population = new Population(10, 10);
        assertEquals(10, population.getIndividuals().size());
    }

    @Test
    void get_fittest_should_return_individual_with_highest_fitness_for_hot_temperature() {
        Population population = new Population(10, 10);
        Individual fittest = population.getFittest(Climate.HOT);
        for (Individual individual :
                population.getIndividuals()) {
            assertTrue(fittest.getFitness(Climate.HOT) >= individual.getFitness(Climate.HOT));
        }
    }

    @Test
    void get_fittest_among_group_should_return_individual_with_highest_fitness_for_hot_temperature() {
        Population population = new Population(10, 10);
        Individual fittest = population.getFittestAmongGroup(Climate.COLD, 5);
        for (Individual individual :
                population.getIndividuals()) {
            assertTrue(fittest.getFitness(Climate.COLD) >= individual.getFitness(Climate.COLD));
        }
    }

    @Test
    void sorted_individuals_by_fitness_should_return_sorted_individuals() {
        Population population = new Population(50, 50);
        List<Individual> sortedIndividuals = population.sortedIndividualsByFitness(Climate.HOT);
        // Check if sorted
        for (int i = 0; i < sortedIndividuals.size() - 1; i++) {
            assertTrue(sortedIndividuals.get(i).getFitness(Climate.HOT) >= sortedIndividuals.get(i + 1).getFitness(Climate.HOT));
        }
    }

    @Test
    void average_fitness_should_improve_after_evolution_for_same_climate() {
        Population population = new Population(20000, 200);
//        System.out.println("Initial Population: " + population.getIndividuals());
        double initialAverageFitness = population.getAverageFitness(Climate.HOT);
        System.out.println("Initial Average Fitness: " + initialAverageFitness);
        Population newGen1 = population.evolve_v2(Climate.HOT, 0.05, 20);
//        System.out.println("New Population: " + newGen.getIndividuals());
        System.out.println("New Average Fitness 1: " + newGen1.getAverageFitness(Climate.HOT));
        System.out.println("New Average Fitness 1 for Cold: " + newGen1.getAverageFitness(Climate.COLD));
        assertTrue(newGen1.getAverageFitness(Climate.HOT) > initialAverageFitness);

        Population newGen2 = newGen1.evolve_v2(Climate.HOT, 0.05, 20);
//        System.out.println("New Population: " + newGen.getIndividuals());
        System.out.println("New Average Fitness 2: " + newGen2.getAverageFitness(Climate.HOT));
        System.out.println("New Average Fitness 2 for Cold: " + newGen2.getAverageFitness(Climate.COLD));
        assertTrue(newGen2.getAverageFitness(Climate.HOT) > initialAverageFitness);
    }
}