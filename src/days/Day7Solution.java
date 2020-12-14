package days;

import java.util.ArrayList;
import java.util.LinkedList;
import fileReader.ReadFile;
import graphProblems.SimpleOrientedGraph;
import stringFunctions.StringSymbols;

public class Day7Solution {
	
	private static final String FILE_NAME = "day7Input.txt";
	private static final String MY_BAG_COLOR = "shiny gold bag"; 
	private static final String NO_OTHER_BAGS = "no other bags";
	
	private static LinkedList<String> input;
	private static SimpleOrientedGraph rules;

	public static void main(String[] args) {
		//load raw input
		input = ReadFile.getFileAsList(FILE_NAME);
		setRulesGraph();
		System.out.printf("part1Solution: %d\n", part1Solution());
		System.out.printf("part2Solution: %f\n", part2Solution());
	}
	
	private static int part1Solution() {
		return rules.getPredecessorsAll(MY_BAG_COLOR).size();
	}
	
	private static double part2Solution() {
		LinkedList<String> output = rules.getSuccessorEdgesAllAsString(MY_BAG_COLOR, 1.0);
		double sum = 0;
		for(String out : output) {
			sum += Double.parseDouble(out.split(";")[1]); 
		}
		return sum;
	}

	private static void setRulesGraph() {
		rules = new SimpleOrientedGraph();
		ArrayList<String[]> rulesInput= convertInput(); 
		for(String[] rule : rulesInput) {
			rules.addEdge(rule[0], rule[1], Integer.parseInt(rule[2]));
		}
	}
	
	/**
	 * Convert input so that each line contains only one inside bag and its value
	 * @return
	 */
	private static ArrayList<String[]> convertInput(){
		ArrayList<String[]> convertedInput = new ArrayList<String[]>();
		for(String inputLine : input) {
			//contains no inner bag
			if(inputLine.contains(NO_OTHER_BAGS))continue;
			//remove dot at the end
			inputLine = inputLine.substring(0, inputLine.length()-1);
			String[] removedContain = inputLine.split("s contain ");
			String outerBag = removedContain[0];
			String[] innerBagsInfo = removedContain[1].split(", ");
			for(String innerBagInfo : innerBagsInfo) {
				innerBagInfo = innerBagInfo.replaceAll("bags", "bag");
				int whiteSpacePos = StringSymbols.getWhitespacePos(innerBagInfo);
				String innerBagCount = innerBagInfo.substring(0, whiteSpacePos);
				String innerBagName = innerBagInfo.substring(whiteSpacePos+1);
				convertedInput.add(new String[] {outerBag, innerBagName, innerBagCount});
			}
		}
		return convertedInput;
	}
	
	
}
