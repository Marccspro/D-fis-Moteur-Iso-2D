package fr.verdiangames.main.gfx;

import java.util.Arrays;

public class Screen {
	public int width, height;
	public int[] pixels;
	public int[] zBuffer;
	public int xIsoOrigin;
	public int yIsoOrigin;
	public int tileSize = 64;
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		zBuffer = new int[width * height];
		
		xIsoOrigin = (int) ((double) width / 2.0);
		yIsoOrigin = (int) ((double) height * 7.0/8.0);
	}
	
	public void clearDepth() {
		Arrays.fill(zBuffer, 100000);
	}
	
	public void drawBlock(double xp, double yp, double zp, int color) {
		double yyp = 10000 - yp;
		int depth = (int) Math.sqrt(xp * xp + yyp * yyp + zp * zp);

		int xProj = (int) (xp * tileSize / 2 - zp * tileSize / 2);
		int yProj = (int) (xp * -tileSize / 4 - zp * tileSize / 4 - yp * tileSize / 4);
		
		if (xProj < -xIsoOrigin - tileSize / 2) return;
		if (xProj > -xIsoOrigin + width + tileSize / 2) return;
		if (yProj < -yIsoOrigin - tileSize / 2) return;
		if (yProj > -yIsoOrigin + height + tileSize / 2) return;
		
		float hColor = (float) (yp + 10) / 10.0f;
		
		for (int x = 0; x < tileSize; x++) {
			for (int y = 0; y < tileSize / 2; y++) {
				int xx = x + xProj + xIsoOrigin - tileSize / 2;
				int yy = y + yProj + yIsoOrigin - tileSize / 4;
				
				if (x - tileSize / 2 >= y * 2) continue;
				if (x + tileSize / 2 < y * 2) continue;
				if (x < tileSize / 2 - y * 2) continue;
				if (x - tileSize >= tileSize / 2 - y * 2) continue;
				
				if (getDepth(xx, yy) <= depth) continue;
				writeDepth(xx, yy, depth);
				
				drawPixel(xx, yy, color, 0.9f * hColor);
			}	
		}
		
		for (int x = 0; x < tileSize / 2; x++) {
			for (int y = 0; y < tileSize / 4; y++) {				
				int xx = x + xProj + xIsoOrigin - tileSize / 2;
				int yy = y + yProj + yIsoOrigin - tileSize / 4 + x / 2 + 1 + tileSize / 4;
				
				if (getDepth(xx, yy) <= depth) continue;
				writeDepth(xx, yy, depth);
				
				drawPixel(xx, yy, color, 0.8f * hColor);
			}	
		}
		
		for (int x = 0; x < tileSize / 2; x++) {
			for (int y = 0; y < tileSize / 4; y++) {				
				int xx = x + xProj + xIsoOrigin - tileSize / 2 + tileSize / 2;
				int yy = y + yProj + yIsoOrigin - x / 2 + tileSize / 4;
				
				if (getDepth(xx, yy) <= depth) continue;
				writeDepth(xx, yy, depth);

				drawPixel(xx, yy, color, 0.7f * hColor);
			}	
		}
	}
	
	public void drawBlock(double xp, double yp, double zp, int color, Texture texture) {
		double yyp = 10000 - yp;
		int depth = (int) Math.sqrt(xp * xp + yyp * yyp + zp * zp);

		int xProj = (int) (xp * tileSize / 2 - zp * tileSize / 2);
		int yProj = (int) (xp * -tileSize / 4 - zp * tileSize / 4 - yp * tileSize / 4);
		
		if (xProj < -xIsoOrigin - tileSize / 2) return;
		if (xProj > -xIsoOrigin + width + tileSize / 2) return;
		if (yProj < -yIsoOrigin - tileSize / 2) return;
		if (yProj > -yIsoOrigin + height + tileSize / 2) return;
		
		float hColor = (float) (yp + 10) / 10.0f;
		
		for (int x = 0; x < tileSize; x++) {
			for (int y = 0; y < tileSize / 2; y++) {
				int xx = x + xProj + xIsoOrigin - tileSize / 2;
				int yy = y + yProj + yIsoOrigin - tileSize / 4;
				
				if (x - tileSize / 2 >= y * 2) continue;
				if (x + tileSize / 2 < y * 2) continue;
				if (x < tileSize / 2 - y * 2) continue;
				if (x - tileSize >= tileSize / 2 - y * 2) continue;
				
				if (getDepth(xx, yy) <= depth) continue;
				writeDepth(xx, yy, depth);
				
				double scaleFactor = (double)texture.width / tileSize;
				
				int xt = (int) (x * scaleFactor);
				int yt = (int) (y * scaleFactor);
				
				int c = texture.pixels[xt + yt * texture.width];
				
				drawPixel(xx, yy, c, 0.9f * hColor);
			}	
		}
		
		for (int x = 0; x < tileSize / 2; x++) {
			for (int y = 0; y < tileSize / 4; y++) {				
				int xx = x + xProj + xIsoOrigin - tileSize / 2;
				int yy = y + yProj + yIsoOrigin - tileSize / 4 + x / 2 + 1 + tileSize / 4;
				
				if (getDepth(xx, yy) <= depth) continue;
				writeDepth(xx, yy, depth);
				
				drawPixel(xx, yy, color, 0.8f * hColor);
			}	
		}
		
		for (int x = 0; x < tileSize / 2; x++) {
			for (int y = 0; y < tileSize / 4; y++) {				
				int xx = x + xProj + xIsoOrigin - tileSize / 2 + tileSize / 2;
				int yy = y + yProj + yIsoOrigin - x / 2 + tileSize / 4;
				
				if (getDepth(xx, yy) <= depth) continue;
				writeDepth(xx, yy, depth);

				drawPixel(xx, yy, color, 0.7f * hColor);
			}	
		}
	}
	
	public void drawSprite(int xp, int yp, int zp) {
		double yyp = 10000 - yp;
		int depth = (int) Math.sqrt(xp * xp + yyp * yyp + zp * zp);
		
		int xProj = (int) (xp * tileSize / 2 - zp * tileSize / 2);
		int yProj = (int) (xp * -tileSize / 4 - zp * tileSize / 4 - yp * tileSize / 4);
		int xx = xProj + xIsoOrigin;
		int yy = yProj + yIsoOrigin - tileSize / 4;
		
		drawPoint(xx, yy, tileSize / 2, depth, 0xffffff);
	}
	
	public void drawSprite(int xp, int yp, int zp, Texture texture) {
		double yyp = 10000 - yp * 100;
		int depth = (int) Math.sqrt(xp * xp + yyp * yyp + zp * zp);
		
		int xProj = (int) (xp * tileSize / 2 - zp * tileSize / 2);
		int yProj = (int) (xp * -tileSize / 4 - zp * tileSize / 4 - yp * tileSize / 4);
		
		if (xProj < -xIsoOrigin - tileSize / 2) return;
		if (xProj > -xIsoOrigin + width + tileSize / 2) return;
		if (yProj < -yIsoOrigin - tileSize / 2) return;
		if (yProj > -yIsoOrigin + height + tileSize) return;
		
		int xx = xProj + xIsoOrigin;
		int yy = yProj + yIsoOrigin - tileSize / 4;
		
		drawPoint(xx, yy, tileSize, depth, texture);
	}
	
	public void drawPixel(int x, int y, int color, float light) {
		if (x < 0 || y < 0 || x >= width || y >= height) return;
		
		if (light < 0) light = 0;
		if (light > 1) light = 1;

		int r = (int) (((color >> 16) & 0xff) * light);
		int g = (int) (((color >> 8) & 0xff) * light);
		int b = (int) (((color) & 0xff) * light);
		
		
		pixels[x + y * width] = r << 16 | g << 8 | b;
	}
	
	public void drawLine(int xa, int ya, int xb, int yb, int color, int size) {
		int xx = xa + xb;
		int yy = ya + yb;
		double lenght = Math.sqrt(xx * xx + yy * yy);
		
		for (int i = 0; i < lenght; i++) {
			double n = (double)i / lenght;
			int xLerp = (int) (xa + (xb - xa) * n);
			int yLerp = (int) (ya + (yb - ya) * n);
			
			drawPoint(xLerp, yLerp, size, color);
		}
	}
	
	public void drawPoint(int xp, int yp, int radius, int color) {
		for (int x = 0; x < radius; x++) {
			for (int y = 0; y < radius; y++) {
				int xx = x - radius / 2 + xp;
				int yy = y - radius / 2 + yp;
				
				drawPixel(xx, yy, color, 1);
			}	
		}
	}
	
	public void drawPoint(int xp, int yp, int radius, int depth, int color) {
		for (int x = 0; x < radius; x++) {
			for (int y = 0; y < radius; y++) {
				int xx = x - radius / 2 + xp;
				int yy = y - radius / 2 + yp;
				
				if (getDepth(xx, yy) <= depth) continue;
				writeDepth(xx, yy, depth);

				drawPixel(xx, yy, color, 1);
			}	
		}
	}
	
	public void drawPoint(int xp, int yp, int radius, int depth, Texture texture) {
		for (int x = 0; x < radius; x++) {
			for (int y = 0; y < radius; y++) {
				int xx = x - radius / 2 + xp;
				int yy = y - radius / 2 + yp;
				
				double scaleFactor = (double)texture.width / radius;
				
				int xt = (int) (x * scaleFactor);
				int yt = (int) (y * scaleFactor);
				
				int color = texture.pixels[xt + yt * texture.width];
				if (color == 0xffff00ff) continue;
				
				if (getDepth(xx, yy) <= depth) continue;
				writeDepth(xx, yy, depth);
				
				
				drawPixel(xx, yy, color, 1);
			}	
		}
	}
	
	public void writeDepth(int x, int y, int depth) {
		if (x < 0 || y < 0 || x >= width || y >= height) return;

		zBuffer[x + y * width] = depth;
	}
	
	public int getDepth(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return 0;
		
		return zBuffer[x + y * width];
	}
}
