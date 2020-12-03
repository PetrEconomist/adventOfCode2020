package days;

import java.util.LinkedList;

import fileReader.ReadFile;
/**Starting at [X;Y] i need to move to the end (maximum Y). 
For each move I change current coordinated. 
After changing coordinates, I evaluate, whether I hit the tree or not.
Each line repeats indefinitely till the end.*/ 
public class Day3Solution {
	
	static final String FILE_NAME = "day3Input.txt";
	
	static final int START_X = 0;
	static final int START_Y = 0;

	static final char SYMBOL_TREE = '#';
	static final char SYMBOL_SPACE = '.';
	
	public static void main(String[] args) {

		//Load the text input with map (symbols)
		final LinkedList<String> input = ReadFile.getFileAsList(FILE_NAME);
		int solutionPart1 = getTreesHit(3,1,input);
		System.out.printf("Part 1 solution: %d", solutionPart1);
		int solutionPart2 = 
				getTreesHit(1,1,input) * 
				getTreesHit(3,1,input) *
				getTreesHit(5,1,input) *
				getTreesHit(7,1,input) *
				getTreesHit(1,2,input);
		System.out.printf("Part 2 solution: %d", solutionPart2);
	}
		
	static int getTreesHit(int slopeX, int slopeY, LinkedList<String> input) {
		//initialise counter to be returned
		int treeCounter = 0;
		//set starting point
		int currentX = START_X;
		int currentY = START_Y;
		//loop until end reached
		for(;currentY<input.size()-slopeY;){
			//let's move to the next coordinate
			currentX+=slopeX;
			currentY+=slopeY;
			//get Y map (line)
			String lineMap = input.get(currentY);
			//Adjust X in case moved out of the map scope (jump at the beginning)
			currentX = currentX % lineMap.length();
			//Evaluate result, increment counter if tree was hit
			if(lineMap.charAt(currentX)==SYMBOL_TREE) {
				treeCounter++;
				//System.out.printf("Tree hit on line %d\n", currentY);
			}else {
				//System.out.printf("Empty space on line %d\n", currentY);
			}
		}
		System.out.printf("Count of trees hit %d\n", treeCounter);
	
		return treeCounter;
	}
}
