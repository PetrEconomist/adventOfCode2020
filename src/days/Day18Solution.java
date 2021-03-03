package days;

import java.util.LinkedList;

import fileReader.ReadFile;

public class Day18Solution {


	private static final String FILE_NAME = "day18Input.txt";
	private static LinkedList<String> input;
	private static String LEFT_BRACKET = "(";
	private static String RIGHT_BRACKET = ")";
	private static String PLUS = "+";
	private static String ASTERISK = "*";
	
	
	public static void main(String[] args) {
		//load raw input
		input = ReadFile.getFileAsList(FILE_NAME);
		long part1Output = part1Solution();
		System.out.printf("part1Solution: %d\n", part1Output);
	}

	private static long part1Solution() {
		long sum = 0;
		for(String inputLine : input) {
			long lineResult = processInput(inputLine);
			System.out.printf("line %s result %d\n", inputLine, lineResult);
			sum += lineResult;
			System.out.printf("Sum %d\n", sum);
		}
		return sum;
	}
	
	private static long processInput(String input) {
		String processedInput = input;
		while(processedInput.contains(LEFT_BRACKET)) {
			processedInput = removeBrackets(processedInput);
		}
		return calculateInput(processedInput);
	}
	
	private static long calculateInput(String inputText) {
		String input = inputText.replaceAll(" ", "");
		input = PLUS + input;
		long result = 0;
		int pos = 0;
		while(pos < input.length()){
			int nextOperandPos = pos + 1;
			
			//find next operand position
			while(nextOperandPos < input.length()) {
				String nextSymbol = "";
				nextSymbol = input.substring(nextOperandPos, nextOperandPos + 1);
				if(nextSymbol.equals(PLUS) || nextSymbol.equals(ASTERISK)){
					break;
				}else {
					nextOperandPos++;
				}
			}
			
			//plus
			if(input.substring(pos, pos + 1).equals(PLUS)) {
				result += Integer.parseInt(input.substring(pos + 1, nextOperandPos));
			}else {
				result *= Integer.parseInt(input.substring(pos + 1, nextOperandPos));
			}
			
			pos = nextOperandPos;
		}
		return result;
	}
	
	private static String removeBrackets(String inputText) {
		if(!inputText.contains(LEFT_BRACKET)) {
			return inputText;
		}else {
			int openingBracket = -1;
			int closingBracket = -1;
			for(int pos = 0; pos < inputText.length(); pos++) {
				//case new inner bracket found
				if(inputText.substring(pos, pos + 1).equals(LEFT_BRACKET)){
					openingBracket = pos;
				}
				//case right bracket found
				else if(inputText.substring(pos, pos + 1).equals(RIGHT_BRACKET)){
					closingBracket = pos;
					break;
				}
			}
			String bracketsContent = inputText.substring(openingBracket + 1, closingBracket);
			String bracketsResult = Long.toString(calculateInput(bracketsContent)); 
			return inputText.substring(0, openingBracket) + bracketsResult + inputText.substring(closingBracket + 1);
		}
	}
	

}
