package views;


import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.HuntTheWumpusGame;
import model.Hunter;


public class TextAreaView extends BorderPane implements Observer {
   
  private HuntTheWumpusGame theGame;
  //private GridPane textPanel;
  private GridPane labelPanel;
  private TextArea textArea;
  private Label message;
  private Button northArrow = new Button("N");
  private Button southArrow = new Button("S");
  private Button eastArrow = new Button("E");
  private Button westArrow = new Button("W"); 

 
  
  public TextAreaView(HuntTheWumpusGame WumpusGame) {
	  theGame = WumpusGame;
	  

	  message = new Label(theGame.getMessage());

	// Need this to send the first move to the game  
  
	  ButtonListener handler = new ButtonListener();
	    northArrow.setOnAction(handler);
	    eastArrow.setOnAction(handler);
	    southArrow.setOnAction(handler);
	    westArrow.setOnAction(handler);
    
    labelPanel = new GridPane(); 
    labelPanel.setHgap(0);
    labelPanel.setVgap(0);
    labelPanel.add(message, 1, 1);
    labelPanel.add(northArrow, 10, 9);
    labelPanel.add(eastArrow, 11, 10);
    labelPanel.add(southArrow, 10, 11);
    labelPanel.add(westArrow, 9, 10);

    textArea = new TextArea();
    textArea.setFont(Font.font("Monospaced", 20.5));
    textArea.setStyle("-fx-text-fill: hotpink;");


    
    
   BorderPane.setAlignment(labelPanel, Pos.CENTER);
   BorderPane.setAlignment(textArea, Pos.TOP_CENTER);
   BorderPane.setMargin(textArea, new Insets(20, 120, 150, 150));
   BorderPane.setMargin(labelPanel, new Insets(0, 0, 80, 300));
    this.setCenter(textArea);
    this.setBottom(labelPanel);
    initializeMap();

  }
  

  @Override
  
  //Updates on change from game
  public void update(Observable o, Object arg) {
	  theGame = (HuntTheWumpusGame) o;
	  if (arg == "startNewGame()") {
			
			initializeMap();
			
		}
	  updateMap();
  }
	
 public void initializeMap() {
 
	 textArea.setText(theGame.toString());
	 message.setText(theGame.getMessage());

	 
 }
  
  	//text area reprints to reflect map
  	public void updateMap() {
  		
  		if (theGame.getPlayerStatus()) {
  		textArea.setText(theGame.toString());
	    message.setText(theGame.getMessage());
	   
  	} else {
  		theGame.endMap();
  		textArea.setText(theGame.toString());
	    message.setText(theGame.getMessage());
  	}
  	}

  	//handle button click for arrow shot
	class ButtonListener implements EventHandler<ActionEvent> {


		@Override
		public void handle(ActionEvent e) {
			
			String shotDir = ((Button)e.getSource()).getText();
			System.out.println(shotDir);
			theGame.arrowShot(shotDir);
			
		}
	}
}
		
	
  