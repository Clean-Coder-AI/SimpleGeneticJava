package cc.suraj.evolution;

import org.junit.jupiter.api.Test;

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
        Individual fittest = population.getFittest(true);
        for (Individual individual :
                population.getIndividuals()) {
            assertTrue(fittest.getFitness(true) >= individual.getFitness(true));
        }
    }

    @Test
    void get_fittest_among_group_should_return_individual_with_highest_fitness_for_hot_temperature() {
        Population population = new Population(10, 10);
        Individual fittest = population.getFittestAmongGroup(true, 5);
        for (Individual individual :
                population.getIndividuals()) {
            assertTrue(fittest.getFitness(true) >= individual.getFitness(true));
        }
    }
}