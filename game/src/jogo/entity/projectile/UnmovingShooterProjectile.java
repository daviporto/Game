package jogo.entity.projectile;

import java.util.Random;

import jogo.entity.spawner.ParticleSpawner;
import jogo.graphics.Screen;
import jogo.graphics.Sprite;

public class UnmovingShooterProjectile extends Projectile{
	private final Random random = new Random();

	public UnmovingShooterProjectile(double x, double y, double direction, boolean player) {
		super(x, y, direction, player);
		range = random.nextInt(100) + 150;
		speed = 6;
		damage = 10;
//		int index = random.nextInt(27);
//		sprite = sprite.rotate(SpriteSheet.bools_projecitles_sprites.getSprite()[index], angle - 90);
		sprite = Sprite.green_bool;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	
	public UnmovingShooterProjectile(double x, double y, double direction) {
		this(x, y, direction, true);
	}
	
	
	public void update () {
		if(level.tileCollision((int)(x + nx), (int)(y + ny ), 7, 4, 6)) { 
			level.add(new ParticleSpawner ((int)x, (int)y + 16 , 20, 40, level, Sprite.typeParticles.YELLOW));
			remove();
		}
		if(level.PLayerProjectileCoolision((int) (x +nx), (int) (y +ny), 15, 16 , 5, (int) damage)) { 
			level.add(new ParticleSpawner ((int)x, (int)y + 16 , 20, 40, level, Sprite.typeParticles.YELLOW));
			remove();
		}
		move();
	}
	
	protected void move() {
		x += nx;
		y += ny;
		
		 if(calculateDistance() > range)  remove();
	}


	private double calculateDistance() {
		double distance = 0;
		distance = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
		return distance;
	}

	public void render(Screen screen) {
		screen.renderprojecTile((int)x , (int)y, this);
	}
}
