package jogo.entity.projectile;

import java.util.ArrayList;
import java.util.List;

import jogo.entity.Entity;
import jogo.graphics.Sprite;
import jogo.level.Level;

public abstract class Projectile extends Entity {
	protected final double xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double x, y;
	protected double nx, ny;
	protected double distance;
	protected double speed, range, damage;
	protected boolean player = true;
	protected List<Entity> entitiesInRange = new ArrayList<Entity>();
	
	 
	public Projectile(double x, double y, double direction) {
		xOrigin = x;
		yOrigin = y;
		angle = direction;
		
		this.x = x;
		this.y = y;
	}
	public Projectile(double x, double y, double direction, boolean player ) {
		xOrigin = x;
		yOrigin = y;
		angle = direction;
		this.player = player;
		
		this.x = x;
		this.y = y;
	}
	
	 public void initialize(Level level) {
		 super.initialize(level);
		 if(!player) entitiesInRange.add(level.getClientPlayer());
		 else entitiesInRange = level.getEntities((int)x, (int)y, 250);
	 }
	
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getSpriteSize() {
		return sprite.SIZE;
	}
	
	protected void move() {
		
	}
}
