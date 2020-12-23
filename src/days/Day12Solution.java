package days;

import java.util.LinkedList;

import fileReader.ReadFile;

public class Day12Solution {


	private static final String FILE_NAME = "dayXInput.txt";
	private static LinkedList<String> input;
	
	
	public static void main(String[] args) {
		//load raw input
		input = ReadFile.getFileAsList(FILE_NAME);
		double part1Output = part1Solution();
		System.out.printf("part1Solution: %f\n", part1Output);
	}

	private static double part1Solution() {
		return 0.0;
		
	}
	

}
