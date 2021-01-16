package days;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;

import fileReader.ReadFile;

public class Day14Solution {


	private static final String FILE_NAME = "day14Input.txt";
	private static LinkedList<String> input;
	
	private static String mask;
	static HashMap<Integer, String> codesMemory;
	static HashMap<Long, Integer>  codesMemoryPart2;
	
	private static final String COMMAND_MEMORY = "mem";
	private static final String COMMAND_MASK = "mask";
	private static final int MASK_LENGTH = 36;
	private static final char MASK_SKIP_CODE = 'X';
	private static final char MASK_CODE_LEAVE_UNCHANGED = '0';
	private static final char MASK_CODE_OVERWRITE_WITH_ONE = '1';
	private static final char MASK_CODE_FLOATING = 'X';
	private static final char ONE = '1';
	private static final char FLOATING_BIT = 'X';
	
	
	public static void main(String[] args) {
		//load raw input
		input = ReadFile.getFileAsList(FILE_NAME);
		codesMemory = new HashMap<Integer, String>();
		BigInteger part1Output = part1Solution();
		System.out.printf("part1Solution: %d\n", part1Output);
		codesMemoryPart2 = new HashMap<Long, Integer>() ;
		BigInteger part2Output = part2Solution();
		System.out.printf("part2Solution: %d\n", part2Output);
	}

	private static BigInteger part1Solution() {
		for(String command : input) {
			processCommand(command);
		}
		BigInteger memorySum = BigInteger.ZERO;
		for(int memoryIndex : codesMemory.keySet()) {
			System.out.printf("adding %d to %d\n", binaryToDecimal(codesMemory.get(memoryIndex)), memorySum);
			memorySum = memorySum.add(BigInteger.valueOf(binaryToDecimal(codesMemory.get(memoryIndex))));
		}
		return memorySum;
	}
	
	private static BigInteger part2Solution() {
		for(String command : input) {
			processCommandPart2(command);
		}
		BigInteger memorySum = BigInteger.ZERO;
		for(long memoryIndex : codesMemoryPart2.keySet()) {
			memorySum = memorySum.add(BigInteger.valueOf(codesMemoryPart2.get(memoryIndex)));
		}
		return memorySum;
	}
	
	private static void getMemoryValueTest() {
		String numBinary = "0101";
		System.out.printf("Value %s = %d\n", binaryToDecimal(numBinary), numBinary);
		numBinary = "0001";
		System.out.printf("Value %s = %d\n", binaryToDecimal(numBinary), numBinary);
		numBinary = "1110";
		System.out.printf("Value %s = %d\n", binaryToDecimal(numBinary), numBinary);
		numBinary = "10000";
		System.out.printf("Value %s = %d\n", binaryToDecimal(numBinary), numBinary);
	}
	
	private static long binaryToDecimal(String binary) {
		long decimal = 0;
		for(int numPos = 0; numPos < binary.length(); numPos++) {
			if(binary.charAt((binary.length() - 1) - numPos)=='1') {
				decimal += Math.pow(2, numPos);
			}
		}
		return decimal;
	}
	
	private static void decimalToBinaryTest() {
		long numDecimal = 1;
		System.out.printf("Value %s = %d\n", decimalToBinary(numDecimal, 8), numDecimal);
		numDecimal = 0;
		System.out.printf("Value %s = %d\n", decimalToBinary(numDecimal, 8), numDecimal);
		numDecimal = 15;
		System.out.printf("Value %s = %d\n", decimalToBinary(numDecimal, 8), numDecimal);
		numDecimal = 4;
		System.out.printf("Value %s = %d\n", decimalToBinary(numDecimal, 8), numDecimal);
	}
	
	private static String decimalToBinary(long decimal, int binaryLength) {
		String binary = getZeroString(binaryLength);
		long remaining = decimal;
		while(remaining != 0) {
			int exponent = (int) Math.floor(Math.log(remaining) / Math.log(2));
			binary = binary.substring(0, binary.length() - exponent - 1 ) 
					+ "1" 
					+ binary.substring(binary.length() - exponent);
			remaining -= Math.pow(2, exponent);
		}
		return binary;
	}
	
	private static String getZeroString(int length) {
		String result = "";
		for(int i = 0; i < length; i++) {
			result = result + "0";
		}
		return result;
	}
	
	private static void processCommand(String command) {
		if(command.substring(0,COMMAND_MASK.length()).equals(COMMAND_MASK)){
			commandMask(command);
		}else if(command.substring(0,COMMAND_MEMORY.length()).equals(COMMAND_MEMORY)){
			commandMemory(command);
		}else {
			throw new Error("invalid command type");
		}
	}
	
