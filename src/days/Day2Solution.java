package days;

import java.util.LinkedList;

import fileReader.ReadFile;

public class Day2Solution {
	
	
	public static void main(String[] args) {
		LinkedList<String> input = ReadFile.getFileAsList("day2Input.txt");
		System.out.printf("Part 1 solution: %d\n", part1Solution(input));
		System.out.printf("Part 2 solution: %d\n", part2Solution(input));
		
	}
	
	public static int part1Solution(LinkedList<String> input) {
		int counter = 0;
		for(String inputStr : input) {
			if(new passwordHandler(inputStr).isCountValid()) {
				counter ++;
			}
		}
		return counter;
	}
	
	public static int part2Solution(LinkedList<String> input) {
		int counter = 0;
		for(String inputStr : input) {
			if(new passwordHandler(inputStr).arePossitionsValid()) {
				counter ++;
			}
		}
		return counter;
	}
	
	
	
	
	private static class passwordHandler{
		int criterion1, criterion2;
		String symbol, password;
		
		passwordHandler(String stringInput){
			int posSeparator1 = stringInput.indexOf("-");
			int posSeparator2 = stringInput.indexOf(" ");
			this.criterion1 = Integer.parseInt(stringInput.substring(0, posSeparator1));
			this.criterion2 = Integer.parseInt(stringInput.substring(posSeparator1+1, posSeparator2));
			this.symbol = stringInput.substring(posSeparator2+1, posSeparator2+2);
			this.password = stringInput.substring(posSeparator2+4);
			//System.out.printf("min %d max %d symbol %s password %s\n", criterion1, criterion2, symbol, password);
		}
		
		boolean isCountValid(){
			int occurence = 0;
			for(int i=0;i<password.length();i++) {
				if (password.substring(i, i+1).equals(symbol)){
					occurence++;
				}
			}
			return (criterion1<=occurence && occurence<=criterion2);
		}
		
		boolean arePossitionsValid(){
			int validPositions = 0;
			if(password.subSequence(criterion1-1, criterion1).equals(symbol)) validPositions++;
			if(password.subSequence(criterion2-1, criterion2).equals(symbol)) validPositions++;
			return validPositions==1;
		}
		
	}
}
