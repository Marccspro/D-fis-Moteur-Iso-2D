package fr.verdiangames.main.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.verdiangames.main.game.Level;

public class Texture {
	public int width, height;
	public int[] pixels;
	
	public Texture(String path) {
		try {
			BufferedImage image = ImageIO.read(Level.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			pixels = new int[w * h];
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
