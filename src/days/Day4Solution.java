package days;

import java.util.HashMap;
import java.util.LinkedList;

import collectionsConvertor.listsConvertor;
import fileReader.ReadFile;

public class Day4Solution {
	/**
	 * This class solves puzzle from day 4.
	 * First part needs to evaluate input and get count of valid passports.
	 * The passport is valid, if all required fields are present, country field is optional
	 */
	
	private static final String FILE_NAME = "day4Input.txt"; 
	
	public static void main(String[] args) {

		// Load the text input with map (symbols)
		final LinkedList<String> input = ReadFile.getFileAsList(FILE_NAME);
		// Loop through file lines and join passport inputs from multiple lines together
		final LinkedList<String> passwordsData = listsConvertor.concatEmptyLineSeparatedData(input);
		System.out.printf("Solution part 1: %d\n", part1Solution(passwordsData));
		System.out.printf("Solution part 2: %d\n", part2Solution(passwordsData));
	}
		
		private static int part1Solution(LinkedList<String> passwordsData) {
			int counter = 0;
			// Loop through passports data
			for(String s : passwordsData) {
				// Create passport object, fill with credentials
				Passport pp = new Passport(s.split(" "));
				// Evaluate credentials
				if(pp.isValidPart1Solution()) counter++;
			}
			return counter;
		}
		
		private static int part2Solution(LinkedList<String> passwordsData) {
			int counter = 0;
			// Loop through passports data
			for(String s : passwordsData) {
				// Create passport object, fill with credentials
				Passport pp = new Passport(s.split(" "));
				// Evaluate credentials
				if(pp.isValidPart2Solution()) counter++;
			}
			return counter;
		}
		
	
	/**
	 * Passport object, responsible for handling passport data and its validation
	 * @author Petr
	 *
	 */
	static class Passport{
		private HashMap<String, String> credentials = new HashMap<String, String>();
		
		Passport(String[] credentials){
			initCredentialsMap();
			
			for(String s : credentials) {
				String[] sep = s.split(":");
				setCredential(sep[0], sep[1]);
			}
		}
		
		private void initCredentialsMap() {
			credentials.put("byr", ""); //(Birth Year)
			credentials.put("iyr", ""); //(Issue Year)
			credentials.put("eyr", ""); //(Expiration Year)
			credentials.put("hgt", ""); //(Height)
			credentials.put("hcl", ""); //(Hair Color)
			credentials.put("ecl", ""); //(Eye Color)
			credentials.put("pid", ""); //(Passport ID)
			credentials.put("cid", ""); //(Country ID)
		}
		
		private void setCredential(String credential, String value){
			if(!credential.contains(credential)) {
				throw new NoSuchFieldError(credential + "not valid");
			}
			credentials.replace(credential, value);
		}
		
		/**
		 * Validates if passport is valid
		 * @return valid, if required credentials are filled
		 */
		public boolean isValidPart1Solution() {
			int counter = 0;
			for(String credential : credentials.keySet()) {
				if(credential=="cid") continue;
				if(credentials.get(credential).length()>0) counter++;
			}
			// -1 = cid (country) optional, not included in counter
			return (counter==credentials.keySet().size()-1);
		}
		
		public boolean isValidPart2Solution() {
			int counter = 0;
			for(String credential : credentials.keySet()) {
				if(credential=="cid") continue;
				if(part2Validator(credential, credentials.get(credential))) {
					//System.out.println(credential + " valid");
					counter++;
				}else {
					//System.out.println(credential + " invalid");
				}
					
			}
			// -1 = cid (country) optional, not included in counter
						return (counter==credentials.keySet().size()-1);
		}
		
		private boolean part2Validator(String credentials, String value) {
			switch (credentials) {
				case "byr":
					try {
						int yearBYR = Integer.parseInt(value);
						return (1920 <= yearBYR && yearBYR <= 2002); 
					}catch (Exception e) {
						return false;
					}
				case "iyr":
					try {
						int yearIYR = Integer.parseInt(value);
						return (2010 <= yearIYR && yearIYR <= 2020); 
					}catch (Exception e) {
						return false;
					}
				case "eyr":
					try {
						int yearEYR = Integer.parseInt(value);
						return (2020 <= yearEYR && yearEYR <= 2030);
					}catch (Exception e) {
						return false;
					}
				case "hgt":
					try {
						String unit = value.substring(value.length()-2);
						int hgt = Integer.parseInt(value.substring(0,value.length()-2));
						if(unit.equals("cm")){
							return (150 <= hgt && hgt <= 193);  	
						}else if(unit.equals("in")){
							return (59 <= hgt && hgt <= 76);  	
						}else{
							return false;
						}
					}catch (Exception e) {
						return false;
					}

				case "hcl":
					if(value.length()!=7) return false;
					if(value.charAt(0)!='#') return false;
					value = value.replaceAll("([a-f])", "");
					value = value.replaceAll("([0-9])", "");
					if(value.length()!=1) return false;
					return true;
				case "ecl":
					if(value.equals("amb"))return true;
					if(value.equals("blu"))return true;
					if(value.equals("brn"))return true;
					if(value.equals("gry"))return true;
					if(value.equals("grn"))return true;
					if(value.equals("hzl"))return true;
					if(value.equals("oth"))return true;
					return false;
				case "pid":
					if(value.length()!=9)return false;
					value = value.replaceAll("([0-9])", "");
					if(value.length()!=0) return false;
					return true;
				case "cid": // should never happen
					return false;	
				default:
					return false;
			}

		}
		
	}
	 
	
}
