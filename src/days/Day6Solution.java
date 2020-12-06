package days;

import java.util.HashMap;
import java.util.LinkedList;

import collectionsConvertor.listsConvertor;
import fileReader.ReadFile;
import stringFunctions.StringSymbols;

public class Day6Solution {
	private static final String FILE_NAME = "day6Input.txt"; 
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final LinkedList<String> input = ReadFile.getFileAsList(FILE_NAME);
		final LinkedList<String> joinedLines = listsConvertor.concatEmptyLineSeparatedData(input, "");
		System.out.printf("part1Solution: %d\n", part1Solution(joinedLines));
		final LinkedList<String> joinedLinesSeparated = listsConvertor.concatEmptyLineSeparatedData(input, ";");
		System.out.printf("part2Solution: %d\n", part2Solution(joinedLinesSeparated));
	}
	
	static int part1Solution(LinkedList<String> input) {
		int result = 0;
		
		for(String s : input) {
			result += StringSymbols.getUsedChars(s).size();
			//System.out.printf("input %s, result %d\n", s ,StringSymbols.getUsedChars(s).size());
		}
		
		return result;
	}
	
	static int part2Solution(LinkedList<String> input) {
		int result = 0;
		
		for(String s : input) {
			HashMap<Character, Integer> charCount = StringSymbols.getUsedCharsCount(s);
			char separator = ';';
			int inputsCount = 1;
			if(charCount.containsKey(separator)) {
				inputsCount += charCount.get(separator);
			}
			
			for(char c : charCount.keySet()) {
				if(c==separator)continue;
				if(charCount.get(c)==inputsCount) {
					result++;
					//System.out.printf("char %c in forms %s count ok\n", c, s);
				}
				
			}
			
			//System.out.printf("input %s, result %d\n", s ,StringSymbols.getUsedChars(s).size());
		}
		
		return result;
	}

}
