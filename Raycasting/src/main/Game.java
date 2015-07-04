package main;

import gui.Bitmap;

import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;

public class Game extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 600, HEIGHT = 600;
	public static final int SCALE = 1;
	public static Bitmap wallTexture;
	
	public static Level currentLevel;
	
	public static GameCanvas gameCanvas;
	
	private static boolean windowClosing = false;
	
	public static void Start() {
		try {
			wallTexture = new Bitmap("res/wall.jpg");
		} catch (IOException ex) {
			System.err.println("IOException when loading texture");
		}
		
		currentLevel = new Level(20, 20, 95);
		currentLevel.player = new Player(currentLevel, 1000, 1000);
		
		JFrame frame = new JFrame();
		frame.setSize(WIDTH * SCALE, HEIGHT * SCALE);
		frame.setTitle("Raycasting");
		
		frame.setLayout(new GridLayout());
		
		gameCanvas = new GameCanvas();
		currentLevel.player.canvas = gameCanvas;
		frame.add(gameCanvas);
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);		
		frame.addWindowListener(new WindowListener() {			
			public void windowClosing(WindowEvent e) {
				windowClosing = true;
			}
			
			public void windowDeactivated(WindowEvent e) {
				// Depress all keys when window loses focus
				for(int i = 0; i < 256; i++) {
					gameCanvas.key_state_up[i] = true;
					gameCanvas.key_state_down[i] = false;
				}
			}
			
			public void windowOpened(WindowEvent e) {}	
			public void windowClosed(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
		});
		
		while(!windowClosing) {
			gameCanvas.tick();
			gameCanvas.render();
		}
		
		frame.dispose();
	}
}