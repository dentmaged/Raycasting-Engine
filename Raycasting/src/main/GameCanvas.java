package main;

import gui.Bitmap;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GameCanvas extends Canvas implements KeyListener{
	
	private static final long serialVersionUID = 1L;
	
	private Bitmap bmp;
	private BufferedImage img;

	public GameCanvas() {
		bmp = Game.currentLevel.player.projectionPlane;
		img = new BufferedImage(bmp.width, bmp.height, BufferedImage.TYPE_INT_BGR);
		this.addKeyListener(this);
	}
	
	public void paint(Graphics g) {
		bmp.updatePixels();
		bmp.setBufferedImageRGB(img);
		
		g.drawImage(img, 0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE, null);
		
		StringBuilder debugText = new StringBuilder();
		debugText.append(String.format("Player Pos : %s,%s", 
				Game.currentLevel.player.x, Game.currentLevel.player.y))
			.append(String.format("\nPlayer Rot : %s", Game.currentLevel.player.angle));
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(String.valueOf(e.getKeyChar()).toUpperCase()) {
			
		case "Q":
			Game.currentLevel.player.rotate(-5);
			repaint();
			break;
			
		case "E":
			Game.currentLevel.player.rotate(5);
			repaint();
			break;
		
		case "W":
			Game.currentLevel.player.moveRelative(5, 0);
			repaint();
			break;
		
		case "S":
			Game.currentLevel.player.moveRelative(-5, 0);
			repaint();
			break;
		
		case "A":
			Game.currentLevel.player.moveRelative(0, -5);
			repaint();
			break;
			
		case "D":
			Game.currentLevel.player.moveRelative(0, 5);
			repaint();
			break;
		
		case "Z":
			Game.currentLevel.player.rotate(-2);
			Game.currentLevel.player.moveRelative(0, 4);
			repaint();
			break;
		}		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	
}