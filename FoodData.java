import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class represents the backend for managing all 
 * the operations associated with Foods
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
public class FoodData implements FoodDataADT<Food> {
    
    // List of all the food items.
    private List<Food> foodList;

    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, Food>> indexes;
    
    // Food name tree
	BPTree<String,Food> nameTree;
	// Calories tree
	BPTree<Double,Food> caloriesTree;
    // Carbohydrate tree
    BPTree<Double,Food> carbohydrateTree;
    // Fat tree
    BPTree<Double,Food> fatTree;
    // Fiber tree
    BPTree<Double,Food> fiberTree;
    // Protein tree
    BPTree<Double,Food> proteinTree;
    /**
     * Public constructor
     */
    public FoodData() {
        // TODO : Complete
    	foodList = new ArrayList<Food>();
    	
    	// instantiate indexes hashmap variable
    	indexes = new HashMap<String, BPTree<Double, Food>>();
    	
    	// BPTrees
    	nameTree = new BPTree<String,Food>(3);
    	caloriesTree = new BPTree<Double,Food>(3);
    	// carbohydrate tree
    	carbohydrateTree = new BPTree<Double,Food>(3);
    	// fat tree
    	fatTree = new BPTree<Double,Food>(3);
    	// fiber tree
    	fiberTree = new BPTree<Double,Food>(3);
    	// protein tree
    	proteinTree = new BPTree<Double,Food>(3);
    	
    } // constructor
    
    
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
				foodList.add(newFood);
				
				// adding to trees
				nameTree.insert(name,newFood);
				caloriesTree.insert(calories, newFood);
                carbohydrateTree.insert(carbs, newFood);
                fatTree.insert(fat, newFood);;
                fiberTree.insert(fiber, newFood);
                proteinTree.insert(protein, newFood);

			} // while reading lines
			indexes.put("calories", caloriesTree);
            indexes.put("carbohydrates", carbohydrateTree);
            indexes.put("fat", fatTree);
            indexes.put("fiber", fiberTree);
            indexes.put("protein", proteinTree);
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
    	if(foodList.isEmpty()) {
    		return returnList;
    	}
    	
    	// loop through foodList food names and check if name contains search string
    	for(int i = 0; i < foodList.size(); i++) {
    		String foodName = foodList.get(i).getName().toLowerCase();
    		if(foodName.contains(substring)) {
    			returnList.add(foodList.get(i));
    		}
    	}
    	
        
        List<Food> tempList = nameTree.rangeSearch(substring, "==");
        System.out.println(tempList);
        
        
        
        return returnList;

    } // filterByName()

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
     */
    @Override
    public List<Food> filterByNutrients(List<String> rules) {
        // TODO : Complete
    	// Map of nutrients and their corresponding index
        //private HashMap<String, BPTree<Double, Food>> indexes;
    	// "calories >= 50"
    	
    	//ArrayList<String> ruleList = (ArrayList<String>) rules;
    	//List<String> ruleList = rules;
        int numRules = rules.size();
        ArrayList<List<Food>> tempList = new ArrayList<List<Food>>();
        List<Food> tempCurrentList = new ArrayList<Food>();
        for (int i = 0;i<numRules;i++) {
            String [] splitRule = rules.get(i).split(" ");
            String nutrientType = splitRule[0];
            String comparator = splitRule[1];
            Double value = Double.parseDouble(splitRule[2]);
            BPTree<Double, Food> nutrientTree = indexes.get(nutrientType);
            tempCurrentList = nutrientTree.rangeSearch(value, comparator);
            tempList.add(tempCurrentList);
        } // for
        List<Food> finalList = new ArrayList<Food>();
        finalList.addAll(tempList.get(0));
        for (ListIterator<List<Food>> iter = tempList.listIterator(0); iter.hasNext(); ) {
            finalList.retainAll(iter.next());
        }

        return finalList;
        //return calorTree.rangeSearch(value, comparator);
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFood(skeleton.Food)
     */
    @Override
    public void addFood(Food Food) {
        // TODO : Complete?
    	foodList.add(Food);
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoods()
     */
    @Override
    public List<Food> getAllFoods() {
    	foodList.sort(new Comparator<Food>() { // anonymous class
    		@Override
    		public int compare(Food food1,Food food2) {
    			if(food1.getName().toLowerCase().equals(food2.getName().toLowerCase())) {
    				return 0;
    			}
    			else if(food1.getName().toLowerCase().compareTo(food2.getName().toLowerCase())  < 0 ){
    				return -1;
    			}
    			else {
    				return 1;
    			}
    		} // compare() overridden
    		
    	});
    	
    	return foodList;
    }


	@Override
	public void saveFoods(String filename) {
		File saveFile = null;
		PrintStream writer = null;
		
		try {
			saveFile = new File(filename); // create the file
			writer = new PrintStream(saveFile); // create the writer
			
			// loop through the current arraylist of food
			for(int i = 0; i < foodList.size(); i++) {
				Food current = foodList.get(i);
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
    	ArrayList<Food> tempList = (ArrayList<Food>) foodList;
    	
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
		System.out.println(foods.foodList);
		
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
