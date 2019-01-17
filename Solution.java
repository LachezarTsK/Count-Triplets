import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Solution {

	/**
	 * Adds a new element to the HashMap.
	 * 
	 * Modifies the data in the HashMap as new input is processed.
	 */
	private static void addDataToMap(Map<Long, Triplet> firstElementToTriplets, Long input, Long ratio) {

		if (!firstElementToTriplets.containsKey(input)) {
			Triplet triplet = new Triplet(ratio);
			triplet.incrementTotalOfFirstElement();
			firstElementToTriplets.put(input, triplet);
		} else {
			firstElementToTriplets.get(input).incrementTotalOfFirstElement();
		}

		if (input % ratio == 0 && firstElementToTriplets.containsKey(input / ratio)) {
			firstElementToTriplets.get(input / ratio).incrementTotalOfCouples();
		}

		if (input % (ratio * ratio) == 0 && firstElementToTriplets.containsKey(input / (ratio * ratio))) {
			firstElementToTriplets.get(input / (ratio * ratio)).incrementTotalOfTriplets();
		}
	}

	/**
	 * Sums the total number of triplets for each key of the HashMap and prints the
	 * result.
	 */
	private static void printResult(Map<Long, Triplet> firstElementToTriplets) {
		long triplets = 0;
		for (Long key : firstElementToTriplets.keySet()) {
			triplets += firstElementToTriplets.get(key).getNumberOfTriplets();
		}
		System.out.print(triplets);
	}

	/**
	 * BufferedReader for faster input.
	 */
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int size = Integer.parseInt(st.nextToken());
		long ratio = Long.parseLong(st.nextToken());
		st = new StringTokenizer(br.readLine());

		/**
		 * Stores an input number as a key and the total of triplets for which this
		 * number is the first element.
		 */
		Map<Long, Triplet> firstElementToTriplets = new HashMap<Long, Triplet>();

		for (int index = 0; index < size; index++) {
			long input = Long.parseLong(st.nextToken());
			addDataToMap(firstElementToTriplets, input, ratio);
		}
		printResult(firstElementToTriplets);
	}
}

/**
 * The class calculates and stores the total number of triplets.
 * 
 * The first element of the triplet is a key in the HashMap.
 */
class Triplet {
	/**
	 * Stores the total number of first elements in the triplet.
	 */
	private long firstElement = 0;
	/**
	 * Stores the total number of couples that consist of the first and second
	 * element in the triplet.
	 */
	private long couplesOfFirstAndSecond = 0;
	/**
	 * Stores the total number of triplets.
	 */
	private long triplets = 0;
	private long ratio;

	public Triplet(long ratio) {
		this.ratio = ratio;
	}

	/**
	 * Each time when the input is equal to the first element, the number of its
	 * occurrences is incremented with one.
	 */
	public void incrementTotalOfFirstElement() {
		firstElement++;
	}

	/**
	 * Apart from the corner case when the ratio equals one:
	 * 
	 * Each time when (input/ratio) equals the first element, the total number of
	 * couples is increased with the current total of the first element.
	 */
	public void incrementTotalOfCouples() {
		if (ratio > 1) {
			couplesOfFirstAndSecond += firstElement;
		}
	}

	/**
	 * Apart from the corner case when the ratio equals one:
	 * 
	 * Each time when (input/(ratio*ratio)) equals the first element, the total
	 * number of triplets is increased with the current total of couples.
	 */
	public void incrementTotalOfTriplets() {
		if (ratio > 1) {
			triplets += couplesOfFirstAndSecond;
		}
	}

	/**
	 * Returns the total number of the already calculated triplets when the ratio is
	 * greater than one.
	 * 
	 * If it is a corner case, when the ratio equals one, then:
	 * 
	 * Calculates the total number of triplets, applying the formula for combination
	 * of 'n' elements taken 'k' at a time without repetition.
	 */
	public long getNumberOfTriplets() {
		if (ratio == 1) {
			triplets = (firstElement * (firstElement - 1) * (firstElement - 2)) / (3 * 2);
		}
		return triplets;
	}
}
