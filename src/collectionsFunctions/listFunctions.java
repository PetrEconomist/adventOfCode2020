package collectionsFunctions;

import java.util.LinkedList;

public class listFunctions {
	
	/**
	 * Finds minimal value in given input
	 * @param input where minimum is to be found
	 * @param beginningPos starting position (including)
	 * @param endPos ending position (including)
	 * @return minimal value
	 */
	public static double getMin(LinkedList<Double> input, int beginningPos, int endPos) {
		double min = input.get(beginningPos);
		for(int pos = beginningPos+1;pos<=endPos;pos++) {
			if(input.get(pos)<min) min = input.get(pos);
		}
		return min;
	}
	
	/**
	 * Finds maximal value in given input
	 * @param input where maximum is to be found
	 * @param beginningPos starting position (including)
	 * @param endPos ending position (including)
	 * @return maximal value
	 */
	public static double getMax(LinkedList<Double> input, int beginningPos, int endPos) {
		double max = input.get(beginningPos);
		for(int pos = beginningPos+1;pos<=endPos;pos++) {
			if(input.get(pos)>max) max = input.get(pos);
		}
		return max;
	}
	
}
