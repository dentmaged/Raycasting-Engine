package main;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import static main.CoordGeomMath.*;

public class Ray {
	
	public int x, y;
	public float angle;
	
	private Line2D.Float line;
	private boolean upwards, rightbound;
	
	public Ray(int x, int y, float angle) {
		this.x = x;
		this.y = y;
		
		this.angle = angle % 360;
		if(this.angle < 0) this.angle += 360;
		
		if(this.angle < 0 || this.angle >= 360)
			System.err.println("AAAAAAA");
		
		this.upwards = (this.angle <= 180);
		this.rightbound = (this.angle <= 90 || this.angle >= 270);
		
		line = new Line2D.Float(this.x, this.y, 
				100000f * (float)Math.cos(Math.toRadians(angle)), 
				100000f * (float)Math.sin(Math.toRadians(angle)));
	}
	
	public Point2D.Float intersection(Level level) {
		
		// Horizontal Intersections First
		// Calculate Xa, Ya
		float Ya = 0, Xa = 0;
		Ya = level.gridsize;
		Ya *= (upwards ? 1 : -1);
		
		Xa = (float)(Ya / Math.tan(Math.toRadians(angle)));
		
		// Check for Point of intersection
		Point2D.Float hPoi;
		boolean wallIntersect = false;
		
		int yOffs = 0;
		if(!upwards) yOffs = -level.gridsize;
		
		Line2D gridline = new Line2D.Float(0, y + level.gridsize - (y % level.gridsize) + yOffs,
				level.gridsize * level.walls.length, y + level.gridsize - (y % level.gridsize) + yOffs);
		
		hPoi = getIntersection(gridline, line);
//		if(hPoi != null) System.out.println("hPoi = " + hPoi);
		
		// Go to next point of intersection until a wall is hit
//		System.out.println("hPoi : " + hPoi);
		if(hPoi != null)
			try {
				while (!wallIntersect){
					wallIntersect = level.walls
						[((int)(hPoi.x - (hPoi.x % level.gridsize)) / level.gridsize) -1]
						[((int)(hPoi.y) / level.gridsize) + (upwards ? -1 : -2)];
//					System.out.println(((hPoi.x - (hPoi.x % level.gridsize)) / level.gridsize)
//							+ " " + (((hPoi.y) / level.gridsize) + (upwards ? -1 : 0)));
					if(wallIntersect)
						break;
					
					hPoi.x += Xa;
					hPoi.y += Ya;
				}
			} catch(ArrayIndexOutOfBoundsException ex ) {
//				System.out.println("Out of bounds (Horizontal), hence no walls in ray's path");
			}
		
		// Vertical Intersections
		// Calculate Xa, Ya
		Xa = level.gridsize;
		Xa *= (rightbound ? 1 : -1);
		
		Ya = (float)(Xa * Math.tan(Math.toRadians(angle)));
		
		Point2D.Float vPoi;
		wallIntersect = false;
		
		int xOffs = 0;
		if(!rightbound) xOffs = -level.gridsize;
		
		gridline = new Line2D.Float(x + level.gridsize - (x % level.gridsize) + xOffs, 0,
				x + level.gridsize - (x % level.gridsize) + xOffs, level.gridsize * level.walls[0].length);
		
		vPoi = getIntersection(gridline, line);
//		if(vPoi != null) System.out.println("vPoi = " + vPoi);
		
		// Checks if the point of intersection is at a wall
//		System.out.println("vPoi : " + vPoi);
		if(vPoi != null)
			try {
				while(!wallIntersect) {
					wallIntersect = level.walls
						[(int)(vPoi.x / level.gridsize) + (rightbound ? -1 : -2)]
						[((int)(vPoi.y - (vPoi.y % level.gridsize))) / level.gridsize - 1];
					if(wallIntersect)
						break;
					
					vPoi.x += Xa;
					vPoi.y += Ya;
				}
			} catch(ArrayIndexOutOfBoundsException ex) {
//				System.out.println("Out of bounds (Vertical), hence no walls in ray's path" + vPoi);
			}
		
//		System.out.println("vPoi : " + (vPoi != null));
//		System.out.println("hPoi : " + (hPoi != null));
		
		if(vPoi != null && hPoi == null) {
//			System.out.println("RETURNING vPoi");
			return vPoi;
		}
		
		else if(vPoi == null && hPoi != null) {
//			System.out.println("RETURNING hPoi");
			return hPoi;
		}
		
		if(vPoi != null && hPoi != null)
			return getDistance(vPoi, new Point2D.Float(x, y)) < getDistance(hPoi, new Point2D.Float(x, y))
					? vPoi : hPoi;
		else
			return null;
	}
}