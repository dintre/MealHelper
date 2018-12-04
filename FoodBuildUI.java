//package cs400;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import javafx.scene.layout.HBox; 
import javafx.scene.layout.VBox; 

/*
 * UI javaFX class of the Meal Helper program
 */
public class FoodBuildUI extends Application {
	
	static ObservableList<String> names = FXCollections.observableArrayList();
	
	
	@Override
	public void start(Stage primaryStage) {
		
		// set title of the window that opens
		primaryStage.setTitle("Food Helper");
		primaryStage.show();	
		
		// instantiate other classes that are needed at startup -------------------------------
		// FoodData - the list of food
	    FoodData foodList = new FoodData();
		
		
		
		// establish initial borderPane layout
		BorderPane border = new BorderPane();
		
		// create sections
		
		// top section  --------------------------------------------------------------------------------------------------------------
		VBox headingbox = new VBox();
	    headingbox.setPadding(new Insets(20, 100, 20, 100));
	    headingbox.setSpacing(10);
	    headingbox.setStyle("-fx-background-color: #4527A0;");
	    // set label for title of the program
	    Label programTitle = new Label();
	    programTitle.setText("Meal Helper"); 
	    programTitle.setTextFill(Color.web("#FAFAFA"));
	    programTitle.setFont(Font.font("Cambria", 32));
	    
	    ObservableList food = FXCollections.observableArrayList();
	    
	    	    
	    
	    // left section - food list  --------------------------------------------------------------------------------------------------------------
	    VBox foodBox = new VBox();
	    foodBox.setPadding(new Insets (20, 200, 20, 200));
	    foodBox.setSpacing(10);
	    foodBox.setStyle("-fx-background-color: #B39DDB;");
	    
	    // title for food section
	    Label foodLabel = new Label();
	    foodLabel.setText("Food");
	    foodLabel.setPadding(new Insets(10));
	    foodLabel.setFont(Font.font("Cambria", 24));
	    
	    
	    
	   // food.addAll("Blueberries", "Flour", "Sugar", "Chicken Breast", "Maple Syrup", "Bacon", "Spaghetti Noodles", "Tomato Sauce", "Peanut Butter", "Raspberry Jelly", "Whole Wheat Bread",
	    		//"Lettuce", "Button Mushrooms", "Egg");
	    		
	    final ListView foodView = new ListView(food);
	    foodView.setPrefSize(300, 300);
	    foodView.setEditable(true);
	    
	    
	    // search field by name
	    TextField foodNameSearch = new TextField();
	    foodNameSearch.setPromptText("Search by name...");
	    
	    // search field by nutrient
	    TextField nutrientSearch = new TextField();
	    nutrientSearch.setPromptText("Search by nutrient...");
	    
	    // run query button
	    Button runSearchQuery = new Button("Search Food");
	    runSearchQuery.setPrefSize(100, 20);
	    
	    // create food button
	    Button createFood = new Button("Create Food");
	    createFood.setPrefSize(100, 20);
	    
	    // add a new food item button
	    Button addFood = new Button("Add to Meal");
	    addFood.setPrefSize(100, 20);
	    
	    // add all foodBox (left pane) components  TODO - readd this?
	    foodBox.getChildren().addAll(foodLabel, foodView, foodNameSearch, nutrientSearch, createFood, addFood, runSearchQuery);
	    
	    
	    
	    // right section - meal list  --------------------------------------------------------------------------------------------------------------
	    VBox mealBox = new VBox();
	    mealBox.setPadding(new Insets (20, 200, 20, 200));
	    mealBox.setSpacing(10);
	    mealBox.setStyle("-fx-background-color: #B39DDB;");
	    Label mealLabel = new Label();
	    mealLabel.setText("Meals");
	    mealLabel.setPadding(new Insets(10));
	    mealLabel.setFont(Font.font("Cambria", 24));
	    ObservableList meals = FXCollections.observableArrayList();
	    meals.addAll("Blueberry Pancakes", "Chicken and Waffles", "Spaghetti and Meatballs", "PBJ", "Shahi Paneer", "Burger and Fries", "Biscuits and Gravy", "Pepperoni Pizza",
	    		"Rice and Beans", "Steak with Mushrooms", "Wedge Salad", "Barbecue Ribs", "Shrimp and Grits");
	    final ListView mealView = new ListView(meals);
	    mealView.setPrefSize(300, 300);
	    mealView.setEditable(true);
	    
	    // meal name text field
	    TextField mealNameField = new TextField();
	    mealNameField.setPromptText("meal name...");
	    mealNameField.setPrefSize(250, 20);
	    
	    // add meal button
	    Button addMeal = new Button();
	    addMeal.setText("Create Meal");
	    addMeal.setPrefSize(100, 20);
	    addMeal.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		System.out.println("Add Meal button pressed. "); // TODO - remove
	    		String mealName = mealNameField.getText();
	    	    Meal newMeal = new Meal(mealName);
	    	    System.out.println(newMeal);
	    	    
	    	    // add new meal to the meal list
	    	    meals.add(newMeal.getName());
	    		};
	    	} );

	    
	    
	    // analyze meal button
	    Button analyzeMeal = new Button();
	    analyzeMeal.setText("Analyze Meal");
	    analyzeMeal.setPrefSize(100, 20);
	    mealBox.getChildren().addAll(mealLabel, mealView, mealNameField, addMeal, analyzeMeal);
	    
	    
	    // bottom section - exit button --------------------------------------------------------------------------------------------------------------
	    HBox bottomMenu = new HBox();
	    bottomMenu.setPadding(new Insets (10) );
	    bottomMenu.setSpacing(10);
	    bottomMenu.setStyle("-fx-background-color: #4527A0;");
	    Button exitButton = new Button("Exit");
	    exitButton.setPrefSize(100, 20);
	    bottomMenu.getChildren().addAll(exitButton);
		
	    
	    // import and export buttons
	    // import foods from a file
	    Button importButton = new Button("Import");
	    importButton.setPrefSize(100, 20);
	    importButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		System.out.println("Load button pressed. "); // TODO - remove
	    		foodList.loadFoods("foodItems.csv");
	    	    food.addAll(foodList.getFoodNames());

	    	}
	    } ); // importButton.setOnAction()
	    
	    // save foods to a file
	    Button exportButton = new Button("Export");
	    exportButton.setPrefSize(100, 20);
	    exportButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		System.out.println("Save button pressed. "); // TODO - remove
	    		foodList.saveFoods("savedFoods.csv");
	    		};
	    	} );
	    
	    
	    // add buttons and title to the heading box
	    headingbox.getChildren().addAll(programTitle, importButton, exportButton);	

	    
	    
	    
	    
	    
	    
	    
	    
	    
	    // middle section for results to be displayed  --------------------------------------------------------------------------------------------------------------

	    
	    
	    VBox middle = new VBox();
	    middle.setPadding(new Insets(20));
	    middle.setSpacing(10);
	    middle.setStyle("-fx-background-color: #B39DDB;");
	    Label tempResultText = new Label();
	    tempResultText.setText("Meal: Blueberry Pancakes");
	    tempResultText.setFont(Font.font("Cambria", 24));
	    tempResultText.setPadding(new Insets(10));
	    
	    ObservableList tempMealDisplay = FXCollections.observableArrayList();
	    tempMealDisplay.addAll("Blueberries", "Flour", "Sugar", "Egg");
	    final ListView tempMeal = new ListView(tempMealDisplay);
	    tempMeal.setPrefSize(300, 300);
	    tempMeal.setEditable(true);
	    
	    Label tempAnalysis = new Label();
	    tempAnalysis.setLineSpacing(20);
	    tempAnalysis.setText("<meal analysis will be here>");
	    
	    
	    middle.getChildren().addAll(tempResultText, tempMeal, tempAnalysis);
	    
	    
	    // add sections to borderPane
		border.setTop(headingbox);
		border.setLeft(foodBox);
		border.setRight(mealBox);
		border.setBottom(bottomMenu);
		border.setCenter(middle);
		//border.setCenter(listView);
	    
		
		
		// create scene with the borderPane
		Scene scene = new Scene(border, Color.DARKGRAY);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		
	} // start()
	

	public static void main(String[] args) {
		
		launch(args);
		
	} // Main()
	
	
	
} // FoodBuildUI class
