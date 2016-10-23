/* TCSS 342 - Spring 2016
 * Assignment 2 - Evolved Names
 * Jieun Lee
 */

/**
 * This Main class is for the Evolved Name project.
 * 
 * @author Jieun Lee
 * @version 1.0 (04-20-2016)
 */
public class Main {

	/**
	 * Runs the Evolved Name project.
	 * 
	 * @param args The args.
	 */
	public static void main(String[] args) {

		// stores the start time for runtime.
		final long start = System.currentTimeMillis();

		// Use 100 genomes and mutation rate of 0.05
		final Population pop = new Population(100, 0.05);
		int generateCount = 0;

		// until the fitness of the most fit genome is zero,
		while (pop.mostFit.fitness() != 0) {
			pop.day();
			generateCount++;

			// prints simultion progress
			System.out.println(pop.mostFit);
		}

		System.out.println("Generations: " + generateCount);

		// runtime statistics
		System.out.println("Running Time: " + (System.currentTimeMillis() - start) + " milliseconds");

		// tests Genome class.
//		 testGenome();

		// tests Population class.
//		 testPopulation();
	}

	/**
	 * Tests Genome class.
	 */
	public static void testGenome() {
		System.out.println("=========== testGenome() ===========");

		// both Genomes should start with initial letter 'A'
		final Genome g = new Genome(0.5);
		System.out.println("g: initial " + g.toString());

		// mutate test for g
		for (int i = 1; i <= 10; i++) {
			g.mutate();
			System.out.println(i + " times matated: " + g.toString());
		}

		System.out.println();

		final Genome g2 = new Genome(0.5);
		System.out.println("g2: initial " + g2.toString());
		
		// mutate test for g2.
		for (int i = 1; i <= 10; i++) {
			g2.mutate();
			System.out.println(i + " times matated: " + g2.toString());
		}

		// crossover test
		g.crossover(g2);
		System.out.println("after g & g2 crossover: " + g.toString());

	}

	/**
	 * Tests Population class.
	 */
	public static void testPopulation() {
		System.out.println("=========== testPopulation() ===========");

		final Population pop = new Population(15, 0.5);

		System.out.println("pop list: " + pop.toString());
		System.out.println("most fit: " + pop.mostFit);

		// after day(), check most-fit
		pop.day();
		System.out.println("pop day: " + pop.toString());
		System.out.println("most fit: " + pop.mostFit);

	}

}
