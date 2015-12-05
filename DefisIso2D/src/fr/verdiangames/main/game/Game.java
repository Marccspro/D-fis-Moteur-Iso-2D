package fr.verdiangames.main.game;

import fr.verdiangames.main.gfx.Screen;

public class Game {
	
	Level level;
	
	public Game() {
		level = new Level("test");
	}
	
	public void update() {
		
	}
	
	public void render(Screen screen) {
		level.render(screen);
	}
}