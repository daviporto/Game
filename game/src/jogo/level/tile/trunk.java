package jogo.level.tile;

import jogo.graphics.Screen;
import jogo.graphics.Sprite;

public class trunk extends Tile {

	public trunk(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
		
	}
	
	public boolean solid() {
		return true;
	}

}
