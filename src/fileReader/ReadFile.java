package fileReader;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.LinkedList;
import java.util.Scanner; // Import the Scanner class to read text files

public class ReadFile {
  public static LinkedList<String> getFileAsList(String fileName) {
	  LinkedList<String> list=new LinkedList<String>();
	  try {
	      File myObj = new File(fileName);
	      Scanner myReader = new Scanner(myObj);
	      
	      while (myReader.hasNextLine()) {
	        String data = myReader.nextLine();
	        list.add(data);
	      }
	      myReader.close();
	  } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	  }
	  return list;
  }
}