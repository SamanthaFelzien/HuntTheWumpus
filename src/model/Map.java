package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * This class creates the map of rooms for which the player, wumpus, pits exist
 *	Samantha Felzien
 * @param <T>
 */
public class Map<T> {
	
	public char slime = 's';
	public char blood = 'b';
	public char goop = 'g';
	private static Point wumpus;
	private static Point hunter; 
	private int height = 11;
	private int width  = 11; 
	Random ran = new Random();
	
	private char[][] map; 

	//create grid of rooms
	
    public Map() {
    
    	intializeMap();
    	placeWumpus(); 
    	placePits();
    	placeHunter();
    	printMap(); 

    }
    
    //Getters
    public Point getWumpus(){
    	return wumpus;
    }

    public static Point getHunter(){
    	return hunter; 
    }
    
	public char[][] getMap() {
	    return map;
	  }
    
   public void intializeMap() {
	map = new char[12][12];
	for (int row = 0; row < map.length; row++) {
		for (int col = 0; col < map[row].length; col++) {
			map[row][col] = ' ';
		}
	}
   }
    
   //Add neighbors (blood, slime, goop) for pit and wumpus. 
   //Wraps around array by checking if the index is out of bounds, 
   //then setting it to other side of array
    public void addNeighborWarnings(char symbol, int row, int col) {
    	char neighbor; 
    	if (symbol == 'w') 
    		neighbor = 'b';
    	 else 
    		 neighbor = 's';
    	
    	if (symbol == 'w') {
        	try {
            	//2 west of warning
            	map[row][col-2] = neighbor;
            	} catch (ArrayIndexOutOfBoundsException e) {
            		
            		map[row][((width+1)+(col-2))] = neighbor;
            			
            	}
        	
        	try {
            	//2 east of warning
            	map[row][col+2] = neighbor;
            	} catch (ArrayIndexOutOfBoundsException e) {
            		int c = ((col + 2) - width) -1;
            		
            		map[row][c] = neighbor; 
            		
            	}
            	
    
        	try {
            	//2 north of warning
            	map[row-2][col] = neighbor;
            	} catch (ArrayIndexOutOfBoundsException e) {
            		//System.out.println("Caught at 2 north of warning row: " + row + "col: " + col);
            		map[((height+1)+(row-2))][col] = neighbor;
            		
            		
            	}
    		
         	try {
         		//2 south of warning
         		map[row+2][col] = neighbor;
         		} catch (ArrayIndexOutOfBoundsException e) {
         			int r = (row+2)-(height+1);
         			map[r][col] = neighbor;	
         		}

         	
            	//NE warning
 
         		try {
             		map[row-1][col+1] = neighbor;
             		} catch (ArrayIndexOutOfBoundsException e) {
             			int r = row-1;
             			int c = col+1; 
             			
             			if (col+1 > width) {
             				c = 0;
             			}
             			if (row-1 < 0) {
             				r = height;
             			}
            			map[r][c] = neighbor;	
             	
             		}
           	try {
            	//SE warning
             		map[row+1][col+1] = neighbor;
             		} catch (ArrayIndexOutOfBoundsException e) {
             			int r = row+1;
             			int c = col+1;
             			
             			if (row+1 > height)
             				r = 0;
             			if (col+1 > width)
             				c = 0;
            			map[r][c] = neighbor;	
                 			
             		}
         	try {
            	//SW warning
             		map[row+1][col-1] = neighbor;
             		} catch (ArrayIndexOutOfBoundsException e) {
             			int r = row+1;
             			int c = col-1;
             			
             			if (row+1 > height)
             				r = 0;
             			if (col-1 < 0)
             				c = width; 
                		map[r][c] = neighbor;	
                 			
             		}
        	try {
            	//NW warning
             		map[row-1][col-1] = neighbor;
             		} catch (ArrayIndexOutOfBoundsException e) {
             			int r = row-1;
             			int c = col-1;
             			if (row-1 < 0)
                    		r = height; 
             			if (col-1 < 0)
             				c = width; 
                			map[r][c] = neighbor;	
                 			
             		}
    	} // end wumpus specific neighbors
    		
     	
     	
     	//Do just one neighbor north and south for slime pit
    	try {
    	//1 west of warning
    		if (symbol == 'p' && (map[row][col-1] == 'b')) {
    			map[row][col-1] = 'g';
    		} 
    		else {
    			map[row][col-1] = neighbor;
    		}
    	} catch (ArrayIndexOutOfBoundsException e) {
    		
    		if (symbol == 'p' && map[row][width] == 'b') {
    			map[row][width] = 'g';
    		} else {	
    		map[row][width] = neighbor;
    		}
    	}
    	
    	
    	try {
       
    		if (symbol == 'p' && map[row][col+1] == 'b') {
    			map[row][col+1] = 'g';
    		} else {
    			map[row][col+1] = neighbor;
    		}
        	} catch (ArrayIndexOutOfBoundsException e) {
 
        		if (symbol == 'p' && map[row][0] == 'b') {
        			map[row][0] = 'g';
        		} else {	
        		map[row][0] = neighbor;
        	}
        	}
    	
    	try {
    	
        	//1 north of warning
    		if (symbol == 'p' && map[row-1][col] == 'b') {
    			map[row-1][col] = 'g';
    		} else {
   
        	map[row-1][col] = neighbor;
    		}
        	} catch (ArrayIndexOutOfBoundsException e) {
        		
        		if (symbol == 'p' && map[height][col] == 'b') {
        			map[height][col] = 'g';
        		} else {
       	
        		map[height][col] = neighbor;	
        	}
        	}
    		
        	
     	try {
        	//1 south of warning
     		if (symbol == 'p' && map[row+1][col] == 'b') {
     			map[row+1][col] = 'g';
    		} 
     		else {
    	
        	map[row+1][col] = neighbor;
    		}
        	} catch (ArrayIndexOutOfBoundsException e) {
        		
        		if (symbol == 'p' && map[0][col] == 'b') {
         			map[0][col] = 'g';
        		} else {
        		
        		map[0][col] = neighbor;
        		}
        		
        	}
    

    	
    }
	
