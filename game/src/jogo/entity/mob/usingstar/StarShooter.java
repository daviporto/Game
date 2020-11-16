package jogo.entity.mob.usingstar;

import jogo.Game;
import jogo.entity.mob.Mob;
import jogo.entity.mob.usingstar.Vampire.Gender;
import jogo.entity.spawner.ParticleSpawner;
import jogo.graphics.AnimatedSprite;
import jogo.graphics.Sprite;
import jogo.graphics.SpriteSheet;

public class StarShooter extends AStar{


	
	private int fireRate = 0;
	private final int SHOOTERTATE = 60;
	
	
	public StarShooter(int x, int y, boolean tilePrecision,boolean lazy,
			int awake, int Followuntil) {
		super(x, y, tilePrecision, lazy, awake, Followuntil);
		
		down = new AnimatedSprite(SpriteSheet.Dummy_down, 32, 32, 3);
		up = new AnimatedSprite(SpriteSheet.Dummy_up, 32, 32, 3);
		left = new AnimatedSprite(SpriteSheet.Dummy_left, 32, 32, 3);
		right = new AnimatedSprite(SpriteSheet.Dummy_right, 32, 32, 3);
		
		animSprite = down;
		xpAmount = 100;
	}
	
	public StarShooter(int x, int y,boolean lazy, int awake, int Followuntil, Gender gender) {
		this(x, y, false, lazy, awake, Followuntil);
	}
	
	public StarShooter(int x, int y,  Gender gender) {
		this(x, y, false, false, 90, 150);
		
	}
	
	
	
	public void update(){
		if(health <= 0) { 
			removed = true;
			level.add(new ParticleSpawner (x, y, 200, 500, level, Sprite.typeParticles.BLOOD));
			
		}
		super.update();
		
		if(fireRate > 0) fireRate--;
		
		if(fireRate > 0) fireRate--;
		
		if(time == Game.FPS) {
			if(burning > 0) {
//				Logger.getGlobal().info("burning working");
				health--;
				burning --;				
			}
			if(freezening > 0) freezening --;
			if(poisoned > 0) {
				poisoned --;
			}
		}
		
		Shoot();
		
		
	}
	
	private void Shoot() {
		double distance, disy, disx;
		Mob e = level.getClientPlayer();
		disx =(e.getX() - x);
		disy = (e.getY() - y);
		distance = Math.abs((disx * disx) + (disy * disy));
		distance = Math.sqrt(distance);
		
		if(distance <= 150 && fireRate <= 0) {
			double dir = Math.atan2(disy, disx); 
			shoot(x, y, dir, Mob.KindofProjectile.FIREBOOL);
			fireRate = freezening > 0? SHOOTERTATE + 10 : SHOOTERTATE;
		}

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
