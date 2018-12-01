import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents the backend for managing all 
 * the operations associated with Foods
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
public class FoodData implements FoodDataADT<Food> {
    
    // List of all the food items.
    private List<Food> FoodList;

    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, Food>> indexes;
    
    
    /**
     * Public constructor
     */
    public FoodData() {
        // TODO : Complete
    }
    
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoods(java.lang.String)
     */
    @Override
    public void loadFoods(String filePath) {
        // TODO : Complete
    	// TODO - remove brand names at beginning of food names?
    	// TODO - handle empty lines
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
				
				// create new scanner that scans just this line
				Scanner lineScanner = new Scanner(fileLine);
				lineScanner.useDelimiter(",");				
				// get id
				String id = lineScanner.next();
				if(id == null) {
					System.out.println("This line is invalid because ID was null. Continuing... "); // TODO - remove test code
					continue; // continue on back to the while loop to scan a new line
				}
				// get name of food
				String name = lineScanner.next();
				// declare remaining nutritional values to get
				int calories;
				int fat;
				int carbs;
				int fiber;
				int protein;
				
				if(lineScanner.next().equals("calories")) {
					calories = lineScanner.nextInt();
				}
				
				if(lineScanner.next().equals("fat")) {
					fat = lineScanner.nextInt();
				}
				
				if(lineScanner.next().equals("carbohydrate")) {
					carbs = lineScanner.nextInt();
				}
				
				if(lineScanner.next().equals("fiber")) {
					fiber = lineScanner.nextInt();
				}
				
				if(lineScanner.next().equals("protein")) {
					protein = lineScanner.nextInt();
				}
				
				Food newFood = new Food(id, name, calories, fat, carbs, fiber, protein);
				
			} // while reading lines
			
			
		} // try
		catch (FileNotFoundException e) { // catch when the file isn't found
			e.printStackTrace();
		} // catch fileNotFound
		
		
		
		finally {
			
			
		} // finally
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByName(java.lang.String)
     */
    @Override
    public List<Food> filterByName(String substring) {
        // TODO : Complete
        return null;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
     */
    @Override
    public List<Food> filterByNutrients(List<String> rules) {
        // TODO : Complete
        return null;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFood(skeleton.Food)
     */
    @Override
    public void addFood(Food Food) {
        // TODO : Complete
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoods()
     */
    @Override
    public List<Food> getAllFoods() {
        // TODO : Complete
        return null;
    }


	@Override
	public void saveFoods(String filename) {
		// TODO Auto-generated method stub
		
	}

}
