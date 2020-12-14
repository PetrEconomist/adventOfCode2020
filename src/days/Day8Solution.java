package days;

import java.util.HashSet;
import java.util.LinkedList;

import fileReader.ReadFile;

public class Day8Solution {

	private static final String FILE_NAME = "day8Input.txt";
	private static LinkedList<String> input;

	public static void main(String[] args) {
		//load raw input
		input = ReadFile.getFileAsList(FILE_NAME);
		System.out.printf("part1Solution: %d\n", part1Solution());
		System.out.printf("part1Solution: %d\n", part2Solution());
	}
	
	private static int part1Solution() {
		Console console = new Console (input);
		return console.runConsolePart1(0);
	}
	
	private static int part2Solution() {
		int changedPos = 0;
		while(true) {
			try{
				return (new Console (changeInput(changedPos))).runConsolePart2(0);
			} catch (Exception e) {
				changedPos++;
			}
		}
	}
	
	private static LinkedList<String> changeInput(int changedPos) {
		LinkedList<String> output = new LinkedList<String>();
		output.addAll(input);
		String argument = input.get(changedPos).substring(0, 3);
		switch (argument) {
			case "nop": output.set(changedPos, "jmp" + input.get(changedPos).substring(4));
						return output;
			case "jmp": output.set(changedPos, "nop" + input.get(changedPos).substring(4));
						return output;
			case "acc": return null;	
			default: 	throw new IllegalAccessError("Illegal argument " + argument); 
		}	
	}
	
	private static class Console {
		private LinkedList<String> commands;
		private HashSet<Integer> performedCommands;
		private int accumulator = 0;
		
		Console(LinkedList<String> commands){
			this.commands = commands;
			performedCommands = new HashSet<Integer>();
		}
		
		public int runConsolePart1(int commandLine){
			if(performedCommands.contains(commandLine))return accumulator;
			performedCommands.add(commandLine);
			String command = commands.get(commandLine);
			String commandCode = command.substring(0, 3);
			int commandValue = Integer.parseInt(command.substring(4));
			switch (commandCode) {
				case "acc": accumulator += commandValue;
							return runConsolePart1(commandLine+1);
				case "jmp": return runConsolePart1(commandLine+commandValue);
				case "nop":	return runConsolePart1(commandLine+1);
				default:	throw new IllegalArgumentException("unknow command " + commandCode);
			}
		}
		
		public int runConsolePart2(int commandLine){
			if(performedCommands.contains(commandLine)) throw new IllegalArgumentException("Infinite loop");
			if(commandLine==commands.size())return accumulator;
			performedCommands.add(commandLine);
			String command = commands.get(commandLine);
			String commandCode = command.substring(0, 3);
			int commandValue = Integer.parseInt(command.substring(4));
			switch (commandCode) {
				case "acc": accumulator += commandValue;
							return runConsolePart2(commandLine+1);
				case "jmp": return runConsolePart2(commandLine+commandValue);
				case "nop":	return runConsolePart2(commandLine+1);
				default:	throw new IllegalArgumentException("unknow command " + commandCode);
			}
		}
		
		
	}

}
