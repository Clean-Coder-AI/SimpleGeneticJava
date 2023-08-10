package cc.suraj.evolution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class EvolutionApplication implements CommandLineRunner {

	//Run simulation

	private static Logger LOG = LoggerFactory
			.getLogger(EvolutionApplication.class);

	public static void main(String[] args) {
		LOG.error("STARTING THE APPLICATION");
		SpringApplication.run(EvolutionApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		LOG.error("EXECUTING : command line runner");
//		//Create a population
//		Population population = new Population(30, 20);
//		Climate climate = Climate.HOT;
//		// Combine three logs into one
//		LOG.info("");
//		LOG.info("""
//				----------------------------------------
//				Initial average fitness for hot: %s
//				Initial average fitness for cold: %s
//				----------------------------------------
//				""".formatted(
//				Double.toString(population.getAverageFitness(Climate.HOT)),
//				Double.toString(population.getAverageFitness(Climate.COLD))));
//		Population population0 = population.evolve_v2(climate, 0.03, 30);
//		Population population1 = population.evolve_v2(climate, 0.03, 20);
//		Population population2 = population.evolve_v2(climate, 0.03, 10);
//		Population population3 = population.evolve_v2(climate, 0.03, 0);
//		for (int i = 0; i < 3; i++) {
//			LOG.info("""
//					------------POP1 30 Randomness----------------------------
//					Generation: %s
//					Average fitness for hot: %s
//					Average fitness for cold: %s
//					Fittest individual for hot: %s
//					Fittest individual for cold: %s
//					----------------------------------------
//					""".formatted(
//							Integer.toString(i),
//					Double.toString(population0.getAverageFitness(Climate.HOT)),
//					Double.toString(population0.getAverageFitness(Climate.COLD)),
//					Integer.toString(population0.getFittest(Climate.HOT).getFitness(Climate.HOT)),
//					Integer.toString(population0.getFittest(Climate.COLD).getFitness(Climate.COLD))));
//			LOG.info("""
//					------------POP2 20 Randomness----------------------------
//					Generation: %s
//					Average fitness for hot: %s
//					Average fitness for cold: %s
//					Fittest individual for hot: %s
//					Fittest individual for cold: %s
//					----------------------------------------
//					""".formatted(
//					Integer.toString(i),
//					Double.toString(population1.getAverageFitness(Climate.HOT)),
//					Double.toString(population1.getAverageFitness(Climate.COLD)),
//					Integer.toString(population1.getFittest(Climate.HOT).getFitness(Climate.HOT)),
//					Integer.toString(population1.getFittest(Climate.COLD).getFitness(Climate.COLD))));
//			LOG.info("""
//  					------------POP3 10 Randomness----------------------------
//					Generation: %s
//					Average fitness for hot: %s
//					Average fitness for cold: %s
//					Fittest individual for hot: %s
//					Fittest individual for cold: %s
//					----------------------------------------
//					""".formatted(
//					Integer.toString(i),
//					Double.toString(population2.getAverageFitness(Climate.HOT)),
//					Double.toString(population2.getAverageFitness(Climate.COLD)),
//					Integer.toString(population2.getFittest(Climate.HOT).getFitness(Climate.HOT)),
//					Integer.toString(population2.getFittest(Climate.COLD).getFitness(Climate.COLD))));
//			LOG.info("""
//   					------------POP4 0 Randomness----------------------------
//					Generation: %s
//					Average fitness for hot: %s
//					Average fitness for cold: %s
//					Fittest individual for hot: %s
//					Fittest individual for cold: %s
//					----------------------------------------
//					""".formatted(
//					Integer.toString(i),
//					Double.toString(population3.getAverageFitness(Climate.HOT)),
//					Double.toString(population3.getAverageFitness(Climate.COLD)),
//					Integer.toString(population3.getFittest(Climate.HOT).getFitness(Climate.HOT)),
//					Integer.toString(population3.getFittest(Climate.COLD).getFitness(Climate.COLD))));
//			population0 = population0.evolve_v2(climate, 0.03, 30);
//			population1 = population1.evolve_v2(climate, 0.03, 20);
//			population2 = population2.evolve_v2(climate, 0.03, 10);
//			population3 = population3.evolve_v2(climate, 0.03, 0);
//		}
//
//
//	}

	@Override
	public void run(String... args) throws Exception {
		// Create a population
		Population population = new Population(30, 20);
		Climate hot = Climate.HOT;
		Climate cold = Climate.COLD;

		List<Double> averageFitnessHot = new ArrayList<>();
		List<Double> averageFitnessCold = new ArrayList<>();
		List<Integer> fittestHot = new ArrayList<>();
		List<Integer> fittestCold = new ArrayList<>();

		averageFitnessHot.add(population.getAverageFitness(hot));
		averageFitnessCold.add(population.getAverageFitness(cold));
		fittestHot.add(population.getFittest(hot).getFitness(hot));
		fittestCold.add(population.getFittest(cold).getFitness(cold));
		// evolve the population for 30 generations
		for (int i = 0; i < 30; i++) {
			population = population.evolve_v2(hot, 0.03, 0);
			averageFitnessHot.add(population.getAverageFitness(hot));
			averageFitnessCold.add(population.getAverageFitness(cold));
			fittestHot.add(population.getFittest(hot).getFitness(hot));
			fittestCold.add(population.getFittest(cold).getFitness(cold));
		}
		// create a csv file with the columns: generation, average fitness hot, average fitness cold, fittest hot, fittest cold
		try (PrintWriter writer = new PrintWriter(new File("evolution.csv"))) {
			StringBuilder sb = new StringBuilder();
			sb.append("generation");
			sb.append(',');
			sb.append("average fitness hot");
			sb.append(',');
			sb.append("average fitness cold");
			sb.append(',');
			sb.append("fittest hot");
			sb.append(',');
			sb.append("fittest cold");
			sb.append('\n');

			for (int i = 0; i < 30; i++) {
				sb.append(i);
				sb.append(',');
				sb.append(averageFitnessHot.get(i));
				sb.append(',');
				sb.append(averageFitnessCold.get(i));
				sb.append(',');
				sb.append(fittestHot.get(i));
				sb.append(',');
				sb.append(fittestCold.get(i));
				sb.append('\n');
			}

			writer.write(sb.toString());

			LOG.info("done!");

		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage(), e);
		}


	}

}
