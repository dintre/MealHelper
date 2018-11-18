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
	    foodBox.setPadding(new Insets (100));
	    foodBox.setSpacing(10);
	    foodBox.setStyle("-fx-background-color: #336699;");
	    Label foodLabel = new Label();
	    foodLabel.setText("Food");
	    foodBox.getChildren().add(foodLabel);
	    
	    // right section - meal list
	    VBox mealBox = new VBox();
	    mealBox.setPadding(new Insets (100));
	    mealBox.setSpacing(10);
	    mealBox.setStyle("-fx-background-color: #336699;");
	    Label mealLabel = new Label();
	    mealLabel.setText("Meals");
	    mealBox.getChildren().add(mealLabel);
	    
		
	    // add sections to borderPane
		border.setTop(headingbox);
		border.setLeft(foodBox);
		border.setRight(mealBox);
	    
		// create scene with the borderPane
		Scene scene = new Scene(border, Color.DARKGRAY);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		
		
		
		
		
		/*
	    ToolBar toolbar = new ToolBar();
	    HBox statusbar = new HBox();
	    border.setTop(toolbar);
	    border.setCenter(null);
	    border.setBottom(statusbar);
		
		Scene scene = new Scene(border, Color.DARKGRAY);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		// Food List -------------- Pane and label 
		
		primaryStage.setTitle("Food List");
		StackPane foodPane = new StackPane();
		Scene foodScene = new Scene(foodPane, 1600, 900, Color.DARKGRAY);
		
		Label foodLabel = new Label();
		foodLabel.setText("Foods");
		foodPane.getChildren().add(foodLabel);
		StackPane.setAlignment(foodLabel, Pos.CENTER_LEFT);
	    primaryStage.setScene(foodScene);
		primaryStage.show();
		
		
		// Meal List -------------- Pane and label 
		
		primaryStage.setTitle("meal List");
		StackPane mealPane = new StackPane();
		Scene mealScene = new Scene(mealPane, 1600, 900, Color.DARKGRAY);
		
		Label mealLabel = new Label();
		mealLabel.setText("Meals");
		mealPane.getChildren().add(mealLabel);
		StackPane.setAlignment(mealLabel, Pos.CENTER_RIGHT);
	    primaryStage.setScene(mealScene);
		primaryStage.show();
		*/
		
		
//		// TextField
//		
/*		primaryStage.setTitle("TextField");
	    HBox hbox = new HBox();
     	Scene scene2 = new Scene(hbox, 400, 400, Color.DARKGRAY);
//		
		Label label = new Label();
		label.setAlignment(Pos.CENTER);
		label.setMinHeight(25);
		label.setText("Name: ");
		
		TextField input = new TextField();
		input.setMaxHeight(20); input.setMaxWidth(200);
		input.setPromptText("Input Prompt Text");
		input.setFocusTraversable(false);
		hbox.getChildren().addAll(label, input);
		primaryStage.setScene(scene);
		primaryStage.show();*/
		
//		// PasswordField - HBox
//		
//		primaryStage.setTitle("PasswordField");
//		HBox hbox = new HBox(10.0);
//		Scene scene = new Scene(hbox, 800, 600, Color.DARKGRAY);
//		
//		Label nameLabel = new Label();
//		nameLabel.setAlignment(Pos.CENTER);
//		nameLabel.setMinHeight(25);
//		nameLabel.setText("Name: ");
//		
//		TextField nameInput = new TextField();
//		nameInput.setMaxHeight(20); nameInput.setMaxWidth(200);
//		nameInput.setPromptText("Input Name");
//		nameInput.setFocusTraversable(false);
//		
//		Label passwordLabel = new Label();
//		passwordLabel.setAlignment(Pos.CENTER);
//		passwordLabel.setMinHeight(25);
//		passwordLabel.setText("Password: ");
//		
//		PasswordField passwordInput = new PasswordField();
//		passwordInput.setMaxHeight(20); passwordInput.setMaxWidth(200);
//		passwordInput.setPromptText("Input Password");
//		passwordInput.setFocusTraversable(false);
//		
//		hbox.getChildren().addAll(nameLabel, nameInput, passwordLabel, passwordInput);
//		primaryStage.setScene(scene);
//		primaryStage.show();
		
//		// PasswordField - VBox
//		
//		primaryStage.setTitle("PasswordField");
//		VBox vbox = new VBox(10.0);
//		Scene scene = new Scene(vbox, 800, 600, Color.DARKGRAY);
//		
//		Label nameLabel = new Label();
//		nameLabel.setAlignment(Pos.CENTER);
//		nameLabel.setMinHeight(25);
//		nameLabel.setText("Name: ");
//		
//		TextField nameInput = new TextField();
//		nameInput.setMaxHeight(20); nameInput.setMaxWidth(200);
//		nameInput.setPromptText("Input Name");
//		nameInput.setFocusTraversable(false);
//		
//		Label passwordLabel = new Label();
//		passwordLabel.setAlignment(Pos.CENTER);
//		passwordLabel.setMinHeight(25);
//		passwordLabel.setText("Password: ");
//		
//		PasswordField passwordInput = new PasswordField();
//		passwordInput.setMaxHeight(20); passwordInput.setMaxWidth(200);
//		passwordInput.setPromptText("Input Password");
//		passwordInput.setFocusTraversable(false);
//		
//		vbox.getChildren().addAll(nameLabel, nameInput, passwordLabel, passwordInput);
//		primaryStage.setScene(scene);
//		primaryStage.show();
		
