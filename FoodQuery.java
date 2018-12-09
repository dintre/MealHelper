import java.util.List;


/*
 * runs searches of the BP trees storing food data
 */
public class FoodQuery {
	private String comparator;
	private String nutrient;
	private Double nCount;
	public List<String> results;
	public BPTree tree;
	public List<Food> returnList;
	
	public FoodQuery(String input, BPTree inputTree) {
		String delims = "[ ]";
		String[] query = input.split(delims);
		if(query.length != 3) {
			throw new IllegalArgumentException("Query must be formatted like 'calories <= 50'");
		}
		
		nutrient = query[0].toLowerCase();
		comparator = query[1];
		
		if(nutrient.equals("id")) {
			tree = inputTree;;
			returnList = tree.rangeSearch(query[2],comparator);
		}
		
		if(nutrient.equals("calories")) {
			tree = inputTree;
			nCount = Double.parseDouble(query[2]);
			returnList = tree.rangeSearch(nCount,comparator);
		}
		
		else if(nutrient.equals("protein")) {
			tree = inputTree;
			nCount = Double.parseDouble(query[2]);
			returnList = tree.rangeSearch(nCount,comparator);
		}
		
		else if(nutrient.equals("fiber")) {
			tree = inputTree;
			nCount = Double.parseDouble(query[2]);
			returnList = tree.rangeSearch(nCount,comparator);
		}
		
		else if(nutrient.equals("carbs")) {
			tree = inputTree;
			nCount = Double.parseDouble(query[2]);
			returnList = tree.rangeSearch(nCount,comparator);
		}
		
		else if(nutrient.equals("fat")) {
			tree = inputTree;
			nCount = Double.parseDouble(query[2]);
			returnList = tree.rangeSearch(nCount,comparator);
		}
		
		else if(nutrient.equals("name")) {
			tree = inputTree;
			returnList = tree.rangeSearch(query[2],comparator);
		}
		
		else {
			throw new IllegalArgumentException("Query must be on protein, fiber, carbs, fat, or name.");
		}
		
    }
	
	List returnQuery() {
		return returnList;
	}
	
	List multipleQueries (List prior) {
		List results = returnList;
		results.retainAll(prior);
		return results;
	}

	
	public static void main(String[] args) {
		BPTree<Double, Food> calorieTree = new BPTree<>(3);
		Food egg = new Food("123", "Egg", 100.0, 20.0, 25.0, 10.0, 5.0);
		calorieTree.insert(egg.getCalories(), egg);
		FoodQuery calorieQuery = new FoodQuery("calories <= 300", calorieTree);
		System.out.println(calorieQuery.returnQuery());

	} // Main()

} // class FoodQuery
