package jogo.entity.mob;

import java.util.List;

import jogo.graphics.AnimatedSprite;
import jogo.graphics.Screen;
import jogo.graphics.Sprite;
import jogo.graphics.SpriteSheet;

public class Chaser extends Mob{
	
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.Dummy_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.Dummy_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.Dummy_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.Dummy_right, 32, 32, 3);
	
	private AnimatedSprite animSprite = down;
	
	private double xaxis = 0 ;
	private double	yaxis = 0;
	private double speed = 0.8;
	
	
	public Chaser(int x, int y ) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.dummy;
	}
	
	private void move() {
		xaxis = 0;
		yaxis = 0;
		
		List<Player> players = level.getPlayers(this, 100);
		if(players.size() > 0) {
			
		Player player = players.get(0);
		System.out.println(x + "    "  + player.getX() );
		if((int)x < (int)player.getX()) xaxis += speed;//if chaser x is less than player go to right
		if((int)x > (int)player.getX()) xaxis -= speed;
		if((int)y < (int)player.getY()) yaxis += speed;
		if((int)y > (int)player.getY()) yaxis -= speed;
		System.out.println(xaxis );
		}
		
		if(xaxis != 0 || yaxis != 0) {
			move(xaxis, yaxis);
			walking = true;
		} else {
			
			walking = false;
		}
		
	}
	
	public void update(){
		if(burning > 0) {
			health--;
			burning--;
		}
		
		move();
		
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
		
		
		
	}


	public void render(Screen screen) {
		sprite = animSprite.getSprite(); 
		 screen.renderMob((int)x, (int)y, this);
		
	}
	
	@Override
	public void Burning() {
		burning = 10;
	}

	@Override
	public void Freezening() {
		freezening = 10;
	}

	@Override
	public void poisoned() {
	}
	
	
	
}
