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
import jnr.ffi.LibraryLoader;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinUser.INPUT;
import com.sun.jna.ptr.IntByReference;
import java.awt.SystemColor;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

public class Fire {
	private static Timer u;
	private static Timer h;
	private static JFrame frame;
	private static boolean isOn;
	static int fps = (int) Math.floor(1000/(9)); 
	static File f;
	int screenWidth;
	int screenHeight;
	private static JPanel panel;
	private static JLabel lblNewLabel;
	private static int shot; // tracks shots
	private static String str2 = "C:\\Users\\justi\\eclipse-workspace\\JustinProgram\\image\\Dot crosshair.PNG";
	private static String str1 = "C:\\Users\\justi\\eclipse-workspace\\JustinProgram\\image\\Dot crosshair 2.PNG";
	private static Icon icon1;
	private static Icon icon2;
	private static int yPos, xPos;

	static interface User32 extends Library {
	    @SuppressWarnings("deprecation")
		public static User32 INSTANCE = (User32) Native.loadLibrary("User32", User32.class); 
	    
	    short GetAsyncKeyState(int key);
	    short GetKeyState(int key);

	    IntByReference GetKeyboardLayout(int dwLayout);
	    int MapVirtualKeyExW (int uCode, int nMapType, IntByReference dwhkl);

	    boolean GetKeyboardState(byte[] lpKeyState);

	    int ToUnicodeEx(int wVirtKey, int wScanCode, byte[] lpKeyState, char[] pwszBuff, int cchBuff, int wFlags, IntByReference dwhkl);
	 
        int SendInput(DWORD dword, INPUT[] pInputs, int cbSize);

	}


	private static void getKeyType() throws LineUnavailableException, IOException, UnsupportedAudioFileException {

	
		// checks if mouse1 is getting clicked
	    if((User32.INSTANCE.GetKeyState(MouseEvent.BUTTON1) & 0x80) == 0x80) {
	    	// starts up timer
	    	
	    	u.setDelay(fps);
	    	isOn=true;
	    	
	    	
	    } else {
	    	// stops up timer
//	    	xPos = (2560/2)-10;
//	    	yPos = (1440/2)-10;
//	    	panel.setBounds(-3000, -3000, 2560, 1440);
//	    	isOn=false;
//
//	    	u.setDelay(0);
	    }

	}
	
	public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
//		run();
		icon1 = new ImageIcon(str1);
		icon2 = new ImageIcon(str2);
//		f = new File("C:\\Users\\justi\\eclipse-workspace\\JustinProgram\\sounds\\ABshot.wav");
		
		
//		AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL()); 
		u = new Timer(0,(ActionEvent e)->{
			
			if(!(yPos < 645) && isOn) {
//				panel.setBounds(xPos, yPos-=8, 20, 20);	
			}

			/*
			try {
				playSound();
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			*/
			
		}); 
		
		
		
		int direction = 1;
		h = new Timer(0,(ActionEvent e)->{
			// first turn right
//			if (panel.getX() < pixels to right)
			// dir = 1
			// else
			// dir = -1
			// panel.getX()+5;
			
			h.setDelay(fps);
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
		
		yPos = (1440/2)-10;
		xPos = (2560/2)-10;
		// this is for crosshair panel
		panel = new JPanel();
		panel.setBackground(new Color(0,0,0));
		panel.setBounds(xPos, yPos, 20, 20);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		// niko v3 crosshair - good
		

		
		
		frame.getContentPane().setLayout(null);
		lblNewLabel = new JLabel(icon1);
		lblNewLabel.setBounds(0, 0, 20, 20);
	
		panel.add(lblNewLabel);
		
		
		
		frame.setVisible(true);
	
		u.start();
	    while (true) {
	    	
	        getKeyType();
	    }

	}

	/*
	public static void playSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
	    
	    AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());  
	    Clip clip = AudioSystem.getClip();
	    clip.open(audioIn);
	    clip.start();
	}
	*/
	//70 first
}
