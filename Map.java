package com.chms.main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Map {
	
	private static JFrame frame;
	private static Timer t;
	private static Color c;
	private static int tolerance = 20;
	private static JLabel lblNewLabel;
	private static Robot robot;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 * @throws AWTException 
	 */
	public Map() throws AWTException {
		// fps at 75;
		
        robot = new Robot();
		frame = new JFrame();	
		
		lblNewLabel = new JLabel("");
		int fps = (int) Math.floor(1000/75); 
		c = new Color(200,200, 60);
		t = new Timer(fps,(ActionEvent e)->{
			System.out.println("Changing");
			handleScreen(createScreenCapture(), c);
		});
		t.start();
		
		initialize();
	}

    // Method to capture the screen
    public static BufferedImage createScreenCapture() {
    	System.out.println("icon");
 
    	// Toolkit.getDefaultToolkit().getScreenSize()
    	Rectangle screenRect = new Rectangle(0,10,470,500);
		BufferedImage bmi = robot.createScreenCapture(screenRect);

		return bmi;
       
    }
	public static void handleScreen(BufferedImage screenshot, Color c) {
		// return [x,y]
		int red,green,blue;
		int offset = 1;
		int targetRed = c.getRed();
		int targetGreen = c.getGreen();
		int targetBlue = c.getBlue();

		
		for (int y = 0; y < screenshot.getHeight(); y+=2) {
		    for (int x = 0; x < screenshot.getWidth(); x+=2) {
		        int pixel = screenshot.getRGB(x, y);

		        red = (pixel >> 16) & 0xFF;
		        green = (pixel >> 8) & 0xFF;
		        blue = pixel & 0xFF;

		        // Check if pixel values are within tolerance of target color

		        if (Math.abs(red - targetRed) <= tolerance &&
		            Math.abs(green - targetGreen) <= tolerance &&
		            Math.abs(blue - targetBlue) <= tolerance) {
		        	// Bard, finish your code here

		        }
		    }
		}
		lblNewLabel.setIcon(new ImageIcon(screenshot));
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		frame.setBounds(-1150,200,470,490);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(null);
		
		frame.setUndecorated(true);
		frame.setAlwaysOnTop(true);
		frame.setBackground(new Color(0, 0, 0, 0));
		frame.setFocusable(false);
		
		lblNewLabel.setBounds(0, 0, 470, 490);
		frame.getContentPane().add(lblNewLabel);
		frame.setVisible(true);
	
	}
}



/*
	enemy colors: 
	188 189 98
	160 160 50
	190 190 60
	180 180 100
	200 200 60 // border
* */