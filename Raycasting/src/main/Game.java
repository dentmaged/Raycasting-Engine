package main;

import gui.Bitmap;

import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JFrame;

public class Game extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 600, HEIGHT = 600;
	public static final int SCALE = 1;
	public static Bitmap wallTexture;
	
	public static Level currentLevel;
	
	public static GameCanvas gameCanvas;
	
	public static void Start() {
		try {
			wallTexture = new Bitmap("res/wall.jpg");
		} catch (IOException ex) {
			System.err.println("IOException when loading texture");
		}
		
		currentLevel = new Level(20, 20, 96);
		currentLevel.player = new Player(currentLevel, 1000, 400);
		
		JFrame frame = new JFrame();
		frame.setSize(WIDTH * SCALE, HEIGHT * SCALE);
		frame.setTitle("Raycasting");
		
		frame.setLayout(new GridLayout());
		
		gameCanvas = new GameCanvas();
		frame.add(gameCanvas);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}