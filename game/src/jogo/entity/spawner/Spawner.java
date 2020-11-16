package jogo.entity.spawner;


import jogo.entity.Entity;
import jogo.graphics.Screen;
import jogo.level.Level;
  


public class Spawner extends Entity{
	
	public enum Type{
		MOB, PARTICLE;
	}
	
	protected Type type;

	public Spawner(int x, int y, Type type , int amount, Level level) {
		initialize(level);
		this.x = x;
		this.y = y;
		this.type = type;
	}

	@Override
	public void render(Screen screen) {
		
	}
	
}
