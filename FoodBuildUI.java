//package cs400;

import java.io.File;
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
		
		// establish initial borderPane layout
		BorderPane border = new BorderPane();
		
		// create sections
		
		// top section
		VBox headingbox = new VBox();
	    headingbox.setPadding(new Insets(20, 100, 20, 100));
	    headingbox.setSpacing(10);
	    headingbox.setStyle("-fx-background-color: #336699;");
	    // set label for title of the program
	    Label programTitle = new Label();
	    programTitle.setText("Meal Helper"); 
	    programTitle.setFont(Font.font("Cambria", 32));
	    
	    Button importButton = new Button("Import");
	    importButton.setPrefSize(100, 20);
	    Button exportButton = new Button("Export");
	    exportButton.setPrefSize(100, 20);
	    headingbox.getChildren().addAll(programTitle, importButton, exportButton);	

	    
	    
	    // left section - food list
	    VBox foodBox = new VBox();
	    foodBox.setPadding(new Insets (20, 200, 20, 200));
	    foodBox.setSpacing(10);
	    foodBox.setStyle("-fx-background-color: #336699;");
	    
	    // title for food section
	    Label foodLabel = new Label();
	    foodLabel.setText("Food");
	    foodLabel.setPadding(new Insets(10));
	    foodLabel.setFont(Font.font("Cambria", 24));
	    
	    // search field by name
	    TextField foodNameSearch = new TextField();
	    foodNameSearch.setPromptText("Search by name...");
	    
	    // search field by nutrient
	    TextField nutrientSearch = new TextField();
	    nutrientSearch.setPromptText("Search by nutrient...");
	    
	    // run query button
	    Button runSearchQuery = new Button("Search");
	    runSearchQuery.setPrefSize(100, 20);
	    
	    // add a new food item button
	    Button addFood = new Button("Add Food");
	    addFood.setPrefSize(100, 20);
	    
	    
	    // add all foodBox (left pane) components
	    foodBox.getChildren().addAll(foodLabel, foodNameSearch, nutrientSearch, runSearchQuery, addFood);
	    
	    
	    
	    // right section - meal list
	    VBox mealBox = new VBox();
	    mealBox.setPadding(new Insets (20, 200, 20, 200));
	    mealBox.setSpacing(10);
	    mealBox.setStyle("-fx-background-color: #336699;");
	    Label mealLabel = new Label();
	    mealLabel.setText("Meals");
	    mealLabel.setPadding(new Insets(10));
	    mealLabel.setFont(Font.font("Cambria", 24));

	    
	    // analyze meal button
	    Button analyzeMeal = new Button();
	    analyzeMeal.setText("Analyze Meal");
	    analyzeMeal.setPrefSize(100, 20);
	    
	    // analysis summary section
	    Label analysisResults = new Label();
	    analysisResults.setText("Meal Analysis Results:");
	    
	    
	    mealBox.getChildren().addAll(mealLabel, analyzeMeal, analysisResults);
	    
	    
	    // bottom section - exit button
	    HBox bottomMenu = new HBox();
	    bottomMenu.setPadding(new Insets (20, 20, 20, 1000) );
	    bottomMenu.setSpacing(10);
	    bottomMenu.setStyle("-fx-background-color: #336699;");
	    Button exitButton = new Button("Exit");
	    exitButton.setPrefSize(100, 20);
	    bottomMenu.getChildren().addAll(exitButton);
		
	    
	    
	    // add sections to borderPane
		border.setTop(headingbox);
		border.setLeft(foodBox);
		border.setRight(mealBox);
		border.setBottom(bottomMenu);
	    
		
		
		// create scene with the borderPane
		Scene scene = new Scene(border, Color.DARKGRAY);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		/*
		// scene for clicking the add food button
		VBox addFoodForm = new VBox();
		addFoodForm.setStyle("-fx-background-color: #336699;");
		Scene addFoodScene = new Scene(addFoodForm, Color.DARKGRAY);
		primaryStage.setTitle("Add Food");
		primaryStage.setScene(addFoodScene);
		primaryStage.show();
		*/
		
		
		
	} // start()
	

	public static void main(String[] args) {
		
		launch(args);
		
	} // Main()
	
	
	
} // FoodBuildUI class
