package stringFunctions;

import java.util.HashMap;
import java.util.HashSet;

public class StringSymbols {
	
	/**
	 * Goes though given string and return characters used
	 * @param inStr
	 * @return
	 */
	public static HashSet<Character> getUsedChars(String inStr){
		HashSet<Character> output = new HashSet<Character>();
		for(int i=0; i < inStr.length();i++) {
			output.add(inStr.charAt(i));
		}
		return output;
	}
	
	public static HashMap<Character, Integer> getUsedCharsCount(String inStr){
		HashMap<Character, Integer> output = new HashMap<Character, Integer>();
		for(int i=0; i < inStr.length();i++) {
			char c = inStr.charAt(i);
			//key does not exits, create with value zero
			if(!output.containsKey(c)){
				output.put(c, 0);
			}
			//key exists (always) -> increment
			output.replace(c, output.get(c) + 1);
		}
		return output;
	}
	
}
