package jogo.entity.mob;

import java.util.Random;

import jogo.graphics.AnimatedSprite;
import jogo.graphics.Screen;
import jogo.graphics.Sprite;
import jogo.graphics.SpriteSheet;

public class MenuMob extends Mob {
	private static Random random = new Random();
	private int time = 0;
	private int xaxis = 0;
	private int yaxis = 0;
	private int maxWidth;
	private int maxHeight;

	private AnimatedSprite down;
	private AnimatedSprite up;
	private AnimatedSprite left;
	private AnimatedSprite right;
	private AnimatedSprite animSprite;

	public MenuMob(int x, int y, int maxWidth, int maxHeight) {
		this.x = x;
		this.y = y;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;

		int spriteKey = random.nextInt(6);

		switch (spriteKey) {
		case 0:
			down = new AnimatedSprite(SpriteSheet.blue_down, 32, 32, 3);
			up = new AnimatedSprite(SpriteSheet.blue_up, 32, 32, 3);
			left = new AnimatedSprite(SpriteSheet.blue_left, 32, 32, 3);
			right = new AnimatedSprite(SpriteSheet.blue_right, 32, 32, 3);
			break;

		case 1:
			down = new AnimatedSprite(SpriteSheet.Ghost_down, 32, 32, 3);
			up = new AnimatedSprite(SpriteSheet.Ghost_up, 32, 32, 3);
			left = new AnimatedSprite(SpriteSheet.Ghost_left, 32, 32, 3);
			right = new AnimatedSprite(SpriteSheet.Ghost_right, 32, 32, 3);
			break;

		case 2:
			down = new AnimatedSprite(SpriteSheet.blueMage_down, 32, 32, 3);
			up = new AnimatedSprite(SpriteSheet.blueMage_up, 32, 32, 3);
			left = new AnimatedSprite(SpriteSheet.blueMage_left, 32, 32, 3);
			right = new AnimatedSprite(SpriteSheet.blueMage_right, 32, 32, 3);
			break;

		case 3:
			down = new AnimatedSprite(SpriteSheet.pumpkinHead_down, 32, 32, 3);
			up = new AnimatedSprite(SpriteSheet.pumpkinHead_up, 32, 32, 3);
			left = new AnimatedSprite(SpriteSheet.pumpkinHead_left, 32, 32, 3);
			right = new AnimatedSprite(SpriteSheet.pumpkinHead_right, 32, 32, 3);
			break;

		case 4:
			down = new AnimatedSprite(SpriteSheet.maleVampire_down, 32, 32, 3);
			up = new AnimatedSprite(SpriteSheet.maleVampire_up, 32, 32, 3);
			left = new AnimatedSprite(SpriteSheet.maleVampire_left, 32, 32, 3);
			right = new AnimatedSprite(SpriteSheet.maleVampire_right, 32, 32, 3);
			break;
		case 5:
			down = new AnimatedSprite(SpriteSheet.famaleVampire_down, 32, 32, 3);
			up = new AnimatedSprite(SpriteSheet.famaleVampire_up, 32, 32, 3);
			left = new AnimatedSprite(SpriteSheet.famaleVampire_left, 32, 32, 3);
			right = new AnimatedSprite(SpriteSheet.famaleVampire_right, 32, 32, 3);
			break;
		case 6:
			down = new AnimatedSprite(SpriteSheet.witch_down, 32, 32, 3);
			up = new AnimatedSprite(SpriteSheet.witch_up, 32, 32, 3);
			left = new AnimatedSprite(SpriteSheet.witch_left, 32, 32, 3);
			right = new AnimatedSprite(SpriteSheet.witch_right, 32, 32, 3);
			break;

		}
		animSprite = down;

		sprite = Sprite.blueDummy;
		health = 100;
	}

	public void update() {
		super.update();
		time++;
		if (time % (random.nextInt(50) + 30) == 0) {
			xaxis = random.nextInt(3) - 1;
			yaxis = random.nextInt(3) - 1;

		}

		if (walking)
			animSprite.update();
		else
			animSprite.setFrame(0);

		if (yaxis < 0) {
			animSprite = up;
			direction = Direction.UP;
		}
		if (yaxis > 0) {
			animSprite = down;
			direction = Direction.DOWN;
		}
		if (xaxis < 0) {
			animSprite = left;
			direction = Direction.LEFT;
		}
		if (xaxis > 0) {
			animSprite = right;
			direction = Direction.RIGHT;
		}

		if (xaxis != 0 || yaxis != 0) {
			move(xaxis, yaxis);
			walking = true;
		} else {
			walking = false;
		}

	}

	public void move(double xaxis, double yaxis) {
		if (xaxis > 400)
			System.out.println("olha a merda ai");
		if (xaxis != 0 && yaxis != 0) {
			move(xaxis, 0);
			move(0, yaxis);
			return;
		}

		if (xaxis > 0)
			direction = Direction.RIGHT;
		if (xaxis < 0)
			direction = Direction.LEFT;
		if (yaxis < 0)
			direction = Direction.DOWN;
		if (yaxis > 0)
			direction = Direction.UP;

		while (xaxis != 0) {
			if (Math.abs(xaxis) > 1) {
				this.x += super.abs(xaxis);
				xaxis -= abs(xaxis);
			} else {
				if (xaxis + x > maxWidth + 32)
					xaxis = -1;
				if (xaxis + x <= -32)
					xaxis = 1;
				this.x += xaxis;
				xaxis = 0;
			}
		}

		while (yaxis != 0) {
			if (Math.abs(yaxis) > 1) {
				this.y += super.abs(yaxis);
				yaxis -= abs(yaxis);
			} else {
				if (yaxis + y > maxHeight + 32)
					yaxis = -1;
				if (yaxis + y <= -32)
					yaxis = 1;
				this.y += yaxis;
				yaxis = 0;
			}
		}

	}

	@Override
	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) x, (int) y, this);
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
