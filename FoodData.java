import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
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
    private HashMap<String, BPTree<Double, Food>> indexes;
    
    
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
			input = new Scanner(foodFile);
			
			// while reading each line from the file
			while(input.hasNextLine()) {
				fileLine = input.nextLine();
				
				// check if line is blank, then continue
				if(fileLine.length() == 0) {
					continue; // continue back to while loop start
				}
				String[] commaSplitter = fileLine.split(",");
				if(commaSplitter.length == 0) {
					continue; // continue back to while loop start
				}
				String id = commaSplitter[0];
				String name = commaSplitter[1];
				double calories = Double.parseDouble(commaSplitter[3]);
				double fat = Double.parseDouble(commaSplitter[5]);
				double carbs = Double.parseDouble(commaSplitter[7]);
				double fiber = Double.parseDouble(commaSplitter[9]);
				double protein = Double.parseDouble(commaSplitter[11]);

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
    	substring = substring.toLowerCase();
    	// create list to be returned
    	ArrayList<Food> returnList = new ArrayList<Food>();
    	
    	// if the list of food list is empty, return empty list
    	if(FoodList.isEmpty()) {
    		return returnList;
    	}
    	
    	// loop through foodList food names and check if name contains search string
    	for(int i = 0; i < FoodList.size(); i++) {
    		String foodName = FoodList.get(i).getName().toLowerCase();
    		if(foodName.contains(substring)) {
    			returnList.add(FoodList.get(i));
    			System.out.println(FoodList.get(i).getName()); // TODO - remove test code
    		}
    	}

        return returnList;
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
        // TODO : Complete?
    	FoodList.add(Food);
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoods()
     */
    @Override
    public List<Food> getAllFoods() {
        // TODO : Complete
    	// TODO - should this return just the food names? Or have a separate method for that?
    	return FoodList;
    }


	@Override
	public void saveFoods(String filename) {
		File saveFile = null;
		PrintStream writer = null;
		
		try {
			saveFile = new File(filename); // create the file
			writer = new PrintStream(saveFile); // create the writer
			
			// loop through the current arraylist of food
			for(int i = 0; i < FoodList.size(); i++) {
				Food current = FoodList.get(i);
				// write the line
				writer.println(current.getId() + "," + current.getName()
				+ ",calories," + current.getCalories() + ",fat," + 
				current.getFat() + ",carbohydrates," + current.getCarbohydrates() 
				+ ",fiber," + current.getFiber() + ",protein," 
				+ current.getProtein());
			} // for loop
			
		} // try
		
		catch (IOException e){ // could not save for some reason
			System.out.println("Could not save the food list. ");
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			if(writer != null) {
				writer.close();
			}
		}
		
	} // saveFoods()

	// TODO - do we need this? Or can we make the original one work on its own
	public ArrayList<String> getFoodNames(){
    	ArrayList<Food> tempList = (ArrayList<Food>) FoodList;
    	
    	ArrayList<String> returnList = new ArrayList<String>();
    	
    	for(int i = 0; i < tempList.size(); i++) {
    		returnList.add(tempList.get(i).getName());
    	}
    	   	
    	return returnList;
	}
	
	
	public static void main(String[] args) {
		
		FoodData foods = new FoodData();
		foods.loadFoods("foodItems.csv");
		
		System.out.println("Food list is this: ");
		System.out.println(foods.FoodList);
		
		System.out.println();
		System.out.println();
		System.out.println("Filtering by names ");
		System.out.println();
		System.out.println("Filter by 'soy' :"); foods.filterByName("soy");
		System.out.println();
		System.out.println("Filter by 'nut' :"); foods.filterByName("nut");
		
		foods.saveFoods("testSave.csv");
		
	} // Main()
	
	
	
	
} // class FoodData
