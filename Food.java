package application;
/*
 * Food objects store a food item with its nutritional information
 * @author Trevor Dinsmoor (dinsmoor@wisc.edu)
 */
public class Food {
	private String id; // unique identifier of this food
	private String name; // name of the food
	private double calories; // number of calories in this food
	private double fat; // grams of fat in this food
	private double carbohydrates; // grabs of carbs in this food
	private double fiber; // grams of fiber in this food
	private double protein; // grams of protein in this food
	
	// getter methods
	
	/*
	 * @return id of a food item
	 */
	public String getId() {
		return id;
	} // getId()
	
	/*
	 * @return name of a food item
	 */
	public String getName() {
		return name;
	} // getName()
	
	/*
	 * @return calorie content of a food item
	 */
	public double getCalories() {
		return calories;
	} // getCalories()
	
	/*
	 * @return fat content of a food item
	 */
	public double getFat() {
		return fat;
	} // getFat()
	
	/*
	 * @return carbohydrates of a food item
	 */
	public double getCarbohydrates() {
		return carbohydrates;
	} // getCarbohydrates()
	
	/*
	 * @return fiber content of a food item
	 */
	public double getFiber() {
		return fiber;
	} // getFiber()
	
	/*
	 * @return protein content of a food item
	 */
	public double getProtein() {
		return protein;
	} // getProtein()
	
	/*
	 * constructor for a food item that contains all types of nutritional information
	 * @param id - the unique ID for the food item
	 * @param name - display name of the food item
	 * @param calories - the food's number of calories
	 * @param fat - the food's grams of fat
	 * @param carbohydrates - the food's number of carbs
	 * @param fiber - the food's fiber content
	 * @param protein - the food's protein content
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
	
} // class Food
