package jogo.entity.mob;

import static jogo.util.MathUtils.clamp;

import java.util.List;

import components.Fields.FieldInt;
import components.Objects.DSObject;
import jogo.entity.Entity;
import jogo.entity.projectile.AnimateIceBollProjectil;
import jogo.entity.projectile.AnimatePoisonBollProjectile;
import jogo.entity.projectile.AnimatedFireboolProjectile;
import jogo.entity.projectile.BasicProjectile;
import jogo.entity.projectile.Projectile;
import jogo.entity.projectile.UnmovingShooterProjectile;
import jogo.entity.projectile.WizardProjectile;
import jogo.entity.spawner.ParticleSpawner;
import jogo.graphics.Screen;
import jogo.graphics.Sprite;
import jogo.util.Vector2i;

public abstract class Mob extends Entity {

	protected boolean moving = false;
	protected boolean walking = false;

	protected int health = -500;
	protected int MaxHealth;
	protected int burning = 0;
	protected int freezening = 0;
	protected int poisoned = 0;
	public final int UNSIGNEDHEALTH = -500;

	public void save(DSObject o) {
		super.save(o);
		o.pushField(new FieldInt("health", health));
		o.pushField(new FieldInt("maxHealth", MaxHealth));
		o.pushField(new FieldInt("burning", burning));
		o.pushField(new FieldInt("freezening", freezening));
		o.pushField(new FieldInt("poisoned", poisoned));
	}

	protected enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	public enum KindofProjectile {
		WIZZARD, FIREBOOL, ICEBOOL, POISONBOOL, RANDOMBOOL, BASIC, GREENBALL, NULL;

		public static byte getByte(KindofProjectile k) {
			if (k == NULL)
				return -1;
			if (k == WIZZARD)
				return 0;
			if (k == FIREBOOL)
				return 1;
			if (k == ICEBOOL)
				return 2;
			if (k == POISONBOOL)
				return 3;
			if (k == RANDOMBOOL)
				return 4;
			if (k == BASIC)
				return 5;
			if (k == GREENBALL)
				return 6;

			return 0;
		}

		public static KindofProjectile getKind(byte b) {
			switch (b) {
			case -1:
				return NULL;
			case 0:
				return WIZZARD;
			case 1:
				return FIREBOOL;
			case 2:
				return ICEBOOL;
			case 3:
				return POISONBOOL;
			case 4:
				return RANDOMBOOL;
			case 5:
				return BASIC;
			case 6:
				return GREENBALL;

			}
			return BASIC;
		}
	}

	protected Direction direction;
	protected KindofProjectile kind;

	public void move(double xaxis, double yaxis) {
		if (xaxis != 0 && yaxis != 0) {
			move(xaxis, 0);
			move(0, yaxis);
			return;
		}

		if (xaxis > 0)
			direction = Direction.RIGHT;
		if (xaxis < 0)
			direction = Direction.LEFT;
		if (yaxis < 0)
			direction = Direction.DOWN;
		if (yaxis > 0)
			direction = Direction.UP;

		while (xaxis != 0) {
			if (Math.abs(xaxis) > 1) {
				if (!collision(abs(xaxis), yaxis) & !level.mobCollision(this, x + abs(xaxis), y)) {
					this.x += abs(xaxis);
				}
				xaxis -= abs(xaxis);
			} else {
				if (!collision(abs(xaxis), yaxis) & !level.mobCollision(this, x + xaxis, y)) {
					this.x += xaxis;
				}
				xaxis = 0;
			}
		}

		while (yaxis != 0) {
			if (Math.abs(yaxis) > 1) {
				if (!collision(xaxis, abs(yaxis)) & !level.mobCollision(this, x, y + abs(yaxis))) {
					this.y += abs(yaxis);
				}
				yaxis -= abs(yaxis);
			} else {
				if (!collision(xaxis, abs(yaxis)) & !level.mobCollision(this, x, y + yaxis)) {
					this.y += yaxis;
				}
				yaxis = 0;
			}

		}
	}

	public int abs(double value) {
		if (value < 0)
			return -1;
		return 1;
	}

	public void update() {
		if (burning > 0)
			addParticles(Sprite.FireParticles);
		if (poisoned > 0)
			addParticles(Sprite.poisonParticles);
		if (freezening > 0)
			addParticles(Sprite.iceParticles);
	}

