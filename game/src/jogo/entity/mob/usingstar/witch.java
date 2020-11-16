package jogo.entity.mob.usingstar;

import components.Fields.FieldBoolean;
import components.Fields.FieldInt;
import components.Objects.DSObject;
import components.dataBase.DSDataBase;
import jogo.Game;
import jogo.entity.spawner.ParticleSpawner;
import jogo.graphics.AnimatedSprite;
import jogo.graphics.Screen;
import jogo.graphics.Sprite;
import jogo.graphics.SpriteSheet;

public class witch extends AStar {

	private int fireRate = 0;
	private final int SHOOTERTATE;
	private final int MAXHEALTH;
	private final boolean shootPlayer;

	private witch(boolean lazy, int awake, int spawnX, int spawnY, int SHOOTERTATE, int MAXHEALTH,
			int followUntil, boolean shootPlayer) {
		super(spawnX, spawnY, awake, followUntil,  lazy);
		this.SHOOTERTATE = SHOOTERTATE;
		this.MAXHEALTH = MAXHEALTH;
		this.shootPlayer = shootPlayer;
		
		down = new AnimatedSprite(SpriteSheet.witch_down, 32, 32, 3);
		up = new AnimatedSprite(SpriteSheet.witch_up, 32, 32, 3);
		left = new AnimatedSprite(SpriteSheet.witch_left, 32, 32, 3);
		right = new AnimatedSprite(SpriteSheet.witch_right, 32, 32, 3);

		animSprite = down;
	}

	public witch(int x, int y, boolean tilePrecision, boolean lazy, int awake, int Followuntil, boolean shootPlayer,
			int shootRate) {
		super(x, y, tilePrecision, lazy, awake, Followuntil);

		down = new AnimatedSprite(SpriteSheet.witch_down, 32, 32, 3);
		up = new AnimatedSprite(SpriteSheet.witch_up, 32, 32, 3);
		left = new AnimatedSprite(SpriteSheet.witch_left, 32, 32, 3);
		right = new AnimatedSprite(SpriteSheet.witch_right, 32, 32, 3);

		animSprite = down;

		MAXHEALTH = health = 170;
		SHOOTERTATE = shootRate;

		this.shootPlayer = shootPlayer;

		xpAmount = 300;
	}

	public witch(int x, int y, boolean lazy, int awake, int Followuntil, boolean shootPlayer) {
		this(x, y, false, lazy, awake, Followuntil, shootPlayer, 80);
	}

	public witch(int x, int y, boolean shootPlayer) {
		this(x, y, false, false, 90, 150, shootPlayer, 80);

	}

	public void save(DSDataBase db, int entityIndex) {
		String name = "Witch" + Integer.toString(entityIndex);
		DSObject o = new DSObject(name);
		super.save(o);
		o.pushField(new FieldInt("xpAmount", xpAmount));
		o.pushField(new FieldBoolean("shootPLayer", shootPlayer));
		o.pushField(new FieldInt("SHOOTERTATE", SHOOTERTATE));
		o.pushField(new FieldInt("MAXHEALTH", MAXHEALTH));
		o.pushField(new FieldInt("fireRate", fireRate));
		db.pushObject(o);
	}

	public witch load(DSObject o) {
		boolean lazy = o.getAndRemoveField("lazy").getBoolean();
		int awake = o.getAndRemoveField("awake").getInt();
		int spawnX = o.getAndRemoveField("spawnX").getInt();
		int spawnY = o.getAndRemoveField("spawnX").getInt();
		int SHOOTERTATE = o.getAndRemoveField("SHOOTERTATE").getInt();
		int MAXHEALTH = o.getAndRemoveField("MAXHEALTH").getInt();
		int followUntil = o.getAndRemoveField("followUntil").getInt();
		boolean shootPLayer = o.getAndRemoveField("shootPLayer").getBoolean();
		
		
		witch e = new witch(lazy, awake, spawnX, spawnY, SHOOTERTATE,MAXHEALTH, followUntil , shootPLayer);
		
		e.x = o.popField().getInt();
		e.y = o.popField().getInt();
		e.health = o.popField().getInt();
		e.MaxHelath = o.popField().getInt();
		e.burning = o.popField().getInt();
		e.freezening = o.popField().getInt();
		e.poisoned = o.popField().getInt();
		e.xpAmount = o.popField().getInt();
		e.fireRate = o.popField().getInt();
		
		return e;
	}

	public void update() {
		if (health <= 0) {
			removed = true;
			level.add(new ParticleSpawner(x, y, 200, 500, level, Sprite.typeParticles.BLOOD));
		}

		if (fireRate > 0)
			fireRate--;
		super.update();
		if (time == Game.FPS) {
			if (burning > 0) {
//				Logger.getGlobal().info("burning working");
				health--;
				burning--;
			}
			if (freezening > 0)
				freezening--;
			if (poisoned > 0) {
				poisoned--;
			}

			if (health < MAXHEALTH)
				health += 2;
		}

		if (shootPlayer) {
			if (super.shootPlayer(fireRate, KindofProjectile.POISONBOOL))
				fireRate = SHOOTERTATE;
		} else if (super.shootClosest(fireRate, KindofProjectile.POISONBOOL))
			fireRate = SHOOTERTATE;
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob(x, y - 16, this);

	}

	@Override
	public void Burning() {
		burning = 5;

	}

	@Override
	public void Freezening() {
		freezening = 5;

	}

	@Override
	public void poisoned() {

	}

}
