package combinations;


public class Combinations {
	
	public static void main(String[] args) {
		createCombinationsTest();
	}
	private static void createCombinationsTest() {
		String[] options = {"a", "b", "c"};
		String[] combinations = createCombinations(options, 3);
		for(int combID = 0; combID < combinations.length; combID++) {
			//System.out.print("combination " + combID + ": " + combinations[combID] + "\n");
			System.out.print(combinations[combID] + "\n");
		}
	}
	
	public static String[] createCombinations(String[] options, int positions) {
		String[] combinations = options; //first position equals options
		//start with second postions (e.i. from 1 and not from 0)
		for(int position = 1; position < positions; position++) {
			combinations = extendCombinations(options, combinations);
		}
		return combinations;
	}
	
	private static String[] extendCombinations(String[] options, String[] toBeExtended) {
		int totalCombinationsCount = toBeExtended.length * options.length;
		String[] combinations = new String[totalCombinationsCount];
		for(int combinationID = 0; combinationID < totalCombinationsCount; combinationID++) {
			String oldPart = toBeExtended[combinationID % toBeExtended.length];
			String newPart = ";" + options[combinationID / toBeExtended.length];
			String combination = oldPart + newPart;
			combinations[combinationID] = combination;
		}
		return combinations;
	}
}
