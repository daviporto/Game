package jogo.level.tile.level1;

import jogo.graphics.Screen;
import jogo.graphics.Sprite;
import jogo.level.tile.Tile;

public class Level1floor extends Tile{
	public Level1floor(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
}