	private static void processCommandPart2(String command) {
		if(command.substring(0,COMMAND_MASK.length()).equals(COMMAND_MASK)){
			commandMask(command);
		}else if(command.substring(0,COMMAND_MEMORY.length()).equals(COMMAND_MEMORY)){
			commandMemoryPart2(command);
		}else {
			throw new Error("invalid command type");
		}
	}
	
	private static void commandMask(String command) {
		String maskShort = command.substring(command.indexOf('=') + 2);
		//add zeros at the beginning
		mask = getZeroString(MASK_LENGTH - maskShort.length()) + maskShort;
	}
	
	private static void commandMemory(String command) {
		int memoryIndex = getMemoryInt(command);
		int commandValue = Integer.parseInt(command.substring(command.indexOf('=') + 2));   
		String memoryInput = decimalToBinary(commandValue, MASK_LENGTH);
		codesMemory.remove(memoryIndex);
		String memoryNew = getNewMemory(memoryInput);
		codesMemory.put(memoryIndex, memoryNew);
	}
	
	private static void commandMemoryPart2(String command) {
		int memoryIndex = getMemoryInt(command);
		int commandValue = Integer.parseInt(command.substring(command.indexOf('=') + 2));   
		String memoryIndexBinary = decimalToBinary(memoryIndex, MASK_LENGTH);
		String memoryAdressRaw = setUsingMemoryAddressDecoder(memoryIndexBinary);
		String[] memoryAddressCombinations = getAddressCombinations(memoryAdressRaw);
		for(int combinationIndex = 0; combinationIndex < memoryAddressCombinations.length; combinationIndex++) {
			long memoryAddress = binaryToDecimal(memoryAddressCombinations[combinationIndex]);
			codesMemoryPart2.remove(memoryAddress);
			codesMemoryPart2.put(memoryAddress, commandValue);
		}
	}
	
	private static String[] getAddressCombinations(String memoryAddressRaw) {
		String[] combinations = {""};
		for(int charPos = 0; charPos < memoryAddressRaw.length(); charPos++) {
			if(memoryAddressRaw.charAt(charPos) == FLOATING_BIT) {
				combinations = getExtendedArray(combinations, new String[]{"0","1"});
			}
			else {
				combinations = getExtendedArray(combinations, new String[] {memoryAddressRaw.substring(charPos, charPos+1)});
			}
		}
		return combinations;
	}
	
	/**
	 * Extends input array elements by extensions.
	 * It takes input array and creates new array, with old elements extended by one of extensions.
	 * That is new elements count will be old count multiplied by extensions count. 
	 * @param inputArray array which elements should be extended
	 * @param extensions extensions (various) to be attached to input array
	 * @return old elements extended by one extensions (using all extensions)
	 */
	private static String[] getExtendedArray(String[] inputArray, String[] extensions) {
		String[] output = new String[inputArray.length * extensions.length];
		for(int extensionsIndex = 0; extensionsIndex < extensions.length; extensionsIndex++) {
			for(int inputIndex = 0; inputIndex < inputArray.length; inputIndex++) {
				output[inputIndex + (extensionsIndex * inputArray.length)] = inputArray[inputIndex] + extensions[extensionsIndex];
			}
		}
		return output;
	}
	
	private static String setUsingMemoryAddressDecoder(String input) {
		String output = input;
		for(int charPos = 0; charPos < mask.length(); charPos++) {
			if(mask.charAt(charPos)==MASK_CODE_LEAVE_UNCHANGED) {
				continue;
			}
			else if(mask.charAt(charPos)==MASK_CODE_OVERWRITE_WITH_ONE) {
				output = replaceCharAtIndex(output, charPos, ONE);
			}
			else if(mask.charAt(charPos)==MASK_CODE_FLOATING) {
				output = replaceCharAtIndex(output, charPos, FLOATING_BIT);
			}else {
				throw new Error("Invalid mask");
			}
		}
		return output;
	}
	
	private static String replaceCharAtIndex(String input, int index, char replacement) {
		String output = input;
		output = output.substring(0, index) 
				 + replacement
				 + output.substring(index + 1);
		return output;
	}
	
	
	
	private static String getNewMemory(String memoryOld) {
		String memoryNew = memoryOld;
		for(int charPos = 0; charPos < mask.length(); charPos++) {
			if(mask.charAt(charPos)!=MASK_SKIP_CODE) {
				memoryNew = memoryNew.substring(0, charPos) + 
							mask.charAt(charPos) + 
							memoryNew.substring(charPos + 1);
			}
		}
		return memoryNew;
	}
	
	private static int getMemoryInt(String command) {
		String memoryCode = command.substring(command.indexOf("[")+1, command.indexOf("]"));
		return Integer.parseInt(memoryCode);
	}
	
}
