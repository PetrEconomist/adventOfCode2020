package days;

import java.util.HashSet;
import java.util.LinkedList;

import fileReader.ReadFile;
import numberFunctions.NumberFunctions;

public class Day5Solution {

	private static final String FILE_NAME = "day5Input.txt"; 
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//load input
		final LinkedList<String> input = ReadFile.getFileAsList(FILE_NAME);
		System.out.printf("Part 1 solution: %d\n", part1Solution(input));
		System.out.printf("Part 2 solution: %d\n", part2Solution(input));
	}
	
	private static int part1Solution(LinkedList<String> input) {
		//loop over inputs
		int maxID = 0;
		for(String s : input) {
			Seat st = new Seat(s);
			
		//save if max ID
		maxID = Math.max(maxID, st.getID(s));
		}
		return maxID;
	}
	
	private static int part2Solution(LinkedList<String> input) {
		//loop over inputs
		HashSet<Integer> seatsIDs = new HashSet<Integer>();
		
		for(String s : input) {
			Seat st = new Seat(s);
			seatsIDs.add(st.getID(s));
		//save ID
		}
		for(int seat : seatsIDs) {
			if(!seatsIDs.contains(seat+1) && seatsIDs.contains(seat+2))return seat+1;
		}
		return -1;
	}
		
	private static class Seat {
		
		Seat(String s){
			
		}
		
		//calculate row, column
		private int[] getRowColumn(String seatCode) {
			String rowCode = seatCode.substring(0, 7);
			int[] rowRange = new int[] {0, 127};
			for(int i=0; i<rowCode.length();i++) {
				rowRange = NumberFunctions.halfRange(rowRange[0], rowRange[1], 'F', 'B', rowCode.charAt(i));
			}
			if(rowRange[0]!=rowRange[1]) throw new IllegalArgumentException("invalid row");
			//System.out.printf("row %d\n", rowRange[0]);
			
			String colCode = seatCode.substring(seatCode.length()-3);
			int[] colRange = new int[] {0, 7};
			for(int i=0; i<colCode.length();i++) {
				colRange = NumberFunctions.halfRange(colRange[0], colRange[1], 'L', 'R', colCode.charAt(i));
			}
			if(colRange[0]!=colRange[1]) throw new IllegalArgumentException("invalid column");
			//System.out.printf("column %d\n", colRange[0]);
			return (new int[] {rowRange[0], colRange[0]});
		}
		
		
		//calculate ID
		int getID(String seatCode) {
			int[] rc = getRowColumn(seatCode);
			return (rc[0] * 8 + rc[1]);
		}
	}

}
