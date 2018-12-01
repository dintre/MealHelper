/*
 * 
 * 
 */
public class Food {
	private String id; // unique identifier of this food
	private String name; // name of the food
	private double calories; // number of calories in this food
	private double fat; // grams of fat in this food
	private double carbohydrates; // grabs of carbs in this food
	private double fiber; // grams of fiber in this food
	private double protein; // grams of protein in this food
	
	/*
	 * constructor for a food item
	 */
	public Food(String id, String name) {
		this.id = id;
		this.name = name;
	} // constructor Food
	
	/*
	 * constructor for a food item that
	 * contains all types of nutritional
	 * information
	 */
	public Food(String id, String name, double calories, double fat, double carbohydrates, double fiber, double protein) {
		this.id = id;
		this.name = name;
		this.calories = calories;
		this.fat = fat;
		this.carbohydrates = carbohydrates;
		this.fiber = fiber;
		this.protein = protein;
	} // constructor food
	
	/*
	 * deletes a food item
	 * @param id - the id of the food
	 */
	public void deleteFood(int id) {
		
		
	} // deleteFood()
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {

		
		
	} // Main()

	
} // class Food
