package assignment5;

import java.applet.Applet;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.*;
import java.awt.event.*;

public class JavaCars extends Applet {
	ArrayList<ArrayList<Integer>> xCoordinates;
	ArrayList<Double> timeRecords;
	final int INITIAL_X = 0;
	// The method that will be automatically called  when the applet is started 
    public void init() 
    { 
    	xCoordinates = new ArrayList<ArrayList<Integer>>(5);//initialize arraylist with capacity of 5
    	//add 5 integer arraylists to xCoordinatesto represent 5 cars
    	for(int i = 0; i < 5; i++){
    		ArrayList<Integer> xCoordinate = new ArrayList<Integer>();
    		xCoordinate.add(INITIAL_X);
    		xCoordinates.add(xCoordinate);
    	}
    	timeRecords = new ArrayList<Double>();
    } 


    //This method gets called when the applet is terminated 
    //That's when the user goes to another page or exits the browser. 
    public void stop() 
    { 
    	// no actions needed here now. 
    } 


    //The standard method that you have to use to paint things on screen 
    //This overrides the empty Applet method, you can't called it "display" for example.

    public void paint(Graphics g) 
    { 
    	Graphics2D g2 = (Graphics2D)g; // create the car body 
    	Rectangle body = new Rectangle(100, 110, 60, 10); // create the car tires 
    	Ellipse2D.Double frontTire = new Ellipse2D.Double(110, 120, 10, 10); 
    	Ellipse2D.Double rearTire = new Ellipse2D.Double(140, 120, 10, 10);
    	
    	// create the 4 points connecting the windshields and roof 
    	Point2D.Double r1 = new Point2D.Double(110, 110); 
    	Point2D.Double r2 = new Point2D.Double(120, 100); 
    	Point2D.Double r3 = new Point2D.Double(140, 100); 
    	Point2D.Double r4 = new Point2D.Double(150, 110); 
    	
    	// create the windshields and roof of the car 
    	Line2D.Double frontWindshield = new Line2D.Double(r1, r2); 
    	Line2D.Double roofTop = new Line2D.Double(r2, r3); 
    	Line2D.Double rearWindshield = new Line2D.Double(r3, r4); 
    	
    	// draw all of the car parts on the screen 
    	g2.draw(body); 
    	g2.draw(frontTire); 
    	g2.draw(rearTire); 
    	g2.draw(frontWindshield); 
    	g2.draw(roofTop); 
    	g2.draw(rearWindshield); 
    	// draw the label under the car g2.drawString("UT JavaMobile 1.0", 100, 150);
    }
}
