package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class represents the back end for managing all
 * the operations associated with Food items stored in trees.
 * It connects the UI to the BP trees that actually store the food items
 *
 * @author sapan (sapan@cs.wisc.edu)
 * @author Trevor Dinsmoor (dinsmoor@wisc.edu)
 * @author Jie Gu (jgu68@wisc.edu)
 */
public class FoodData implements FoodDataADT<Food> {
    // instance variables
    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, Food>> indexes;
    // Food name tree
    private BPTree<String,Food> nameTree;
    // Calories tree
    private BPTree<Double,Food> caloriesTree;
    // Carbohydrate tree
    private BPTree<Double,Food> carbohydrateTree;
    // Fat tree
    private BPTree<Double,Food> fatTree;
    // Fiber tree
    private BPTree<Double,Food> fiberTree;
    // Protein tree
    private BPTree<Double,Food> proteinTree;
    
    /**
     * Public constructor
     */
    public FoodData() {
        // instantiate indexes hashmap variable
        indexes = new HashMap<String, BPTree<Double, Food>>();
        // BPTrees
        nameTree = new BPTree<String,Food>(3);
        // calorie tree
        caloriesTree = new BPTree<Double,Food>(3);
        // carbohydrate tree
        carbohydrateTree = new BPTree<Double,Food>(3);
        // fat tree
        fatTree = new BPTree<Double,Food>(3);
        // fiber tree
        fiberTree = new BPTree<Double,Food>(3);
        // protein tree
        proteinTree = new BPTree<Double,Food>(3);
        // put trees into the HashMap
        indexes.put("calories", caloriesTree);
        indexes.put("carbohydrates", carbohydrateTree);
        indexes.put("fat", fatTree);
        indexes.put("fiber", fiberTree);
        indexes.put("protein", proteinTree);
    } // constructor    
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoods(java.lang.String)
     */
    @Override
    public void loadFoods(String filePath) {
        // clear any existing food trees
        indexes.remove("calories", caloriesTree);
        indexes.remove("carbohydrates", carbohydrateTree);
        indexes.remove("fat", fatTree);
        indexes.remove("fiber", fiberTree);
        indexes.remove("protein", proteinTree);
        // instantiate new trees.
        nameTree = new BPTree<String,Food>(3);
        caloriesTree = new BPTree<Double,Food>(3);
        carbohydrateTree = new BPTree<Double,Food>(3);
        fatTree = new BPTree<Double,Food>(3);
        fiberTree = new BPTree<Double,Food>(3);
        proteinTree = new BPTree<Double,Food>(3);
        // add new trees to the HashMap
        indexes.put("calories", caloriesTree);
        indexes.put("carbohydrates", carbohydrateTree);
        indexes.put("fat", fatTree);
        indexes.put("fiber", fiberTree);
        indexes.put("protein", proteinTree);
        
        // create variable for the file to be loaded
        File foodFile = null;
        // create scanner to look through file
        Scanner input = null;
        // create line of file data
        String fileLine = null;
        // create file name
        String fileName = filePath;
        try {
            foodFile = new File(fileName);
            input = new Scanner(foodFile);
            // while reading each line from the file
            while(input.hasNextLine()) {
                fileLine = input.nextLine();
                
                // check if line is blank, then continue
                if(fileLine.length() == 0) {
                    continue; // continue back to while loop start
                }
                String[] commaSplitter = fileLine.split(",");
                if(commaSplitter.length == 0) {
                    continue; // continue back to while loop start
                }
                String id = commaSplitter[0];
                String name = commaSplitter[1];
                double calories = Double.parseDouble(commaSplitter[3]);
                double fat = Double.parseDouble(commaSplitter[5]);
                double carbs = Double.parseDouble(commaSplitter[7]);
                double fiber = Double.parseDouble(commaSplitter[9]);
                double protein = Double.parseDouble(commaSplitter[11]);
                Food newFood = new Food(id, name, calories, fat, carbs, fiber, protein);
                // adding to trees
                nameTree.insert(name,newFood);
                caloriesTree.insert(calories, newFood);
                carbohydrateTree.insert(carbs, newFood);
                fatTree.insert(fat, newFood);;
                fiberTree.insert(fiber, newFood);
                proteinTree.insert(protein, newFood);
            } // while reading lines
        } // try
        catch (FileNotFoundException e) { // catch when the file isn't found
            e.printStackTrace();
        } // catch fileNotFound    
        catch (Exception e) { // catch any other exceptions
            e.printStackTrace();
        }
        finally {
            // close the scanner
            input.close();
        } // finally
    } // loadFoods()

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByName(java.lang.String)
     */
    @Override
    public List<Food> filterByName(String substring) {
        List<Food> finalList = new ArrayList<Food>();
        String inputBreakdown [] = substring.split(" ");
        if(inputBreakdown.length == 2) {
            return finalList;
        }
        else {
            substring = inputBreakdown [2];
        }
        
        String searchString = "calories >= 0.0"; // string to pass to return all results, initially

        // instantiates a FoodQuery object to perform searching
        FoodQuery currentQuery = new FoodQuery(searchString, caloriesTree);
        finalList = currentQuery.substringQuery(substring); // actually filters by name
        // sort alphabetically
        finalList.sort(new Comparator<Food>() { // anonymous class
            @Override
            public int compare(Food food1,Food food2) {
                if(food1.getName().toLowerCase().equals(food2.getName().toLowerCase())) {
                    return 0;
                }
                else if(food1.getName().toLowerCase().compareTo(food2.getName().toLowerCase())  < 0 ){
                    return -1;
                }
                else {
                    return 1;
                }
            } // compare() overridden    
        }); // end of anonymous inner class
        return finalList;
    } // filterByName()

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
     */
    @Override
    public List<Food> filterByNutrients(List<String> rules) {
        int numRules = rules.size();
        ArrayList<List<Food>> tempList = new ArrayList<List<Food>>();
        List<Food> tempCurrentList = new ArrayList<Food>();
        boolean goodValidComparator;
        boolean goodQuery;
        // loop through list of rules in effect
        for (int i = 0; i < numRules; i++) {
            goodValidComparator = false;
            goodQuery = false;
            String [] splitRule = rules.get(i).split(" ");
            String validQuery[] = new String[]{"calories","carbohydrates","fat","fiber","protein","name"};
            String validComparator[] = new String[] {"<=","==",">="};
            for (int j = 0; j<6; j++) {
                String nutrientType = splitRule[0].toLowerCase();
                String comparator = splitRule[1];
                for (int k = 0; k<3;k++) {
                    if (comparator.equals(validComparator[k])) {
                        goodValidComparator = true;
                        break;
                    }
                }
                if (nutrientType.equals(validQuery[j])&&goodValidComparator) {
                    goodQuery = true;
                    break;
                }
            }
            if(splitRule.length != 1 && goodQuery) {
                String nutrientType = splitRule[0].toLowerCase();
                String comparator = splitRule[1];
                // if there's already a name filter
                if(nutrientType.equals("name")) {
                    String value = splitRule[2];
                    String searchString = "calories >= 0.0";
                    FoodQuery nameQuery = new FoodQuery(searchString, caloriesTree);
                    tempCurrentList = nameQuery.substringQuery(value);
                    tempList.add(tempCurrentList);
                    continue;
                }
                String userInput = rules.get(i);
                BPTree<Double, Food> nutrientTree = indexes.get(nutrientType);
                // instantiates a FoodQuery object to perform searching
                FoodQuery currentQuery = new FoodQuery(userInput, nutrientTree);
                tempCurrentList = currentQuery.returnQuery(); // gets the results from the query object
                tempList.add(tempCurrentList);
            }
            else {
                Food incorrect = new Food("zzIncorrect","zzIncorrect",0,0,0,0,0);
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Incorrect Query Format");
                alert.setHeaderText("Query format needs to be {nutrient or name} {<=,==,>=} {integer or name}!");
                alert.show();
                List<Food> incorrectList = new ArrayList<Food>();
                incorrectList.add(incorrect);
                return incorrectList;
            }
        } // for
        List<Food> finalList = new ArrayList<Food>();
        if(tempList.size() != 0) {
            finalList.addAll(tempList.get(0));
        }
        for (ListIterator<List<Food>> iter = tempList.listIterator(0); iter.hasNext(); ) {
            finalList.retainAll(iter.next());
        }
        // sort list alphabetically before returning
        finalList.sort(new Comparator<Food>() { // anonymous class
            @Override
            public int compare(Food food1,Food food2) {
                if(food1.getName().toLowerCase().equals(food2.getName().toLowerCase())) {
                    return 0;
                }
                else if(food1.getName().toLowerCase().compareTo(food2.getName().toLowerCase())  < 0 ){
                    return -1;
                }
                else {
                    return 1;
                }
            } // compare() overridden    
        }); // end of anonymous inner class
        return finalList;
    } // filterByNutrients()

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFood(skeleton.Food)
     */
    @Override
    public void addFood(Food food) {
        // adding to trees
        String name = food.getName();
        Double calories = food.getCalories();
        Double carbs = food.getCarbohydrates();
        Double fat = food.getFat();
        Double fiber = food.getFiber();
        Double protein = food.getProtein();
        // insert at trees for indexing
        nameTree.insert(name,food);
        caloriesTree.insert(calories, food);
        carbohydrateTree.insert(carbs, food);
        fatTree.insert(fat, food);;
        fiberTree.insert(fiber, food);
        proteinTree.insert(protein, food);
    } // addFood()

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoods()
     */
    @Override
    public List<Food> getAllFoods() {
        // initial display of all food items without filters by searching on
        // calories greater than or equal to 0.
        List<Food> foods = caloriesTree.rangeSearch(0.0, ">=");
        // sorts foods alphabetically
        foods.sort(new Comparator<Food>() { // anonymous class
            @Override
            public int compare(Food food1,Food food2) {
                if(food1.getName().toLowerCase().equals(food2.getName().toLowerCase())) {
                    return 0;
                }
                else if(food1.getName().toLowerCase().compareTo(food2.getName().toLowerCase())  < 0 ){
                    return -1;
                }
                else {
                    return 1;
                }
            } // compare() overridden    
        }); // end of anonymous inner class
        return foods;
    } // getAllFoods()

    /*
     * (non-Javadoc)
     * @see application.FoodDataADT#saveFoods(java.lang.String)
     */
    @Override
    public void saveFoods(String filename) {
        File saveFile = null;
        PrintStream writer = null;
        try {
            saveFile = new File(filename); // create the file
            writer = new PrintStream(saveFile); // create the writer
            List<Food> foods = caloriesTree.rangeSearch(0.0, ">=");
            // loop through the current arraylist of food
            for(int i = 0; i < foods.size(); i++) {
                Food current = foods.get(i);
                // write the line
                writer.println(current.getId() + "," + current.getName()
                + ",calories," + current.getCalories() + ",fat," +
                current.getFat() + ",carbohydrates," + current.getCarbohydrates()
                + ",fiber," + current.getFiber() + ",protein,"
                + current.getProtein());
            } // for loop
            
        } // try        
        catch (IOException e){ // could not save for some reason
            System.out.println("Could not save the food list. ");
        }        
        catch (Exception e) { // catch any other exceptions
            e.printStackTrace();
        }        
        finally { // close the writer before finishing method
            if(writer != null) {
                writer.close();
            }
        } // finally        
    } // saveFoods()
    
} // class FoodData