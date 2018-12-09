import java.util.List;

/*
 * runs searches of the BP trees storing food data
 */
public class FoodQuery {
	private String comparator;
	private String nutrient;
	private int nCount;
	public List<String> results;
	private BPTree tree;
	
	public FoodQuery(String input) {
		String delims = "[ ]";
		String[] query = input.split(delims);
		if(query.length != 3) {
			throw new IllegalArgumentException("Query must be formatted like 'calories <= 50'");
		}
		
		nutrient = query[0];
		comparator = query[1];
		nCount = Integer.parseInt(query[2]);		
		   
    }
    
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		

	} // Main()

} // class FoodQuery
