package gui;

import java.awt.geom.Point2D;

import main.Game;
import main.Player;
import main.Ray;
import static main.CoordGeomMath.*;

public class ProjectionPlane extends Bitmap {
	
	private Bitmap wallTex;
	private int playerDist;
	
	public ProjectionPlane(int width, int height, int playerDist) {
		super(width, height);
		this.playerDist = playerDist;
		wallTex = Game.wallTexture;
	}
	
	public void updatePixels() {
		Player player = Game.currentLevel.player;
		
		for(float n = -width / 2; n < width / 2; n += 0.25f) {
			
			float B = (float)(n * (float)player.fov/(float)player.projectionPlane.width);
			Ray ray = new Ray(player.x, player.y, 
					(int)player.angle + B);
			
			Point2D.Float poi = ray.intersection(player.level);
			
			if(poi == null) {
				for (int y = 0; y < height; y++)
					pixels[((int)n + width/2) * height + y] = 0x000000;
				continue;
			}
			
			float dist = (float)(getDistance(poi, new Point2D.Float(player.x, player.y)));
//			System.out.println(dist);
			
			float projectedWallHeight = 2f * (float)
					(((float)player.level.gridsize / 
					((float)dist * Math.cos(Math.toRadians(wrapAngle(B)))))
					* (float)(playerDist));
//			System.out.println(projectedWallHeight);
			
			int wallBottom = (int)(height/2 - projectedWallHeight/2);
			int wallTop = (int)(height/2 + projectedWallHeight/2);
			
			int textureOffsetX, textureOffsetY;
			int dir; // North/South = 0, East/West = 1
			
			if((int)poi.y % player.level.gridsize == 0) dir = 0;
			else dir = 1;
			
			if(dir == 0) {
				textureOffsetX = 
						((int)poi.x % player.level.gridsize) * (wallTex.width / player.level.gridsize);				
			} else {
				textureOffsetX = 
						((int)poi.y % player.level.gridsize) * (wallTex.width / player.level.gridsize);
			}
			
			for (int y = 0; y < height; y++) {
				
				if(y >= wallBottom && y <= wallTop) {
					
					textureOffsetY = (int)(((wallTop - y) / projectedWallHeight) * wallTex.height);
					
					setPixelRGB(((int)n + width/2) * height + y, 
							wallTex.pixels[Math.min(wallTex.height * wallTex.width - 1, textureOffsetX * wallTex.height + textureOffsetY)]);
				}
				else
					if(y < height/2)
						setPixelRGB(((int)n + width/2) * height + y, 0x00000F);
					else
						setPixelRGB(((int)n + width/2) * height + y, 0x874A00);
			}
			
//			for (int y = 0; y < height; y++) {
//				pixels[(n + width/2) * height + y] = (int) (getDistance(poi, new Point(player.x, player.y)));
//			}
		}
	}
}