package days;

import java.util.LinkedList;

import fileReader.ReadFile;


public class dayOneSolution {
	
	public static void main(String[] args) {
		int expectedSum = 2020;
		LinkedList<Integer> input = convertStringToIntList(ReadFile.getFileAsList("dayOneInput.txt"));
		int[] pair = getPairSumEqual(input, expectedSum);
		int result = pair[0]*pair[1];
		System.out.printf("Answer, day 1 part 1 %d\n", result);
		
		int[] triplet = getTripleSumEqual(input, expectedSum);
		int result2 = triplet[0]*triplet[1]*triplet[2];
		System.out.printf("Answer, day 1 part 2 %d\n", result2);

	}
	
	public static int[] getPairSumEqual(LinkedList<Integer> input, int expectedSum) {
		int[] pair = new int[2];
		for(int i=0;i<input.size();i++) {
			int firstVal = input.get(i);
			for(int j=i+1;j<input.size();j++) {
				if(firstVal + input.get(j)==expectedSum) {
					pair[0]=firstVal;
					pair[1]=input.get(j);
					System.out.printf("Found solution %d & %d\n", pair[0], pair[1] );
					return pair;
				}
			}
		}
		return null;
	}
		
	public static int[] getTripleSumEqual(LinkedList<Integer> input, int expectedSum) {
	int[] triplet = new int[3];
	int[] remainderPair = new int[2];
	for(int i=0;i<input.size();i++) {
		int err = 1827;
		if(i==14) {
			System.out.printf("here is some error\n");
		}
		int remainder = expectedSum-input.get(i);
		LinkedList<Integer> remainderInput = (LinkedList<Integer>) input.clone();
		remainderInput.remove(i);
		remainderPair = getPairSumEqual(remainderInput, remainder);
		if(remainderPair!=null) {
			triplet[0] = input.get(i);
			triplet[1] = remainderPair[0];
			triplet[2] = remainderPair[1];
			if(triplet[0]==err) {
				System.out.printf("here is some error\n");
			}
			System.out.printf("Found solution %d & %d & %d\n", triplet[0], triplet[1], triplet[2] );
			return triplet;
		}
	}
		return null;
	}
	
	public static LinkedList<Integer> convertStringToIntList(LinkedList<String> input){
		LinkedList<Integer> output = new LinkedList<Integer>();
		for(String s : input) {
			output.add(Integer.parseInt(s));
		}
		return output;
	}
}
