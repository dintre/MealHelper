import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/*
 * stores food data
 */
public class BPTree {

	
	List<Food> foodList;
	
	
	public List<Food> loadFoodFile(){
		// create variable for the file to be loaded
		File foodFile = null;
		// create scanner to look through file
		Scanner input = null;
		// create line of file data
		String fileLine = null;
		// create file name
		String fileName = "foodItems.csv";
		
		try {
			foodFile = new File(fileName);
			System.out.println("Reading food data. "); // TODO - remove
			input = new Scanner(foodFile);
			
			// while reading each line from the file
			while(input.hasNextLine()) {
				fileLine = input.nextLine();
				
				// check if line is blank, then continue
				if(fileLine.length() == 0) {
					System.out.println("That line was empty. Continuing... "); // TODO - remove
					continue; // continue back to while loop start
				}
				
				// split the data in the file by commas
				String[] commaSplitter = fileLine.split(","); 
				if(commaSplitter.length != 12) { // if line format is wrong (not twelve fields)
					System.out.println("That line was badly formatted/incorrect. Continuing... ");
					continue; // continue back to the while loop start
				} 
				
				// otherwise the line is valid. Create a food item
				Food newFood = new Food(commaSplitter[]);
				
			} // while reading lines
			
			
		} // try
		catch (FileNotFoundException e) { // catch when the file isn't found
			e.printStackTrace();
		} // catch fileNotFound
		
		
		
		finally {
			
			
		} // finally
		
		return foodList;

	} // loadFoodFile()
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {

	} // Main()

} // class BPTree
