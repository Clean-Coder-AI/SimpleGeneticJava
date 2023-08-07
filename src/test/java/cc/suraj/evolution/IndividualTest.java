package cc.suraj.evolution;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IndividualTest {

    @Test
    void should_create_individual_with_correct_number_of_genes() {
        Individual individual = new Individual(10);
        assertEquals(10, individual.getGenes().size());
    }

    @Test
    void individual_should_have_genes_with_value_0_or_1() {
        Individual individual = new Individual(10);
        for (int gene :
                individual.getGenes()) {
            assertTrue(gene == 0 || gene == 1);
        }
    }

    @Test
    void get_fittest_should_return_correct_fitness_for_hot_temperature() {
        List<Integer> strongGenes = List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        Individual individual = new Individual(strongGenes);
        assertEquals(10, individual.getFitness(Climate.HOT));

        List<Integer> weakGenes = List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        individual = new Individual(weakGenes);
        assertEquals(0, individual.getFitness(Climate.HOT));
    }

    @Test
    void get_fittest_should_return_individual_with_highest_fitness_for_cold_temperature() {
        List<Integer> weakGenes = List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        Individual individual = new Individual(weakGenes);
        assertEquals(0, individual.getFitness(Climate.COLD));

        List<Integer> strongGenes = List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        individual = new Individual(strongGenes);
        assertEquals(10, individual.getFitness(Climate.COLD));
    }

    @Test
    void clone_individual_should_return_new_individual_with_same_genes() {
        Individual individual = new Individual(10);
        Individual clone = individual.cloneIndividual();
        for (int i = 0; i < individual.getGenes().size(); i++) {
            assertEquals(individual.getGenes().get(i), clone.getGenes().get(i));
        }
    }

    @Test
    void clone_individual_should_return_different_individual() {
        Individual individual = new Individual(10);
        Individual clone = individual.cloneIndividual();
        assertNotEquals(individual, clone);
    }

    @Test
    void mutate_should_not_change_individual_if_mutation_rate_is_0() {
        Individual individual = new Individual(10);
        Individual clone = individual.cloneIndividual();
        individual.mutate(0);
        // Assert values are equal
        for (int i = 0; i < individual.getGenes().size(); i++) {
            assertEquals(individual.getGenes().get(i), clone.getGenes().get(i));
        }
    }

    @Test
    void mutate_should_change_individual_if_mutation_rate_is_1() {
        Individual individual = new Individual(10);
        Individual clone = individual.cloneIndividual();
        individual.mutate(1);
        assertNotEquals(individual, clone);
    }

    @Test
    void mutation_should_change_individual_if_mutation_rate_is_0_5() {
        Individual individual = new Individual(20);
        // Output mutation result to console for ten runs
        for (int i = 0; i < 20; i++) {
            System.out.println(individual.mutate(0.04));
        }
        Individual clone = individual.cloneIndividual();
        individual.mutate(0.5);
        System.out.println(individual);
        assertNotEquals(individual, clone);
    }

    @Test
    void generate_random_indices_should_return_correct_number_of_indices() {
        Individual individual = new Individual(10);
        List<Integer> indices = individual.generateRandomIndices(10, 5);
        assertEquals(5, indices.size());
    }
}