package application;
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
			returnList = tree.rangeSearch("",">=");
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
	List<Food> returnQuery() {
		return returnList;
	}
	
	/*
	 * Returns the list of filtered results, filtered by name
	 * @param substring Substring to search names to see if they contain
	 * @return results List filtered by the name containing the substring 
	 */
	List<Food> substringQuery (String substring) {
		List results = new ArrayList<Food>();
		String currentFood = new String();
		substring = substring.toLowerCase();
		for(int i = 0; i < returnList.size(); i++) {
			currentFood = returnList.get(i).getName().toLowerCase();
			if(currentFood.contains(substring)) {
				results.add(returnList.get(i));
			}
		}
		
		return results;
	}

} // class FoodQuery
