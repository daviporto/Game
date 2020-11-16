package jogo.entity.mob;

import jogo.graphics.AnimatedSprite;
import jogo.graphics.Screen;
import jogo.graphics.Sprite;
import jogo.graphics.SpriteSheet;

public class Ghost extends Mob{
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.Ghost_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.Ghost_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.Ghost_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.Ghost_right, 32, 32, 3);
	private AnimatedSprite animSprite = down;
	
	
	public Ghost(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.ghost;
	}

	@Override
	public void update() {
		int xaxis = 0;
		int yaxis = 0;
		xaxis--;
		if(walking) animSprite.update();
		else animSprite.setFrame(0);
	
		if(yaxis < 0) {
			animSprite = up;
			direction = Direction.UP;
		}
		if(yaxis > 0) {
			animSprite = down;
			direction = Direction.DOWN;
		}
		if(xaxis < 0) { 
			animSprite = left;
			direction = Direction.LEFT;
		}
		if(xaxis > 0) {
			animSprite = right;
			direction = Direction.RIGHT;
		}
		
		if(xaxis != 0 || yaxis != 0) {
			move(xaxis, yaxis);
			walking = true;
		} else {
			
			walking = false;
		}
	}

	@Override
	public void render(Screen screen) {
		sprite = animSprite.getSprite(); 
		 screen.renderMob(x, y, this);
		
	}

	@Override
	public void Burning() {
		burning = 7;
		
	}

	@Override
	public void Freezening() {
		freezening = 7;		
	}

	@Override
	public void poisoned() {
		poisoned = 7;
	}

}



