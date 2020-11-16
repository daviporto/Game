package jogo.level.tile;

import jogo.graphics.Screen;
import jogo.graphics.Sprite;

public class WhiteFlowerTile extends Tile {

	public WhiteFlowerTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
		
	}

}
