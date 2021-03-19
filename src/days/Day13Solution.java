package days;

import java.util.LinkedList;

import fileReader.ReadFile;
import modulo.ModularArithmetic;

public class Day13Solution {


	private static final String FILE_NAME = "day13Input.txt";
	private static final String NO_BUS_INFORMATION = "x";
	private static LinkedList<String> input;
	
	
	public static void main(String[] args) {
		//load raw input
		input = ReadFile.getFileAsList(FILE_NAME);
		System.out.printf("part1Solution: %d\n", part1Solution());
		System.out.printf("part2Solution: %d\n", part2Solution());
	}

	private static long part1Solution() {
		int timestamp = Integer.parseInt(input.get(0));
		String[] buses = input.get(1).split(",");
		int waitingTimeMin = Integer.MAX_VALUE;
		int busWaitingTimeMin = timestamp;
		for(int i=0;i<buses.length;i++) {
			if(buses[i].equals(NO_BUS_INFORMATION)) continue;
			int busNr = Integer.parseInt(buses[i]);
			int waitingTime = getWaitingTime(timestamp, busNr);
			if(waitingTime < waitingTimeMin) {
				waitingTimeMin = waitingTime;
				busWaitingTimeMin = busNr;
			}
		}
		return waitingTimeMin * busWaitingTimeMin;	
	}
	
	private static long part2Solution() {
		String[] buses = input.get(1).split(",");
		int[][] statusTimestampBuses = new int[buses.length][2];
		int offsetIndex = 0;
		int busIndex = 1;
		for(int i=0;i<buses.length;i++) {
			if(buses[i].equals(NO_BUS_INFORMATION)) {
				//fictiv values, do not influence result
				statusTimestampBuses[i][offsetIndex] = 0;
				statusTimestampBuses[i][busIndex] = 1;
				continue;
			};
			int busNumber = Integer.parseInt(buses[i]);
			statusTimestampBuses[i][offsetIndex] = (int) ModularArithmetic.getBasicNumber(busNumber - i, busNumber);
			statusTimestampBuses[i][busIndex] = busNumber;
		}
		return ModularArithmetic.ChineseRemainderTheorem(statusTimestampBuses);
	}
		
	private static int getWaitingTime(int timestamp, int busNr) {
		return busNr - (timestamp % busNr);
	}


}
