package days;

import java.util.HashMap;
import java.util.LinkedList;

import javax.xml.catalog.CatalogManager;

import fileReader.ReadFile;

public class Day15Solution {


	private static final int[] INPUT_RAW = {18, 8, 0 ,5, 4, 1, 20};
	private static Game gm;
	
	public static void main(String[] args) {
		gm = new Game(INPUT_RAW);
		System.out.printf("part 1 Solution: %d\n", getNumberInTurn(2020));
		System.out.printf("part 2 Solution: %d\n", getNumberInTurn(30000000));
	}

	private static int getNumberInTurn(int turnId) {
		int lastPosition = 0;
		while(lastPosition < turnId - 1) { //if I want turn 10, I to end with position 9
			lastPosition = gm.setNextNumber();
		}
		return gm.getLastNumber();
	}
	
	static class Game {
		private HashMap<Integer, Integer> positions;
		private int lastPosition;
		private int lastNumber;
		
		public Game(int[] firstNumbers){
			positions = new HashMap<Integer, Integer>();
			//set first position
			int arrPos = 0;
			lastPosition = 0;
			lastNumber = firstNumbers[arrPos];
			//continue with next positions
			for(arrPos++; arrPos < firstNumbers.length; arrPos++) {
				addNextNumber(firstNumbers[arrPos]);
			}
		}
		
		private void addNextNumber(int number) {
			positions.put(lastNumber, lastPosition);
			lastPosition++;
			lastNumber = number;
		}
		
		public int getLastNumber() {
			return lastNumber;
		}
		
		/**
		 * Sets next number
		 * @return last position with number
		 */
		public int setNextNumber() {
			if(positions.containsKey(lastNumber)) {
				addNextNumber(lastPosition - positions.get(lastNumber));
			}else {
				addNextNumber(0);
			}
			return lastPosition;
		}
		
	}
}