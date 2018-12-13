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
	// instance variables
    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, Food>> indexes;
    // Food name tree
	private BPTree<String,Food> nameTree;
	// Calories tree
	private BPTree<Double,Food> caloriesTree;
    // Carbohydrate tree
    private BPTree<Double,Food> carbohydrateTree;
    // Fat tree
    private BPTree<Double,Food> fatTree;
    // Fiber tree
    private BPTree<Double,Food> fiberTree;
    // Protein tree
    private BPTree<Double,Food> proteinTree;
    
    /**
     * Public constructor
     */
    public FoodData() {
    	// instantiate indexes hashmap variable
    	indexes = new HashMap<String, BPTree<Double, Food>>();
    	// BPTrees
    	nameTree = new BPTree<String,Food>(3);
    	// calory tree
    	caloriesTree = new BPTree<Double,Food>(3);
    	// carbohydrate tree
    	carbohydrateTree = new BPTree<Double,Food>(3);
    	// fat tree
    	fatTree = new BPTree<Double,Food>(3);
    	// fiber tree
    	fiberTree = new BPTree<Double,Food>(3);
    	// protein tree
    	proteinTree = new BPTree<Double,Food>(3);
    	// put trees into the HashMap
		indexes.put("calories", caloriesTree);
        indexes.put("carbohydrates", carbohydrateTree);
        indexes.put("fat", fatTree);
        indexes.put("fiber", fiberTree);
        indexes.put("protein", proteinTree);
    } // constructor    
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoods(java.lang.String)
     */
    @Override
    public void loadFoods(String filePath) {
    	// clear any existing food trees
		indexes.remove("calories", caloriesTree);
        indexes.remove("carbohydrates", carbohydrateTree);
        indexes.remove("fat", fatTree);
        indexes.remove("fiber", fiberTree);
        indexes.remove("protein", proteinTree);
    	// instantiate new trees.
    	nameTree = new BPTree<String,Food>(3);
    	caloriesTree = new BPTree<Double,Food>(3);
    	carbohydrateTree = new BPTree<Double,Food>(3);
    	fatTree = new BPTree<Double,Food>(3);
    	fiberTree = new BPTree<Double,Food>(3);
    	proteinTree = new BPTree<Double,Food>(3);
    	// add new trees to the HashMap
		indexes.put("calories", caloriesTree);
        indexes.put("carbohydrates", carbohydrateTree);
        indexes.put("fat", fatTree);
        indexes.put("fiber", fiberTree);
        indexes.put("protein", proteinTree);
    	
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
				// adding to trees
				nameTree.insert(name,newFood);
				caloriesTree.insert(calories, newFood);
                carbohydrateTree.insert(carbs, newFood);
                fatTree.insert(fat, newFood);;
                fiberTree.insert(fiber, newFood);
                proteinTree.insert(protein, newFood);
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
    	String searchString = "name == " + substring;
    	FoodQuery currentQuery = new FoodQuery(searchString, nameTree);

    	List<Food> finalList = currentQuery.substringQuery(substring);
        finalList.sort(new Comparator<Food>() { // anonymous class
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
    	}); // end of anonymous inner class
        return finalList;
    } // filterByName()

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
     */
    @Override
    public List<Food> filterByNutrients(List<String> rules) {
    	
        int numRules = rules.size();
        ArrayList<List<Food>> tempList = new ArrayList<List<Food>>();
        List<Food> tempCurrentList = new ArrayList<Food>();

        for (int i = 0; i < numRules; i++) {
            String [] splitRule = rules.get(i).split(" ");
            if(splitRule.length != 1) {
            	String nutrientType = splitRule[0].toLowerCase();
            	String comparator = splitRule[1];
            	Double value = Double.parseDouble(splitRule[2]);
            	String userInput = rules.get(i);
            	BPTree<Double, Food> nutrientTree = indexes.get(nutrientType);
            	FoodQuery currentQuery = new FoodQuery(userInput, nutrientTree);
            	tempCurrentList = currentQuery.returnQuery();
            	tempList.add(tempCurrentList);
            }
        } // for
        List<Food> finalList = new ArrayList<Food>();
        if(tempList.size() != 0) {
        	finalList.addAll(tempList.get(0));
        }
        for (ListIterator<List<Food>> iter = tempList.listIterator(0); iter.hasNext(); ) {
            finalList.retainAll(iter.next());
        }
        // sort list to return
        finalList.sort(new Comparator<Food>() { // anonymous class
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
    	}); // end of anonymous inner class
        return finalList;
    } // filterByNutrients()

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFood(skeleton.Food)
     */
    @Override
    public void addFood(Food food) {
    	//foodList.add(food);
		// adding to trees
    	String name = food.getName();
    	Double calories = food.getCalories();
    	Double carbs = food.getCarbohydrates();
    	Double fat = food.getFat();
    	Double fiber = food.getFiber();
    	Double protein = food.getProtein();
    	// insert at trees for indexing
		nameTree.insert(name,food);
		caloriesTree.insert(calories, food);
        carbohydrateTree.insert(carbs, food);
        fatTree.insert(fat, food);;
        fiberTree.insert(fiber, food);
        proteinTree.insert(protein, food);
    } // addFood()

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoods()
     */
    @Override
    public List<Food> getAllFoods() {
    	List<Food> foods = caloriesTree.rangeSearch(0.0, ">=");
    	foods.sort(new Comparator<Food>() { // anonymous class
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
    	}); // end of anonymous inner class
    	
    	return foods;
    } // getAllFoods()

	@Override
	public void saveFoods(String filename) {
		File saveFile = null;
		PrintStream writer = null;
		try {
			saveFile = new File(filename); // create the file
			writer = new PrintStream(saveFile); // create the writer
			List<Food> foods = caloriesTree.rangeSearch(0.0, ">=");
			// loop through the current arraylist of food
			for(int i = 0; i < foods.size(); i++) {
				Food current = foods.get(i);
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
		catch (Exception e) { // catch any other exceptions
			e.printStackTrace();
		}		
		finally { // close the writer before finishing method
			if(writer != null) {
				writer.close();
			}
		} // finally		
	} // saveFoods()
	
	/*
	 * main method for testing
	 */
	public static void main(String[] args) {

		
	} // Main()
	
} // class FoodData
