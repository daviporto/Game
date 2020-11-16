package jogo.entity.projectile;

import java.util.Random;

import jogo.entity.mob.Mob.KindofProjectile;
import jogo.entity.spawner.ParticleSpawner;
import jogo.graphics.AnimatedSprite;
import jogo.graphics.Screen;
import jogo.graphics.Sprite;
import jogo.graphics.SpriteSheet;

public class AnimatePoisonBollProjectile extends Projectile{
	
	protected final Random random = new Random();
    private AnimatedSprite anim;
	public static final int FIRE_RATE = 60; //higher is slower
	public static final KindofProjectile type = KindofProjectile.POISONBOOL;

	public AnimatePoisonBollProjectile(double x, double y, double direction, boolean player) {
		super(x, y, direction, player);
		range = random.nextInt(100) + 150;
		speed = 4;
		damage = 25;
		
		anim = new AnimatedSprite(SpriteSheet.poisonbools_projecitles_sprites, 22, 22, 10);
		
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		
	}
	
	public AnimatePoisonBollProjectile(double x, double y, double direction) {
		this(x,y,direction, true);
	}
	
	

	public void update () {
		anim.update();
		if(level.tileCollision((int)(x + nx), (int)(y + ny ), 9,34, 0)) { 
			level.add(new ParticleSpawner ((int)x, (int)y + 16 , 30, 25, level, Sprite.poisonParticles));
			remove();
		}
		
		if(level.projectileCollision((int)(x + nx), (int)(y + ny ), 9, 5, 16, (int)damage, entitiesInRange, type)) { 
			level.add(new ParticleSpawner ((int)x, (int)y + 16 , 30, 25, level, Sprite.poisonParticles));
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
		Sprite sprite= anim.getSprite();
		sprite = sprite.rotate(sprite,110 +angle );
		screen.renderSprite((int)x + 5, (int)y+15, sprite, true);
	}
	
}


