//package cs400;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

//import javax.swing.event.ChangeListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
	public ArrayList<Meal> mealsList = new ArrayList<Meal>(); // list of meals that is only
	// populated while program is running

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
		// create borderPane sections
		// top section 
		HBox headingbox = new HBox();
	    headingbox.setPadding(new Insets(10, 120, 10, 100));
	    headingbox.setSpacing(10);
	    headingbox.setStyle("-fx-background-color: #4527A0;");
	    // left section 
	    VBox foodBox = new VBox();
	    foodBox.setPadding(new Insets (20, 80, 20, 80));
	    foodBox.setSpacing(10);
	    foodBox.setStyle("-fx-background-color: #B39DDB;");
	    // right section
	    VBox mealBox = new VBox();
	    mealBox.setPadding(new Insets (20, 80, 20, 80));
	    mealBox.setSpacing(10);
	    mealBox.setStyle("-fx-background-color: #B39DDB;");
		
	    // top section content  --------------------------------------------------------------------------------------------------------------
	    // set label for title of the program
	    Label programTitle = new Label();
	    programTitle.setText("Meal Helper"); 
	    programTitle.setTextFill(Color.web("#FAFAFA"));
	    programTitle.setFont(Font.font("Cambria", 32));
	    programTitle.setPadding(new Insets(15));
	    
	    // left section - food list  --------------------------------------------------------------------------------------------------------------
	    ObservableList<Food> food = FXCollections.observableArrayList();
	    // title label
	    Label foodLabel = new Label();
	    foodLabel.setText("Food");
	    foodLabel.setPadding(new Insets(10));
	    foodLabel.setFont(Font.font("Cambria", 24));
	    // food view
	    ListView<Food> foodView = new ListView<Food>(food);
	    foodView.setPrefSize(400, 300);
	    // use to store object but display food's name
	    foodView.setCellFactory(param -> new ListCell<Food>() {
	    	@Override
	    	protected void updateItem(Food item, boolean empty) {
	    		super.updateItem(item, empty);
	    		if (empty || item == null || item.getName() == null) {
	    			setText(null);
	    		}
	    		else {
	    			setText(item.getName());
	    		}
	    	}		
		} ); // food view display cellFactory
	    // search field by name
	    TextField foodNameSearch = new TextField();
	    foodNameSearch.setPromptText("Search by name...");
	    // search field by nutrient
	    TextField nutrientSearch = new TextField();
	    nutrientSearch.setPromptText("Search by nutrient...");
	    // run query button
	    Button runSearchQuery = new Button("Search Food");
	    runSearchQuery.setPrefSize(120, 20);
	    // create food button
	    Button createFood = new Button("Create Food");
	    createFood.setPrefSize(120, 20);// create food button
	    // create food button action
        createFood.setOnAction(new EventHandler<ActionEvent>() {  
            @Override
            public void handle(ActionEvent event) {
                Label nameLabel = new Label("Name of Food:");
                Label IDLabel = new Label("ID of Food:");
                Label calorieLabel = new Label("Calories:");
                Label carbsLabel = new Label("Carbs:");
                Label fatLabel = new Label("Fat:");
                Label fiberLabel = new Label("Fiber:");
                Label proteinLabel = new Label("Protein:");
 
                GridPane addFoodLayout = new GridPane();
                addFoodLayout.setPadding(new Insets(10, 10, 10, 10));
                addFoodLayout.setVgap(5);
                addFoodLayout.setHgap(5);
                TextField name = new TextField();
                TextField ID = new TextField();
                TextField calories = new TextField();
                calories.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                            calories.setText(newValue.replaceAll("[^\\d]", ""));                        }
                    }
                });
                calories.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
                TextField carbs = new TextField();
                TextField fat = new TextField();
                TextField fiber = new TextField();
                TextField protein = new TextField();
                
                Button generateFood = new Button("Create Food!");
                generateFood.setPrefSize(230, 20);
                
                addFoodLayout.add(nameLabel,0,0,1,1);
                addFoodLayout.add(IDLabel,0,1,1,1);
                addFoodLayout.add(calorieLabel,0,2,1,1);
                addFoodLayout.add(carbsLabel,0,3,1,1);
                addFoodLayout.add(fatLabel,0,4,1,1);
                addFoodLayout.add(fiberLabel,0,5,1,1);
                addFoodLayout.add(proteinLabel,0,6,1,1);
                addFoodLayout.add(name,1,0,1,1);
                addFoodLayout.add(ID,1,1,1,1);
                addFoodLayout.add(calories,1,2,1,1);
                addFoodLayout.add(carbs,1,3,1,1);
                addFoodLayout.add(fat,1,4,1,1);
                addFoodLayout.add(fiber,1,5,1,1);
                addFoodLayout.add(protein,1,6,1,1);
                addFoodLayout.add(generateFood, 0, 7,2,1);
                Scene CrFoodScene = new Scene(addFoodLayout, 285, 275);
 
                // New window (Stage)
                Stage CrFood = new Stage();
                CrFood.setTitle("Add Food");
                CrFood.setScene(CrFoodScene);
 
                // Set position of second window, related to primary window.
                CrFood.setX(primaryStage.getX() + 700);
                CrFood.setY(primaryStage.getY() + 350);
 
                CrFood.show();
                
                generateFood.setOnAction(new EventHandler<ActionEvent>() {
                	@Override
                	public void handle(ActionEvent event) {

                		Double caloriesVal;
                		
                		CrFood.close();
                	}
                }); // action event of generateFood button
                Food newFood = new Food("test","test",1,2,3,4,5);
                foodList.addFood(newFood);
            }
        }); // create food button action
        
	    // add all foodBox (left pane)
	    foodBox.getChildren().addAll(foodLabel, foodView, createFood, foodNameSearch, nutrientSearch, runSearchQuery);
	    // end of left food section ---------------------------------------------------------------------------------------------------------------

	    // right section - meal list  --------------------------------------------------------------------------------------------------------------
	    Label mealLabel = new Label();
	    mealLabel.setText("Meals");
	    mealLabel.setPadding(new Insets(10));
	    mealLabel.setFont(Font.font("Cambria", 24));
	    // list of meals
	    ObservableList<Meal> meals = FXCollections.observableArrayList();
	    meals.addAll(mealsList); // add from the list variable of this class
	    ListView<Meal> mealView = new ListView<Meal>(meals);
	    mealView.setPrefSize(400, 300);
	    // use to store object but display meal's name
	    mealView.setCellFactory(param -> new ListCell<Meal>() {
	    	@Override
	    	protected void updateItem(Meal item, boolean empty) {
	    		super.updateItem(item, empty);
	    		if (empty || item == null || item.getName() == null) {
	    			setText(null);
	    		}
	    		else {
	    			setText(item.getName());
	    		}
	    	}
		} ); // cell factory for displaying meals well
	    // middle section for results to be displayed  -------------------------------------------------------------------------------------------------
	    VBox middle = new VBox();
	    middle.setPadding(new Insets(20));
	    middle.setSpacing(10);
	    middle.setStyle("-fx-background-color: #B39DDB;");
	    
	    
	    // new createMeal button
	    Button createMeal = new Button("Create Meal");
	    createMeal.setPrefSize(120, 20);
	    createMeal.setOnAction(new EventHandler<ActionEvent>() {  
            @Override
            public void handle(ActionEvent event) {
            	VBox mealLayout = new VBox();
            	mealLayout.setPadding(new Insets(20));
            	// fields and labels within the dialogue
                Label nameLabel = new Label("Name of Meal:"); 
                TextField mealName = new TextField();
                mealName.setPromptText("Enter a meal name...");
                Label foodListLabel = new Label("Choose foods to add.");
                // get multi selected food items and put in a list to add to a meal
	            ObservableList<Food> tempFoodList = FXCollections.observableArrayList();
	            ListView<Food> foodListView = new ListView<Food>(tempFoodList);
	            tempFoodList.addAll(foodList.getAllFoods());
	            foodListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // allow multiple selection
	            foodListView.setCellFactory(param -> new ListCell<Food>() {
	    	    	@Override
	    	    	protected void updateItem(Food item, boolean empty) {
	    	    		super.updateItem(item, empty);
	    	    		if (empty || item == null || item.getName() == null) {
	    	    			setText(null);
	    	    		}
	    	    		else {
	    	    			setText(item.getName());
	    	    		}
	    	    	}	
	    		} ); // cell factory for displaying food well
	    		// button for creating the meal and closing the window
                Button generateMeal = new Button("Create Meal");
                generateMeal.setPadding(new Insets(10));
                // add components to the VBox
                mealLayout.getChildren().addAll(nameLabel, mealName, foodListLabel, foodListView, generateMeal);
                // scene for the meal dialogue
                Scene CrMealScene = new Scene(mealLayout, 450, 350); 
                // New window (Stage)
                Stage CrMeal = new Stage();
                CrMeal.setTitle("Add Meal");
                CrMeal.setScene(CrMealScene);
                // Set position of second window, related to primary window.
                CrMeal.setX(primaryStage.getX() + 500);
                CrMeal.setY(primaryStage.getY() + 280);
                CrMeal.show();
                
                // generateMeal button action
                generateMeal.setPrefSize(120, 20);
                generateMeal.setOnAction(new EventHandler<ActionEvent>() {
                	@Override
                	public void handle(ActionEvent event) {
                		Meal newMeal = new Meal(mealName.getText());
                		newMeal.addFoodList(foodListView.getSelectionModel().getSelectedItems());
                		mealsList.add(newMeal); // so it's stored in list
                		meals.add(newMeal); // so it shows in right section
                		CrMeal.close();
                	}
                }); // generateMeal button action
            }
        }); // create Meal button action
	    
	    // Edit Selected Meal button
	    Button editMeal = new Button("Edit Selected");
        editMeal.setPrefSize(120, 20);
	    editMeal.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
            public void handle(ActionEvent event) {
	    		Label title = new Label();
	    		title.setText("Current Food Ingredients:");
	            // get multi selected food items and put in a list to add to a meal
	            ObservableList<Food> curFoodList = FXCollections.observableArrayList();
	            Meal curMeal = mealView.getSelectionModel().getSelectedItem();
	            curFoodList.addAll(curMeal.getIngredientList());
	            ListView<Food> curListView = new ListView<Food>(curFoodList);
	            curListView.setPadding(new Insets(10));
	            curListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // allow multiple selection
	            curListView.setCellFactory(param -> new ListCell<Food>() {
	    	    	@Override
	    	    	protected void updateItem(Food item, boolean empty) {
	    	    		super.updateItem(item, empty);
	    	    		if (empty || item == null || item.getName() == null) {
	    	    			setText(null);
	    	    		}
	    	    		else {
	    	    			setText(item.getName());
	    	    		}
	    	    	}	
	    		} ); // cell factory for displaying food well
	            
	            // Vbox to display
	            VBox display = new VBox();
	            // button that will add and close the window
	            Button remove = new Button();
	            remove.setText("Remove Selected from Meal");
	            remove.setPrefSize(200, 20);
	            remove.setPadding(new Insets(10));
	            
	            // pieces for adding more ingredients to the meal
	            Label foodListLabel = new Label("Choose foods to add.");
                // get multi selected food items and put in a list to add to a meal
	            ObservableList<Food> tempFoodList = FXCollections.observableArrayList();
	            ListView<Food> foodListView = new ListView<Food>(tempFoodList);
	            tempFoodList.addAll(foodList.getAllFoods());
	            foodListView.setPadding(new Insets(10));
	            foodListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // allow multiple selection
	            foodListView.setCellFactory(param -> new ListCell<Food>() {
	    	    	@Override
	    	    	protected void updateItem(Food item, boolean empty) {
	    	    		super.updateItem(item, empty);
	    	    		if (empty || item == null || item.getName() == null) {
	    	    			setText(null);
	    	    		}
	    	    		else {
	    	    			setText(item.getName());
	    	    		}
	    	    	}	
	    		} ); // cell factory for displaying food well
	            // button for adding more foods to meal
	            Button add = new Button("Add Foods");
	            add.setPrefSize(100, 20);
	            // VBox details
	            display.getChildren().addAll(title, curListView, remove, foodListLabel, foodListView, add);
	            display.setPadding(new Insets(10));
	            display.setStyle("-fx-background-color: #B39DDB;");
	            display.setPrefSize(300, 400);
	            // establish scene
	    		Scene editFoodSc = new Scene(display, 400, 500);
	            // new window (stage)
	            Stage editFoods = new Stage();
	            editFoods.setTitle("Edit Meal Content");
	            editFoods.setScene(editFoodSc);
	            editFoods.show();
	            // set action for the remove button
	            remove.setOnAction(new EventHandler<ActionEvent>() {
	            	@Override
	            	public void handle(ActionEvent event) {
	            		curMeal.removeMultipleFoods(curListView.getSelectionModel().getSelectedItems());
	    	    		middle.getChildren().clear();
	            		editFoods.close();
	            	}
	            } ); // remove button action
	            // set action for the add button
	            add.setOnAction(new EventHandler<ActionEvent>() {
	            	@Override
	            	public void handle(ActionEvent event) {
	            		curMeal.addFoodList(foodListView.getSelectionModel().getSelectedItems());
	    	    		middle.getChildren().clear();
	            		editFoods.close();
	            	}
	            } );
	    	}
	    } ); // edit selected meal button action
	    
	    // buttons that will be defined more below
	    Button showIngredients = new Button();
	    Button analyzeMeal = new Button();
	    // meal/right section
	    mealBox.getChildren().addAll(mealLabel, mealView, createMeal, editMeal, analyzeMeal, showIngredients);
	    // end of right meals section ------------------------------------------------------------------------------------------------------------------
	    
	    
	    // middle section main content ------------------------------------------------------------
	    // for displaying the selected meals' ingredients
	    Label mealIngredientsDisplay = new Label();
	    mealIngredientsDisplay.setText("Ingredients for: ");
	    mealIngredientsDisplay.setFont(Font.font("Cambria", 24));
	    mealIngredientsDisplay.setPadding(new Insets(10));
	    // list of currently selected meals' ingredients
	    ObservableList<Food> ingredients = FXCollections.observableArrayList();
	    ListView<Food> ingredientView = new ListView<Food>(ingredients);
	    // use to store object but display food's name
	    ingredientView.setCellFactory(param -> new ListCell<Food>() {
	    	@Override
	    	protected void updateItem(Food item, boolean empty) {
	    		super.updateItem(item, empty);
	    		if (empty || item == null || item.getName() == null) {
	    			setText(null);
	    		}
	    		else {
	    			setText(item.getName());
	    		}
	    	}
		} ); // cell factory for displaying food ingredients well
	    mealView.setPrefSize(200, 200);
	    mealView.setEditable(false);
	    // Show Ingredients Button
	    showIngredients.setText("Show Ingredients");
	    showIngredients.setPrefSize(120, 20);
	    showIngredients.setOnAction(new EventHandler<ActionEvent>() {
	    	public void handle(ActionEvent event) {
	    		Meal curMeal = (Meal) mealView.getSelectionModel().getSelectedItem();
	    		mealIngredientsDisplay.setText(curMeal.getName() + "'s Ingredients:");
	    		ingredients.clear();
	    		ingredients.addAll(curMeal.getIngredientList());
	    		middle.getChildren().clear();
	    	    middle.getChildren().addAll(mealIngredientsDisplay, ingredientView);
	    	};
	    } ); // showIngredients button action
	    // list and listview to display current meal's nutitritional information
    	ObservableList<String> nutritionList = FXCollections.observableArrayList();
	    ListView<String> nutritionView = new ListView<String>(nutritionList);   
	    // analyze meal button
	    analyzeMeal.setText("Analyze Meal");
	    analyzeMeal.setPrefSize(120, 20);
	    analyzeMeal.setOnAction(new EventHandler<ActionEvent>() {
	    	public void handle(ActionEvent event) {
	    		Meal curMeal = (Meal) mealView.getSelectionModel().getSelectedItem();
	    		double [] nutritionArray = curMeal.analyzeMealNutrition();
	    		String calorieResult = "Calories: " + nutritionArray[0];
	    		String fatResult = "Fat: " + nutritionArray[1];
	    		String carbsResult = "Carbs: " + nutritionArray[2];
	    		String fiberResult = "Fiber: " + nutritionArray[3];
	    		String proteinResult = "Protein: " + nutritionArray[4];
	    		nutritionList.addAll(calorieResult, fatResult, carbsResult, fiberResult, proteinResult);
	    		ingredientView.setPrefSize(300, 300);
	    		ingredientView.setEditable(false);
	    		Label nutritionTitle = new Label(curMeal.getName() + "'s Nutritional Content:");
	    	    nutritionTitle.setFont(Font.font("Cambria", 24));
	    	    nutritionTitle.setPadding(new Insets(10));
	    	    middle.getChildren().clear();
	    	    middle.getChildren().addAll(nutritionTitle, nutritionView);
	    	};
	    } ); // analyzeMeal button action
	    // add all components to the middle
	    //middle.getChildren().addAll(mealIngredientsDisplay, ingredientView);
	    // end of middle section ----------------------------------------------------------------------------------------------------------------------
	    

	    
	    // bottom section - exit button --------------------------------------------------------------------------------------------------------------
	    HBox bottomMenu = new HBox();
	    bottomMenu.setPadding(new Insets (10) );
	    bottomMenu.setSpacing(10);
	    bottomMenu.setStyle("-fx-background-color: #4527A0;");
	    Button exitButton = new Button("Exit");
	    exitButton.setPrefSize(120, 20);
	    exitButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		primaryStage.close();
	    	}
	    } );
	    bottomMenu.getChildren().addAll(exitButton);
		// end of bottom section --------------------------------------------------------------------------------------------------------------------
	    
	    // for top section but keeping down here to make it simpler ----------------
	    // import and export buttons
	    // import foods from a file
	    Button importButton = new Button("Import");
	    importButton.setPrefSize(120, 20);
	    importButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		foodList.loadFoods("foodItems.csv");
	    	    food.addAll(foodList.getAllFoods());
	    	}
	    } ); // importButton.setOnAction()
	    // save foods to a file
	    Button exportButton = new Button("Export");
	    exportButton.setPrefSize(120, 20);
	    exportButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		foodList.saveFoods("savedFoods.csv");
	    		};
	    	} );
	    // add buttons and title to the heading box
	    headingbox.getChildren().addAll(programTitle, importButton, exportButton);	
	    // end of extra top section pieces -----------------------------------------------------------------
	    
	    
	    // add sections to borderPane
		border.setTop(headingbox);
		border.setLeft(foodBox);
		border.setRight(mealBox);
		border.setBottom(bottomMenu);
		border.setCenter(middle);
		
		// create scene with the borderPane
		Scene scene = new Scene(border, Color.DARKGRAY);
		primaryStage.setScene(scene);
		primaryStage.show();
	} // start()

	public static void main(String[] args) {
		
		launch(args);
		
	} // Main()
	
	
} // FoodBuildUI class
