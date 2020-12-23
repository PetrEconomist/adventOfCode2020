package days;

import java.util.LinkedList;

import collectionsConvertor.ListsConvertor;
import fileReader.ReadFile;


public class Day1Solution {
	
	public static void main(String[] args) {
		int expectedSum = 2020;
		LinkedList<Integer> input = ListsConvertor.convertToInt(ReadFile.getFileAsList("day1Input.txt"));
		
		part1Solution(input, expectedSum);
		part2Solution(input, expectedSum);
		
	}
	
	public static void part1Solution(LinkedList<Integer> input, int expectedSum) {
		int[] pair = getPairSumEqual(input, expectedSum);
		int result = pair[0]*pair[1];
		System.out.printf("Answer, day 1 part 1 %d\n", result);
	}
	
	public static void part2Solution(LinkedList<Integer> input, int expectedSum) {
		int[] triplet = getTripleSumEqual(input, expectedSum);
		int result2 = triplet[0]*triplet[1]*triplet[2];
		System.out.printf("Answer, day 1 part 2 %d\n", result2);
	}
	
	/**
	 * Finds two numbers that sum equals expected value
	 * @param input numbers in which we find match
	 * @param expectedSum sum of two numbers which is looked for
	 * @return two numbers which sum equals expectedSume
	 */
	public static int[] getPairSumEqual(LinkedList<Integer> input, int expectedSum) {
		int[] pair = new int[2];
		for(int i=0;i<input.size();i++) {
			int firstVal = input.get(i);
			for(int j=i+1;j<input.size();j++) {
				if(firstVal + input.get(j)==expectedSum) {
					pair[0]=firstVal;
					pair[1]=input.get(j);
					System.out.printf("Found solution for sum %d : [%d;%d]\n", expectedSum, pair[0], pair[1] );
					return pair;
				}
			}
		}
		//couple not found
		return null;
	}
	
	/**
	 * Finds three numbers that sum equals expected value
	 * @param input numbers in which we find match
	 * @param expectedSum sum of two numbers which is looked for
	 * @return two numbers which sum equals expectedSume
	 */
	public static int[] getTripleSumEqual(LinkedList<Integer> input, int expectedSum) {
	int[] triplet = new int[3];
	int[] remainderPair = new int[2];
	for(int i=0;i<input.size();i++) {
		int remainder = expectedSum-input.get(i);
		LinkedList<Integer> remainderInput = (LinkedList<Integer>) input.clone();
		remainderInput.remove(i);
		remainderPair = getPairSumEqual(remainderInput, remainder);
		//found pair which sum to remainder
		if(remainderPair!=null) {
			triplet[0] = input.get(i);
			triplet[1] = remainderPair[0];
			triplet[2] = remainderPair[1];
			System.out.printf("\"Found solution for sum %d: [%d;%d;%d]\n", expectedSum, triplet[0], triplet[1], triplet[2] );
			return triplet;
			}
		}
	//couple not found
		return null;
	}
	

}
