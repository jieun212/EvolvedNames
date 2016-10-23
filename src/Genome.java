/* TCSS 342 - Spring 2016
 * Assignment 2 - Evolved Names
 * Jieun Lee
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Genome class have some internal representation of the string of characters.
 * Genome class is able to mutate, to crossover with another Genome and to
 * measure Genome fitness.
 * 
 * @author Jieun Lee
 * @version 1.0 (04-20-2016)
 */
public class Genome {
	
	/**
	 * A target String.
	 */
	public String target = "CHRISTOPHER PAUL MARRIOTT";

	/**
	 * A character list.
	 */
	// http://stackoverflow.com/questions/2482078/how-to-get-a-random-letter-from-a-list-of-specific-letters
	public static List<Character> CHARACTERS = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ', '-', '\'');

	/**
	 * A mutation rate.
	 */
	private double myMutationRate;

	/**
	 * A current character list.
	 */
	private List<Character> myGenomeList;

	/**
	 * Constructor that initializes a Genome with value 'A'and assigns the
	 * internal mutation rate.
	 * 
	 * @param mutationRate The mutation rate is between zero and one.
	 */
	public Genome(double mutationRate)  {
		myMutationRate = mutationRate;
		myGenomeList = new ArrayList<Character>();
		myGenomeList.add('A');
	}

	/**
	 * A copy constructor that initializes a Genome with the same values as the
	 * input gene.
	 * 
	 * @param gene The Genome.
	 */
	public Genome(Genome gene) {
		myMutationRate = gene.myMutationRate;
		myGenomeList = new ArrayList<Character>();
		for (Character c : gene.myGenomeList) {
			myGenomeList.add(c);
		}
	}

	/**
	 * Mutates the string in this Genome by adding a new character, deleting a
	 * randomly selected character, and changing a character.
	 */
	public void mutate() {
		final Random random = new Random();

		// how to generate random number between 0.0 and 1.0
		// for (Math.random() * (1.0 - 0.0) + 0.0)
		// http://java67.blogspot.com/2015/01/how-to-get-random-number-between-0-and-1-java.html

		// http://stackoverflow.com/questions/2482078/how-to-get-a-random-letter-from-a-list-of-specific-letters
		// for "random.nextInt(CHARACTERS.size())"

		// adds a randomly selected character to a randomly selected position in
		// the string.
		if (Math.random() * 1.0 < myMutationRate) {
			myGenomeList.add(random.nextInt(myGenomeList.size()), CHARACTERS.get(random.nextInt(CHARACTERS.size())));
		}

		// deletes a single character from randomly selected position of the
		// string,
		// but do this only if the string has length at least 2.
		if (Math.random() * 1.0 < myMutationRate && myGenomeList.size() > 1) {
			myGenomeList.remove(random.nextInt(myGenomeList.size()));
		}

		// replaces each char in the string by a randomly selected char.
		for (int i = 0; i < myGenomeList.size(); i++) {
			if (Math.random() * 1.0 < myMutationRate) {
				myGenomeList.set(i, CHARACTERS.get(random.nextInt(CHARACTERS.size())));
			}
		}
	}

	/**
	 * Updates the current Genome by crossing it over with other.
	 * 
	 * @param other The other Genome.
	 */
	public void crossover(Genome other) {
		// creates new line by these steps for each index in the string starting
		// at the first index.
		final List<Character> newList = new ArrayList<Character>();
		final Random random = new Random();
		int max = Math.max(myGenomeList.size(), other.myGenomeList.size());

		for (int i = 0; i < max; i++) {
			// randomly choose one of the two parent string.
			// if the parent string has a character at this index, copy that
			// character into the new list
			// otherwise, end the new list here.
			if (random.nextBoolean() && i < myGenomeList.size()) {
				newList.add(myGenomeList.get(i));
			} else if (i < other.myGenomeList.size()) {
				newList.add(other.myGenomeList.get(i));
			} else {
				break;
			}
		}
		myGenomeList = newList;
	}

	/**
	 * Returns the fitness of the Genome.
	 * 
	 * @return The fitness of the Genome.
	 */
	public Integer fitness() {

		/*
		 * #1. Easy algorithm, wrote this to compare the result
		 */
		// Step 1. n = length of current string / m = length of target string
		final int n = myGenomeList.size();
		final int m = target.length();

		// Step 2. l be the max(n, m);
		final int l = Math.max(n, m);

		// Step 3. f be initialized to |m-n|
		int f = Math.abs(m - n); // -> length penalty

		// Step 4. For each character position i <= i <= l, add one to f if
		// the character in the current string is different from the character
		// in the target string (or if one of the two characters does not exist)
		for (int i = 0; i < l; i++) {
			if (i < myGenomeList.size() && i < m) {
				if (myGenomeList.get(i) != target.charAt(i)) {
					f++;
				}
			}
			if (myGenomeList.size() + i < l) {
				f++;
			}
		}
		return f;

		
		/* DON'T USE this algorithm !!!! it takes more than 3000ms
		 * 
		 * #2. Wagner-Fischer algorithm
		 */
		
//		// Step 1. currentLength = length of current string / targetLength =
//		// length of target string
//		final int currentLength = myCurrentList.size();
//		final int targetLength = TARGET.length();
//
//		// Step 2. create matrix D[n+1][m+1], initialized with 0s
//		final int[][] matrix = new int[currentLength + 1][targetLength + 1];
//
//		// Step 3. fill 1st row with column indices and fill the 1st column with
//		// row indices
//		for (int i = 0; i <= currentLength; i++) {
//			matrix[i][0] = i;
//		}
//		for (int i = 1; i <= targetLength; i++) { // i start with 1 because
//			// matrix[0][0] already has value.
//			matrix[0][i] = i;
//		}
//
//		// Step 4. implement 'the nested loop' to fill in the rest of the
//		// matrix.
//		// (provided by Dr.Marriott)
//		for (int i = 1; i <= currentLength; i++) {
//			for (int j = 1; j <= targetLength; j++) {
//				if (myCurrentList.get(i - 1) == TARGET.charAt(j - 1)) {
//					matrix[i][j] = matrix[i - 1][j - 1];
//				} else {
//					matrix[i][j] = Math.min(Math.min(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1),
//							matrix[i - 1][j - 1] + 1);
//				}
//			}
//		}
//
//		// Step 5. returns integer value = D[n, m] + (Math.abs(n-m)+1)/2
//		return matrix[currentLength][targetLength] + (Math.abs(currentLength - targetLength) + 1) / 2;
	}

	/**
	 * Displays the Genome's character string and fitness in an easy to read
	 * format.
	 * 
	 * @return The Genome's character string and fitness.
	 */
	public String toString() {
		String result = "(\"";
		for (Character c : myGenomeList) {
			result += c;
		}
		result += "\", ";
		result += fitness();
		result += ")";
		return result;
	}

}