    //place wumpus on map and return the point of wumpus location
	public Point placeWumpus() {
		
        int row = ran.nextInt(12);
        int col = ran.nextInt(12);
        
        map[row][col] = 'w';
        
        wumpus = new Point (row, col);
        
        addNeighborWarnings('w', row, col);
       
        
		return wumpus;
 
	}
	
	//place pits
	public void placePits() {
		
		//get random number between 3 and 5 for number of pits on map
		int Low = 3;
		int High = 5;
		int numPits = ran.nextInt(High-Low) + Low;
		int row; 
		int col; 
		
		//create pits
	
		//if the pit is placed on a wumpus or blood, try new locations for pits
		for (int i = 0; i < numPits; i++) {
			row = ran.nextInt(12);
			col= ran.nextInt(12);
			Point pit = new Point(row, col);
				if (pit == wumpus || Math.abs(pit.x - wumpus.x) < 1 || Math.abs(pit.y - wumpus.y) < 1) {
					pit =  new Point (ran.nextInt(12), ran.nextInt(12));
					i--;
				} else {
					map[row][col] = 'p'; 
					
					
					addNeighborWarnings('p', row, col);
			}
		}
		}
	

//place hunter on map, but not on any warnings or wumpus/pits
	//return point of hunter
	public Point placeHunter() {
		int row;
		int col;
		
		
		row = ran.nextInt(12);
		col= ran.nextInt(12);
		
		for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if ((i == row && j == col) && (map[i][j] == 'w' || map[i][j] == 'p' || map[i][j] == 's' || map[i][j] == 'b' || map[i][j] == 'g')) {
                	row = ran.nextInt(12);
            		col= ran.nextInt(12);
                }
            }
		}
	
		hunter = new Point (row, col);
		return hunter; 
	}
	
	
	public void printMap() {
		 for (int i = 0; i < map.length; i++) {
	            for (int j = 0; j < map[i].length; j++) {
	                System.out.print(map[i][j] + " ");
	            }
	            System.out.println();
	}
	
	
}
}