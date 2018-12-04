import java.util.ArrayList;

/*
 * 
 */
public class Meal {
	private ArrayList<Food> ingredientList; // list of food items in this meal
	private String name; // name of the meal
	private int id; // TODO - do i need this?
	
	/*
	 * constructor for a meal
	 */
	public Meal(String name) {
		this.name = name;
		ingredientList = new ArrayList<Food>();
	} // constructor Meal
	
	// getters and setters
	
	public String getName() {
		return name;
	} // getName()
	
	/*
	 * adds a food item to this meal
	 * @param food - the food to be added
	 */
	public void addFood(Food food) {
		ingredientList.add(food);
	} // addFood()
	
	/*
	 * removes a food item from the meal
	 * @param food - the food to be removed
	 */
	public void removeFood(Food food) {
		ingredientList.remove(food);
	}
	
	// TODO - should this work this way?
	// used to display food names that are in a meal
	public ArrayList<String> getIngredientList(){
		ArrayList<String> returnList = new ArrayList<String>();
		
		for(int i = 0; i < ingredientList.size(); i++) {
			returnList.add(ingredientList.get(i).getName());
		}
		
		return returnList;
	} // getIngredientList()
	
	
	
	
	
	
	
	
	public static void main(String[] args) {

		
		
	} // Main()

	
} // class Meal
