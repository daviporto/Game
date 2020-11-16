package jogo.entity.mob;

import components.Fields.FieldInt;
import components.Objects.DSObject;
import components.dataBase.DSDataBase;
import jogo.Game;
import jogo.entity.spawner.ParticleSpawner;
import jogo.graphics.AnimatedSprite;
import jogo.graphics.Screen;
import jogo.graphics.Sprite;
import jogo.graphics.SpriteSheet;


public class Dummy extends Mob{
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.blue_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.blue_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.blue_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.blue_right, 32, 32, 3);
	private AnimatedSprite animSprite = down;
	
	
	private int time = 0;
	private int xaxis = 0;
	private int yaxis = 0;
	private int struck = 0;
	private int spawnX, spawnY;
	private int xp; // experiencia para ser adicionada ao player apos a morte
	
	private Dummy() {
		
	}
	
	public Dummy(int x, int y, boolean tilePrecision) {
		if(tilePrecision) {
			this.x = spawnX =  x << 4;
			this.y = spawnY = y << 4; 			
		}else {
			this.x = spawnX = x ;
			this.y = spawnY = y ;
		}
		sprite = Sprite.blueDummy;
		health = 100;
	}
	
	public Dummy(int x, int y, int fixedX, int fixedY, boolean tilePrecision) {
		if(tilePrecision) {
			this.x =  x << 4;
			this.y = y << 4; 			
		}else {
			this.x = x ;
			this.y  = y ;
		}
		
		spawnX = fixedX;
		spawnY = fixedY;
		
		sprite = Sprite.blueDummy;
		health = 100;
		xpAmount = 100;
	}
	
	
	@Deprecated
	public Dummy(int x, int y) {
		this.x = spawnX = x ;
		this.y = spawnY = y ;
		sprite = Sprite.blueDummy;
		health = 100;
	}
	
	public void save(DSDataBase db, int entityIndex){
		String name = "Dummy" + Integer.toString(entityIndex);
		DSObject o = new DSObject(name);
		super.save(o);
		o.pushField(new FieldInt("spawnX", spawnX));
		o.pushField(new FieldInt("spawnY", spawnY));
		o.pushField(new FieldInt("xpAmount", xpAmount));
		db.pushObject(o);
	}
	
	public void load(DSObject o) {
		Dummy e = new Dummy();
		e.x = o.popField().getInt();
		e.y = o.popField().getInt();
		e.health = o.popField().getInt();
		e.MaxHelath = o.popField().getInt();
		e.burning = o.popField().getInt();
		e.freezening = o.popField().getInt();
		e.poisoned = o.popField().getInt();
		e.spawnX = o.popField().getInt();
		e.spawnY = o.popField().getInt();
		e.xpAmount = o.popField().getInt();
		sprite = Sprite.blueDummy;
	}

	@Override
	public void update() {
		super.update();
		time++;
		if(health <= 0) { 
			removed = true;
			level.add(new ParticleSpawner (x, y, 100, 100, level, Sprite.smokeParticles));
		}
		
		if(struck > 0) struck --;
		
		if(time % (random.nextInt(50) + 30 )== 0 ) {
			xaxis = random.nextInt(3) - 1;
			if(xaxis > 0) {
				if((xaxis + x )> (spawnX + 20)) { xaxis = -1; // if goingo too far away from spawn invert direction                      
			}else if((x + xaxis) < (spawnX - 20)) xaxis = 1;
			
			yaxis = random.nextInt(3) - 1;
			if(yaxis > 0){
				if((yaxis + y) > (spawnY + 20)) yaxis = -1;
			}else  if((y + yaxis) < (spawnY - 20)) yaxis = 1;
			
			if(struck == 0) if(random.nextInt(5) == 0) xaxis = yaxis = 0;
		}
	}
	
		if(time % Game.FPS == 0) {
			if(burning > 0) {
				health--;
				burning --;				
			}
			if(freezening > 0) freezening --;
			if(poisoned > 0) poisoned --;
		}
 
		if(walking) animSprite.update();
		else animSprite.setFrame(0);
	
		if(yaxis < 0) {
			animSprite = up;
			direction = Direction.UP;
		}
		if(yaxis > 0) {
			animSprite = down;
			direction = Direction.DOWN;
		}
		if(xaxis < 0) { 
			animSprite = left;
			direction = Direction.LEFT;
		}
		if(xaxis > 0) {
			animSprite = right;
			direction = Direction.RIGHT;
		}
		
		if(xaxis != 0 || yaxis != 0) {
			if(freezening > 0) if(random.nextInt(3) == 0) return;
			move(xaxis, yaxis);
			walking = true;
		} else {
			walking = false;
		}
	}
	
	public void damageTaked(int damage) {
		struck = 100;
	}

	@Override
	public void render(Screen screen) {
		sprite = animSprite.getSprite(); 
		screen.renderMob((int)x, (int)y, this);
	}

	@Override
	public void Burning() {
		burning = 6;
	}

	@Override
	public void Freezening() {
		freezening = 6;
	}

	@Override
	public void poisoned() {
		poisoned =6;
	}
	
	public int getXp() {
		return xp;
	}
	

}
