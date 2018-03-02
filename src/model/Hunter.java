package model;

import java.awt.Point;
import java.awt.event.KeyListener;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

//Hunter object maintains current location of hunter and symbol 'o'
public class Hunter{
	
	private char symbol; 
	private Point currentLoc; 

	
	public Hunter() {	
		currentLoc = new Point(Map.getHunter());
		symbol = 'o';
	}
	
	public char getSymbol() {
		return this.symbol;
	}
	
	public Point getHunterLoc() {
		return currentLoc;
		
	}
	
	public void setHunterLoc(Point currHunterLoc) {
		this.currentLoc = currHunterLoc; 
	}
	

}
