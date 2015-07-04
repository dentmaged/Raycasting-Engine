package main;

import gui.ProjectionPlane;

public class Player {
	
	public Level level;
	public ProjectionPlane projectionPlane;
	public GameCanvas canvas;
	
	public int height;
	public int fov;
	
	public int x, y;
	public float angle;
	
	public final float topSpeed = 15f;
	public float xspeed = 0f, yspeed = 0f;
	public float acc = 4f;
	public float dec = acc * 1.5f;
	
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
		float dx, dy;
		// Moving Forward
		dx = (float)(Math.cos(Math.toRadians(angle)) * forward);
		dy = (float)(Math.sin(Math.toRadians(angle)) * forward);
		// Add Strafing
		dx += (float)(Math.cos(Math.toRadians(angle + 90)) * strafe);
		dy += (float)(Math.sin(Math.toRadians(angle + 90)) * strafe);
		
		this.x += (int)(dx * topSpeed);
		this.y += (int)(dy * topSpeed);
	}
	
	public void accelerateRelative(float forward, float strafe) {
		System.out.println(Game.gameCanvas.a_down);
		
		float dax, day;
		// Moving Forward
		dax = (float)(Math.cos(Math.toRadians(angle)) * forward);
		day = (float)(Math.sin(Math.toRadians(angle)) * forward);
		// Add Strafing
		dax += (float)(Math.cos(Math.toRadians(angle + 90)) * strafe);
		day += (float)(Math.sin(Math.toRadians(angle + 90)) * strafe);
		
		dax *= acc; 
		day *= acc;
		
		xspeed += dax;
		yspeed += day;
		
		float speed = (float)Math.sqrt(xspeed * xspeed + yspeed * yspeed);
		
		if(speed > Math.abs(topSpeed)) {
			
			float ang = 0;
			if(canvas.d_down && !canvas.a_down) {
				if(canvas.w_down && !canvas.s_down) 
					ang = 45;
				else if(canvas.s_down && !canvas.w_down)
					ang = 135;
				else
					ang = 90;					
			}				
			else if(canvas.a_down && !canvas.d_down) {
				if(canvas.w_down && !canvas.s_down) 
					ang = 315;
				else if(canvas.s_down && !canvas.w_down)
					ang = 225;
				else
					ang = 270;
			}
			else if(canvas.w_down && !canvas.s_down)
				ang = 0;
			else if(canvas.s_down && !canvas.w_down)
				ang = 180;
			
			xspeed = topSpeed * (float)Math.cos(Math.toRadians(angle + ang));
			yspeed = topSpeed * (float)Math.sin(Math.toRadians(angle + ang));
		}
	}
	
	public void decelerateRelative(float forward, float strafe) {
		if(forward > 0) {
			System.out.println("DECS");
			xspeed -= dec;
			if(xspeed < 0) xspeed = 0;
		}
		if(forward < 0) {
			xspeed += dec;
			if(xspeed > 0) xspeed = 0;
		}
		if(strafe > 0) {
			yspeed -= dec;
			if(yspeed < 0) yspeed = 0;
		}
		if(strafe < 0) {
			yspeed += dec;
			if(yspeed > 0) yspeed = 0;
		}		
	}
	
	public void decelerate() {
		boolean xp = xspeed > 0, yp = yspeed > 0;
		
		xspeed -= Math.signum(xspeed) * dec;
		yspeed -= Math.signum(yspeed) * dec;
		
		if(xp && xspeed < 0) xspeed = 0;
		else if (!xp && xspeed > 0) xspeed = 0;
		
		if(yp && yspeed < 0) yspeed = 0;
		else if (!yp && yspeed > 0) yspeed = 0;			
	}
	
	public void upadatePosition() {
		x += xspeed;
		y += yspeed;
		System.out.println(xspeed);
	}
}