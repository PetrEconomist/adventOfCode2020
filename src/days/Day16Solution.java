package days;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import fileReader.ReadFile;

public class Day16Solution {


	private static final String FILE_NAME = "day16Input.txt";
	private static LinkedList<String> input;
	private static TicketSystem ticketSystem;
	private static int[] myTicket;
	private static int errorRatePart1;
	
	
	public static void main(String[] args) {
		//load raw input
		input = ReadFile.getFileAsList(FILE_NAME);
		ticketSystem = TicketSystem.getInstance();
		processInput();
		System.out.printf("part1Solution: %d\n", errorRatePart1);
		System.out.printf("part2Solution: %d\n", part2Solution());
		
	}
	
	public static long part2Solution() {
		int[] criteriaFields = ticketSystem.matchValuesOnTickets();
		long result = 1; //result is sum, 1 is neutral
		//departures = criteria 0 to 5
		for(int criteriaID = 0; criteriaID < 6; criteriaID++) {
			int fieldValue = myTicket[criteriaFields[criteriaID]];
			result *= fieldValue;
		}
		return result;
			
	}

	//private static double part1Solution() {
		//not used, part of processInput
	//}
		
	/*
	 * Takes inputs and processes all its parts, e.i.:
	 * - valid positions
	 * - my ticket
	 * - nearby tickets
	 */
	private static void processInput() {
		boolean loadedRanges = false;
		boolean loadedMyTicket = false;
		for(String inputLine : input) {
			if(inputLine.length()==0) {
				//skip empty lines
				continue;
			}
			
			if(inputLine.equals(new String("your ticket:"))) {
				loadedRanges = true;
				continue;
			}
			
			if(inputLine.equals(new String("nearby tickets:")))  {
				loadedMyTicket = true;
				continue;
			}
			
			if(loadedRanges && loadedMyTicket) {
				//load nearby tickets and validate
				errorRatePart1 += ticketSystem.validateTicket(inputLine);
			}else if(loadedRanges) {
				//load my Ticket
				String[] myTicketText = inputLine.split(",");
				myTicket = new int[myTicketText.length];
				for(int fieldID = 0; fieldID < myTicketText.length; fieldID++) {
					myTicket[fieldID] = Integer.parseInt(myTicketText[fieldID]);
				}
					
			}else{
				// first part (followed by blank line) are valid values
				ticketSystem.processRangeInput(inputLine);
			}
			
		}
	}
	
	private static class TicketSystem{
		private static TicketSystem instance = null;
		private static final int MAX_VALUE = 1000;
		private static final int FIELDS_COUNT = 20;
		private static final char FIELD_NAME_END_CHAR = ':';
		private static final char FIELD_RANGE_SEPARATOR = '-';
		private static final char CHAR_SPACE = ' ';
		//values generally accepted by any of the rules
		private boolean[] validValues;
		//valid ranges for given rules (numbered by order in input
		private int[][] fieldRanges;
		private boolean[][] fieldValues;
		
		//singleton
		private TicketSystem() {
			validValues = new boolean[MAX_VALUE];
			fieldRanges = new int[FIELDS_COUNT][4];
			fieldValues = new boolean[FIELDS_COUNT][MAX_VALUE];
		}
		
		//singleton getInstance 
		//https://www.javatpoint.com/singleton-class-in-java
		public static TicketSystem getInstance() {
			if(instance==null) {
				instance = new TicketSystem();
			}
			return instance;
		}
		
		/**
		 * Validates ticket values, if in valid range
		 * @param ticketValues comma deliminated values on the ticket
		 * @return invalid value on the ticket, zero if no invalid ticket
		 */
		int validateTicket(String ticketValues) {
			String[] values = ticketValues.split(",");
			for(String value : values) {
				boolean valueValid = validValues[Integer.parseInt(value)];
				if(!valueValid) {
					return Integer.parseInt(value);
				}
			}
			//passed initial check, store used values on the ticket
			for(int valuePos = 0; valuePos < values.length; valuePos++) {
				fieldValues[valuePos][Integer.parseInt(values[valuePos])] = true;
			}
			return 0; //no invalid field, does not influence error rate
		}
		
