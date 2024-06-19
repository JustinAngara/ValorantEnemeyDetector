package com.chms.main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import javax.swing.border.LineBorder;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import java.awt.SystemColor;



public class Fire {
	private static Timer u;
	
	private static JFrame frame;
	static File f;
	int screenWidth;
	int screenHeight;
	private static JPanel panel;
	private static boolean isButtonDown;
	private static JLabel lblNewLabel;
	private static int shot; // tracks shots

	static interface User32 extends Library {
	    @SuppressWarnings("deprecation")
		public static User32 INSTANCE = (User32) Native.loadLibrary("User32", User32.class); 
	    
	    short GetAsyncKeyState(int key);
	    short GetKeyState(int key);

	    IntByReference GetKeyboardLayout(int dwLayout);
	    int MapVirtualKeyExW (int uCode, int nMapType, IntByReference dwhkl);

	    boolean GetKeyboardState(byte[] lpKeyState);

	    int ToUnicodeEx(int wVirtKey, int wScanCode, byte[] lpKeyState, char[] pwszBuff, int cchBuff, int wFlags, IntByReference dwhkl);

	}

	public static void run() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
	    long currTime = System.currentTimeMillis();

	    while (System.currentTimeMillis() < currTime + 2000000)
	    {
	        for (int key = 1; key < 256; key++)
	            {
	                if (isKeyPressed(key)) {
	                    getKeyType(key);
	                    
	                }
	                if(!isKeyPressed(key)) {
	                	getKeyType(key);
	                	
	                }
	            }
	    }
	}
	
	private static boolean isKeyPressed(int key) {
	    return User32.INSTANCE.GetAsyncKeyState(key) == -32767;
	}



	private static boolean getKeyType(int key) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		int keyCode1=MouseEvent.BUTTON1;
		isButtonDown = (User32.INSTANCE.GetKeyState(keyCode1) & 0x80) == 0x80;
	    byte[] keystate = new byte[256];
	    User32.INSTANCE.GetKeyboardState(keystate); 
	    if(isButtonDown) {
	    	// starts up timer
	    	lblNewLabel.setVisible(true);
	    	u.start();
	    	
	    } else {
	    	// stops up timer
	    	shot=0;
	    	panel.setBounds((2560/2)-10, (1440/2)-10, 20, 20);
	    	lblNewLabel.setVisible(false);
	    	u.stop();
	    }
	    return isButtonDown;
	}
	public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
//		run();
		
		f = new File("C:\\Users\\justi\\eclipse-workspace\\JustinProgram\\sounds\\ABshot.wav");
		int fps = (int) Math.floor(1000/(2*9.75)); 
		
//		AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL()); 
		u = new Timer(0,(ActionEvent e)->{
			shot++;
		
			System.out.println(panel.getY());
			if(!(panel.getY() < 645)) {
				panel.setBounds(panel.getX(), panel.getY()-5, panel.getHeight(), panel.getWidth());	
			} else {
				// start the left and right timer
			}
			
			/*
			try {
				playSound();
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			*/
			u.setDelay(fps);
		}); 
		frame = new JFrame();
		frame.setBounds(0, 0, 2560, 1440);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getRootPane().putClientProperty("apple.awt.draggableWindowBackground", false);
		
		frame.setUndecorated(true);
		frame.setAlwaysOnTop(true);
		frame.setBackground(new Color(0, 0, 0, 0));
		frame.setFocusable(false);
		frame.getContentPane().setLayout(null);
		
		
		// this is for crosshair panel
		panel = new JPanel();
		panel.setBackground(new Color(0,0,0,0));
		panel.setBounds((2560/2)-10, (1440/2)-10, 20, 20);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		Icon icon;
		// niko v3 crosshair - good
		
		icon = new ImageIcon("C:\\Users\\justi\\eclipse-workspace\\JustinProgram\\image\\Custom Crosshairt.PNG");
		frame.getContentPane().setLayout(null);
		lblNewLabel = new JLabel(icon);
		lblNewLabel.setBounds(0, 0, 20, 20);
	
		panel.add(lblNewLabel);
		
		
		
		frame.setVisible(true);
		run();
		
	}
	public static void playSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
	    
	    AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());  
	    Clip clip = AudioSystem.getClip();
	    clip.open(audioIn);
	    clip.start();
	}
	//70 first
}
