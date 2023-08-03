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
		Population population = new Population(200, 100);
		LOG.error("Initial fittest individual: " + population.getFittest(true).getFitness(true));
		LOG.error("Initial population", population);
		boolean temperature = true;
		//Evolve population 100 times
		for (int i = 0; i < 1000; i++) {
//			randomize hot/cold temperature
			temperature = Math.random() < 0.5;
			population = population.evolve(temperature, 0.03, 10, 5);
		}

		//Print fittest individual
		LOG.error("Fittest individual: " + population.getFittest(temperature).getFitness(temperature));
		LOG.error("Population after evolution", population);

	}

}
