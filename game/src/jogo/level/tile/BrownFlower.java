package jogo.level.tile;

import jogo.graphics.Screen;
import jogo.graphics.Sprite;

public class BrownFlower extends Tile {

	public BrownFlower(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
		
	}

}
