package numberFunctions;

public class NumberFunctions {
	
	/**
	 * Reduces range (from min to max) to lower or upper half, based on criterion (e.i. selection of the half) 
	 * @param min minimal range
	 * @param max maximal range
	 * @param minHalf code for selection of lower half
	 * @param maxHalf code for selection of upper half
	 * @param half code for half selection
	 * @return halved range, its min and max
	 */
	public static int[] halfRange(int min, int max, char lHalf, char uHalf, char half) {
		int rangeHalf = (max - min)/2;
		if(half==lHalf) {
			max = min + rangeHalf;
		}else if(half==uHalf) {
			min = min + 1 + rangeHalf;
		}else {
			throw new IllegalArgumentException("Selected half " + half + " is invalid, "
					+ " can be either " + lHalf + " or " + uHalf);
		}
		return new int[] {min, max};
	}
}
