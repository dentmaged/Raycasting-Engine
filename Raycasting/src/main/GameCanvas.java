package main;

import gui.Bitmap;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GameCanvas extends Canvas implements KeyListener{
	
	private static final long serialVersionUID = 1L;
	
	/* Keycode Indices
	 * Letter | Key | Key + Shift | Key + Ctrl | Key + Shift + Ctrl
	 * W | 46 | 110 | 174 | 238
	 * A | 63 | 127 | 191 | 255
	 * S | 62 | 126 | 190 | 254
	 * D | 58 | 122 | 186 | 250
	 * Q | 47 | 111 | 175 | 239
	 * E | 42 | 106 | 170 | 234
	 */
	public boolean[] key_state_up = new boolean[256];
	public boolean[] key_state_down = new boolean[256];
	public boolean w_down = false, a_down = false, s_down = false, d_down = false;
	
	private Bitmap bmp;
	private BufferedImage img;

	public GameCanvas() {
		bmp = Game.currentLevel.player.projectionPlane;
		img = new BufferedImage(bmp.width, bmp.height, BufferedImage.TYPE_INT_BGR);
		this.addKeyListener(this);
		
		// Set all the keys to be 'up'
		for(int i = 0; i < 256; i++) {
			key_state_up[i] = true;
			key_state_down[i] = false;
		}
	}
	
	public void tick() {
		w_down = key_state_down[87]; a_down = key_state_down[65];
		s_down = key_state_down[83]; d_down = key_state_down[68];
		
		// Acceleration is breaking my balls, i'll get around
		// to doing it later
//		if(w_down) // W 
//			Game.currentLevel.player.accelerateRelative(1, 0);
//		else
//			Game.currentLevel.player.decelerateRelative(1, 0);
//		
//		if(a_down) // A
//			Game.currentLevel.player.accelerateRelative(0, -1);
//		else
//			Game.currentLevel.player.decelerateRelative(0, -1);
//		
//		if(s_down) // S
//			Game.currentLevel.player.accelerateRelative(-1, 0);
//		else
//			Game.currentLevel.player.decelerateRelative(-1, 0);
//		
//		if(d_down) // D
//			Game.currentLevel.player.accelerateRelative(0, 1);
//		else
//			Game.currentLevel.player.decelerateRelative(0, 1);
		
		if(w_down)
			Game.currentLevel.player.moveRelative(1, 0);
		if(a_down)
			Game.currentLevel.player.moveRelative(0, -1);
		if(s_down)
			Game.currentLevel.player.moveRelative(-1, 0);
		if(d_down)
			Game.currentLevel.player.moveRelative(0, 1);
		
		
		if(key_state_down[81]) // Q
			Game.currentLevel.player.rotate(-5);
		if(key_state_down[69]) // E
			Game.currentLevel.player.rotate(5);
		
		Game.currentLevel.player.upadatePosition();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		bmp.updatePixels();
		bmp.setBufferedImageRGB(img);
		
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(img, 0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE, null);
		g.dispose();
		bs.show();
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
		int code = e.getKeyCode();
		//if(0 < code && code <= 255) {
			key_state_down[code] = true;
			key_state_up[code] = false;			
		//}
	}
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		//if(0 < code && code <= 255) {
			key_state_up[code] = true;
			key_state_down[code] = false;
		//}
	}
	
	public void keyTyped(KeyEvent e) {}	
}