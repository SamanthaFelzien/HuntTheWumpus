package controller;

import java.util.Observer;


/**
 * This class is the controller that functions 
 * as the go between for the view and the model 
 * using event handlers and observers
 * 
 * Samantha Felzien
 */

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.HuntTheWumpusGame;
import model.Map;
import views.ImageAreaView;
import views.TextAreaView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class WumpusMain extends Application {

  public static void main(String[] args) {
    launch(args);
  }
  private HuntTheWumpusGame theGame;
 
//Create menu bar
  private MenuBar menuBar;

  private Observer currentView;
  private Observer imageView;
  private Observer textView;
  
 
  private BorderPane pane;
 // public static final int width = 254;
  //public static final int height = 360;

  @Override
  public void start(Stage stage) throws Exception {
	 stage.setTitle("Hunt The Wumpus");
    pane = new BorderPane();

    Scene scene = new Scene(pane, 800, 800);
    
    
    setupMenus();
    pane.setTop(menuBar);
    initalizeMapFirstTime();
    
 // Set up the views
    textView = new TextAreaView(theGame);
    imageView = new ImageAreaView(theGame);
    theGame.addObserver(textView);
    theGame.addObserver(imageView);
 
    setViewTo(imageView);
    stage.setScene(scene);
    stage.show();
    scene.getRoot().requestFocus();

    
 //get player moves from keyboard
    scene.setOnKeyPressed(e -> {
    	char move; 
    if (theGame.getPlayerStatus()) {	
        if (e.getCode() == KeyCode.RIGHT) {
        	move = 'e';
           theGame.moveHunter(move); 
        } else if (e.getCode() == KeyCode.LEFT) {
        	move = 'w';
           theGame.moveHunter(move); 
        } else if (e.getCode() == KeyCode.UP) {
        	move = 'n';
           theGame.moveHunter(move); 
        } else if (e.getCode() == KeyCode.DOWN) {
        	move = 's';
            theGame.moveHunter(move); 
         }
        System.out.println(e.getCode());
    }
    });
  }
    
  //change view
    private void setViewTo(Observer newView) {
    	 pane.setCenter(null);
         currentView = newView;
         pane.setCenter((Node) currentView);
	
    }

    //start first game
	private void initalizeMapFirstTime() {
    theGame = new HuntTheWumpusGame();
	
    }

	//set up menu
	private void setupMenus() {
    	MenuItem newGame = new MenuItem("New Game");
        Menu options = new Menu("Options");
        
        MenuItem imageView = new MenuItem("Image View");
        MenuItem textView = new MenuItem("Text Area View");
        
        Menu views = new Menu("Views");
        views.getItems().addAll(imageView, textView);
        
        options.getItems().addAll(newGame, views);

        menuBar = new MenuBar();
        menuBar.getMenus().addAll(options);
        
        MenuItemListener menuListener = new MenuItemListener();
        newGame.setOnAction(menuListener);
        imageView.setOnAction(menuListener);
        textView.setOnAction(menuListener);
	
}

	//handle user menu selection
	private class MenuItemListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
          // Find out the text of the JMenuItem that was just clicked
          String text = ((MenuItem) e.getSource()).getText();
          if (text.equals("Image View"))
            setViewTo(imageView);
          else if (text.equals("Text Area View"))
            setViewTo(textView);
          else if (text.equals("New Game"))
            theGame.startNewGame(); 
          
        }
      }

   

}