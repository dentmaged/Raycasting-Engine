package main;

import gui.ProjectionPlane;

public class Player {
	
	public Level level;
	public ProjectionPlane projectionPlane;
	
	public int height;
	public int fov;
	
	public int x, y;
	public float angle;
	
	public Player(Level level, int x, int y) {
		this.level = level;
		this.x = x;
		this.y = y;
		
		height = 32;
		angle = 0.0f;
		fov = 60;
		
		projectionPlane = new ProjectionPlane(Game.WIDTH, Game.HEIGHT, 
				(int)((320/2) / Math.tan(Math.toRadians(fov/2))));
	}
	
	public void rotate(int angle) {
		this.angle += angle;
		this.angle %= 360;
		if(this.angle < 0) this.angle += 360;
	}
	
	public void moveRelative(int forward, int strafe) {
		int dx, dy;
		// Moving Forward
		dx = (int)(Math.cos(Math.toRadians(angle)) * forward);
		dy = (int)(Math.sin(Math.toRadians(angle)) * forward);
		// Add Strafing
		dx += (int)(Math.cos(Math.toRadians(angle + 90)) * strafe);
		dy += (int)(Math.sin(Math.toRadians(angle + 90)) * strafe);
		
		this.x += dx;
		this.y += dy;
	}
}