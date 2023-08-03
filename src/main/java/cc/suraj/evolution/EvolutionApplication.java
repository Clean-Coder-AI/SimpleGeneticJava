package cc.suraj.evolution;

import org.jline.utils.Log;
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

		//Create a population
		Population population = new Population(100, 100);
		Log.error("Initial fittest individual: " + population.getFittest(true).getFitness(true));

		//Evolve population
		population = population.evolve(true, 0.01, 5);

		//Print fittest individual
		LOG.error("Fittest individual: " + population.getFittest(true).getFitness(true));

	}

}
