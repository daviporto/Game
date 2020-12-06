package jogo.events.checkPoints;

import jogo.level.Level;
import jogo.util.Vector2i;

public class SaveCheckPoint implements Runnable{
	private Level level;
	private Vector2i x;
	private Vector2i y;
	
	public SaveCheckPoint(Level level, Vector2i x, Vector2i y) {
		this.level = level;
		this.x = x;
		this.y = y;
	}
	
	public Vector2i GetX() {
		return x;
	}
	
	public Vector2i GetY() {
		return y;
	}
	
	public void run() {
		level.saveCheckPoint("lastCheckPoint");
	}
}
