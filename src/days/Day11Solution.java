package days;

import java.util.LinkedList;

import fileReader.ReadFile;

public class Day11Solution {


	private static final String FILE_NAME = "day11Input.txt";
	private static LinkedList<String> input;
	private static final char FLOOR = '.';
	private static final char OCCUPIED = '#';
	private static final char VACANT = 'L';
	private static final char OUT_OF_MAP = 'o';
	private static final int MAX_OCCUPIED_BEFORE_OCCUPATION = 0;
	private static final int MAX_OCCUPIED_BEFORE_VACATION = 4;
	
	public static void main(String[] args) {
		//load raw input
		input = ReadFile.getFileAsList(FILE_NAME);
		double part1Output = part1Solution();
		System.out.printf("part1Solution: %f\n", part1Output);
	}

	private static int part1Solution() {
		LinkedList<String> stableMap = getStableMap(input);
		return getOccupiedSeatsCount(stableMap);
	}

	private static int getOccupiedSeatsCount(LinkedList<String> map) {
		int occupiedSeatsCounter = 0;
		for(int row = 0;row < map.size(); row++) {
			for(int column = 0; column < map.get(row).length(); column++) {
				if(getPositionStatus(map, row, column) == OCCUPIED) occupiedSeatsCounter++;
			}
		}
		return occupiedSeatsCounter;
	}
	
	private static LinkedList<String> getStableMap(LinkedList<String> originalMap) {
		int changedSeatsCount = 0;
		LinkedList<String> reloadedMap = new LinkedList<String>();
		for(int row = 0;row < originalMap.size(); row++) {
			String reloadedRow = "";
			for(int column = 0; column < originalMap.get(row).length(); column++) {
				char status = getPositionStatus(originalMap, row, column);
				if(status == OUT_OF_MAP) throw new Error("something went wrong, out of map " +
						"(row " + row + " column" + column + ")");
				if(status == FLOOR) {
					reloadedRow += status;
					continue;
				}
				
				String adjacentSeatsStatuses = getAdjacentSeatsStatus(originalMap, row, column);
				int countOccupied = countCharInString(OCCUPIED, adjacentSeatsStatuses);
				if(status == VACANT) {
					if(countOccupied==MAX_OCCUPIED_BEFORE_OCCUPATION) {
						reloadedRow += OCCUPIED;
						changedSeatsCount++;
					}else {
						reloadedRow += VACANT;
					}
					
				}else if(status == OCCUPIED) {
					if(countOccupied>MAX_OCCUPIED_BEFORE_VACATION) {
						reloadedRow += VACANT;
						changedSeatsCount++;
					}else {
						reloadedRow += OCCUPIED;
					}
					
				}
			}
			reloadedMap.add(reloadedRow);
		}
		if(changedSeatsCount==0) {
			return reloadedMap;
		}else {
			return getStableMap(reloadedMap);
		}
	}
	
	private static int countCharInString(char c, String s) {
		int counter = 0;
		for(int i=0; i<s.length();i++) {
			if(s.charAt(i)==c) {
				counter++;
			}
		}
		return counter;
	}
	
	private static String getAdjacentSeatsStatus(LinkedList<String> map, int row, int column) {
		String adjacentSeatsStatuses = "";
		
		//named after world directions (North, East, West, Souht)
		//N
		adjacentSeatsStatuses += getAdjacentSeatStatus(map, row, -1, column, 0);
		//NE
		adjacentSeatsStatuses += getAdjacentSeatStatus(map, row, -1, column, +1);
		//E
		adjacentSeatsStatuses += getAdjacentSeatStatus(map, row, 0, column, +1);
		//SE
		adjacentSeatsStatuses += getAdjacentSeatStatus(map, row, +1, column, +1);
		//S
		adjacentSeatsStatuses += getAdjacentSeatStatus(map, row, +1, column, 0);
		//SW
		adjacentSeatsStatuses += getAdjacentSeatStatus(map, row, +1, column, -1);
		//W
		adjacentSeatsStatuses += getAdjacentSeatStatus(map, row, 0, column, -1);
		//NW
		adjacentSeatsStatuses += getAdjacentSeatStatus(map, row, -1, column, -1);
		
		return adjacentSeatsStatuses;
	}
	
	private static char getAdjacentSeatStatus(LinkedList<String> map, int row, int stepRow, int column, int stepColumn) {
		char adjacentSeatStatus = FLOOR;
		int offsetRow = stepRow;
		int offsetColumn = stepColumn;
		while(adjacentSeatStatus == FLOOR) {
			adjacentSeatStatus = getPositionStatus(map, row + offsetRow, column + offsetColumn);
			offsetRow+=stepRow;
			offsetColumn+=stepColumn;
		}
		return adjacentSeatStatus;
	}

	private static char getPositionStatus(LinkedList<String> map, int row, int column){
		if(row>=map.size()) return OUT_OF_MAP;
		if(row<0) return OUT_OF_MAP ;
		if(column>=map.get(0).length()) return OUT_OF_MAP ;
		if(column<0) return OUT_OF_MAP ;
		return  map.get(row).charAt(column);
	}
	

}
