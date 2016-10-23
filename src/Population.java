/* TCSS 342 - Spring 2016
 * Assignment 2 - Evolved Names
 * Jieun Lee
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Population class maintains a list of Genomes representing the current
 * population. It updates the list of Genomes every breeding cycle. It displays
 * the entire population and the most-fit individual in the population.
 * 
 * @author Jieun Lee
 * @version 1.0 (04-20-2016)
 */
public class Population {

	/**
	 * A data element that is equal to the most-fit Genome in the population.
	 */
	public Genome mostFit;

	/**
	 * The number of Genomes.
	 */
	private Integer myNumGenomes;

	/**
	 * A list of Genomes.
	 */
	private List<Genome> myPopulationList;

	/**
	 * Constructor that initializes a Population with a number of default
	 * genomes.
	 * 
	 * @param numGenomes The number of Genomes.
	 * @param mutationRate The mutation rate.
	 */
	public Population(Integer numGenomes, Double mutationRate) {
		myNumGenomes = numGenomes;
		myPopulationList = new ArrayList<Genome>();
		for (int i = 0; i < myNumGenomes; i++) {
			myPopulationList.add(new Genome(mutationRate));
		}
		mostFit = myPopulationList.get(0);
	}

	/**
	 * Called every breeding cycle.
	 */
	public void day() {

		// bubble sort: (215/325ms), (398/493ms), (279/390ms), ...
		bubbleSort(myPopulationList);

		// STEP
		// 1. update mostFit to most-fit Genome in the population. (this is
		// Genome w/ lowest fitness)
		mostFit = myPopulationList.get(0);

		// 2. delete the least-fit half of the population
		final List<Genome> newList = myPopulationList.subList(0, myPopulationList.size() / 2);
		myPopulationList = newList;

		// 3. create new Genomes from the remaining population until
		// the number of Genomes are restored by doing with equal chance
		for (int i = myPopulationList.size(); i < myNumGenomes; i++) {
			final Random random = new Random();
			final Genome tempGen;
			if (random.nextBoolean()) {
				// a. pick a remaining Genomes at random and clone it(copy
				// constructor) and mutate the clone
				tempGen = new Genome(myPopulationList.get(random.nextInt(myPopulationList.size())));
				tempGen.mutate();
			} else {
				// b. pick a remaining Genomes at random and clone it and then
				// cross over the clone
				// w/ another remaining Genomes selected at random and then
				// mutate the result
				tempGen = new Genome(myPopulationList.get(random.nextInt(myPopulationList.size())));
				tempGen.crossover(myPopulationList.get(random.nextInt(myPopulationList.size())));
				tempGen.mutate();
			}
			myPopulationList.add(tempGen);
		}
	}

	/**
	 * Displays the entire population and most-fit individual in the population.
	 * 
	 * @return The entire population and most-fit individual in the population.
	 */
	public String toString() {
		String result = null;
		for (Genome g : myPopulationList) {
			result += g + "\n";
		}
		return result;
	}

	/**
	 * Bubble sort.
	 * (codes from Canvas)
	 * 
	 * @param list The list.
	 */
	private static void bubbleSort(List<Genome> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			boolean inversion = false;
			for (int j = 0; j < list.size() - i - 1; j++) {
				// check for inversion between item j and j+1
				// if so swap them
				if (list.get(j).fitness() > list.get(j + 1).fitness()) {
					inversion = true;
					swap(list, j, j + 1);
				}
			}
			if (!inversion)
				break;
		}
	}

	/**
	 * Swaps given elements in the given list.
	 * (codes from Canvas)
	 * 
	 * @param list The list.
	 * @param i The elements of the list.
	 * @param j The elements of the list.
	 */
	private static void swap(List<Genome> list, int i, int j) {
		final Genome temp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, temp);
	}

}
