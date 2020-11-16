package jogo.entity.particle;


import jogo.entity.Entity;
import jogo.graphics.Screen;
import jogo.graphics.Sprite;

public class Particle extends Entity{

	private Sprite sprite;
	
	private int life;
	private int time = 0;
	
	protected double xx, yy, zz;
	protected double xa, ya, za;
	
	
	public Particle (int x, int y,int life) {
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + random.nextInt(20) - 10;
		sprite = Sprite.particle_normal;
		
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 2.0;
	}
	
	public Particle (int x, int y,int life, Sprite.typeParticles Type) {
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + random.nextInt(20) - 10;
		if(Type.equals(Sprite.typeParticles.NORMAL)) sprite = Sprite.particle_normal;
		else if(Type.equals(Sprite.typeParticles.BLOOD)) sprite = Sprite.blood_particle;
		else if (Type.equals(Sprite.typeParticles.YELLOW)) sprite = Sprite.yellow_particle;
		
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 2.0;
	}
	
	public Particle (int x, int y,int life, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + random.nextInt(20) - 10;
		this.sprite = sprite;
		
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 2.0;
	}
	
	
	public void update() {
		time++;
		if(time >= Integer.MAX_VALUE - 10) time = 0;
		if(time > life)	remove();
		
		za -= 0.1;
		
		if(zz < 0) {
			zz = 0;
			za *= -0.8;
//			xa *= 0.4;
			ya *= 0.5;
		}
		move(xx + xa, (yy + ya) + (zz + za));
	}
	
	
	
	private void move(double x, double y) {
		if(Collision(x, y)) {
			 xa *= -0.5;
			 ya *= -0.5;
			 za *= -0.5;	
		}
		this.xx += xa;
		this.yy += ya;
		this.zz += za;
		
	}
	
	public boolean Collision(double x, double y) {
		boolean solid = false;
		for(int corner = 0; corner < 4; corner ++) {
			double xt = (x - corner % 2 * 16) / 16 ;
			double yt = (y - corner / 2 * 16) / 16;
			int ix= (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
//			c % 2 == 0 check the left side.
			if(corner % 2 == 0) ix = (int) Math.floor(xt);
			if(corner / 2 == 0) iy = (int) Math.floor(yt);
			if(level.getTile( ix ,  iy ).solid()) solid = true;
		}
		return solid;
	}

	public void render(Screen screen) {
		screen.renderSprite((int)xx, ((int)yy )- (int) zz, sprite, true);
		// yy - zz not + because the numbers are negatives, eg -10 > -11 then if you add this numbers in true they wil
		// result in a lesser number
		
	}
 
}
