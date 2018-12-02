import java.util.ArrayList;

/*
 * 
 */
public class Meal {
	private ArrayList<Food> foodList; // list of food items in this meal
	private String name; // name of the meal
	private int id;
	
	/*
	 * constructor for a meal
	 */
	public Meal(String name) {
		this.name = name;
		foodList = new ArrayList<Food>();
	} // constructor Meal
	
	/*
	 * adds a food item to this meal
	 * @param food - the food to be added
	 */
	public void addFood(Food food) {
		foodList.add(food);
	} // addFood()
	
	/*
	 * removes a food item from the meal
	 * @param food - the food to be removed
	 */
	public void removeFood(Food food) {
		foodList.remove(food);
	}
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {

		
		
	} // Main()

	
} // class Meal
