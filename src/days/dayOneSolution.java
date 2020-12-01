package days;

import java.util.LinkedList;

import fileReader.ReadFile;


public class dayOneSolution {
	
	public static void main(String[] args) {
		int expectedSum = 2020;
		int[] pair = getPairSumEqual("dayOneInput.txt", expectedSum);
		int result = pair[0]*pair[1];
		System.out.printf("Answer, day 1 part 1 %d\n", result);

	}
	
	public static int[] getPairSumEqual(String fileName, int expectedSum) {
		int[] pair = new int[2];
		LinkedList<Integer> input = convertStringToIntList(ReadFile.getFileAsList(fileName));
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
		
		return pair;
	}
	
	public static LinkedList<Integer> convertStringToIntList(LinkedList<String> input){
		LinkedList<Integer> output = new LinkedList<Integer>();
		for(String s : input) {
			output.add(Integer.parseInt(s));
		}
		return output;
	}
}
