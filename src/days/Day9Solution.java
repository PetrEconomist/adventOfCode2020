package days;

import java.util.HashSet;
import java.util.LinkedList;

import collectionsConvertor.listsConvertor;
import collectionsFunctions.listFunctions;
import fileReader.ReadFile;

public class Day9Solution {

	private static final String FILE_NAME = "day9Input.txt";
	private static final int CONSIDER_PREVIOUS_COUNT = 25;
	private static LinkedList<Double> input;
	
	
	public static void main(String[] args) {
		//load raw input
		input = listsConvertor.convertToDouble(ReadFile.getFileAsList(FILE_NAME));
		double part1Output = part1Solution();
		System.out.printf("part1Solution: %f\n", part1Output);
		System.out.printf("part2Solution: %f\n", part2Solution(part1Output));
	}

	private static double part1Solution() {
		int testedPos = CONSIDER_PREVIOUS_COUNT + 1;
		while(true) {
			if(!isValidNumber(testedPos)) {
				return input.get(testedPos);
			}
			testedPos++;
		}
	}
	
	private static double part2Solution(double sum) {
		int[] positions = getContinuousSet(sum);
		double min = listFunctions.getMin(input, positions[0], positions[1]);
		double max = listFunctions.getMax(input, positions[0], positions[1]);
		return min+max;
	}
	
	/**
	 * Finds a contiguous set of at least two numbers in input 
	 * which sum to the given sum
	 * @param sum sum which we want to find
	 * @return positions of the end and the beginning of the range with sum
	 */
	private static int[] getContinuousSet(double sum) {
		//convert input into array
		double[] inputArr = input.stream().mapToDouble(Double::doubleValue).toArray();
		int beginningPos = 0;
		int endPos = beginningPos + 1;
		while(true) {
			if(endPos<=beginningPos) {
				endPos++;
				continue;
			}
			double rangeSum = 0.0;
			for(int pos=beginningPos;pos<=endPos;pos++) {
				rangeSum+=inputArr[pos];
			}
			if(rangeSum==sum) {
				return new int[] {beginningPos, endPos};
			}else if(rangeSum<sum){
				endPos++;
			}else if(rangeSum>sum){
				beginningPos++;
			}
		}
	}
	
	private static boolean isValidNumber(int position) {
		return getSums(position).contains(input.get(position));
	}
	
	/**
	 * Goes through input and gets sums of previous numbers before given position
	 * @return set of sums of previous numbers
	 */
	private static HashSet<Double> getSums(int position){
		HashSet<Double> sums = new HashSet<Double>();
		for(int i = position - CONSIDER_PREVIOUS_COUNT; i < position; i++) {
			for(int j = i + 1; j < position; j++) {
				sums.add(input.get(i)+input.get(j));
			}
		}
		return sums;
	}

}
