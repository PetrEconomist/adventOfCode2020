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
	
	/**
	 * Joins list items together (concatenate). Items' data are separated by empty item. 
	 * Deletes empty items and returns joined data about items. 
	 * @param input with
	 * @return
	 */
	public static LinkedList<String> concatEmptyLineSeparatedData(LinkedList<String> input){
		LinkedList<String> output = new LinkedList<String>();
		// Loop over input
		int item = 0;
		while(item<input.size()) {
			//If empty line, skip to the next item
			if(input.get(item).length()<1) {
				item++;
				continue;
			}
			//Loop until empty line, concat
			String conStr = "";
			while(input.get(item).length()>=1) {
				if(conStr.length()==0) {
					conStr = input.get(item);
				}else {
					conStr = conStr +  " " + input.get(item);
				}
				item++;
				if(item==input.size())break;
			}
			output.add(conStr); 
		}
		return output;		 
	}
}
