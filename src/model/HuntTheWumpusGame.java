package model;

import java.awt.Point;
import java.io.IOException;
import java.util.Observable;
import java.util.Scanner;

/**
 * 
 * This class is the game. gets user moves around map 
 * and displays warnings/death based on user move
 *	Samantha Felzien
 * 
 */

public class HuntTheWumpusGame extends Observable{
	
	private Map map;
	private char[][] setMap;
	private char[][] dynamicMap;
	private int height = 11; 
	private int width = 11; 
	private Point hunterPos;
	private Hunter hunter;
	private boolean playerStatus; 
	private Point wumpusPos;
	private Point indexPrev; 
	private Point pitIndex; 
	private boolean wumpusDeath;
	private boolean pitDeath; 
	private char charPrev; 
	private char charAtIndex; 
	private String message = "Safe for now";

	public HuntTheWumpusGame() {	
		
		initializeMap();

	}
	
	//if new game started
	public void startNewGame() {
		 // The state of this model just changed so tell any observer to update themselves
	    //reset message for new game
	    initializeMap();
	    playerStatus = true;  
	    message = "Safe for now";
	    setChanged();
	    notifyObservers("startNewGame()");
	}
	
	//initalize map
		public void initializeMap() {
			map = new Map(); 
			hunter = new Hunter();
			dynamicMap = new char[12][12];
			setMap = map.getMap();
			playerStatus = true;
			wumpusPos = map.getWumpus();
			pitIndex = new Point();
			wumpusDeath = false;
			pitDeath = false;
			
			
			for (int row = 0; row < dynamicMap.length; row++) {
				for (int col = 0; col < dynamicMap[row].length; col++) {
					dynamicMap[row][col] = 'x';
				}
			}
			hunterPos= hunter.getHunterLoc();
			
			dynamicMap[hunterPos.x][hunterPos.y] = hunter.getSymbol();
			System.out.println(toString());
		}
		

	
	//Get key pressed from controller, wrap array,
	//get character at the index from the move,
	//and call moveOutcome to determine what happens from the move
	public void moveHunter(char move) {
	
	dynamicMap[hunterPos.x][hunterPos.y] = charPrev;
	if (charPrev == 'o') {
	dynamicMap[hunterPos.x][hunterPos.y] = ' ';
	} 
	else dynamicMap[hunterPos.x][hunterPos.y] = charPrev; 
	
		
	indexPrev = hunterPos; 
	  
	
	 if (move == 'n'){
	
		 moveOutcome(wrapIndices(hunterPos.x-1, hunterPos.y));
	 }
	   
	   if (move == 's') {
		   
		   moveOutcome(wrapIndices(hunterPos.x+1, hunterPos.y));
		 
	   }
	   
	   
	   if (move == 'e') {
		   moveOutcome(wrapIndices(hunterPos.x, hunterPos.y+1));
		 
	   }
	   
	   if (move == 'w') {
		   moveOutcome(wrapIndices(hunterPos.x, hunterPos.y-1));
		   
	   }
	   		hunter.setHunterLoc(hunterPos);

		   charPrev = charAtIndex; 
		   {printMap();}
		  
		};

	public void arrowShot(String shotDir) {
		//if shot is north or south, check to see if wumpus is on the same y coordinate as hunter
		if (shotDir == "N" || shotDir == "S") {
			if (hunterPos.y == wumpusPos.y) {
				setMessage("You shot the wumpus!");
				playerStatus = false;  
			} else {
				setMessage("You shot yourself!");
				playerStatus = false; 
			}
		}
		
		//if east or west, check if wumpus is on same x coordinate as hunter
			if (shotDir.equals("E") || shotDir.equals("W")) {
				if (hunterPos.x == wumpusPos.x) {
						setMessage("It's a hit!");
						//shotWumpus(true);
						playerStatus = false;  
					} else {
						setMessage("You shot yourself!");
						//shotSelf(true);
						playerStatus = false; 
					}
			}
			
		setChanged();
        notifyObservers();
	   
   }
	
	//wrap array around
	   public char wrapIndices(int row, int col) {
		   if (row > height) {
			   row = ((row % height) - 1);
		   }
		   if (row < 0) {
			   row = (row + 12);
		   }
		   if (col > width ) {
			   col = ((col % width) - 1);
		   }
		   if (col < 0 ) {
			   col = (col + 12);
		   }
		   
		   charAtIndex = setMap[row][col];
		   hunterPos = new Point(row, col);
		   return charAtIndex; 
	   }

	   
	public char[][] getSetMap() {
		return this.setMap;
	}   
	

