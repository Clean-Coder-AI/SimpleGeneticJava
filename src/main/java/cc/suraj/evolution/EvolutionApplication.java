package cc.suraj.evolution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EvolutionApplication implements CommandLineRunner {

	//Run simulation

	private static Logger LOG = LoggerFactory
			.getLogger(EvolutionApplication.class);

	public static void main(String[] args) {
		LOG.error("STARTING THE APPLICATION");
		SpringApplication.run(EvolutionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.error("EXECUTING : command line runner");
		//Create a population
		Population population = new Population(10, 20);
		Climate climate = Climate.HOT;
		// Combine three logs into one
		LOG.info("""
				----------------------------------------
				Initial population: %s
				Initial average fitness for hot: %s
				Initial average fitness for cold: %s
				----------------------------------------
				""".formatted(
				population.getIndividuals(),
				Double.toString(population.getAverageFitness(Climate.HOT)),
				Double.toString(population.getAverageFitness(Climate.COLD))));
		LOG.info("Initial population: " + population.getIndividuals());

		LOG.info("Initial average fitness for hot: " + population.getAverageFitness(Climate.HOT));
		LOG.info("Initial average fitness for cold: " + population.getAverageFitness(Climate.COLD));
		//Evolve population 100 times
		for (int i = 0; i < 3; i++) {
//			randomly select climate
//			climate = Math.random() < 0.5 ? Climate.HOT : Climate.COLD;
//			population = population.evolve(climate, 0.03, 10, 5);
			population = population.evolve_v2(climate, 0.03, 20);
//			LOG.info("""
//					----------------------------------------
//					Generation: %i
//					Average fitness for hot: %d
//					Average fitness for cold: %f
//					Fittest individual for hot: %f
//					Fittest individual for cold: %f
//					----------------------------------------
//					""".formatted(
//							i,
//					population.getAverageFitness(Climate.HOT),
//					population.getAverageFitness(Climate.COLD),
//					population.getFittest(Climate.HOT).getFitness(Climate.HOT),
//					population.getFittest(Climate.COLD).getFitness(Climate.COLD)));
			LOG.info("Population: " + population.getIndividuals());
		}

//		for (int i = 0; i < 500; i++) {
////			randomly select climate
////			climate = Math.random() < 0.5 ? Climate.HOT : Climate.COLD;
////			population = population.evolve(climate, 0.03, 10, 5);
//			population = population.evolve_v2(Climate.COLD, 0.03, 0);
//		}

		//Print fittest individual
		LOG.info("Fittest individual: " + population.getFittest(climate).getFitness(climate));
		Climate anotherClimate = climate == Climate.HOT ? Climate.COLD : Climate.HOT;
		LOG.info("Fittest individual for another climate: " + population.getFittest(anotherClimate).getFitness(anotherClimate));
		LOG.info("Final Average fitness: " + population.getAverageFitness(climate));
		// Print average fitness for another climate
		LOG.info("Final Average fitness for another climate: " + population.getAverageFitness(anotherClimate));
	}

}
