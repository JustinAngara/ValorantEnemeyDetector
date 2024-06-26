package com.chms.main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.JLabel;
import javax.swing.JSlider;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JInternalFrame;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
	


public class DetectObject implements NativeKeyListener {
	private Timer t;
	private JFrame frame;
	private Color enemyBoxC;
	private Color playerBoxC; 
	private static Random random;
	private static JLabel lblNewLabel;
	private static List<Cluster> clusters = new ArrayList<>();
	private static int tolerance, maxIterations;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */
	public DetectObject() {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		
		lblNewLabel = new JLabel("TESTING");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(0, 0, 2560, 1440);
		frame.getContentPane().add(lblNewLabel);
		
		tolerance = 47;

		maxIterations = 15;
		
		JLabel toleranceLabel = new JLabel("Tolerance: "+tolerance);
		toleranceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		toleranceLabel.setBounds(0, 605, 200, 21);
		frame.getContentPane().add(toleranceLabel);
		
		JSlider toleranceSlider = new JSlider(0, 60, 35);

        // set spacing

		toleranceSlider.setMajorTickSpacing(50);
		toleranceSlider.setMinorTickSpacing(5);
        
		toleranceSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				tolerance = toleranceSlider.getValue();
				toleranceLabel.setText("Tolerance: "+tolerance);
				
			}
		});
		toleranceSlider.setBounds(0, 632, 200, 26);
		frame.getContentPane().add(toleranceSlider);
		
		

		JLabel lblMaxIterations = new JLabel("Max Iterations: "+maxIterations);
		lblMaxIterations.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaxIterations.setBounds(232, 605, 200, 21);
		frame.getContentPane().add(lblMaxIterations);
		
		JSlider maxIterationSlider = new JSlider(0, 35, 15);
		maxIterationSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				maxIterations = maxIterationSlider.getValue();
				lblMaxIterations.setText("Max Iterations: "+maxIterationSlider.getValue());
			}
		});
		maxIterationSlider.setMinorTickSpacing(5);
		maxIterationSlider.setMajorTickSpacing(50);
		maxIterationSlider.setBounds(232, 632, 200, 26);
		frame.getContentPane().add(maxIterationSlider);
		

		
		
		
		
		random = new Random();
		// look at red, and green. +-2 variation, 230 and 40 for outline
		enemyBoxC = new Color(240,240,120);
		
		playerBoxC = new Color(255,230,64);
		
		// fps at 75;
		int fps = (int) Math.floor(1000/75); 
		t = new Timer(fps,(ActionEvent e)->{
			handleScreen(createScreenCapture(), enemyBoxC);
		}); 
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	public static boolean isEnemyPixel() {
		
		return false;
	}
	public static void handleScreen(BufferedImage screenshot, Color c) {
		// return [x,y]
		int red,green,blue;
		int offset = 1;
		int targetRed = c.getRed();
		int targetGreen = c.getGreen();
		int targetBlue = c.getBlue();
		
		System.out.println(c);
		
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

//		        	floodFill(screenshot, x, y, targetRed, targetGreen, targetBlue, maxIterations);
		        	
//		        	// Calculate valid offsets based on image dimensions

/*
		        	int minXOffset = Math.max(-offset, -x); // Don't go below 0 (left edge)
		            int maxXOffset = Math.min(offset, screenshot.getWidth() - x - 1); // Don't go above width-1 (right edge)
		            int minYOffset = Math.max(-offset, -y); // Don't go below 0 (top edge)
		            int maxYOffset = Math.min(offset, screenshot.getHeight() - y - 1); // Don't go above height-1 (bottom edge)

		            // Loop through valid offsets and set pixels
		            for (int offsetX = minXOffset; offsetX <= maxXOffset; offsetX++) {
		              for (int offsetY = minYOffset; offsetY <= maxYOffset; offsetY++) {
		                screenshot.setRGB(x + offsetX, y + offsetY, 0x008000);
		              }
		            }
*/
		            System.out.println(screenshot.getHeight());
		            System.out.println("Y VAlUE AT DETECTION: "+y);
		        }
		    }
		}
		
		lblNewLabel.setIcon(new ImageIcon(screenshot));
		
	}
	// Generate a random integer representing a color (RGB)
	private static int generateRandomColor() {
	  
		
	  int green = random.nextInt(256);
	  
	  return 0 | (green << 8) | 0;
	}
	// Flood fill function (recursive)
	private static void floodFill(BufferedImage image, int x, int y, int targetR, int targetG, int targetB, int iterations) {
	  if (iterations <= 0 || x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight()) {
	    return; // Base case: out of bounds or max iterations reached
	  }

	  int pixel = image.getRGB(x, y);
	  int red = (pixel >> 16) & 0xFF;
	  int green = (pixel >> 8) & 0xFF;
	  int blue = pixel & 0xFF;

	  // Check if pixel is within tolerance and not already marked
	  if (Math.abs(red - targetR) <= tolerance && Math.abs(green - targetG) <= tolerance &&
	      Math.abs(blue - targetB) <= tolerance && image.getRGB(x, y) != 0x008000) {
		  clusters.add(new Cluster(x, y)); 
		  
		  image.setRGB(x, y, generateRandomColor()); // Mark the pixel (red in this case)

	    // Recursively fill adjacent pixels (limited iterations)
	    
	    floodFill(image, x + 1, y, targetR, targetG, targetB, iterations - 1);
	    floodFill(image, x - 1, y, targetR, targetG, targetB, iterations - 1);
	    floodFill(image, x, y + 1, targetR, targetG, targetB, iterations - 1);
	    floodFill(image, x, y - 1, targetR, targetG, targetB, iterations - 1);

	    floodFill(image, x + 1, y + 1, targetR, targetG, targetB, iterations - 1);
	    floodFill(image, x - 1, y + 1, targetR, targetG, targetB, iterations - 1);
	    floodFill(image, x + 1, y - 1, targetR, targetG, targetB, iterations - 1);
	    floodFill(image, x - 1, y - 1, targetR, targetG, targetB, iterations - 1);
	  }
	}
	
	
    // Method to capture the screen
    public static BufferedImage createScreenCapture() {
        try {
            Robot robot = new Robot();
            // Toolkit.getDefaultToolkit().getScreenSize()
            Rectangle screenRect = new Rectangle(0, 407, 2560, 777-407);
            return robot.createScreenCapture(screenRect);
        } catch (AWTException e) {
            e.printStackTrace();
            return null;
        }
    }
    
	
	
	private void initialize() {
		frame.setBounds(3043, 953, 1600, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		runNativeHook();
		
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode()==NativeKeyEvent.VC_G) {
			
			if(t.isRunning()) {
				t.stop();
			} else {
				t.start();
			}
			
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	public void runNativeHook() {
		
		GlobalScreen.addNativeKeyListener(this);
		LogManager.getLogManager().reset();

		// Get the logger for "org.jnativehook" and set the level to off.
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {}
	}
}