	protected void shoot(double x, double y, double direction, KindofProjectile kind, boolean player) {
		Projectile p = null;
		this.kind = kind;

		if (kind == KindofProjectile.BASIC)
			p = new BasicProjectile((int) x, (int) y, direction, player);
		else if (kind == KindofProjectile.WIZZARD)
			p = new WizardProjectile((int) x, (int) y, direction);

		else if (kind == KindofProjectile.RANDOMBOOL)
			p = new UnmovingShooterProjectile((int) x, (int) y, direction);

		else if (kind == KindofProjectile.FIREBOOL)
			p = new AnimatedFireboolProjectile((int) x, (int) y, direction, player);

		else if (kind == KindofProjectile.ICEBOOL)
			p = new AnimateIceBollProjectil((int) x, (int) y, direction, player);

		else if (kind == KindofProjectile.POISONBOOL)
			p = new AnimatePoisonBollProjectile((int) x, (int) y, direction, player);

		else if (kind == KindofProjectile.GREENBALL)
			p = new UnmovingShooterProjectile((int) x, (int) y, direction, player);

		else if (kind == KindofProjectile.NULL) {
			p = new WizardProjectile((int) x, (int) y, direction);
			System.err.println("default projectile loaded");
		}

		level.add(p);

	}

	protected void shoot(double x, double y, double direction, KindofProjectile kind) {
		shoot(x, y, direction, kind, true);
	}

	protected boolean shootClosest(int fireRate, Mob.KindofProjectile kind) {
		List<Entity> entities = level.getEntities(this, 5000);

		double min = 0;
		Entity closest = null;
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			double distance = Vector2i.getDistance(new Vector2i(x, y), new Vector2i(e.getX(), e.getY()));
			if (i == 0 || distance < min) {
				min = distance;
				closest = e;
			}
		}
		if (closest != null && fireRate <= 0) {
			double dx = closest.getX() - x;
			double dy = closest.getY() - y;
			double dir = Math.atan2(dy, dx);
			shoot(x, y, dir, kind, false);
			return true;
		}
		return false;
	}

	@SuppressWarnings("unused")
	private void shootRandom(Mob.KindofProjectile kind) {
		List<Entity> entities = level.getEntities(this, 500);
		entities.add(level.getClientPlayer());
		Entity rand;

		int index = random.nextInt(entities.size());
		rand = entities.get(index);

		if (rand != null) {
			double dx = rand.getX() - x;
			double dy = rand.getY() - y;
			double dir = Math.atan2(dy, dx);
			shoot(x, y, dir, kind, false);
		}

	}

	protected boolean shootPlayer(int fireRate, Mob.KindofProjectile kind) {
		Player player = level.getPlayer();
		if (player == null)
			return false;
		double distance = Vector2i.getDistance(new Vector2i(x, y), new Vector2i(player.getX(), player.getY()));
		if (distance > 110)
			return false;
		if (player != null && fireRate <= 0) {
			double dx = player.getX() - x;
			double dy = player.getY() - y;
			double dir = Math.atan2(dy, dx);
			shoot(x, y, dir, kind, false);
			return true;
		}

		return false;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = clamp(health, 0, 100);
	}

	public void damageTaked(int damage, KindofProjectile Kind) {
		health = clamp((health - damage), 0, 100);
		if (Kind == KindofProjectile.BASIC)
			return;
		if (Kind == KindofProjectile.FIREBOOL)
			Burning();
		else if (Kind == KindofProjectile.ICEBOOL)
			Freezening();
		else if (Kind == KindofProjectile.POISONBOOL)
			poisoned();
	}

	private boolean collision(double xa, double ya) {
		xa += 16;
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = ((x + xa) - c % 2 * 15) / 16;
			double yt = ((y + ya) - c / 2 * 15) / 16;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0)
				ix = (int) Math.floor(xt);
			if (c / 2 == 0)
				iy = (int) Math.floor(yt);
			if (level.getTile(ix, iy + 1).solid())
				solid = true;
		}
		return solid;
	}

	public abstract void render(Screen screen);

	public abstract void Burning();

	public abstract void Freezening();

	public abstract void poisoned();

	public void addParticles(Sprite[] particles) {
		new ParticleSpawner(x + 16, y + 16, 2, 1, level, particles, 16);
	}

}
