/*  Assignment 5: Java Cars
 *  Create drag racer by using Java Applet
 *  Section: 16185
 *  Name: Doyoung Kim
 *  UTEID: dk24338
 */
package assignment5;

import java.applet.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class JavaCars extends Applet implements ActionListener, Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3967869949812585952L;
	final int INITIAL_X = 0;
	final double REFRESH_RATE = 1000/30;//30fps
	
	private ArrayList<Car2D> cars;
	private Button startButton;
	private Button pauseButton;
	private Button resetButton;
	private boolean buttonUpdated;
	private StopWatch stopwatch;
	private Random rand;
	private Dimension windowSize;
	private int winnerNumber;
	
	// The method that will be automatically called  when the applet is started 
    public void init() 
    {
    	this.setBackground(Color.WHITE);
    	//add 5 cars on starting line
    	cars = new ArrayList<Car2D>(5);
    	for(int i = 0; i < 5; i++){
    		cars.add(new Car2D(INITIAL_X, i*30, i + 1));
    	}
    	
    	//create 3 buttons
    	startButton = new Button("Start");
    	this.add(startButton);
    	startButton.addActionListener(this);
    	pauseButton = new Button("Pause");
    	pauseButton.setEnabled(false);
    	this.add(pauseButton);
    	pauseButton.addActionListener(this);
    	resetButton = new Button("Reset");
    	this.add(resetButton);
    	resetButton.addActionListener(this);
    	buttonUpdated = false;
    	
    	stopwatch = new StopWatch();
    	rand = new Random();
    	windowSize = getSize();
    	winnerNumber = -1;//-1 means no one
    	
        Thread th = new Thread(this);
        th.start();
    }
    
    public void start(){
    	System.out.println("start");
    }
    
    public void run(){
    	try {
            while(true) {
            	System.out.println("running");
            	//if cars are supposed to move
            	if(!startButton.isEnabled()&&pauseButton.isEnabled()){
            		//stop watch used to keep track of how long the updating takes
            		StopWatch sw = new StopWatch();
            		sw.start();
            		//move the cars by random amount
            		Car2D leadingCar = cars.get(0);
            		for(Car2D car : cars){
            			car.TranslateX(car.GetX() + rand.nextInt(4) + 1);
            			if(leadingCar.GetX() < car.GetX()){
            				leadingCar = car;
            			}
            		}
            		//check whether 1st place has crossed the finish line
                	if(leadingCar.GetX() > 310){//370 is x-coordinate of finishing line - 60 width of car
                		stopwatch.stop();
                		startButton.setEnabled(false);
                		pauseButton.setEnabled(false);
                		buttonUpdated = false;
                		winnerNumber = leadingCar.GetCarNumber();
                	}
                	repaint();
                	sw.stop();
                	//sleep to match the refresh rate
                	try{
                		Thread.sleep((int)REFRESH_RATE - sw.getElapsedTime());
                	}
                	catch(IllegalArgumentException iae){
                		System.out.println(iae.getMessage());
                	}
                	catch(InterruptedException ie){
                		System.out.println(ie.getMessage());
                	}
            	}
            	else{
            		try{
            			Thread.sleep((int)REFRESH_RATE);
            		}
            		catch(InterruptedException ie){
            			System.out.println(ie.getMessage());
            		}
            	}
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    //event handler for buttons
	public void actionPerformed(ActionEvent evt){
    	if(evt.getSource() == startButton){
    		startButton.setEnabled(false);
    		pauseButton.setEnabled(true);
    		buttonUpdated = false;
    		stopwatch.start();
    	}
    	else if(evt.getSource() == pauseButton){
    		stopwatch.stop();
    		startButton.setEnabled(true);
    		pauseButton.setEnabled(false);
    		buttonUpdated = false;
    	}
    	else if(evt.getSource() == resetButton){
    		stopwatch.stop();
    		stopwatch.reset();
    		startButton.setEnabled(true);
    		pauseButton.setEnabled(false);
    		//move cars back to starting line
    		for(Car2D car : cars){
    			car.TranslateX(INITIAL_X);
    		}
    		buttonUpdated = false;
    		winnerNumber = -1;
    		repaint();
    	}
    }
    
    public void update(Graphics g) {
    	System.out.println("update");
    	
    	//double buffer used to avoid flickering
    	Graphics offgc;
        Image offscreen = null;
        Dimension d = this.getSize();

        // create the offscreen buffer and associated Graphics
        offscreen = createImage(d.width, d.height);
        offgc = offscreen.getGraphics();
        // clear the exposed area
        offgc.setColor(getBackground());
        offgc.fillRect(0, 0, d.width, d.height);
        offgc.setColor(getForeground());
        // do normal redraw
        paint(offgc);
        // transfer offscreen to window
        g.drawImage(offscreen, 0, 0, this);
    }

    //The standard method that you have to use to paint things on screen 
    //This overrides the empty Applet method, you can't called it "display" for example.
    public void paint(Graphics g) 
    { 	
    	Dimension d = getSize();
    	//clear the screen
    	g.setColor(Color.WHITE);
    	g.fillRect(0, 0, d.width, d.height);
    	g.setColor(Color.BLACK);
    	System.out.println("Paint");
    	//redraw the button only when 
    	if(!buttonUpdated || !d.equals(windowSize)){
    		this.startButton.setLocation(0,151);
    		this.startButton.setSize(50, 30);
    		this.pauseButton.setLocation(50, 151);
    		this.pauseButton.setSize(50, 30);
    		this.resetButton.setLocation(100, 151);
    		this.resetButton.setSize(50, 30);
    		buttonUpdated = true;
    	}
    	windowSize = d;
    	Graphics2D g2 = (Graphics2D)g;
    	//draw start line
    	g2.draw(new Rectangle(60, 0, 20, 150));
    	drawRotate(g2, 65, 15, 90, "S       T       A       R       T");
    	//draw finish line
    	g2.draw(new Rectangle(370, 0, 20, 150));
    	drawRotate(g2, 376, 11, 90, "F      I      N      I      S      H");
    	//draw all of the cars
    	for(Car2D car : cars){
    		car.Draw(g);
    	}
    	//draw current time since start
    	g2.drawString(((double)stopwatch.getElapsedTime()/1000) + "sec", 151, 165);
    	g2.drawString("JavaCars by Doyoung Kim (dk24338)", 151, 176);
    	//if there's a winner display who won
    	if(winnerNumber > 0){
    		FontMetrics fm = g2.getFontMetrics();
        	Rectangle2D r = fm.getStringBounds("Number " + winnerNumber + " is a winner!", g2);
        	int x = (this.getWidth() - (int) r.getWidth()) / 2;
        	g.drawString("Number " + winnerNumber + " is a winner!", x, 200);
    	}
    	//g2.drawString(d.width + ", " + d.height, 0, 10);
    }
    
    //method used to draw strings with angle
    public static void drawRotate(Graphics2D g2d, double x, double y, int angle, String text) 
    {    
        g2d.translate((float)x,(float)y);
        g2d.rotate(Math.toRadians(angle));
        g2d.drawString(text,0,0);
        g2d.rotate(-Math.toRadians(angle));
        g2d.translate(-(float)x,-(float)y);
    }   
    
    //This method gets called when the applet is terminated 
    //That's when the user goes to another page or exits the browser. 
    public void stop() 
    { 
    	System.out.println("stop");
    } 
}
