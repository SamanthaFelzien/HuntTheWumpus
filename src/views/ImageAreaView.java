package views;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.HuntTheWumpusGame;
import model.Hunter;
import views.TextAreaView.ButtonListener;

/**
 * This class shows the map and updates on player move and game changed
 * Samantha Felzien
 */

public class ImageAreaView extends BorderPane implements Observer{
	private HuntTheWumpusGame theGame;
	private GridPane labelPanel;
	  private Canvas canvas;
	  private BorderPane window;
	  private GraphicsContext gc;
	  private Image hunter;
	  private Image blood;
	  private Image goop;
	  private Image wumpus;
	  private Image background;
	  private Image slime;
	  private Image pit;
	  private Hunter hunterObj; 
	  private Point initHunterPos; 
	  private Button northArrow = new Button("N");
	  private Button southArrow = new Button("S");
	  private Button eastArrow = new Button("E");
	  private Button westArrow = new Button("W");
	  
	  
	  //set up scene with canvas and buttons
	 public ImageAreaView(HuntTheWumpusGame WumpusGame) {
		 theGame = WumpusGame; // Need this to send the first move to the game
		 
		  canvas = new Canvas(600,600);
		  gc = canvas.getGraphicsContext2D();
		  gc.setFill(Color.BLACK);
		  gc.fillRect(0, 0, canvas.getHeight(), canvas.getWidth());
		  
		  
		  
		  labelPanel = new GridPane(); 
		  labelPanel.setHgap(0);
		  labelPanel.setVgap(0);
		  labelPanel.add(northArrow, 10, 9);
		  labelPanel.add(eastArrow, 11, 10);
		  labelPanel.add(southArrow, 10, 11);
		  labelPanel.add(westArrow, 9, 10);
		  
		  blood = new Image("file:images/Blood.png", false);
		  goop = new Image("file:images/Goop.png", false);
		  background = new Image("file:images/Ground.png", false);
		  slime = new Image("file:images/Slime.png", false);
		  pit = new Image("file:images/SlimePit.png", false);
		  hunter = new Image("file:images/TheHunter.png", false);
		  wumpus = new Image("file:images/Wumpus.png", false);
		


		  ButtonListener handler = new ButtonListener();
		    northArrow.setOnAction(handler);
		    eastArrow.setOnAction(handler);
		    southArrow.setOnAction(handler);
		    westArrow.setOnAction(handler);  
		    
		  	

		    //window = new BorderPane();
		    this.setCenter(canvas);
		    this.setBottom(labelPanel);
		    BorderPane.setAlignment(labelPanel, Pos.BOTTOM_CENTER);
		    BorderPane.setAlignment(canvas, Pos.TOP_CENTER);
		    BorderPane.setMargin(canvas, new Insets(0, 100, 0, 100));
		    BorderPane.setMargin(labelPanel, new Insets(0, 100, 0, 350));
		    //this.setTop(canvas);
		    //this.setBottom(labelPanel);
	
		    
		   initializeMap();

	 }
	 
	 //draw background and hunter before any moves have been made
	 public void initializeMap() {
		 hunterObj = new Hunter();
		 initHunterPos =  hunterObj.getHunterLoc(); 
		  
		    for (int r = 0; r < 12; r++) {
		      for (int c = 0; c < 12; c++) {
		    	  gc.drawImage(background, (c)*50, (r)*50);
		      	}
		      }
		  gc.drawImage(hunter, (initHunterPos.y*50), (initHunterPos.x*50));

	 }

	 //handle changes from the game (either new moves or a new game)
	@Override
	public void update(Observable o, Object arg1) {
		System.out.println(arg1);
		theGame = (HuntTheWumpusGame) o;
		if (arg1 == "startNewGame()") {
	
			initializeMap();
		}
		
		  updateDrawings();
	}
	 

	//draw on canvas the image depending on characters in map from game
		public void updateDrawings() {
			
			//fill in initial hunter position as visited
			gc.fillRect((initHunterPos.y*50), (initHunterPos.x*50), 50, 50);
			
			//if the game is still being played
			if (theGame.getPlayerStatus()) {
		    char[][] temp = theGame.getDynamicMap();
			  
		    for (int r = 0; r < 12; r++) {
		      for (int c = 0; c < 12; c++) {
		    	  char playerChar =  temp[r][c];
	
		    	  if (playerChar == 'b') {
		    		  gc.fillRect((c)*50, (r)*50,50,50);
		    		  gc.drawImage(blood, (c)*50, (r)*50);
		    	  } 
		    	  if (playerChar == 'g') {
		    		  gc.fillRect((c)*50, (r)*50,50,50);
		    		  gc.drawImage(goop, (c)*50, (r)*50);
		    	  } 
		    	  if (playerChar == 's') {
		    		  gc.fillRect((c)*50, (r)*50,50,50);
		    		  gc.drawImage(slime, (c)*50, (r)*50);
		    	  } 
		    	  if (playerChar == 'p') {
		    		  gc.fillRect((c)*50, (r)*50,50,50);
		    		  gc.drawImage(pit, (c)*50, (r)*50);
		    		 
		    	  } 
		    	  if (playerChar == 'w') {
		    		  
		    		  gc.fillRect((c)*50, (r)*50,50,50);
		    		  gc.drawImage(wumpus, (c)*50, (r)*50);
		    		  
		    	
		    	  } 
		    	  if (playerChar == 'o') {
		    		  gc.drawImage(hunter, (c)*50, (r)*50);
		    	  }
		    	  
		    	  if (playerChar == ' ') {
		    		  	gc.fillRect((c)*50, (r)*50,50,50);
		    	  }
		    	  
		      	}
		      
		    }
		} // end if game is still being played
			
			//if game is over
			else {
				
				char[][] setMap = theGame.getSetMap();
				char[][] temp = theGame.getDynamicMap();
				  
			    for (int r = 0; r < 12; r++) {
			      for (int c = 0; c < 12; c++) {
			    	  char playerChar =  temp[r][c];
			    	  char setChar = setMap[r][c];
			    	 
			    	  
			    	  
			    	  if (playerChar != 'x') {
			    		  gc.fillRect((c)*50, (r)*50,50,50);
			    	  }
			    	  if (setChar == 'b') {
			    		  gc.drawImage(blood, (c)*50, (r)*50);
			    	  } 
	
			    	  if (setChar == 'g') {
			    		  gc.drawImage(goop, (c)*50, (r)*50);
			    	  } 
			    	  if (setChar == 's') {
			    		  gc.drawImage(slime, (c)*50, (r)*50);
			    	  } 
			    	  if (setChar == 'p') {
			    		  gc.drawImage(pit, (c)*50, (r)*50);
			    		  if (theGame.pitDeath()) {
			    			 gc.drawImage(hunter, theGame.getPitIndex().y*50, theGame.getPitIndex().x*50);
			    		  }
			    	  } 
			    	  if (setChar == 'w') {
			    		  gc.drawImage(wumpus, (c)*50, (r)*50);
			    		  if (theGame.wumpusDeath()==true) {
				    		  gc.drawImage(wumpus, (c)*50, (r)*50);
				    		  gc.drawImage(hunter, (c)*50, (r)*50);
				    		  }
			    	
			    	  	} 
			      	}
			      
			    }
			}
			
	}
		
		//handle arrow shot
		class ButtonListener implements EventHandler<ActionEvent> {


			@Override
			public void handle(ActionEvent e) {
				
				String shotDir = ((Button)e.getSource()).getText();
				System.out.println(shotDir);
				theGame.arrowShot(shotDir);
				
			}
		}

}
