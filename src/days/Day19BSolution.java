package days;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Pattern;

import fileReader.ReadFile;

public class Day19BSolution {
	// vyhodnotí jednotlivé zprávy na základì daných kritérií

	private static final String FILE_NAME = "day19BInput2.txt";
	private static LinkedList<String> input;
	private static HashMap<IntegerPair, HashSet<Integer>> rules;
	private static HashSet<String> messages;
	private static HashMap<String, HashSet<Integer>> combinationsIDs;
	private static final String PIPE = "|";
	private static int INVALID_COMBINATION;
	// most fundamental rule, all other rules are subordinated (on lower levels, on
	// its parts)
	private static int FUNDAMENTAL_RULE = 0;

	public static void main(String[] args) {
		// load raw input
		input = ReadFile.getFileAsList(FILE_NAME);
		loadInputs();
		double part1Output = part1Solution();
		//printAllMessagesAndCombinations();
		System.out.printf("part1Solution: %s\n", part1Output);
	}
	
	private static void printAllMessagesAndCombinations() {
		for(String message : combinationsIDs.keySet()) {
			System.out.printf("message %s options: \n", message);
			for(Integer combination : combinationsIDs.get(message)) {
				System.out.printf("%d, ", combination);
			}
			System.out.printf("\n");
		}
	}

	/***************************************************
	 * Input processing
	 */

	/*
	 * Loads input file, containing rules and messages, separated by blank line
	 */
	private static void loadInputs() {
		messages = new HashSet<String>();
		rules = new HashMap<IntegerPair, HashSet<Integer>>();
		combinationsIDs = new HashMap<String, HashSet<Integer>>();

		boolean isloadingMessages = false;
		for (String inputLine : input) {
			// check if valid input line
			if (inputLine.length() < 1) {
				isloadingMessages = true;
				continue;
			} else {
				if (!isloadingMessages) {
					loadRule(inputLine);
				} else {
					messages.add(inputLine);
				}
			}
		}
		System.out.printf("Loaded %d rules\n", rules.size());
		System.out.printf("Loaded %d messages\n", messages.size());
	}

	// processes one rule
	private static void loadRule(String ruleDescription) {
		int colonPos = ruleDescription.indexOf(":");
		int ruleID = Integer.parseInt(ruleDescription.substring(0, colonPos));

		int positionOfDirectChar = ruleDescription.indexOf('"');
		if (positionOfDirectChar != -1) {
			// option 1: direct value
			// directly given single value as option
			String option = ruleDescription.substring(positionOfDirectChar + 1, positionOfDirectChar + 2);
			addBasicRule(option, ruleID);
		} else {
			// option 2: rule description
			processRuleDescription(ruleID, ruleDescription.substring(colonPos + 2)); // colon followed by space
		}
	}

	static private void addBasicRule(String option, int ruleID) {
		if (combinationsIDs.containsKey(option)) {
			combinationsIDs.get(option).add(ruleID);
		} else {
			HashSet<Integer> rules = new HashSet<Integer>();
			rules.add(ruleID);
			combinationsIDs.put(option, rules);
		}
	}

	// na základì mapy pravidel vygeneruje možnosti pro dané pravidlo
	// generates options for rule
	private static void processRuleDescription(int ruleID, String arguments) {
		String[] argumentsArr = arguments.split(Pattern.quote(" "+ PIPE + " "));
		
		for(int i = 0; i < argumentsArr.length; i++) {
			processArguments(ruleID, argumentsArr[i]);
		}
	}

	private static void processArguments(int ruleID, String arguments) {
		// rules separated by space
		String[] rulesIDs = arguments.split(" ");
		// combine two sets
		if (rulesIDs.length < 2) {
			throw new Error("Invalid number of rules, expected two, but given less; " + " ruleID " + ruleID
					+ " arguments " + arguments);
		} else if (rulesIDs.length == 2) {
			int ruleAID = Integer.parseInt(rulesIDs[0]);
			int ruleBID = Integer.parseInt(rulesIDs[1]);
			addNewRuleIntoRules(new IntegerPair(ruleAID, ruleBID), ruleID);
		} else {
			throw new Error("Invalid number of rules, expected two, but given more");
		}
	}
	
