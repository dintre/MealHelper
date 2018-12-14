package application;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParsePosition;

//import javax.swing.event.ChangeListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import javafx.scene.input.MouseEvent;
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
 * Creates a the UI of a meal/food planning and creating program upon running
 * @author Trevor Dinsmoor (dinsmoor@wisc.edu)
 * @author Jie Gu (jgu68@wisc.edu)
 */
public class FoodBuildUI extends Application {
	// variables that persist and must be created here
	static ObservableList<String> names = FXCollections.observableArrayList();
	public ArrayList<Meal> mealsList = new ArrayList<Meal>(); // list of meals that is only populated while program is running
	public ArrayList<String> filtersList = new ArrayList<String>(); // list of filters currently active
	// for storing new food's values
    String nfoodName;
    String nfoodID;
    Double nfoodcalories;
    Double nfoodcarbs;
    Double nfoodfat;
    Double nfoodfiber;
    Double nfoodprotein;
	
    /*
     * sorts the items in the meal list so they're always displayed alphabetically by name
     * @param mealList - the list of meals
     * @return mealList - sorted list of meals
     */
    private ArrayList<Meal> sortMeals(ArrayList<Meal> mealList){
		mealList.sort(new Comparator<Meal>() { // anonymous class
    		@Override
    		public int compare(Meal meal1,Meal meal2) {
    			if(meal1.getName().toLowerCase().equals(meal2.getName().toLowerCase())) {
    				return 0;
    			}
    			else if(meal1.getName().toLowerCase().compareTo(meal2.getName().toLowerCase())  < 0 ){
    				return -1;
    			}
    			else {
    				return 1;
    			}
    		} // compare() overridden	
    	}); // end of anonymous inner class
		return mealList;
	} // sortMeals()
    
