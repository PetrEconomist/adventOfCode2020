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
		//first adapter is included, e.i. one total combination, valid
		CrateCombinations combinations = new CrateCombinations(1, 0, 0, 0);
		/* adaptors' combination is represented as sequence of 0 and 1 where
		 * 1 stands for used adaptor
		 * 0 stands for missing adaptor (left-out or not available 
		 */ 
		for(int adapterValue = minAdapterValue+1; adapterValue<=maxAdapterValue;adapterValue++) {
			boolean adapterAvailable = inputSorted.contains(adapterValue);
			combinations = getCombinationsCount(adapterAvailable, combinations);
		}
		return combinations.combinationsCount;
	}

	
	
	/**
	 * Returns number of valid combinations, if previous combinations are extended by one element, which will be placed at the end 
	 * @param elementAvailable can I add element at the end (is available) or will it be empty?
	 * @param combinationss previous combinations
	 * @return new combinations, if 0/1 is added to the previous combinations
	 */
	private static CrateCombinations getCombinationsCount(boolean elementAvailable, CrateCombinations combinations) {
		/**
		 * After adding one element to existing combinations ending with "00" will became "000"
		 * It does not matter if element available, as it will influence count of new combinations
		 */
		long x000 = combinations.x00 - combinations.x000;
		/**
		 * After adding one element to existing combinations ending with "0" there are two options:
		 * - either *"0" was also *"00", then it will become invalid and therefore is not included anymore
		 * - or *"0" was combination of *"10" and then it will be valid
		 * As zero will be added (either as not available or as option "element left-out" x0 becomes x00, but without invalid combinations 
		 */
		long x00 = combinations.x0 - combinations.x000;
		
		if(elementAvailable) {
			//element available - two options (can be left-out or used)
			long newCombinations = combinations.combinationsCount * 2;
			//half of new combinations ends with zero (left-out element)
			long x0 = newCombinations / 2;
			return new CrateCombinations(newCombinations - x000, x000, x00, x0);
		}else {
			//element not available - only option of left-out element
			long newCombinations = combinations.combinationsCount;
			long x0 = newCombinations; //all new combinations end with 0;
			return new CrateCombinations(newCombinations - x000, x000, x00, x0);
		}
	}
	
	private static class CrateCombinations{
		//total count of combinations
		long combinationsCount;
		//count of combinations ending with "000"
		long x000;
		//count of combinations ending with "00"
		long x00;
		//count of combinations ending with "0"
		long x0;
		
		CrateCombinations(long combinationsCount, long x000, long x00, long x0){
			this.combinationsCount = combinationsCount;
			this.x000 = x000;
			this.x00 = x00;
			this.x0 = x0;
		}
	}
}
