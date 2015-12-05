package fr.verdiangames.main.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.verdiangames.main.gfx.Screen;
import fr.verdiangames.main.gfx.Texture;

public class Level {
	public int width, height;
	public int[] tiles;
	
	public int[] level0Tiles = new int[] {0xff007FFF};
	public int[] level1Tiles = new int[] {0xff007F0E, 0xff005408};
	public int[] level2Tiles = new int[] {0xff808080};
	
	Texture tree = new Texture("/tree.png");
	Texture grass = new Texture("/grass.png");
	
	public Level(String map) {
		try {
			BufferedImage image = ImageIO.read(Level.class.getResource("/maps/" + map + ".png"));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Screen screen) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int tile = tiles[x + y * width];
				int h = getTileLevel(tile);
				if (tile == 0xff007F0E) {
					screen.drawBlock(x, h, y, tile, grass);					
				}else {
					screen.drawBlock(x, h, y, tile);
				}
				if (tile == 0xff005408) {
					screen.drawSprite(x, 1, y, tree);					
				}
			}	
		}
	}
	
	public int getTileLevel(int tile) {
		for (int i = 0; i < level0Tiles.length; i++) {
			if (tile == level0Tiles[i]) return 0;
		}
		for (int i = 0; i < level1Tiles.length; i++) {
			if (tile == level1Tiles[i]) return 1;
		}
		for (int i = 0; i < level2Tiles.length; i++) {
			if (tile == level2Tiles[i]) return 2;
		}
		
		return 0;
	} 
}