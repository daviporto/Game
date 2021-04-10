package jogo.entity.projectile;

import java.util.Random;

import jogo.entity.mob.Mob.KindofProjectile;
import jogo.entity.spawner.ParticleSpawner;
import jogo.entity.spawner.Spawner;
import jogo.graphics.AnimatedSprite;
import jogo.graphics.Screen;
import jogo.graphics.Sprite;
import jogo.graphics.SpriteSheet;

public class WizardProjectile extends Projectile{
	protected final Random random = new Random();
	public static final int FIRE_RATE = 20; //higher is slower
	public static final int MANA_COST = 10;
	public static final KindofProjectile type = KindofProjectile.WIZZARD;

	public WizardProjectile(double x, double y, double direction) {
		super(x, y, direction);
		range = random.nextInt(100) + 150;
		speed = 4;
		damage = 20;
		sprite = Sprite.green_bool;
		sprite.rotate(Sprite.green_bool, direction  - 90);
		
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		

	}


	public void update () {
		if(level.tileCollision((int)(x + nx), (int)(y + ny ), 7, 4, 6)) { 
			level.add(new ParticleSpawner ((int)x, (int)y + 16 , 44, 50, level));
			remove();
		
		}
		
		if(level.projectileCollision((int)(x + nx), (int)(y + ny ), 7, 4, 6, (int)damage, entitiesInRange, type)) { 
			level.add(new ParticleSpawner ((int)x, (int)y + 16 , 44, 50, level));
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
		screen.renderprojecTile((int)x + 5, (int)y+15, this);
	}
	
}
