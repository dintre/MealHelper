
import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of a query to run through a BPTree and return a list of
 * Food objects that meet the conditions of the query
 * 
 * @author Libby Maese (libby@cs.wisc.edu)
 *
 * @param String input - expect a string that contains a category to search on, a
 * comparator, and the comparison point
 * @param inputTree inputTree - expect a tree with all the food data stored by the category given
 */

public class FoodQuery {
	private String comparator;
	private String category;
	private Double nCount;
	public List<String> results;
	public BPTree tree;
	public List<Food> returnList;
	
	/*
	 * runs searches of the BP trees storing food data
	 */
	public FoodQuery(String input, BPTree inputTree) {
		String delims = "[ ]";
		
		//splits query appropriately
		String[] query = input.split(delims);
		
		//throws an error if query is not correctly formatted
		if(query.length != 3) {
			throw new IllegalArgumentException("Query must be formatted like 'calories <= 50'");
		}
		
		category = query[0].toLowerCase();
		comparator = query[1];
		
		
		//will run an appropriate search based on the category and populate the returnList appropriately
		if(category.equals("id")) {
			tree = inputTree;
			returnList = tree.rangeSearch(query[2],comparator);
		}
		
		if(category.equals("calories")) {
			tree = inputTree;
			nCount = Double.parseDouble(query[2]);
			returnList = tree.rangeSearch(nCount,comparator);
		}
		
		else if(category.equals("protein")) {
			tree = inputTree;
			nCount = Double.parseDouble(query[2]);
			returnList = tree.rangeSearch(nCount,comparator);
		}
		
		else if(category.equals("fiber")) {
			tree = inputTree;
			nCount = Double.parseDouble(query[2]);
			returnList = tree.rangeSearch(nCount,comparator);
		}
		
		else if(category.equals("carbs")) {
			tree = inputTree;
			nCount = Double.parseDouble(query[2]);
			returnList = tree.rangeSearch(nCount,comparator);
		}
		
		else if(category.equals("fat")) {
			tree = inputTree;
			nCount = Double.parseDouble(query[2]);
			returnList = tree.rangeSearch(nCount,comparator);
		}
		
		else if(category.equals("name")) {
			tree = inputTree;
			returnList = tree.rangeSearch("",comparator);
			returnList = this.substringQuery(query[2]);
		}
		
		else {
			throw new IllegalArgumentException("Query must be on protein, fiber, carbs, fat, or name.");
		}
		
    }
	
	/**
	 * Returns the list of food items resulting from the query
	 *
	 * @return List of food items
	 */
	List returnQuery() {
		return returnList;
	}
	
	/**
	 * Compares the returnList of this query with another list of food objects and returns matches
	 *
	 * @param List of food items to be compared
	 * @return List of food items that match between the two lists
	 */
	List multipleQueries (List prior) {
		List results = returnList;
		results.retainAll(prior);
		return results;
	}
	
	List substringQuery (String substring) {
		List results = new ArrayList<Food>();
		String currentFood = new String();
		for(int i = 0; i < returnList.size(); i++) {
			currentFood = returnList.get(i).getName();
			if(currentFood.contains(substring)) {
				results.add(returnList.get(i));
			}
		}
		
		return results;
	}
 
	
	/*public static void main(String[] args) {
		BPTree<Double, Food> calorieTree = new BPTree<>(3);
		Food egg = new Food("123", "Egg", 100.0, 20.0, 25.0, 10.0, 5.0);
		Food sugar = new Food("124", "Sugar cube", 5.0, 20.0, 25.0, 10.0, 5.0);
		Food bacon = new Food("126", "Bacon", 80.0, 20.0, 25.0, 10.0, 5.0);
		Food coffee = new Food("125", "coffee", 10.0, 20.0, 25.0, 10.0, 5.0);
		calorieTree.insert(egg.getCalories(), egg);
		calorieTree.insert(sugar.getCalories(), sugar);
		calorieTree.insert(bacon.getCalories(), bacon);
		calorieTree.insert(coffee.getCalories(), coffee);
		FoodQuery calorieQuery = new FoodQuery("calories <= 1000", calorieTree);
		System.out.println(calorieQuery.returnQuery());
		System.out.println(calorieQuery.substringQuery("Egg"));

	}*/ // Main()

} // class FoodQuery