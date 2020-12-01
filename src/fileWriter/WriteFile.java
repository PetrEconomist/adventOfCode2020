package fileWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class WriteFile {

	/**
	 * Tries to create new txt file or in project folder
	 * @param fileName name of created txt file
	 */
	private static void createFile(String fileName) {
	    try {
	      File myObj = new File(fileName);
	      if (myObj.createNewFile()) {
	        System.out.printf("File created: %s\n", myObj.getName());
	      } else {
	        System.out.printf("File already exists.\n");
	      }
	    } catch (IOException e) {
	      System.out.printf("An error occurred.\n");
	      e.printStackTrace();
	    }
	}
	
	/**
	 * Writes input into file, each array field on new line.
	 * If file does not exist in project folder, creates a file.
	 * @param fileName name of file to be written in
	 * @param inputLines lines to be loaded int the file
	 */

	public static void writeIntoFile(String fileName, String[] inputLines) {
		createFile(fileName);
		
		String inputStr = "";
		for(String s: inputLines) {
			inputStr = inputStr + s + "\n";
      }
		
		try {
		    Files.write(Paths.get(fileName), inputStr.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
	  }
	
	
	
}
