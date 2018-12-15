package application;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
 * The meal class represents a collection of food items
 * that compose a meal.
 * Meals can display their nutritional content and can have
 * food items added to and removed from them at any time
 * @author Trevor Dinsmoor (dinsmoor@wisc.edu)
 */
public class Meal {
	// instance variables
	private ArrayList<Food> ingredientList; // list of food items in this meal
	private String name; // name of the meal
	
	/*
	 * constructor for a meal
	 * @param name - the meal's name
	 */
	public Meal(String name) {
		this.name = name;
		ingredientList = new ArrayList<Food>();
	} // constructor Meal
	
	/*
	 * @return name - meal's name
	 */
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
	} // removeFood()
	
	/*
	 * Used to display food that is in a meal
	 * @return - list of food (ingredients) in the meal
	 */
	public List<Food> getIngredientList(){
		return ingredientList;
	} // getIngredientList()
	
	/*
	 * Calculates a meal's nutritional content by adding
	 * the values of each food/ingredient's fat, calories,
	 * protein, fiber, and carbs
	 * @return nutrition - array of the nutrtional content totals
	 */
	public double[] analyzeMealNutrition() {
		double calories = 0;
		double fat = 0;
		double carbs = 0;
		double fiber = 0;
		double protein = 0;
		// loop through the ingredient list and add up the values
		for(int i = 0; i < ingredientList.size(); i++) {
			Food currentFood = ingredientList.get(i);
			calories = calories + currentFood.getCalories();
			fat = fat + currentFood.getFat();
			carbs = carbs + currentFood.getCarbohydrates();
			fiber = fiber + currentFood.getFiber();
			protein = protein + currentFood.getProtein();	
		}
		// array to return
		double nutrition [] = new double[5];
		nutrition[0] = calories;
		nutrition[1] = fat;
		nutrition[2] = carbs;
		nutrition[3] = fiber;
		nutrition[4] = protein;
		return nutrition;
	} //analyzeMealNutrition()
	
	/*
	 * @param foods - list of foods to add to a meal
	 * Adds this list of foods as the meal's ingredient list
	 */
	public void addFoodList(List<Food> foods) {
		for(int i = 0; i < foods.size(); i++) {
			ingredientList.add(foods.get(i));
		}
	} // addFoodList()
	
	/*
	 * @param foods - list of foods to remove from the meal
	 * Removes the list of foods from the meal's ingredient list
	 */
	public void removeMultipleFoods(List<Food> foods) {
		for(int i = 0; i < foods.size(); i++) {
			ingredientList.remove(foods.get(i));
		}
	} // removeMultipleFoods()
	
} // class Meal
