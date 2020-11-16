package jogo.entity.mob.usingstar;

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

public class Vampire extends AStar {
	private int fireRate = 0;
	private final int SHOOTERTATE;
	private final int MAXHEALTH;
	private final boolean shootPlayer;

	public enum Gender {
		MALE, FAMELE
	}

	private Gender gender;

	private Vampire(boolean lazy, int awake, int spawnX, int spawnY, int SHOOTERTATE, int MAXHEALTH, int followUntil,
			boolean shootPlayer) {
		super(spawnX, spawnY, awake, followUntil, lazy);
		this.SHOOTERTATE = SHOOTERTATE;
		this.MAXHEALTH = MAXHEALTH;
		this.shootPlayer = shootPlayer;

	}

	public Vampire(int x, int y, boolean tilePrecision, boolean lazy, int awake, int Followuntil, boolean shootPlayer,
			Gender gender, int shootRate) {
		super(x, y, tilePrecision, lazy, awake, Followuntil);
		this.gender = gender;
		if (gender == Gender.FAMELE) {
			down = new AnimatedSprite(SpriteSheet.famaleVampire_down, 32, 32, 3);
			up = new AnimatedSprite(SpriteSheet.famaleVampire_up, 32, 32, 3);
			left = new AnimatedSprite(SpriteSheet.famaleVampire_left, 32, 32, 3);
			right = new AnimatedSprite(SpriteSheet.famaleVampire_right, 32, 32, 3);
			animSprite = down;
		} else {
			down = new AnimatedSprite(SpriteSheet.maleVampire_down, 32, 32, 3);
			up = new AnimatedSprite(SpriteSheet.maleVampire_up, 32, 32, 3);
			left = new AnimatedSprite(SpriteSheet.maleVampire_left, 32, 32, 3);
			right = new AnimatedSprite(SpriteSheet.maleVampire_right, 32, 32, 3);
			animSprite = down;
		}

		MAXHEALTH = health = 250;
		this.shootPlayer = shootPlayer;
		xpAmount = 100;
		SHOOTERTATE = shootRate;
	}

	public Vampire(int x, int y, boolean lazy, int awake, int Followuntil, boolean shootPlayer, Gender gender) {
		this(x, y, false, lazy, awake, Followuntil, shootPlayer, gender, 80);
	}

	public Vampire(int x, int y, boolean shootPlayer, Gender gender) {
		this(x, y, false, false, 90, 150, shootPlayer, gender, 80);

	}

	public void save(DSDataBase db, int entityIndex) {
		String name = "Vampire" + Integer.toString(entityIndex);
		DSObject o = new DSObject(name);
		super.save(o);
		o.pushField(new FieldInt("xpAmount", xpAmount));
		o.pushField(new FieldBoolean("shootPLayer", shootPlayer));
		o.pushField(new FieldInt("SHOOTERTATE", SHOOTERTATE));
		o.pushField(new FieldInt("MAXHEALTH", MAXHEALTH));
		o.pushField(new FieldInt("fireRate", fireRate));
		byte genderB = (byte) (gender == Gender.FAMELE ? 0 : 1);
		o.pushField(new FieldByte("gender", genderB));
		db.pushObject(o);
	}

	public Vampire load(DSObject o) {
		boolean lazy = o.getAndRemoveField("lazy").getBoolean();
		int awake = o.getAndRemoveField("awake").getInt();
		int spawnX = o.getAndRemoveField("spawnX").getInt();
		int spawnY = o.getAndRemoveField("spawnX").getInt();
		int SHOOTERTATE = o.getAndRemoveField("SHOOTERTATE").getInt();
		int MAXHEALTH = o.getAndRemoveField("MAXHEALTH").getInt();
		int followUntil = o.getAndRemoveField("followUntil").getInt();
		boolean shootPLayer = o.getAndRemoveField("shootPLayer").getBoolean();

		Vampire e = new Vampire(lazy, awake, spawnX, spawnY, SHOOTERTATE, MAXHEALTH, followUntil, shootPLayer);

		e.x = o.popField().getInt();
		e.y = o.popField().getInt();
		e.health = o.popField().getInt();
		e.MaxHelath = o.popField().getInt();
		e.burning = o.popField().getInt();
		e.freezening = o.popField().getInt();
		e.poisoned = o.popField().getInt();
		e.xpAmount = o.popField().getInt();
		e.fireRate = o.popField().getInt();
		byte genderB = o.popField().getByte();
		e.gender = genderB == 0 ? Gender.FAMELE : Gender.MALE;

		if (e.gender == Gender.FAMELE) {
			down = new AnimatedSprite(SpriteSheet.famaleVampire_down, 32, 32, 3);
			up = new AnimatedSprite(SpriteSheet.famaleVampire_up, 32, 32, 3);
			left = new AnimatedSprite(SpriteSheet.famaleVampire_left, 32, 32, 3);
			right = new AnimatedSprite(SpriteSheet.famaleVampire_right, 32, 32, 3);
			animSprite = down;
		} else {
			down = new AnimatedSprite(SpriteSheet.maleVampire_down, 32, 32, 3);
			up = new AnimatedSprite(SpriteSheet.maleVampire_up, 32, 32, 3);
			left = new AnimatedSprite(SpriteSheet.maleVampire_left, 32, 32, 3);
			right = new AnimatedSprite(SpriteSheet.maleVampire_right, 32, 32, 3);
			animSprite = down;
		}

		return e;
	}

	public void update() {
		super.update();
		if (health <= 0) {
			removed = true;
			level.add(new ParticleSpawner(x, y, 200, 500, level, Sprite.typeParticles.BLOOD));
		}

		if (fireRate > 0)
			fireRate--;

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
			if (super.shootPlayer(fireRate, KindofProjectile.ICEBOOL))
				fireRate = freezening > 0 ? SHOOTERTATE + 10 : SHOOTERTATE;
		} else if (super.shootClosest(fireRate, KindofProjectile.ICEBOOL))
			fireRate = freezening > 0 ? SHOOTERTATE + 10 : SHOOTERTATE;
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob(x - 16, y - 16, this);

	}

}
