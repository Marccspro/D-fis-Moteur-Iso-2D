package fr.verdiangames.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import javax.swing.JFrame;

import fr.verdiangames.main.game.Game;
import fr.verdiangames.main.gfx.Screen;
import fr.verdiangames.main.inputs.Input;

public class Main extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public static final int SCALE = 1;
	public static final int WIDTH = 900 / SCALE;
	public static final int HEIGHT = 480 / SCALE;
	
	public boolean running = false;
	
	public BufferedImage image;
	public int[] pixels;
	Screen screen;
	Game game;
	Input input;
	
	int seed = 0;
	
	public void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		screen = new Screen(WIDTH, HEIGHT);
		seed = new Random().nextInt();
		game = new Game();
	}
	
	public void start() {
		running = true;
		new Thread(this).start();
	}
	
	public void run() {
		init();
		
		long before = System.nanoTime();
		
		double updatedTime = 0.0;
		double renderedTime = 0.0;
		
		int tick = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			boolean ticked = false;
			if (now - before - updatedTime > 1000000000.0 / 60.0) {
				update();
				
				tick++;
				if (tick % 60 == 0) {
					ticked = true;
					tick = 0;
				}
				updatedTime += 1000000000.0 / 60.0;
			}else if (now - before - renderedTime > 1000000000.0 / 60.0){
				render();
				frames++;
				renderedTime += 1000000000.0 / 60.0;
			}else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if (ticked) {
				System.out.println(frames + " fps");
				frames = 0;
			}
		}
	}
	
	int time = 0;
	double xr, yr;
	
	int xStart, yStart, xEnd, yEnd;
	int moveX, moveY;
	boolean shift = false;
	
	public void update() {
		time++;
		if (!input.focused) {
			return;
		}
		game.update();
		
		if (input.keys[KeyEvent.VK_Z]) {
			screen.yIsoOrigin++;
		}
		if (input.keys[KeyEvent.VK_S]) {
			screen.yIsoOrigin--;
		}
		if (input.keys[KeyEvent.VK_Q]) {
			screen.xIsoOrigin++;
		}
		if (input.keys[KeyEvent.VK_D]) {
			screen.xIsoOrigin--;
		}
		
		if (input.keys[KeyEvent.VK_SHIFT]) {
			if (input.buttons[MouseEvent.BUTTON1]) {
				if (!shift) {
					xStart = input.x / SCALE;
					yStart = input.y / SCALE;
					shift = true;
				}
				xEnd = input.x / SCALE;
				yEnd = input.y / SCALE;
				
				moveX = (int)((xStart - xEnd) * 0.1);
				moveY = (int)((yStart - yEnd) * 0.1);
				
				screen.xIsoOrigin += moveX;
				screen.yIsoOrigin += moveY;
			}else {
				shift = false;
			}
		}
	}
	
	public void render() {
		Random rand = new Random(seed);
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(2);
			return;
		}
		
		screen.clearDepth();

		game.render(screen);

//			for (int x = 0; x < 5; x++) {
//				for (int z = 0; z < 5; z++) {
//					screen.drawBlock(x, 0, z, 0x003355);
//					
//					if (rand.nextFloat() > 0.5) {					
//						screen.drawBlock(x, 1, z, 0x005500);
//						
//						if (rand.nextFloat() > 0.7) {					
//							screen.drawBlock(x, 2, z, 0x335500);
//							
//							if (rand.nextFloat() > 0.9) {					
//								screen.drawBlock(x, 3, z, 0x555555);
//							}
//						}
//					}
//				}
//			}
//			
		if (input.keys[KeyEvent.VK_SHIFT] && input.buttons[MouseEvent.BUTTON1] && shift) {
			screen.drawLine(xStart, yStart, xEnd, yEnd, 0xffffff, 1);			
		}

		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
			screen.pixels[i] = 0;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		if (!input.focused && time % 30 > 10) {
			g.setFont(new Font("Arial", 0, 50));
			
			g.setColor(Color.BLACK);
			g.drawString("CLICK TO FOCUS !", getWidth() * 1/4 + 2, getHeight() / 2 + 3);
			
			g.setColor(Color.WHITE);
			g.drawString("CLICK TO FOCUS !", getWidth() * 1/4, getHeight() / 2);
		
		}
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		
		Dimension dim = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		main.setMinimumSize(dim);
		main.setPreferredSize(dim);
		main.setMaximumSize(dim);
		
		main.input = new Input();
		main.addKeyListener(main.input);
		main.addMouseListener(main.input);
		main.addMouseMotionListener(main.input);
		main.addFocusListener(main.input);
		
		
		JFrame frame = new JFrame("Iso 2D");
		
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(main);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		main.start();
	}
}