//		// Form - VBox
//		
//		primaryStage.setTitle("Button");
//		VBox vbox = new VBox(10.0);
//		Scene scene = new Scene(vbox, 800, 600, Color.DARKGRAY);
//		
//		Label nameLabel = new Label();
//		nameLabel.setAlignment(Pos.CENTER);
//		nameLabel.setMinHeight(25);
//		nameLabel.setText("Name: ");
//		
//		TextField usernameInput = new TextField();
//		usernameInput.setMaxHeight(20); usernameInput.setMaxWidth(200);
//		usernameInput.setPromptText("Input Username");
//		usernameInput.setFocusTraversable(false);
//		
//		Label passwordLabel = new Label();
//		passwordLabel.setAlignment(Pos.CENTER);
//		passwordLabel.setMinHeight(25);
//		passwordLabel.setText("Password: ");
//		
//		PasswordField passwordInput = new PasswordField();
//		passwordInput.setMaxHeight(20); passwordInput.setMaxWidth(200);
//		passwordInput.setPromptText("Input Password");
//		passwordInput.setFocusTraversable(false);
//		
//		Button submitButton = new Button();
//		submitButton.setText("Submit");
//		submitButton.setOnAction(new EventHandler<ActionEvent>() {
//			
//			@Override
//			public void handle(ActionEvent event) {
//				// TODO Auto-generated method stub
//				System.out.println("The username is: " + usernameInput.getText());
//				System.out.println("The password is: " + passwordInput.getText());
//			}
//		});
//		
//		vbox.getChildren().addAll(nameLabel, usernameInput, passwordLabel, passwordInput, submitButton);
//		primaryStage.setScene(scene);
//		primaryStage.show();
		
//		// GridPane - Form
//		
//		primaryStage.setTitle("Form");
//		GridPane gPane = new GridPane();
//		Scene scene = new Scene(gPane, 800, 600, Color.DARKGRAY);
//		
//		Label nameLabel = new Label();
//		nameLabel.setAlignment(Pos.CENTER);
//		nameLabel.setMinHeight(25);
//		nameLabel.setText("Name: ");
//		
//		TextField usernameInput = new TextField();
//		usernameInput.setMaxHeight(20); usernameInput.setMaxWidth(200);
//		usernameInput.setPromptText("Input Username");
//		usernameInput.setFocusTraversable(false);
//		
//		Label passwordLabel = new Label();
//		passwordLabel.setAlignment(Pos.CENTER);
//		passwordLabel.setMinHeight(25);
//		passwordLabel.setText("Password: ");
//		
//		PasswordField passwordInput = new PasswordField();
//		passwordInput.setMaxHeight(20); passwordInput.setMaxWidth(200);
//		passwordInput.setPromptText("Input Password");
//		passwordInput.setFocusTraversable(false);
//		
//		Button submitButton = new Button();
//		submitButton.setText("Submit");
//		submitButton.setOnAction(new EventHandler<ActionEvent>() {
//			
//			@Override
//			public void handle(ActionEvent event) {
//				// TODO Auto-generated method stub
//				System.out.println("The username is: " + usernameInput.getText());
//				System.out.println("The password is: " + passwordInput.getText());
//			}
//		});
//		
//		gPane.add(nameLabel, 0, 0);
//		gPane.add(usernameInput, 1, 0);
//		gPane.add(passwordLabel, 0, 1);
//		gPane.add(passwordInput, 1, 1);
//		gPane.add(submitButton, 0, 2, 2, 1);
//		GridPane.setHalignment(submitButton, HPos.CENTER);
//		primaryStage.setScene(scene);
//		primaryStage.show();
			
//		// StackPane
//		
//		primaryStage.setTitle("Stage and Scene");
//		StackPane sPane = new StackPane();
//	    Rectangle helpIcon = new Rectangle(30.0, 25.0);
//	    helpIcon.setFill(new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE,
//	        new Stop[]{
//	        new Stop(0,Color.web("#4977A3")),
//	        new Stop(0.5, Color.web("#B0C6DA")),
//	        new Stop(1,Color.web("#9CB6CF")),}));
//	    helpIcon.setStroke(Color.web("#D0E6FA"));
//	    helpIcon.setArcHeight(3.5);
//	    helpIcon.setArcWidth(3.5);
//
//	    Text helpText = new Text("?");
//	    helpText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
//	    helpText.setFill(Color.WHITE);
//	    helpText.setStroke(Color.web("#7080A0")); 
//
//	    sPane.getChildren().addAll(helpIcon, helpText);
//	    sPane.setAlignment(Pos.CENTER);
//	    
//	    Scene scene = new Scene(sPane, 400, 400, Color.DARKGRAY);
//		primaryStage.setScene(scene);
//		primaryStage.show();
		
		
//		// ListView
//		primaryStage.setTitle("ListView");
//		StackPane sPane = new StackPane();
//		Scene scene = new Scene(sPane, 1600, 900, Color.DARKGRAY);
//		
//		ListView<String> nameList = new ListView<>();
//		nameList.setItems(names);
//		sPane.getChildren().add(nameList);
//		primaryStage.setScene(scene);
//		primaryStage.show();
		
		
		
	} // start()
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
		
		
		launch(args);
	} // Main()
	
} // FoodBuildUI class
