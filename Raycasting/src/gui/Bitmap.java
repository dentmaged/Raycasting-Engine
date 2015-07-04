package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bitmap {
	
	public final int width, height;
	public int[] pixels;
	
	public Bitmap(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[this.width * this.height];
	}
	
	public Bitmap(String location) throws IOException {
		
		BufferedImage image = ImageIO.read(new File(location));
		
		width = image.getWidth();
		height = image.getHeight();
		pixels = new int[width * height];
		
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
				pixels[x * height + y] = image.getRGB(x, y);
	}
	
	public void updatePixels() {
	}
	
	public void setBufferedImageRGB(BufferedImage img) {		
		for(int x = 0; x < pixels.length / height; x++) {
			for(int y = 0; y < height; y++) {
				img.setRGB(x, y, pixels[x * height + y]);
			}
		}
	}
	
	public void setPixelRGB(int index, int color){
		try {
			pixels[index] = color;
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.err.println("Pixel index out of bounds");
		}
	}
}