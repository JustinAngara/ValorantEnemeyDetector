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



public class Fire {
	private static Timer t;
	private static JFrame frame;
	static File f;
	int screenWidth;
	int screenHeight;

	static boolean isButtonDown;

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
	    	t.start();
	    	
	    } else {
	   
	    	t.stop();
	    }
	    return isButtonDown;
	}
	public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
//		run();
		f = new File("C:\\Users\\justi\\eclipse-workspace\\JustinProgram\\sounds\\ak.wav");
		int fps = (int) Math.floor(1000/8.75); 
		t = new Timer(0,(ActionEvent e)->{
			
			System.out.println("NOW");
			try {
				playSound();
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			t.setDelay(fps);
		}); 
//		frame = new JFrame();
//		frame.setBounds(0, 0, 2560, 1440);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.getContentPane().setLayout(new GridBagLayout());
//		frame.getRootPane().putClientProperty("apple.awt.draggableWindowBackground", false);
//		
//		Icon icon;
//		// niko v3 crosshair - good
//		
//		icon = new ImageIcon("C:\\Users\\justi\\eclipse-workspace\\JustinProgram\\image\\simple crosshair.PNG");
//		JLabel lblNewLabel = new JLabel(icon);
//		
//		frame.getContentPane().add(lblNewLabel);
//		System.out.println(lblNewLabel.getBounds());
//		frame.setUndecorated(true);
//		frame.setAlwaysOnTop(true);
//		frame.setBackground(new Color(0, 0, 0, 0));
//		frame.setFocusable(false);
//		
//		frame.setVisible(true);
		run();
		
	}
	public static void playSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
	    
	    AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());  
	    Clip clip = AudioSystem.getClip();
	    clip.open(audioIn);
	    clip.start();
	}
	
}