	private static void addNewRuleIntoRules(IntegerPair ip, int id) {
		if(rules.containsKey(ip)) {
			rules.get(ip).add(id);
		}else {
			HashSet<Integer> ids = new HashSet<Integer>();
			ids.add(id);
			rules.put(ip, ids);
		}
	}

	/***************************************************
	 * Solution assessment
	 */

	/**
	 * Validates message if it is valid, if it can be matched on the fundamental
	 * rule
	 * 
	 * @param message message to be assessed
	 * @return true, if message can be matched with the fundamental rule
	 */
	private static boolean isMessageValid(String message) {
		// get IDs of the most superior rules (more rules can match the message as the
		// whole)
		HashSet<Integer> messagesRules = new HashSet<Integer>();
		messagesRules = getSuperiorRuleID(message);
		// assesses, if any of most superior rule matches fundamental rule
		System.out.printf("message %s valid %b\n", message, messagesRules.contains(Integer.valueOf(FUNDAMENTAL_RULE)));
		return messagesRules.contains(Integer.valueOf(FUNDAMENTAL_RULE));
	}

	/**
	 * Identifies possible rules IDs based on part of the message
	 * 
	 * @param message message we want to process
	 * @return possible combinations that match message pattern
	 */
	private static HashSet<Integer> getSuperiorRuleID(String message) {
		setCombinations(message);	
		return getCombinationsIDs(message);
	}
	
	/**
	 * Sets possible combinations for given message pattern and stores in global combinations depository
	 * If pattern already known, does nothing
	 * @param message pattern to be analyzed for possible combinations
	 */
	private static void setCombinations(String message) {
		//already processed message
		if(combinationsIDs.containsKey(message)) {
			return;
		}
		HashSet<Integer> superiorRulesID = new HashSet<Integer>();
		for (int rider = 1; rider < message.length(); rider++) {
			HashSet<Integer> superiorRulesIDLeft = getSuperiorRuleID(message.substring(0, rider));
			HashSet<Integer> superiorRulesIDRight = getSuperiorRuleID(message.substring(rider));
			superiorRulesID.addAll(getSuperiorRules(superiorRulesIDLeft, superiorRulesIDRight));
		}
		combinationsIDs.put(message, superiorRulesID);
	}

	/**
	 * Based on left and right rules IDs identifies possible superior IDs
	 * 
	 * @param leftRules  rules IDs of left part
	 * @param rightRules rules IDs of right part
	 * @return rules created from left and right part (e.i. superior rule)
	 */
	private static HashSet<Integer> getSuperiorRules(HashSet<Integer> leftRules, HashSet<Integer> rightRules) {
		HashSet<Integer> superiorCombination = new HashSet<Integer>();
		for (int leftID : leftRules) {
			for (int rightID : rightRules) {
				HashSet<Integer> superiorRule = getRuleID(leftID, rightID);
				if (superiorRule != null) {
					superiorCombination.addAll(superiorRule);
				}
			}
		}
		return superiorCombination;
	}



	/**
	 * If ruleA and ruleB can be aggregated in superior rule, returns its ID If no
	 * superior rule matches combination of ruleA and ruleB, returns -1
	 * 
	 * @param ruleA left hand side rule ID
	 * @param ruleB right hand side rule ID
	 * @return -1 if rules cannot be aggregated, otherwise aggregation rule ID
	 */
	private static HashSet<Integer> getRuleID(int ruleA, int ruleB) {
		INVALID_COMBINATION = -1;
		IntegerPair combination = new IntegerPair(ruleA, ruleB);
		if (!rules.containsKey(combination)) {
			return null;
		} else {
			return rules.get(combination);
		}
	}

	private static HashSet<Integer> getCombinationsIDs(String letter) {
		return combinationsIDs.get(letter);
	}

	private static int part1Solution() {
		int counterValidMessages = 0;
		for (String message : messages) {
			if (isMessageValid(message)) {
				counterValidMessages++;
			}
		}
		return counterValidMessages;
	}

	// class to tore two integers
	private static class IntegerPair {
		int intA;
		int intB;

		IntegerPair(int intA, int intB) {
			this.intA = intA;
			this.intB = intB;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + intA;
			result = prime * result + intB;
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
			IntegerPair other = (IntegerPair) obj;
			if (intA != other.intA)
				return false;
			if (intB != other.intB)
				return false;
			return true;
		}

	}

}