    /*
     * (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
	@Override
	public void start(Stage primaryStage) {
		// set title of the window that opens
		primaryStage.setTitle("Food Helper");
		primaryStage.show();
		
		// instantiate other classes that are needed at startup
		// FoodData - the list of food
	    FoodData foodList = new FoodData();
		// establish initial borderPane layout
		BorderPane border = new BorderPane();
		
		
		// create borderPane sections to be used throughout this class
		// top section 
		HBox headingbox = new HBox();
	    headingbox.setPadding(new Insets(5));
	    headingbox.setSpacing(10);
	    headingbox.setStyle("-fx-background-color: #4527A0;");
	    // left section 
	    VBox foodBox = new VBox();
	    foodBox.setPadding(new Insets (5, 30, 5, 30));
	    foodBox.setSpacing(5);
	    foodBox.setStyle("-fx-background-color: #B39DDB;");
	    // right section
	    VBox mealBox = new VBox();
	    mealBox.setPadding(new Insets (5, 30, 5, 30));
	    mealBox.setSpacing(5);
	    mealBox.setStyle("-fx-background-color: #B39DDB;");
		
	    // top section content  --------------------------------------------------------------------------------------------------------------
	    // set label for title of the program
	    Label programTitle = new Label();
	    programTitle.setText("Meal Helper"); 
	    programTitle.setTextFill(Color.web("#FAFAFA"));
	    programTitle.setFont(Font.font("Cambria", 22));
	    programTitle.setPadding(new Insets(0,2,0,20));
	    // end of top section content --------------------------------------------------------------------------------------------------------
	    
	    // left section - food list  --------------------------------------------------------------------------------------------------------------
	    ObservableList<Food> food = FXCollections.observableArrayList();
	    
	    Label foodLabel = new Label("Food"); // title label
	    foodLabel.setPadding(new Insets(5));
	    foodLabel.setFont(Font.font("Cambria", 16));
	    // food view that displays list of food
	    ListView<Food> foodView = new ListView<Food>(food);
	    foodView.setPrefSize(400, 190);
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
	    // when clicking on a food item, display its nutritional content in a window
        foodView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                   //Use ListView's getSelected Item
                   Food currentItemSelected = foodView.getSelectionModel().getSelectedItem();
                   //use this to do whatever you want to. Open Link etc.
                   GridPane foodCheck = new GridPane();
                   foodCheck.setPadding(new Insets(10, 10, 10, 10));
                   foodCheck.setVgap(5);
                   foodCheck.setHgap(5);
                   Label nameLabel = new Label("Name of Food: " + currentItemSelected.getName());
                   Label IDLabel = new Label("ID of Food: " + currentItemSelected.getId());
                   Label calorieLabel = new Label("Calories: " + Double.toString(currentItemSelected.getCalories()));
                   Label carbsLabel = new Label("Carbs: " + Double.toString(currentItemSelected.getCarbohydrates()));
                   Label fatLabel = new Label("Fat: " + Double.toString(currentItemSelected.getFat()));
                   Label fiberLabel = new Label("Fiber: " + Double.toString(currentItemSelected.getFiber()));
                   Label proteinLabel = new Label("Protein: " + Double.toString(currentItemSelected.getProtein()));
                   Button okay = new Button("Okay!");
                   okay.setPrefSize(230, 20);
                   foodCheck.add(nameLabel,0,0,1,1);
                   foodCheck.add(IDLabel,0,1,1,1);
                   foodCheck.add(calorieLabel,0,2,1,1);
                   foodCheck.add(carbsLabel,0,3,1,1);
                   foodCheck.add(fatLabel,0,4,1,1);
                   foodCheck.add(fiberLabel,0,5,1,1);
                   foodCheck.add(proteinLabel,0,6,1,1);
                   foodCheck.add(okay, 0, 7,1,1);
                   Scene foodCheckScene = new Scene(foodCheck, 320, 200);
    
                   //New window (Stage)
                   Stage CheckFood = new Stage();
                   CheckFood.setTitle("Food Data");
                   CheckFood.setScene(foodCheckScene);
    
                   // Set position of second window, related to primary window.
                   CheckFood.setX(primaryStage.getX() + 700);
                   CheckFood.setY(primaryStage.getY() + 350);
    
                   CheckFood.show();
                   okay.setOnAction(new EventHandler<ActionEvent>() {                        
                       @Override
                       public void handle(ActionEvent event) {
                           CheckFood.close();
                       }
                   }); // okay's action
                }
            } // handle method for mouse click
        }); // foodView set on mouse click
        
	    // filtering -------------------------------------------------------------
	    // label for food name field
	    Label foodFilterLabel = new Label("Filter by food name");
	    // search field by name
	    TextField foodNameSearch = new TextField();
	    foodNameSearch.setPromptText("<name of food>");  
        // run food query button
	    Button runFoodQuery = new Button("Filter Food");
	    runFoodQuery.setPrefSize(120, 20);
	    // label for nutrient text field
	    Label nutrientFilterLabel = new Label("Filter by a nutrient rule");
	    // search field by nutrient
	    TextField nutrientSearch = new TextField();
	    nutrientSearch.setPromptText("<nutrient> <operator> <value>");
	    // run nutrient query button
	    Button runNutrientQuery = new Button("Filter Food");
	    runNutrientQuery.setPrefSize(120, 20);
	    // clears all current search filters, so full food list is displayed
	    Button clearFilters = new Button("Clear Search Filters");
	    clearFilters.setPrefSize(120, 20);
	    // list of currently active filters
	    ObservableList<String> currentFilterList = FXCollections.observableArrayList();
	    currentFilterList.addAll(filtersList); // add from the instance variable containing filter strings to display
	    // filter label
	    Label filterLabel = new Label("Current Search Filters:");
	    filterLabel.setPadding(new Insets(2));
	    // filter view
	    ListView<String> filterView = new ListView<String>(currentFilterList);
	    filterView.setPrefSize(400, 80);
	    // end of filtering -------------------------------------------------------------
	    
	    // create food button
	    Button createFood = new Button("Create Food");
	    createFood.setPrefSize(120, 20);// create food button
	    // create food button action
        createFood.setOnAction(new EventHandler<ActionEvent>() {  
            @Override
            public void handle(ActionEvent event) {
                // field labels
                Label nameLabel = new Label("Name of Food:");
                Label IDLabel = new Label("ID of Food:");
                Label calorieLabel = new Label("Calories:");
                Label carbsLabel = new Label("Carbs:");
                Label fatLabel = new Label("Fat:");
                Label fiberLabel = new Label("Fiber:");
                Label proteinLabel = new Label("Protein:");
                // pane for the popup window
                GridPane addFoodLayout = new GridPane();
                addFoodLayout.setPadding(new Insets(10, 10, 10, 10));
                addFoodLayout.setVgap(5);
                addFoodLayout.setHgap(5);
                TextField name = new TextField();
                TextField ID = new TextField();
                TextField calories = new TextField();
                TextField carbs = new TextField();
                TextField fat = new TextField();
                TextField fiber = new TextField();
                TextField protein = new TextField();
                DecimalFormat format = new DecimalFormat( "#.0" );
                // for validating the text that is entered in the fields
                calories.setTextFormatter( new TextFormatter<>(c ->
                {
                    if ( c.getControlNewText().isEmpty() ) {
                        return c;
                    }
                    ParsePosition parsePosition = new ParsePosition( 0 );
                    Object object = format.parse( c.getControlNewText(), parsePosition );
                    if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() ) {
                        return null;
                    }
                    else {
                        return c;
                    }
                })); // calories text formatter
                carbs.setTextFormatter( new TextFormatter<>(c -> {
                    if ( c.getControlNewText().isEmpty() ) {
                        return c;
                    }
                    ParsePosition parsePosition = new ParsePosition( 0 );
                    Object object = format.parse( c.getControlNewText(), parsePosition );
                    if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() ) {
                        return null;
                    }
                    else {
                        return c;
                    }
                })); // carbs text formatter
                fat.setTextFormatter( new TextFormatter<>(c -> {
                    if ( c.getControlNewText().isEmpty() ) {
                        return c;
                    }
                    ParsePosition parsePosition = new ParsePosition( 0 );
                    Object object = format.parse( c.getControlNewText(), parsePosition );
                    if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() ) {
                        return null;
                    }
                    else {
                        return c;
                    }
                })); // fat text formatter
                fiber.setTextFormatter( new TextFormatter<>(c -> {
                    if ( c.getControlNewText().isEmpty() ) {
                        return c;
                    }
                    ParsePosition parsePosition = new ParsePosition( 0 );
                    Object object = format.parse( c.getControlNewText(), parsePosition );
                    if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() ) {
                        return null;
                    }
                    else {
                        return c;
                    }
                })); // fiber text formatter
                protein.setTextFormatter( new TextFormatter<>(c -> {
                    if ( c.getControlNewText().isEmpty() ) {
                        return c;
                    }
                    ParsePosition parsePosition = new ParsePosition( 0 );
                    Object object = format.parse( c.getControlNewText(), parsePosition );
                    if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() ) {
                        return null;
                    }
                    else {
                        return c;
                    }
                })); // protein text formatter
                // buttons for creating/canceling the new food item within the window prompting for its nutritional info
                Button generateFood = new Button("Create Food!");
                generateFood.setPrefSize(230, 20);
                Button cancelFood = new Button("Cancel");
                cancelFood.setPrefSize(230,20);
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
                addFoodLayout.add(cancelFood, 0, 8,2,1);
                Scene CrFoodScene = new Scene(addFoodLayout, 285, 290);
                // New window (Stage)
                Stage CrFood = new Stage();
                CrFood.setTitle("Add Food");
                CrFood.setScene(CrFoodScene);
                // Set position of second window, related to primary window.
                CrFood.setX(primaryStage.getX() + 700);
                CrFood.setY(primaryStage.getY() + 350);
                CrFood.show();
                // action button for proceeding with new food item
                generateFood.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        boolean invalid = false;  
                        nfoodName=name.getText();
                        nfoodID = ID.getText();
                        String tempfoodcalories = calories.getText();
                        String tempfoodcarbs = carbs.getText();
                        String tempfoodfat = fat.getText();
                        String tempfoodfiber = fiber.getText();
                        String tempfoodprotein = protein.getText();
                        if (nfoodName.isEmpty()) {
                            invalid = true;
                        }
                        if (nfoodID.isEmpty()) {
                            invalid = true;
                        }
                        if (!tempfoodcalories.isEmpty()) {
                            nfoodcalories = Double.parseDouble(tempfoodcalories);
                        }
                        else {
                            invalid = true;
                        }
                        if (!tempfoodcarbs.isEmpty()) {
                            nfoodcarbs = Double.parseDouble(tempfoodcarbs);
                        }
                        else {
                            invalid = true;
                        }
                        if (!tempfoodfat.isEmpty()) {
                            nfoodfat = Double.parseDouble(tempfoodfat);
                        }
                        else {
                            invalid = true;
                        }
                        if (!tempfoodfiber.isEmpty()) {
                            nfoodfiber = Double.parseDouble(tempfoodfiber);
                        }
                        else {
                            invalid = true;
                        }
                        if (!tempfoodprotein.isEmpty()) {
                            nfoodprotein = Double.parseDouble(tempfoodprotein);
                        }
                        else {
                            invalid = true;
                        }
                        if (!invalid) {
                        	// shows a verification window with the values you entered
                            GridPane verifyFoodLayout = new GridPane();
                            verifyFoodLayout.setPadding(new Insets(10, 10, 10, 10));
                            verifyFoodLayout.setVgap(5);
                            verifyFoodLayout.setHgap(5);
                            Label nameLabel = new Label("Name of Food: " + nfoodName);
                            Label IDLabel = new Label("ID of Food: " + nfoodID);
                            Label calorieLabel = new Label("Calories: " + Double.toString(nfoodcalories));
                            Label carbsLabel = new Label("Carbs: " + Double.toString(nfoodcarbs));
                            Label fatLabel = new Label("Fat: " + Double.toString(nfoodfat));
                            Label fiberLabel = new Label("Fiber: " + Double.toString(nfoodfiber));
                            Label proteinLabel = new Label("Protein: " + Double.toString(nfoodprotein));
                            
                            Button verify = new Button("Okay!");
                            verify.setPrefSize(230, 20);
                            Button cancel = new Button("Cancel!");
                            cancel.setPrefSize(230, 20);
                            verifyFoodLayout.add(nameLabel,0,0,1,1);
                            verifyFoodLayout.add(IDLabel,0,1,1,1);
                            verifyFoodLayout.add(calorieLabel,0,2,1,1);
                            verifyFoodLayout.add(carbsLabel,0,3,1,1);
                            verifyFoodLayout.add(fatLabel,0,4,1,1);
                            verifyFoodLayout.add(fiberLabel,0,5,1,1);
                            verifyFoodLayout.add(proteinLabel,0,6,1,1);
                            verifyFoodLayout.add(verify, 0, 7,1,1);
                            verifyFoodLayout.add(cancel,1,7,1,1);
                            Scene VerifyFoodScene = new Scene(verifyFoodLayout, 285, 275);
             
                            //New window (Stage)
                            Stage VerifyFood = new Stage();
                            VerifyFood.setTitle("Verify");
                            VerifyFood.setScene(VerifyFoodScene);
             
                            // Set position of second window, related to primary window.
                            VerifyFood.setX(primaryStage.getX() + 700);
                            VerifyFood.setY(primaryStage.getY() + 350);        
                            VerifyFood.show();
                            // finishes food creation and closes windows
                            verify.setOnAction(new EventHandler<ActionEvent>() {    
                                @Override
                                public void handle(ActionEvent event) {
                                    Food newFood = new Food(nfoodID,nfoodName,nfoodcalories,nfoodfat,nfoodcarbs,nfoodfiber,nfoodprotein);
                                    foodList.addFood(newFood);
                                    food.add(newFood);
                                    VerifyFood.close();
                                    CrFood.close();
                                }
                            }); // action for verify button
                            // cancels food creation and closes verify window
                            cancel.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    VerifyFood.close();
                                }
                            }); // cancel button action 
                        } // if it's valid
                        
                        else { // otherwise not valid
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Missing Food Data or Incorrect Food Data!");
                            alert.showAndWait();
                        } // else
                    }
                }); // generate food button action
                // stop creating this food item button
                cancelFood.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        CrFood.close();
                	}
                }); // action event of generateFood button
            }
        }); // create food button action

	    // food query button action
	    runFoodQuery.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		String userInput = foodNameSearch.getText();
	    		String userToRule = "name == " + userInput;
                currentFilterList.clear(); // clear old view
	    		food.clear();
                filtersList.add(userToRule); // add new filter input to the list
	    		currentFilterList.addAll(filtersList); // add all filters to the displayed view
	    		food.addAll(foodList.filterByName(userToRule));	
	    		foodBox.getChildren().removeAll(filterLabel, filterView, clearFilters);
	    		foodBox.getChildren().addAll(filterLabel, filterView, clearFilters);
	    		foodNameSearch.clear();
	    	}	
	    }); // action for runFoodQuery button
	    
	    // nutrient query button action
	    runNutrientQuery.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		String userInput = nutrientSearch.getText();
                currentFilterList.clear(); // clear old view
                food.clear();
                filtersList.add(userInput); // add new filter input to the list
                currentFilterList.addAll(filtersList); // add all filters to the displayed view
                food.addAll(foodList.filterByNutrients(filtersList));
                foodBox.getChildren().removeAll(filterLabel, filterView, clearFilters);
	    		foodBox.getChildren().addAll(filterLabel, filterView, clearFilters);
	    		nutrientSearch.clear();
	    	}
	    }); // action for runNutrientQuery button	
	    
	    // clearFilters button action
	    clearFilters.setOnAction(new EventHandler<ActionEvent>() {  
            @Override
            public void handle(ActionEvent event) {
            	food.clear(); // empty the list
            	food.addAll(foodList.getAllFoods());  
            	currentFilterList.clear();
            	filtersList.clear(); // clear displayed list of filters
            	foodBox.getChildren().removeAll(filterLabel, filterView, clearFilters);
            }
	    } ); // clear filters button action
        
	    // add most items to the foodBox (left pane)
	    foodBox.getChildren().addAll(foodLabel, foodView, createFood, foodFilterLabel,
	    		foodNameSearch, runFoodQuery, nutrientFilterLabel, nutrientSearch,
	    		runNutrientQuery);
	    // end of left food section ---------------------------------------------------------------------------------------------------------------

	    // right section - meal list  --------------------------------------------------------------------------------------------------------------
	    Label mealLabel = new Label("Meals");
	    mealLabel.setPadding(new Insets(5));
	    mealLabel.setFont(Font.font("Cambria", 16));
	    // list of meals
	    ObservableList<Meal> meals = FXCollections.observableArrayList();
	    meals.addAll(sortMeals(mealsList)); // add from the list variable of this class
	    ListView<Meal> mealView = new ListView<Meal>(meals);
	    mealView.setPrefSize(400, 190);
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
	    middle.setPadding(new Insets(10));
	    middle.setSpacing(10);
	    middle.setStyle("-fx-background-color: #B39DDB;");
	    
	    // new createMeal button
	    Button createMeal = new Button("Create Meal");
	    createMeal.setPrefSize(120, 20);
	    // action for the create meal button. Causes a popup window to add foods to the meal 
	    createMeal.setOnAction(new EventHandler<ActionEvent>() {  
            @Override
            public void handle(ActionEvent event) {
            	VBox mealLayout = new VBox();
            	mealLayout.setPadding(new Insets(20));
            	mealLayout.setSpacing(5);
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
	            // displays the food names intead of their object references
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
                
                // generateMeal button action to create meal object and close the window
                generateMeal.setPrefSize(120, 20);
                generateMeal.setOnAction(new EventHandler<ActionEvent>() {
                	@Override
                	public void handle(ActionEvent event) {
                		Meal newMeal = new Meal(mealName.getText());
                		newMeal.addFoodList(foodListView.getSelectionModel().getSelectedItems());
                		mealsList.add(newMeal); // so it's stored in list
                		meals.clear();
                		meals.addAll(sortMeals(mealsList)); // so it shows in right section
                		CrMeal.close();
                	}
                }); // generateMeal button action
            }
        }); // create Meal button action
	    
	    // Edit Selected Meal button
	    Button editMeal = new Button("Edit Selected");
        editMeal.setPrefSize(120, 20);
        // edit button action to open a window for adding/removing foods
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
	            // displays food names instead of object references
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
	            display.setSpacing(5);
	            // button that will add and close the window
	            Button remove = new Button();
	            remove.setText("Remove Selected from Meal");
	            remove.setPrefSize(200, 20);
	            remove.setPadding(new Insets(5));
	            
	            // pieces for adding more ingredients to the meal
	            Label foodListLabel = new Label("Choose foods to add.");
                // get multi selected food items and put in a list to add to a meal
	            ObservableList<Food> tempFoodList = FXCollections.observableArrayList();
	            ListView<Food> foodListView = new ListView<Food>(tempFoodList);
	            tempFoodList.addAll(foodList.getAllFoods());
	            foodListView.setPadding(new Insets(10));
	            foodListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // allow multiple selection
	            // displays food names instead of food object references
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
	            add.setPadding(new Insets(5));
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
	            } ); // action for add button
	    	}
	    } ); // edit selected meal button action
	    
	    // buttons that will be defined more below
	    Button showIngredients = new Button();
	    Button analyzeMeal = new Button();
	    // meal - right section. add all constant items to it
	    mealBox.getChildren().addAll(mealLabel, mealView, createMeal, editMeal, analyzeMeal, showIngredients);
	    // end of right meals section ------------------------------------------------------------------------------------------------------------------
	       
	    // middle section main content ------------------------------------------------------------
	    // for displaying the selected meals' ingredients
	    Label mealIngredientsDisplay = new Label();
	    mealIngredientsDisplay.setText("Ingredients for: ");
	    mealIngredientsDisplay.setFont(Font.font("Cambria", 16));
	    mealIngredientsDisplay.setPadding(new Insets(5));
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
	    // Show Ingredients Button
	    showIngredients.setText("Show Ingredients");
	    showIngredients.setPrefSize(120, 20);
	    showIngredients.setOnAction(new EventHandler<ActionEvent>() {
	    	public void handle(ActionEvent event) {
	    		Meal curMeal = (Meal) mealView.getSelectionModel().getSelectedItem();
	    		mealIngredientsDisplay.setText(curMeal.getName() + "'s Ingredients");
	    		ingredients.clear();
	    		ingredients.addAll(curMeal.getIngredientList());
	    		middle.getChildren().clear();
	    	    middle.getChildren().addAll(mealIngredientsDisplay, ingredientView);
	    	};
	    } ); // showIngredients button action
	    // list and listview to display current meal's nutritional information
    	ObservableList<String> nutritionList = FXCollections.observableArrayList();
	    ListView<String> nutritionView = new ListView<String>(nutritionList);   
	    // analyze meal button
	    analyzeMeal.setText("Analyze Meal");
	    analyzeMeal.setPrefSize(120, 20);
	    // analyze meal button action to display meal's nutritional info in center of the screen
	    analyzeMeal.setOnAction(new EventHandler<ActionEvent>() {
	    	public void handle(ActionEvent event) {
	    		Meal curMeal = (Meal) mealView.getSelectionModel().getSelectedItem();
	    		double [] nutritionArray = curMeal.analyzeMealNutrition();
	    		String calorieResult = "Calories: " + nutritionArray[0];
	    		String fatResult = "Fat: " + nutritionArray[1];
	    		String carbsResult = "Carbs: " + nutritionArray[2];
	    		String fiberResult = "Fiber: " + nutritionArray[3];
	    		String proteinResult = "Protein: " + nutritionArray[4];
	    		nutritionList.clear(); // clear any old values
	    		nutritionList.addAll(calorieResult, fatResult, carbsResult, fiberResult, proteinResult);
	    		ingredientView.setPrefSize(300, 300);
	    		ingredientView.setEditable(false);
	    		Label nutritionTitle = new Label(curMeal.getName() + "'s Nutritional Content");
	    	    nutritionTitle.setFont(Font.font("Cambria", 16));
	    	    nutritionTitle.setPadding(new Insets(5));
	    	    middle.getChildren().clear();
	    	    middle.getChildren().addAll(nutritionTitle, nutritionView);
	    	};
	    } ); // analyzeMeal button action
	    // middle section content -------------------------------------------------------------------------------------------------------------------
	    
	    // bottom section - exit button --------------------------------------------------------------------------------------------------------------
	    HBox bottomMenu = new HBox();
	    bottomMenu.setPadding(new Insets (5) );
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
	    // button action for importing a file of food items. parses the file and adds food items
	    // with nutritional content filled out. This replaces any existing food list
	    importButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		VBox importWindow = new VBox();
	    		importWindow.setSpacing(8);
	    		importWindow.setPadding(new Insets(20));
            	// fields and labels within the dialogue
                Label fileToLoadLabel = new Label("Enter the name of a .csv file to load:"); 
                TextField fileName = new TextField();
                fileName.setPromptText("<fileName>");
	    		// button for laoding and closing the window
                Button doLoad = new Button("Load File");
                // add components to the VBox
                importWindow.getChildren().addAll(fileToLoadLabel, fileName, doLoad);
                // scene for the load dialogue
                Scene loadScene = new Scene(importWindow, 350, 150); 
                // New window (Stage)
                Stage loadStage = new Stage();
                loadStage.setTitle("Load File");
                loadStage.setScene(loadScene);
                // Set position of second window, related to primary window.
                loadStage.setX(primaryStage.getX() + 500);
                loadStage.setY(primaryStage.getY() + 280);
                loadStage.show();
                // doLoad button action
                doLoad.setPrefSize(120, 20);
                // action for running the load function once user has specified a file.
                doLoad.setOnAction(new EventHandler<ActionEvent>() {
                	@Override
                	public void handle(ActionEvent event) {
                		String fileNameInput = fileName.getText();
                		food.clear();
                		foodList.loadFoods(fileNameInput + ".csv");
                		food.addAll(foodList.getAllFoods());
                		loadStage.close(); // close the window
	    			}
                } ); // doLoad button action
	    	}
	    } ); // importButton.setOnAction()
	    
	    // save foods to a file
	    Button exportButton = new Button("Export");
	    exportButton.setPrefSize(120, 20);
	    // button action for saving the current food list to a csv file
	    exportButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event) {
	    		VBox exportWindow = new VBox();
	    		exportWindow.setSpacing(8);
	    		exportWindow.setPadding(new Insets(20));
            	// fields and labels within the dialogue
                Label fileToSaveLabel = new Label("Enter a name for your file:"); 
                TextField fileName = new TextField();
                fileName.setPromptText("<fileName>");
	    		// button for laoding and closing the window
                Button doSave = new Button("Save File");
                // add components to the VBox
                exportWindow.getChildren().addAll(fileToSaveLabel, fileName, doSave);
                // scene for the load dialogue
                Scene saveScene = new Scene(exportWindow, 350, 150); 
                // New window (Stage)
                Stage saveStage = new Stage();
                saveStage.setTitle("Load File");
                saveStage.setScene(saveScene);
                // Set position of second window, related to primary window.
                saveStage.setX(primaryStage.getX() + 500);
                saveStage.setY(primaryStage.getY() + 280);
                saveStage.show();
                // doSave button action
                doSave.setPrefSize(120, 20);
                // button action for actually saving the file after specifying a name for the file
                doSave.setOnAction(new EventHandler<ActionEvent>() {
                	@Override
                	public void handle(ActionEvent event) {
                		String fileNameInput = fileName.getText();
                		foodList.saveFoods(fileNameInput + ".csv");
                		food.addAll(foodList.getAllFoods());
                		saveStage.close(); // close the window
	    			}
                } ); // doSave button action
	    	}
	    } ); // exportButton.setOnAction()
	    
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
	
	/*
	 * launches the program
	 */
	public static void main(String[] args) {
		launch(args);		
	} // Main()	
	
} // FoodBuildUI class
