package days;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import fileReader.ReadFile;

public class Day19Solution {


	private static final String FILE_NAME = "day19Input.txt";
	private static LinkedList<String> input;
	private static HashMap<Integer, String> rules;
	private static HashMap<Integer, HashSet<String>> rulesOptions;
	private static HashSet<String> messages;
	private static final int BASE_RULE = 11;
	private static final String PIPE = "|";
	
	public static void main(String[] args) {
		//load raw input
		input = ReadFile.getFileAsList(FILE_NAME);
		loadInputs();
		int part1Output = getPart1Solution();
		System.out.printf("part1Solution: %d\n", part1Output);
	}
	
	private static int getPart1Solution() {
		setRuleOptions(8);
		setRuleOptions(11);
		HashSet<String> possibleOptionsBeginning = rulesOptions.get(8);
		HashSet<String> possibleOptionsEnd = rulesOptions.get(11);
		int validMessagesCount = 0;
		for(String message : messages) {
			for(String beginning : possibleOptionsBeginning){
				int len = beginning.length();
				if(message.substring(0, len).equals(beginning)) {
					if(message.equals("aaaabaabaaaaaaababaababb")) {
						System.out.printf("");
					}
					if(possibleOptionsEnd.contains(message.substring(len))){
						validMessagesCount++;
						System.out.printf("%s valid\n", message);
						continue;
					}
				}	
			}
			System.out.printf("%s invalid\n", message);
		}
		return validMessagesCount;
	}
	
	//roztøídí vstup na pravidla a zprávy a uloží je pro další zpracování
	//stores rules and messages separately from input for further processing
	private static void loadInputs() {
		messages = new HashSet<String>();
		rules = new HashMap<Integer, String>();
		rulesOptions = new HashMap<Integer, HashSet<String>>();
		
		boolean isloadingMessages = false;
		for(String inputLine : input) {
			//check if valid input line
			if(inputLine.length() < 1) {
				isloadingMessages = true;
				continue;
			}else {
				if(!isloadingMessages) {
					loadRule(inputLine);
				}else {
					messages.add(inputLine);
				}
			}
		}
		System.out.printf("Loaded %d rules\n", rules.size());
		System.out.printf("Loaded %d messages\n", messages.size());
	}
	
	//processes one rule
	private static void loadRule(String ruleDescription) {
		int colonPos = ruleDescription.indexOf(":");
		int ruleID = Integer.parseInt(ruleDescription.substring(0, colonPos));
		if(ruleID == 86) {
			ruleID = 86;
		}
		//option 1: direct value
		int positionOfDirectChar = ruleDescription.indexOf( '"' );
		if( positionOfDirectChar != -1 ) {
			//directly given single value as option
			HashSet<String> option = new HashSet<String>();
			option.add(ruleDescription.substring(positionOfDirectChar + 1, positionOfDirectChar + 2));
			rulesOptions.put(ruleID, option);
		}else {
			rules.put(ruleID, ruleDescription.substring(colonPos + 2)); //colon followed by space
		}
	}
	
	
	
	//na základì mapy pravidel vygeneruje možnosti pro dané pravidlo
	//generates options for rule
	private static void setRuleOptions(int ruleID){
		if(ruleID==86) {
			ruleID = 86; //never reached
		}
		String arguments = rules.get(ruleID);
		//pipe position
		int pipePosition = arguments.indexOf( PIPE );
		HashSet<String> options;
		if(pipePosition == -1) {
			options = processArguments(ruleID, arguments);
		}else {
			//separate two options and then unite options from both options
			String argumentsA = arguments.substring(0, pipePosition);
			String argumentsB = arguments.substring(pipePosition + 2); //after pipe there is space, therefore + 2
			
			options = processArguments(ruleID, argumentsA);
			options.addAll(processArguments(ruleID, argumentsB));
		}
		rulesOptions.put(ruleID, options);
	}
	
	private static HashSet<String> processArguments(int ruleID, String arguments) {
		HashSet<String> options;
		//rules separated by space
		String[] rulesIDs = arguments.split(" ");
		//combine two sets
		if(rulesIDs.length == 1) {
			int argumentID = Integer.parseInt(rulesIDs[0]);
			if(!rulesOptions.containsKey(argumentID)) {
				setRuleOptions(argumentID);
			}
			options = (HashSet<String>) rulesOptions.get(argumentID).clone();
		}else if(rulesIDs.length == 2) {
			int ruleAID = Integer.parseInt(rulesIDs[0]);
			int ruleBID = Integer.parseInt(rulesIDs[1]);
			options = getRuleOptions(ruleID, ruleAID , ruleBID );
		}else {
			throw new Error("invalid number of rules, expected one or two, but given more");
		}
		return options;
	}
	
	private static HashSet<String> getRuleOptions(int targetRule, int sourceRuleA, int sourceRuleB) {
		if(!rulesOptions.containsKey(sourceRuleA) ){
			setRuleOptions(sourceRuleA);
		}
		if(!rulesOptions.containsKey(sourceRuleB) ){
			setRuleOptions(sourceRuleB);
		}
		HashSet<String> optionsSetA = rulesOptions.get(sourceRuleA);
		HashSet<String> optionsSetB = rulesOptions.get(sourceRuleB);
		
		System.out.printf("Combining rules %d and %d into rule %d\n", sourceRuleA, sourceRuleB, targetRule);
		return combineSets(optionsSetA, optionsSetB);
	}
	
	//nakombinuje množiny
	//combines two sets in given order
	private static HashSet<String> combineSets (HashSet<String> setA, HashSet<String> setB){
		HashSet<String> combinations = new HashSet<String>();
		for(String setAItem : setA) {
			for(String setBItem : setB) {
				if((setAItem + setBItem).equals("aaaaaa")) {
					//searching for source of incorrect key (id 8)
					System.out.printf("");
				}
				combinations.add(setAItem + setBItem);
			}
		}
		return combinations;
	}
	


}