	//show where all pits/warnings/wumpus were for text view
	public void endMap() {
		
		char[][] temp = setMap;
	
		 for (int r = 0; r < 12; r++) {
		    for (int c = 0; c < 12; c++) {
		    	if (setMap[r][c] == ' ') {
		    		temp[r][c] = 'x';
		    		}
		    	}
		    }	  
		  
		    for (int r = 0; r < 12; r++) {
		      for (int c = 0; c < 12; c++) {
		    	  if (dynamicMap[r][c] == ' ') {
		    		  temp[r][c] = ' ';
		    		  }
		    	  }
		    }
		    
		    if (pitDeath()) {
		    	temp[pitIndex.x][pitIndex.y] = 'o';
		    }
		    if (wumpusDeath()) {
		    	temp[wumpusPos.x][wumpusPos.y] = 'o';
		    }
		    	  
		    	  
		dynamicMap = temp; 
		
	}
	
	
	//Change area to visited
	public void update() {
		dynamicMap[hunterPos.x][hunterPos.y] = charPrev;
		   if (charPrev == 'o') {
			   dynamicMap[indexPrev.x][indexPrev.y] = ' ';
		   } 
	}

	
	//Returns message and playerstatus (dead or alive) based on character at index from hunter move. 
	//If hunter dies get place of death for proper image printing
	public void moveOutcome(char charAtIndex) {
		//if hunter moves into wumpus
		if (charAtIndex == 'w') {
			playerStatus = false; 
			setMessage("You walked into a wumpus! Death!");
			charAtIndex = 'o';
			wumpusDeath = true;
			
		//if hunter moves in pit	
		} else if (charAtIndex == 'p') {
			playerStatus = false; 
			setMessage("You fell into a pit! Death!");
			pitIndex = hunterPos;
			pitDeath = true; 
			playerStatus = false; 
			charAtIndex = 'o';
		
			
		}
		//if hunter walks into slime
		else if (charAtIndex == 's') {
			setMessage("I can hear the wind");
			playerStatus = true; 
			
		}
		
		//if hunter walks into blood
		else if (charAtIndex == 'b') {
		setMessage("I smell something foul");
		playerStatus = true; 
		}
		//if hunter walks into goop
		else if(  charAtIndex == 'g') {
			setMessage("I smell something foul" + "\n" + "I can hear the wind");
			playerStatus = true; 
			
			
		} else {
			//if space is available 
			playerStatus = true; 
			setMessage("Safe for now");
			charAtIndex = hunter.getSymbol();
			
		}	
		dynamicMap[hunterPos.x][hunterPos.y] = charAtIndex;
		setChanged();
        notifyObservers();
        System.out.println(message);
	
	}
	

	//getter methods
	
	public Point getHunterPos() {
		return hunter.getHunterLoc();
	}

	public boolean pitDeath() {
		return this.pitDeath; 
		
	}
	
	public Point getPitIndex() {
		return this.pitIndex; 
		
	}


	public boolean wumpusDeath() {
		return this.wumpusDeath; 
	}

	  
	 public String getMessage() {
		 return this.message; 
	 }
	
	public char setChar(char gameChar){
		charAtIndex = gameChar; 
		return charAtIndex;
	}
	
	public boolean getPlayerStatus() {
		return this.playerStatus; 
	}
	
	  public char[][] getDynamicMap() {
		    return dynamicMap;
		  }
		public void setMessage(String newMessage) {
			message = newMessage; 
		}


	
	//print map
	public void printMap() {
		 for (int i = 0; i < dynamicMap.length; i++) {
	            for (int j = 0; j < dynamicMap[i].length; j++) {
	                System.out.print(dynamicMap[i][j] + " ");
	            }
	            System.out.println();
		 }
}
	
	public String toString() {
	    String result = "";
	    for (int r = 0; r < 12; r++) {
	      for (int c = 0; c < 12; c++) {
	        result += " " + dynamicMap[r][c] + " ";
	        if (c == 11)
	  	        result += "\n";
	      }
	    }
	    return result;
	  }
}
