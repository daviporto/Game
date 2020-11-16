package jogo.level.tile.spawn_level;

import jogo.graphics.Screen;
import jogo.graphics.Sprite;
import jogo.level.tile.Tile;

public class SpawnBricksTile extends Tile {

	public SpawnBricksTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
		
	}
	
	public boolean solid() {
		return true; 
	}

}
