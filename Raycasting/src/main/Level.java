package main;

import java.util.Random;

public class Level {
	
	public boolean[][] walls;
	public int gridsize;
	
	public Player player;
	
	private Random random = new Random();
	
	public Level(int width, int height, int gridsize) {
		walls = new boolean[width][height];
		this.gridsize = gridsize;
		
		// Create a border of walls around the level
		for(int x = 0; x < walls.length; x++) {
			walls[x][0] = true;
			walls[x][walls[0].length -1] = true;
			
			if(x == 0 || x == walls.length -1)
				for(int y = 0; y < walls[0].length; y++) {
					walls[x][y] = true;
				}
		}
		
		// Randomly place walls within the level
		for(int x = 1; x < walls.length - 1; x++)
			for(int y = 1; y < walls[0].length - 1; y++)
				if(random.nextFloat() <= 0.05f)
					walls[x][y] = true;
		
		// Print the walls
		for(int x = 0; x < walls.length; x++) {
			for(int y = 0; y < walls[0].length; y++) {
				System.out.print(walls[x][y] ? "W" : "0");
			}
			System.out.println();
		}
	}
}