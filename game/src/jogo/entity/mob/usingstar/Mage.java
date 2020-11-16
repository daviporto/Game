package jogo.entity.mob.usingstar;

import java.util.logging.Logger;

import components.Fields.FieldBoolean;
import components.Fields.FieldByte;
import components.Fields.FieldInt;
import components.Objects.DSObject;
import components.dataBase.DSDataBase;
import jogo.Game;
import jogo.entity.spawner.ParticleSpawner;
import jogo.graphics.AnimatedSprite;
import jogo.graphics.Screen;
import jogo.graphics.Sprite;
import jogo.graphics.SpriteSheet;

public class Mage extends AStar{
	private boolean shootPlayer = true;
	private int MAXHEALTH = 300;
	private int fireRate = 60;
	private int SHOOTERTATE = fireRate;
	private KindofProjectile kind = KindofProjectile.FIREBOOL;
	
	private Mage(boolean lazy, int awake, int spawnX, int spawnY, int SHOOTERTATE, int MAXHEALTH, int followUntil,
			boolean shootPlayer) {
		super(spawnX, spawnY, awake, followUntil, lazy);
		this.SHOOTERTATE = SHOOTERTATE;
		this.MAXHEALTH = MAXHEALTH;
		this.shootPlayer = shootPlayer;

	}
	
	public Mage(int x, int y, boolean tilePrecision,boolean lazy,
			int awake, int Followuntil){
		super(x, y, tilePrecision, lazy, awake, Followuntil);
		xpAmount = 700;
		
		up = new AnimatedSprite(SpriteSheet.blueMage_up, 32, 32, 3);
		down = new AnimatedSprite(SpriteSheet.blueMage_down, 32, 32, 3);
		left = new AnimatedSprite(SpriteSheet.blueMage_left, 32, 32, 3);
		right = new AnimatedSprite(SpriteSheet.blueMage_right, 32, 32, 3);
		animSprite = down;
		health = MaxHelath = 300;
	}
	
	
	public void save(DSDataBase db, int entityIndex) {
		String name = "Mage" + Integer.toString(entityIndex);
		DSObject o = new DSObject(name);
		super.save(o);
		o.pushField(new FieldInt("xpAmount", xpAmount));
		o.pushField(new FieldBoolean("shootPLayer", shootPlayer));
		o.pushField(new FieldInt("SHOOTERTATE", SHOOTERTATE));
		o.pushField(new FieldInt("MAXHEALTH", MAXHEALTH));
		o.pushField(new FieldInt("fireRate", fireRate));
		o.pushField(new FieldByte("gender", KindofProjectile.getByte(kind)));
		db.pushObject(o);
	}

	public Mage load(DSObject o) {
		boolean lazy = o.getAndRemoveField("lazy").getBoolean();
		int awake = o.getAndRemoveField("awake").getInt();
		int spawnX = o.getAndRemoveField("spawnX").getInt();
		int spawnY = o.getAndRemoveField("spawnX").getInt();
		int SHOOTERTATE = o.getAndRemoveField("SHOOTERTATE").getInt();
		int MAXHEALTH = o.getAndRemoveField("MAXHEALTH").getInt();
		int followUntil = o.getAndRemoveField("followUntil").getInt();
		boolean shootPLayer = o.getAndRemoveField("shootPLayer").getBoolean();

		Mage e = new Mage(lazy, awake, spawnX, spawnY, SHOOTERTATE, MAXHEALTH, followUntil, shootPLayer);

		e.x = o.popField().getInt();
		e.y = o.popField().getInt();
		e.health = o.popField().getInt();
		e.MaxHelath = o.popField().getInt();
		e.burning = o.popField().getInt();
		e.freezening = o.popField().getInt();
		e.poisoned = o.popField().getInt();
		e.xpAmount = o.popField().getInt();
		e.fireRate = o.popField().getInt();
		e.kind = KindofProjectile.getKind(o.popField().getByte());

		return e;
	}
	
	
	public void setShootPlayer(boolean shootPlayer) {
		this.shootPlayer = shootPlayer;
	}
	
	public void setMaxHealth(int MAXHEALTH) {
		this.MAXHEALTH = MAXHEALTH;
	}
	
	public void setShootRate(int fireRate) {
		this.fireRate = SHOOTERTATE = fireRate;
	}
	
	public void SetKindOfProjectile(KindofProjectile kind) {
		this.kind = kind;
	}
	
	
	public void update() {
		super.update();
		if(health <= 0) { 
			Logger.getGlobal().info("yep i'm dead man ");
			removed = true;
			level.add(new ParticleSpawner (x, y, 200, 500, level, Sprite.typeParticles.BLOOD));
		}
		
		
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
			
			if(health < MAXHEALTH) health += 2;
			}
		if(shootPlayer) {
			if(super.shootPlayer(fireRate, kind)) 
				fireRate = freezening > 0? SHOOTERTATE + 10 : SHOOTERTATE;
		}else if(super.shootClosest(fireRate, kind))
						fireRate = freezening > 0? SHOOTERTATE + 10 : SHOOTERTATE;
		}

	
	public void render(Screen screen) {
		sprite = animSprite.getSprite(); 
		 screen.renderMob(x - 16, y - 16, this);
		
	}

	
	
}
