import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
    //private HashMap<String, BPTree<Double, Food>> indexes;
    
    
    /**
     * Public constructor
     */
    public FoodData() {
        // TODO : Complete
    	FoodList = new ArrayList<Food>();
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
		String fileName = filePath;
		
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
				String[] commaSplitter = fileLine.split(",");
				if(commaSplitter.length == 0) {
					System.out.println("That line was not formatted properly. Continuing... ");
					continue; // continue back to while loop start
				}
				String id = commaSplitter[0];
				String name = commaSplitter[1];
				double calories = Double.parseDouble(commaSplitter[3]);
				double fat = Double.parseDouble(commaSplitter[5]);
				double carbs = Double.parseDouble(commaSplitter[7]);
				double fiber = Double.parseDouble(commaSplitter[9]);
				double protein = Double.parseDouble(commaSplitter[11]);
				System.out.println(id + " " + name + " " + calories + " " + fat + " " + carbs + " " + fiber + " " + protein);

				Food newFood = new Food(id, name, calories, fat, carbs, fiber, protein);
				FoodList.add(newFood);

			} // while reading lines
			
			
		} // try

		catch (FileNotFoundException e) { // catch when the file isn't found
			e.printStackTrace();
		} // catch fileNotFound
		
		catch (Exception e) { // catch any other exceptions
			e.printStackTrace();
		}

		finally {
			// close the scanner
			input.close();
		} // finally

    } // loadFoods()

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

	
	
	public static void main(String[] args) {
		
		FoodData foods = new FoodData();
		foods.loadFoods("foodItems.csv");
		
		System.out.println("Food list is this: ");
		System.out.println(foods.FoodList);
		
	} // Main()
	
	
	
	
} // class FoodData
