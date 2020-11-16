package jogo.level.tile.spawn_level;

import jogo.graphics.Screen;
import jogo.graphics.Sprite;
import jogo.level.tile.Tile;

public class SpawnWaterTile extends Tile {

	public SpawnWaterTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
		
	}

}
