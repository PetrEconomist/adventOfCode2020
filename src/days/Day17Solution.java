package days;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import fileReader.ReadFile;

public class Day17Solution {


	private static final String FILE_NAME = "day17Input.txt";
	private static LinkedList<String> input;
	//map with matrix current state
	private static HashMap<Coordinates, Boolean> currentState;
	//map with matrix previous state
	private static HashMap<Coordinates, Boolean> previousState;
	//symbol in input: active
	private static final char ACTIVE_SYMBOL = '#';
	//count of active neighbors without effect
	private static final int COUNT_ACTIVE_NEIGHBORS_WO_STATUS_CHANGE =  4;
	
	public static void main(String[] args) {
		//load raw input
		input = ReadFile.getFileAsList(FILE_NAME);
		currentState = new HashMap<Coordinates, Boolean>();
		previousState = new HashMap<Coordinates, Boolean>();
		int part1Output = part1Solution();
		System.out.printf("part1Solution: %d\n", part1Output);
	}

	private static int part1Solution() {
		setInitialState();
		for(int cycle = 0; cycle < 6; cycle++) {
			processCycle();
			//System.out.printf("coordinates after cycle %d\n", cycle);
			//printCoordinates();
		}
		return getCountActive();
	}
	
	
	private static void printCoordinates() {
		for(Coordinates c : currentState.keySet()) {
			System.out.printf("coordinate %s status %b\n", c, currentState.get(c));
			
		}
	}
	
	private static void setInitialState() {
		for(int inputLineID = 0; inputLineID < input.size(); inputLineID++) {
			String inputLine = input.get(inputLineID);
			//input line is equivalent to y axis
			for(int charPos = 0; charPos < inputLine.length(); charPos++) {
				//char pos is equivalent to x axis
				boolean active = (inputLine.charAt(charPos) == ACTIVE_SYMBOL);
				currentState.put(new Coordinates(charPos, inputLineID, 0, 0), active);
			}
		}
	}
	
	private static int getCountActive() {
		int counter = 0;
		for(Coordinates c : previousState.keySet()) {
			if(previousState.get(c)) counter++;
		}
		System.out.printf("previous state %d active\n", counter);
		counter = 0;
		for(Coordinates c : currentState.keySet()) {
			if(currentState.get(c)) counter++;
		}
		System.out.printf("current state %d active\n", counter);
		return counter;
	}
	
	private static void processCycle() {
		//switch current state into previous state
		switchCurrentStateToPrevious();
		//looping through known coordinates
		for(Coordinates c : previousState.keySet()) {
			HashSet<Coordinates> neighbors = getNeighborCoodinates(c);
			int countActiveNeighbors = 0;
			//loop through neighbor coordinates
			for(Coordinates neighbor : neighbors) {
				//if more then ... neighbors active, not necessary to further examine
				if(countActiveNeighbors >= COUNT_ACTIVE_NEIGHBORS_WO_STATUS_CHANGE) continue;
				//if not existing, it is inactive and not known
				if(!previousState.containsKey(neighbor)) {
					continue;
				}
				//if exists, get state
				if(previousState.get(neighbor)) countActiveNeighbors++;
			}
			//evaluate new state
			//branch previously active
			if(previousState.get(c)) {
				if(countActiveNeighbors == 2 || countActiveNeighbors == 3) {
					//new status: inactive
					currentState.put(c, true);
				}else {
					currentState.put(c, false);
				}
			}else{
				if(countActiveNeighbors == 3) {
					//new status: active
					currentState.put(c,  true);
				}else {
					currentState.put(c, false);
				}
			}
		}
		
			//increment counter  if neighbor active
		//evaluate new status and add to present map
			
	}
	
	private static void switchCurrentStateToPrevious() {
		//add all current coordinates and its neighbors to previous state
		for(Coordinates c : currentState.keySet()) {
			HashSet<Coordinates> neighbors = getNeighborCoodinates(c);
			//loop through neighbor coordinates
			for(Coordinates neighbor : neighbors) {
				//if neighbor is not in current mapping of coordinates, add as inactive
				if(!currentState.containsKey(neighbor)) {
					previousState.put(neighbor, false); //initially inactive
				}	
			}
			previousState.put(c, currentState.get(c));
		}
		currentState.clear();
	}
	
	private static HashSet<Coordinates> getNeighborCoodinates(Coordinates c){
		HashSet<Coordinates> neighbors = new HashSet<Coordinates>();
		String[] optionsOffset = {"-1", "0", "1"};
		String[] offsetCombinations = combinations.Combinations.createCombinations(optionsOffset, 4);
		for(int combID = 0; combID < offsetCombinations.length; combID++) {
			String[] offsetString = offsetCombinations[combID].split(";");
			int offset[] = new int[offsetString.length];
			for(int i = 0; i < offset.length; i++) {
				offset[i] = Integer.parseInt(offsetString[i]);
			}
			neighbors.add(new Coordinates(
					c.w + offset[0],
					c.x + offset[1],
					c.y + offset[2],
					c.z + offset[3])
					);
		}
		//remove coordinates itself
		neighbors.remove(c);
		return neighbors;
	}
	
	
	//class wrapping coordinates into one object
	static class Coordinates{
		//variables (final) coordinates
		private final int w, x, y, z;
		
		Coordinates(int w, int x, int y, int z){
			this.w = w;
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		
				
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + w;
			result = prime * result + x;
			result = prime * result + y;
			result = prime * result + z;
			return result;
		}



		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Coordinates other = (Coordinates) obj;
			if (w != other.w)
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			if (z != other.z)
				return false;
			return true;
		}



		public String toString() {
			return x + ";" + y + ";" + z;
		}
		
	}
	
	

}
