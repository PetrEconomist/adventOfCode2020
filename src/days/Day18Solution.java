package days;

import java.util.LinkedList;
import java.util.regex.Pattern;

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
		long part2Output = part2Solution();
		System.out.printf("part2Solution: %d\n", part2Output);
	}

	private static long part1Solution() {
		long sum = 0;
		for(String inputLine : input) {
			long lineResult = processInputPart1(inputLine);
			//System.out.printf("line %s result %d\n", inputLine, lineResult);
			sum += lineResult;
			//System.out.printf("Sum %d\n", sum);
		}
		return sum;
	}
	
	private static long part2Solution() {
		long sum = 0;
		for(String inputLine : input) {
			long lineResult = processInputPart2(inputLine);
			System.out.printf("line %s result %d\n", inputLine, lineResult);
			sum += lineResult;
			System.out.printf("Sum %d\n", sum);
		}
		return sum;
	}
	
	private static long processInputPart1(String input) {
		String processedInput = input;
		while(processedInput.contains(LEFT_BRACKET)) {
			processedInput = removeBracketsPart1(processedInput);
		}
		return calculateInputPart1(processedInput);
	}
	
	private static long processInputPart2(String input) {
		String processedInput = input;
		while( processedInput.contains( LEFT_BRACKET ) ) {
			processedInput = removeBracketsPart2( processedInput );
		}
		
		return calculateInputPart2( processedInput );
	}
	
	private static long calculateInputPart2( String inputText ) {
		String input = inputText.replaceAll( " " , "" );
		
		while( input.contains( PLUS ) ) {
			input = processAddition( input );
		}
		while( input.contains( ASTERISK ) ) {
			input = processMultiplication( input );
		}
		
		return Long.parseLong(input);
	}
	
	private static String processAddition( String input ) {
		String formula = getFormulaPartContainingOperation(PLUS, input);
		long formulaResult = calculateInputPart1(formula);
		String processedFormula = input.replaceFirst(Pattern.quote(formula), Long.toString(formulaResult ) ); 
		return processedFormula;
	}
	
	private static String processMultiplication( String input ) {
		String formula = getFormulaPartContainingOperation(ASTERISK, input);
		long formulaResult = calculateInputPart1(formula);
		return input.replaceAll(Pattern.quote(formula), Long.toString(formulaResult) );
	}
	
	private static String getFormulaPartContainingOperation(String operator, String formula) {
		
		//find first operator position
		int operatorPosition = formula.indexOf( operator );
		
		if( ( operatorPosition == -1 ) ) {
			return "";
		}
		
		// move to left until previous operator is found
		int previousOperatorPosition = findPreviousNonNumericPosition( formula , operatorPosition );
		
		// move to the right until next operator is found
		int nextOperatorPosition = findNextNonNumericPosition(formula, operatorPosition);
				
		return formula.substring(previousOperatorPosition + 1, nextOperatorPosition);
		
	}
	
	private static int findPreviousNonNumericPosition( String formula, int startingPosition ) {
		int position = startingPosition - 1;
		while( position >= 0 ) {
			if( !Character.isDigit( formula.charAt( position ) ) ) {
				return position;
			}
			position--;
		}
		
		return position;
	}
	
	private static int findNextNonNumericPosition( String formula, int startingPosition ) {
		int position = startingPosition + 1;
		while( position < formula.length() ) {
			if( !Character.isDigit( formula.charAt( position ) ) ) {
				return position;
			}
			position++;
		}
		
		return position;
	}
	
	
	private static long calculateInputPart1(String inputText) {
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
				result += Long.parseLong(input.substring(pos + 1, nextOperandPos));
			}else {
				result *= Long.parseLong(input.substring(pos + 1, nextOperandPos));
			}
			
			pos = nextOperandPos;
		}
		return result;
	}
	
	private static String removeBracketsPart2(String inputText) {
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
			String bracketsResult = Long.toString(calculateInputPart2(bracketsContent)); 
			return inputText.substring(0, openingBracket) + bracketsResult + inputText.substring(closingBracket + 1);
		}
	}
	
	private static String removeBracketsPart1(String inputText) {
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
			String bracketsResult = Long.toString(calculateInputPart1(bracketsContent)); 
			return inputText.substring(0, openingBracket) + bracketsResult + inputText.substring(closingBracket + 1);
		}
	}
	

}
