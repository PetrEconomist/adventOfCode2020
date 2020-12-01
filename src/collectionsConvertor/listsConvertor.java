package collectionsConvertor;

import java.util.LinkedList;

public class listsConvertor {
	/**
	 * Converts string list to int list
	 * @param input list of numbers, but as strings
	 * @return list of numbers as integers
	 */
	public static LinkedList<Integer> convertToInt(LinkedList<String> input){
		LinkedList<Integer> output = new LinkedList<Integer>();
		for(String s : input) {
			output.add(Integer.parseInt(s));
		}
		return output;
	}
}
