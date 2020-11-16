package jogo.entity.mob;

import java.time.LocalTime;
import java.util.List;

import jogo.graphics.AnimatedSprite;
import jogo.graphics.Screen;
import jogo.graphics.Sprite;
import jogo.graphics.SpriteSheet;
import jogo.level.Node;
import jogo.util.Vector2i;

public class Star extends Mob{

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.Dummy_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.Dummy_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.Dummy_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.Dummy_right, 32, 32, 3);
	
	private AnimatedSprite animSprite = down;
	
	private double xaxis = 0 ;
	private double	yaxis = 0;
	private double speed = 0.8;
	private int time = 0;

	
	private List<Node> path = null;
	
	public Star(int x, int y ) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.dummy;
	}
	
	private void move() {
		xaxis = 0;
		yaxis = 0;
		int py = level.getPlayerx();
		int px = level.getPlayerY();
		Vector2i start = new Vector2i(getX() >> 4, getY() >> 4);
		Vector2i destination = new Vector2i(px >> 4, py >> 4);
		if (time % 3 == 0) {
			long beginning = LocalTime.now().getNano();
			path = level.findPath(start, destination);
			System.out.println("time: "+( LocalTime.now().getNano() - beginning));
		
		}
		if (path != null) {
			if (path.size() > 0) {
				Vector2i vec = path.get(path.size() - 1).tile;
				if (x < vec.getX() << 4) xaxis++;
				if (x > vec.getX() << 4) xaxis--;
				if (y < vec.getY() << 4) yaxis++;
				if (y > vec.getY() << 4) yaxis--;
			}
		}
			
		if(xaxis != 0 || yaxis != 0) {
			move(xaxis, yaxis);
			walking = true;
		} else {
			
			walking = false;
		}
		
	}
	
	public void update(){
		
		
		
		move();
		time++;
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
		 screen.renderMob(x - 16, y - 16, this);
		
	}

	@Override
	public void Burning() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Freezening() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void poisoned() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}

