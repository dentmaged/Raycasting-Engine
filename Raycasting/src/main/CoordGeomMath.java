package main;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class CoordGeomMath {
	
	// Returns the Point of intersection of two lines
	public static Point2D.Float getIntersection(Line2D line1, Line2D line2)  {
	    if (! line1.intersectsLine(line2) ) return null;
	      double px = line1.getX1(),
	            py = line1.getY1(),
	            rx = line1.getX2()-px,
	            ry = line1.getY2()-py;
	      double qx = line2.getX1(),
	            qy = line2.getY1(),
	            sx = line2.getX2()-qx,
	            sy = line2.getY2()-qy;

	      double det = sx*ry - sy*rx;
	      if (det == 0) {
	        return null;
	      } else {
	        double z = (sx*(qy-py)+sy*(px-qx))/det;
//	        if (z==0 ||  z==1) return null;  // intersection at end point!	        
	        return new Point2D.Float(
	          (float)(px+z*rx), (float)(py+z*ry));
	      }
	}
	
	// Returns the distance between two points
	public static float getDistance (Point2D.Float p1, Point2D.Float p2) {		
		return (float)Math.sqrt((p1.x-p2.x)*(p1.x-p2.x) + (p1.y-p2.y)*(p1.y-p2.y));
	}
	
	public static float wrapAngle(float a) {
		float angle = a % 360;
		if (angle < 0) angle += 360;
		return angle;
	}
}