		private void addGeneralRange(int rangeMin, int rangeMax) {
			if(rangeMin > rangeMax) throw new Error ("Invalid range");
			for(int value = rangeMin; value <= rangeMax; value ++) {
				validValues[value] = true;
			}
		}
		
		
		public int[] matchValuesOnTickets() {
			int[] matchedCriteriaWithFields = new int[FIELDS_COUNT];
			boolean[] criteriaMatched = new boolean[FIELDS_COUNT];
			boolean[] matchedFields = new boolean[FIELDS_COUNT];
			
			int toBeMatchedCount = FIELDS_COUNT;
			while(toBeMatchedCount > 0) {
				for(int criteriaID = 0; criteriaID < FIELDS_COUNT; criteriaID++) {
					if(criteriaMatched[criteriaID]) {
						continue;
					}else { 
						int matchResult = matchField(criteriaID, matchedFields);
						if(matchResult > -1) {
							toBeMatchedCount--;
							criteriaMatched[criteriaID] = true;
							matchedFields[matchResult] = true;
							matchedCriteriaWithFields[criteriaID] = matchResult;
						}
					}
				}
			}
			return matchedCriteriaWithFields;
		}
		
		private int matchField(int criteriaID, boolean[] matchedFields) {
			int temporaryResult = -1;
			for(int testedFieldID = 0; testedFieldID < FIELDS_COUNT; testedFieldID++) {
				//true = already matched
				if(matchedFields[testedFieldID]) {
					continue;
				//not matched yet
				}else {
					boolean matchFound = fieldMatches(testedFieldID, criteriaID);
					if(!matchFound) continue;
					//looped OK, all values in given range
					//save temporary result
					if(temporaryResult==-1) {
						temporaryResult = testedFieldID;
					//more options - cannot say
					}else {
						return -1;
					}
				}
			}
			return temporaryResult;
		}
		
		boolean fieldMatches(int valuesFieldID, int criteriaID) {
			int min1 = fieldRanges[criteriaID][0];
			int max1 = fieldRanges[criteriaID][1];
			int min2 = fieldRanges[criteriaID][2];
			int max2 = fieldRanges[criteriaID][3];
			for(int value = 0; value < MAX_VALUE; value++) {
				//field used
				if(fieldValues[valuesFieldID][value]) {
					//invalid
					if(value < min1 || (max1 < value && value < min2) || max2 < value) {
						return false;
					}
				}
			}
			return true;
		}
		
		/**
		 * Zprocesuje vstup a uloží hodnoty validní pro jednotlivá pole
		 * @param rangeInput
		 */
		public void processRangeInput(String rangeInput) {
			int separatorPosition = 0;
			int nextSeparatorPosition = rangeInput.indexOf(FIELD_NAME_END_CHAR, separatorPosition);
			//String criteriaName = rangeInput.substring(separatorPosition, nextSeparatorPosition);
			separatorPosition = nextSeparatorPosition + 2;
			nextSeparatorPosition = rangeInput.indexOf(FIELD_RANGE_SEPARATOR, separatorPosition);
			int min1 = Integer.parseInt(rangeInput.substring(separatorPosition, nextSeparatorPosition));
			separatorPosition = nextSeparatorPosition + 1;
			nextSeparatorPosition = rangeInput.indexOf(CHAR_SPACE, separatorPosition);
			int max1 = Integer.parseInt(rangeInput.substring(separatorPosition, nextSeparatorPosition));
			separatorPosition = nextSeparatorPosition + 4;
			nextSeparatorPosition = rangeInput.indexOf(FIELD_RANGE_SEPARATOR, separatorPosition);
			int min2 = Integer.parseInt(rangeInput.substring(separatorPosition, nextSeparatorPosition));
			int max2 = Integer.parseInt(rangeInput.substring(nextSeparatorPosition+1));
			
			addGeneralRange(min1, max1);
			addGeneralRange(min2, max2);
			addFieldRange(min1, max1, min2, max2);
			
		}
		
		private void addFieldRange(int min1, int max1, int min2, int max2) {
			for(int field = 0; field < FIELDS_COUNT; field++) {
				if(fieldRanges[field][0] > 0) {
					continue;
				}else {
					fieldRanges[field] = new int[] {min1, max1, min2, max2};
					return;
				}
			}
		}
			
	}
		
	
		

	

}
