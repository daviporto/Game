package jogo.entity.mob;

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

public class Shooter extends Mob {

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.Ghost_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.Ghost_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.Ghost_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.Ghost_right, 32, 32, 3);
	private AnimatedSprite animSprite = down;

	private int time = 0;
	private int xaxis = 0;
	private int yaxis = 0;
	private int fireRate = 0;
	private final int BASESHOOTERATE;
	private int shootRate;
	private final int spawnX, spawnY;
	private final boolean shootPlayer;
	
	private Shooter(int spawnX, int spawnY, boolean shootPlayer, int baseShootRate) {
		this.spawnX = spawnY;
		this.spawnY = spawnX;
		this.shootPlayer = shootPlayer;
		BASESHOOTERATE = baseShootRate;
		sprite = Sprite.ghost;
	}

	public Shooter(int x, int y, boolean tilePrecision, boolean shootPLayer) {
		if (tilePrecision) {
			this.x = spawnX = x << 4;
			this.y = spawnY = y << 4;
		} else {
			this.x = spawnX = x;
			this.y = spawnY = y;
		}

		this.shootPlayer = shootPLayer;
		sprite = Sprite.ghost;
		health = 70;
		shootRate = BASESHOOTERATE = 80;
	}


	
	public Shooter(int x, int y, int fixedX, int fixedY, boolean tilePrecision, boolean shootPLayer, int shootRate) {
		if (tilePrecision) {
			this.x = x << 4;
			this.y = y << 4;
		} else {
			this.x = x;
			this.y = y;
		}

		spawnX = fixedX;
		spawnY = fixedY;
		this.shootPlayer = shootPLayer;
		sprite = Sprite.ghost;
		health = 70;
		shootRate = BASESHOOTERATE = shootRate;
		xpAmount = 150;
	}
	
	public Shooter(int x, int y, int fixedX, int fixedY, boolean tilePrecision, boolean shootPLayer) {
		this(x, y, fixedX, fixedY, tilePrecision, shootPLayer, 80);	
	}
	

	public void save(DSDataBase db, int entityIndex) {
		String name = "Dummy" + Integer.toString(entityIndex);
		DSObject o = new DSObject(name);
		super.save(o);
		o.pushField(new FieldInt("spawnX", spawnX));
		o.pushField(new FieldInt("spawnY", spawnY));
		o.pushField(new FieldInt("xpAmount", xpAmount));
		o.pushField(new FieldBoolean("shootPlayer", shootPlayer));
		o.pushField(new FieldInt("BASESHOOTERATE", BASESHOOTERATE));
		o.pushField(new FieldInt("shootRate", shootRate));
		db.pushObject(o);
	}

	public Shooter load(DSObject o) {
		int spawnX = o.getAndRemoveField("spawnX").getInt();
		int spawnY = o.getAndRemoveField("spawnX").getInt();
		int baseShootRate = o.getAndRemoveField("BASESHOOTERATE").getInt();
		boolean shootPLayer = o.getAndRemoveField("shootPLayer").getBoolean();
		
		Shooter e = new Shooter(spawnX, spawnY, shootPLayer, baseShootRate);
		e.x = o.popField().getInt();
		e.y = o.popField().getInt();
		e.health = o.popField().getInt();
		e.MaxHelath = o.popField().getInt();
		e.burning = o.popField().getInt();
		e.freezening = o.popField().getInt();
		e.poisoned = o.popField().getInt();
		e.xpAmount = o.popField().getInt();
		e.shootRate= o.popField().getInt();
		sprite = Sprite.blueDummy;
		
		return e;
	}

	public void update() {
		super.update();
		shootRate = BASESHOOTERATE;

		if (health <= 0) {
			removed = true;
			level.add(new ParticleSpawner(x, y, 200, 500, level, Sprite.typeParticles.BLOOD));
		}
		if (time % Game.FPS == 0) {
			if (burning > 0) {
				health--;
				burning--;
			}
			if (freezening > 0)
				freezening--;
			if (poisoned > 0) {
				poisoned--;
				shootRate = BASESHOOTERATE * 2;
			}
		}

		time++;
		if (time % (random.nextInt(50) + 30) == 0) {
			xaxis = random.nextInt(3) - 1;

			if (xaxis > 0) {
				if ((x + xaxis) > (spawnX + 70))
					xaxis = -1;
			} else if ((x - xaxis) < (spawnX - 70))
				xaxis = 1;// to mob no go too far away prom spawn

			yaxis = random.nextInt(3) - 1;
			if (yaxis > 0) {
				if ((y + yaxis) > (spawnY + 70))
					yaxis = -1;
			} else if ((y - yaxis) < (spawnY - 70))
				yaxis = 1;

			if (random.nextInt(5) == 0)
				xaxis = yaxis = 0;
		}

		if (walking)
			animSprite.update();
		else
			animSprite.setFrame(0);

		if (yaxis < 0) {
			animSprite = up;
			direction = Direction.UP;
		}
		if (yaxis > 0) {
			animSprite = down;
			direction = Direction.DOWN;
		}
		if (xaxis < 0) {
			animSprite = left;
			direction = Direction.LEFT;
		}
		if (xaxis > 0) {
			animSprite = right;
			direction = Direction.RIGHT;
		}

		if (xaxis != 0 || yaxis != 0) {
			move(xaxis, yaxis);
			walking = true;
		} else {
			walking = false;
		}

		if (fireRate > 0)
			fireRate--;

		if (shootPlayer) {
			if (super.shootPlayer(fireRate, KindofProjectile.BASIC))
				fireRate = shootRate;
		} else if (super.shootClosest(fireRate, KindofProjectile.BASIC))
			fireRate = shootRate;
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) x, (int) y, this);
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
		poisoned = 5;
	}

}
