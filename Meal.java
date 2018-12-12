import java.util.ArrayList;
import java.util.List;

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
	public ArrayList<Food> getIngredientList(){
		ArrayList<Food> returnList = new ArrayList<Food>();
		
		for(int i = 0; i < ingredientList.size(); i++) {
			returnList.add(ingredientList.get(i));
		}
		
		return ingredientList;
	} // getIngredientList()
	
	public double[] analyzeMealNutrition() {

		double calories = 0;
		double fat = 0;
		double carbs = 0;
		double fiber = 0;
		double protein = 0;
		
		for(int i = 0; i < ingredientList.size(); i++) {
			Food currentFood = ingredientList.get(i);
			calories = calories + currentFood.getCalories();
			fat = fat + currentFood.getFat();
			carbs = carbs + currentFood.getCarbohydrates();
			fiber = fiber + currentFood.getFiber();
			protein = protein + currentFood.getProtein();	
		}
		double nutrition [] = new double[5];
		nutrition[0] = calories;
		nutrition[1] = fat;
		nutrition[2] = carbs;
		nutrition[3] = fiber;
		nutrition[4] = protein;
		
		return nutrition;
	} //analyzeMealNutrition()
	
	
	public void addFoodList(List<Food> foods) {
		for(int i = 0; i < foods.size(); i++) {
			ingredientList.add(foods.get(i));
		}
		
	} // addFoodList()
	
	public void removeMultipleFoods(List<Food> foods) {
		for(int i = 0; i < foods.size(); i++) {
			ingredientList.remove(foods.get(i));
		}
	} // removeMultipleFoods()
	
	
	
	
	public static void main(String[] args) {

		
		
	} // Main()

	
} // class Meal
