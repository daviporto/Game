package jogo.entity.projectile;

import java.util.List;
import java.util.Random;

import jogo.entity.Entity;
import jogo.entity.mob.Mob.KindofProjectile;
import jogo.entity.spawner.ParticleSpawner;
import jogo.graphics.AnimatedSprite;
import jogo.graphics.Screen;
import jogo.graphics.Sprite;
import jogo.graphics.SpriteSheet;
import jogo.level.Level;

public class BasicProjectile extends Projectile{

	protected final Random random = new Random();
    private Sprite sprite = null;
	public static final int FIRE_RATE = 40; //higher is slower
	private int degres = 0;
	private int time = 0;
	public static final KindofProjectile type = KindofProjectile.BASIC;

	public BasicProjectile(double x, double y, double direction, boolean player) {
		super(x, y, direction, player);
		range = random.nextInt(100) + 150 ;
		speed = 4;
		damage = 20;
		
		 sprite = Sprite.basic_atk_projectile;

			nx = speed * Math.cos(angle);
			ny = speed * Math.sin(angle);
		

	}

	public void update () {
		time ++;
		if(time % 3 == 0) {
			
			degres++;
			if(degres > 360) angle = 0;
			sprite = sprite.rotate(Sprite.basic_atk_projectile, degres );
		}
		
		if(level.tileCollision((int)(x + nx), (int)(y + ny ), 11, 5, 5)) { 
			remove();
		
		}
		
		if(level.projectileCollision((int)(x + nx), (int)(y + ny ),11, 5, 5, (int)damage, entitiesInRange, type)) { 
			level.add(new ParticleSpawner ((int)x, (int)y + 16 , 40, 15, level, Sprite.typeParticles.BLOOD));
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
	
		screen.renderSprite((int)x , (int)y, sprite, true);
	}
	
}



