package days;

import java.util.LinkedList;
import java.util.List;
import collectionsConvertor.ListsConvertor;
import collectionsFunctions.ListFunctions;
import fileReader.ReadFile;

public class Day10Solution {


	private static final String FILE_NAME = "day10Input.txt";
	private static LinkedList<Integer> input;
	private static List<Integer> inputSorted;
	
	public static void main(String[] args) {
		//load raw input
		input = ListsConvertor.convertToInt(ReadFile.getFileAsList(FILE_NAME));
		input.add(0); // add charging outlet as beginning
		input.add(ListFunctions.getMaxInteger(input, 0, input.size()-1)+3); // add charging outlet as beginning
		//sort input
		inputSorted = ListsConvertor.sortIntList(input);
		System.out.printf("part1Solution: %d\n", part1Solution());
		System.out.printf("part2Solution: %d\n", part2Solution());
	}

	private static int part1Solution() {
		int index=0;
		int[] differences = new int[3];
		while(index<inputSorted.size()-1) {
			int nextIndex = index+1;
			int difference = inputSorted.get(nextIndex) - inputSorted.get(index);
			//System.out.printf("Adapter value %d -> value %d, difference %d\n", inputSorted.get(nextIndex), inputSorted.get(index), difference);
			differences[difference-1]++;
			index = nextIndex;
		}
		return (differences[0] * (differences[2]));
	}
	
	
	private static long part2Solution() {
		return adaptersCombinations();
	}
	
	
	/**
	 * Goes through adapters and counts possible combinations, based on rule that
	 * maximal difference between adapters is 3 
	 * @return
	 */
	private static long adaptersCombinations() {
		int minAdapterValue = inputSorted.get(0);
		int maxAdapterValue = inputSorted.get(inputSorted.size()-1);
		crateCombinations combinations = new crateCombinations(1, 0, 0, 0);
		for(int adapterValue = minAdapterValue+1; adapterValue<=maxAdapterValue;adapterValue++) {
			boolean adapterAvailable = inputSorted.contains(adapterValue);
			combinations =getCombinationsCount(adapterAvailable, combinations);
		}
		return combinations.combinationsCount;
	}

	
	
	/**
	 * Returns number of combinations
	 * @param haveOption1 can I add 1 at the end, or only 0?
	 * @param comCount count of combinations before adding new position at the beginning
	 * @param x000 count of combinations ending with 000
	 * @param x00 count of combinations ending with 00
	 * @return new combinations, if 0/1 is added to the previous combinations
	 */
	private static crateCombinations getCombinationsCount(boolean haveOption1, crateCombinations combinations) {
		//all combinations (without those invalid) ending with 00 became invalid after adding 0 at the end
		long x000 = combinations.x00 - combinations.x000;
		//if I add 0 to x0, I become x00, but I need to subtract invalid combinations x000 previously removed  
		long x00 = combinations.x0 - combinations.x000;
		if(haveOption1) {
			long newCombinations = combinations.combinationsCount * 2;
			long x0 = newCombinations / 2; //half of new combinations ends with 0;
			return new crateCombinations(newCombinations - x000, x000, x00, x0);
		}else {
			long newCombinations = combinations.combinationsCount; //does not change, only 0 added at the end
			long x0 = newCombinations; //all new combinations end with 0;
			return new crateCombinations(newCombinations - x000, x000, x00, x0);
		}
	}
	
	
	static long binomi(int n, int k) {
        if ((n == k) || (k == 0))
            return 1;
        else
            return binomi(n - 1, k) + binomi(n - 1, k - 1);
    }
	

	
	private static class crateCombinations{
		long combinationsCount;
		long x000;
		long x00;
		long x0;
		
		crateCombinations(long combinationsCount, long x000, long x00, long x0){
			this.combinationsCount = combinationsCount;
			this.x000 = x000;
			this.x00 = x00;
			this.x0 = x0;
		}
	}
}
