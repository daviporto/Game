package jogo.level.tile.level1;

import jogo.graphics.Screen;
import jogo.graphics.Sprite;
import jogo.level.tile.Tile;

public class Level1wall extends Tile {
	public Level1wall(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
		
	}
	
	public boolean solid() {
		return true; 
	}
}
