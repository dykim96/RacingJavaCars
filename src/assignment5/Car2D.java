/*  Assignment 5: Java Cars
 *  Create drag racer by using Java Applet
 *  Section: 16185
 *  Name: Doyoung Kim
 *  UTEID: dk24338
 */
package assignment5;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Polygon;
import java.awt.geom.*;

public class Car2D {
	private final Color BURNT_ORANGE = new Color(191, 87, 0);
	private int carNumber;
	private int x;
	private int y;
	
	//shapes that make up the car
	private Polygon roof;
	private Rectangle body;
	private Ellipse2D.Double frontTire;
	private Ellipse2D.Double frontWheel;
	private Ellipse2D.Double rearTire;
	private Ellipse2D.Double rearWheel;
	
	public Car2D(){
		carNumber = 0;
		x = 0;
		y = 0;
		roof = new Polygon();
    	roof.addPoint(10, 10);
    	roof.addPoint(20, 0);
    	roof.addPoint(40, 0);
    	roof.addPoint(50, 10);
		body = new Rectangle(0, 10, 60, 10);
		frontTire = new Ellipse2D.Double(40, 20, 10, 10);
		frontWheel = new Ellipse2D.Double(42, 22, 6, 6);
		rearTire = new Ellipse2D.Double(10, 20, 10, 10); 
		rearWheel = new Ellipse2D.Double(12, 22, 6, 6);
	}
	
	public Car2D(int x, int y, int carNumber){
		this.carNumber = carNumber;
		this.x = x;
		this.y = y;
		roof = new Polygon();
    	roof.addPoint(x + 10, y + 10);
    	roof.addPoint(x + 20, y + 0);
    	roof.addPoint(x + 40, y + 0);
    	roof.addPoint(x + 50, y + 10);
		body = new Rectangle(x + 0, y + 10, 60, 10);
		frontTire = new Ellipse2D.Double(x + 40, y + 20, 10, 10);
		frontWheel = new Ellipse2D.Double(x + 42, y + 22, 6, 6);
		rearTire = new Ellipse2D.Double(x + 10, y + 20, 10, 10); 
		rearWheel = new Ellipse2D.Double(x + 12, y + 22, 6, 6);
	}
	
	//Draw Car
	public void Draw(Graphics grph){
		Graphics2D g = (Graphics2D)grph;
		Color temp = g.getColor();
		// draw all of the car parts on the screen
		g.setColor(Color.BLACK);
    	g.draw(roof);
    	g.draw(body);
    	g.draw(frontTire);
    	g.draw(rearTire); 
    	g.fill(frontTire);
    	g.fill(rearTire);
    	g.setColor(Color.GRAY);
    	g.fill(frontWheel);
    	g.fill(rearWheel);
    	g.setColor(BURNT_ORANGE);
    	g.fill(roof);
    	g.fill(body);
		g.setColor(Color.BLACK);
    	g.drawString("[" + carNumber + "]", x + 24, y + 16);
    	
    	g.setColor(temp);
	}
	
	//set car's number
	public void SetCarNumber(int number){
		carNumber = number;
	}
	
	//get car's number
	public int GetCarNumber(){
		return carNumber;
	}
	
	//get x-coordinate of car
	public int GetX(){
		return x;
	}
	
	//change only x-coordinate
	public void TranslateX(int newX){
		SetXY(newX - x, 0);
		x = newX;
	}
	
	//get y-coordinate of car
	public int GetY(){
		return y;
	}
	
	//change only y-coordinate
	public void TranslateY(int newY){
		SetXY(0, newY - y);
		y = newY;
	}
	
	//change both x-coordinate and y-coordinate
	public void Translate(int newX, int newY){
		SetXY(newX - x, newY - y);
		x = newX;
		y = newY;
	}
	
	//method to move individual parts
	private void SetXY(int deltaX, int deltaY){
		roof.translate(deltaX, deltaY);
		body.translate(deltaX, deltaY);
		frontTire.x += deltaX;
		frontTire.y += deltaY;
		frontWheel.x += deltaX;
		frontWheel.y += deltaY;
		rearTire.x += deltaX;
		rearTire.y += deltaY;
		rearWheel.x += deltaX;
		rearWheel.y += deltaY;
	}
}
