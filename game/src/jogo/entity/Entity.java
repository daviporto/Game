package jogo.entity;

import static jogo.util.MathUtils.inTheRange;

import java.awt.Rectangle;
import java.util.Random;

import components.Fields.FieldInt;
import components.Objects.DSObject;
import components.dataBase.DSDataBase;
import jogo.graphics.Screen;
import jogo.graphics.Sprite;
import jogo.level.Level;


public class Entity {
	protected int x, y;
	protected Sprite sprite;
	protected boolean removed = false;
	protected jogo.level.Level level;
	protected final Random random= new Random();
	protected int xpAmount = 0;
	
	
	public Entity(int x, int y, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public Entity() {
		
	}
	
	public void save(DSDataBase db, int entityIndex) {
		
	}
	
	public void save(DSObject o) {
		o.pushField(new FieldInt("x", x));
		o.pushField(new FieldInt("y", y));
	}
	
	
	public boolean samePosition(int x, int y) {
		boolean inx = (inTheRange(x, this.x, this.x + 35));
		boolean iny = (inTheRange(y, this.y, this.y + 35));
		if(inx && iny) return true;
		return false;
	}
	
	public void setXpAmount(int xpAmount) {
		this.xpAmount = xpAmount;
	}
	

	public void render (Screen screen) {
		if(sprite!= null) screen.renderSprite((int)x, (int)y, sprite, true);
	}
	
	public void remove() {
		removed = true;
	}
	
	public int getX() {
		return (int)x;
	}
	public int getY() {
		return (int)y;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	 public boolean isRemoved() {
		return removed;
		
	}
	 
	 public void initialize(Level level) {
		 this.level = level;
	 }

	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public int getXpAmount() {
		return xpAmount;
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(x  , y , 32 , 32);
	}

}
