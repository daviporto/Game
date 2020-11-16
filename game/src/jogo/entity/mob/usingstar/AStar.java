package jogo.entity.mob.usingstar;

import static jogo.util.MathUtils.triangleDistance;

import java.util.List;

import components.Fields.FieldBoolean;
import components.Fields.FieldDouble;
import components.Fields.FieldInt;
import components.Objects.DSObject;
import jogo.entity.mob.Mob;
import jogo.graphics.AnimatedSprite;
import jogo.graphics.Screen;
import jogo.level.Node;
import jogo.util.Vector2i;

/**
 * TODO Put here a description of what this class does.
 *
 * @author davi. Created 26 de set de 2020.
 */
public abstract class AStar extends Mob {

	protected AnimatedSprite down;
	protected AnimatedSprite up;
	protected AnimatedSprite left;
	protected AnimatedSprite right;
	protected AnimatedSprite animSprite;

	private double xaxis = 0;
	private double yaxis = 0;
	@SuppressWarnings("unused")
	private double speed = 0.8;
	protected int time = 0;
	private final boolean lazy;// if player go too far away this entity stop follow him
	private final int awake;
	private final int spawnX, spawnY;
	private boolean following = false;
	private final int followUntil;
	protected int timeCalculos;

	private List<Node> path = null;

	protected AStar(int spawnX, int spawnY, int awake, int followUntil, boolean lazy) {
		this.spawnX = spawnX;
		this.spawnY = spawnY;
		this.awake = awake;
		this.followUntil = followUntil;
		this.lazy = lazy;

	}

	protected AStar(int x, int y, boolean tilePrecision, boolean lazy, int awake, int followUntil) {
		if (tilePrecision) {
			spawnX = this.x = x << 4;
			spawnY = this.y = y << 4;
		} else {
			spawnX = this.x = x;
			spawnY = this.y = y;
		}
		this.lazy = lazy;
		this.awake = awake;
		this.followUntil = followUntil >> 4;
		following = true;
	}

	protected AStar(int x, int y, boolean tilePrecision, boolean lazy) {
		this(x, y, tilePrecision, lazy, 70, 150);
	}

	public void save(DSObject o) {
		o.pushField(new FieldDouble("speed", speed));
		o.pushField(new FieldBoolean("lazy", lazy));
		o.pushField(new FieldInt("awake", awake));
		o.pushField(new FieldInt("spawnX", spawnX));
		o.pushField(new FieldInt("spawnY", spawnY));
		o.pushField(new FieldBoolean("following", following));
		o.pushField(new FieldInt("followUntil", followUntil));
	}

	private void findPath() {
		xaxis = 0;
		yaxis = 0;
		int px = level.getPlayerx();
		int py = level.getPlayerY();
		if ((!following) && (triangleDistance(px, x, py, y) < awake))
			following = true;

		if (following) {
			Vector2i start = new Vector2i(getX() >> 4, getY() >> 4);
			Vector2i destination = new Vector2i(px >> 4, py >> 4);
			if (time % 3 == 0)
				path = level.findPath(start, destination);
			if (path != null) {
				if (!lazy)
					if (path.size() > followUntil) {
						following = false;
						return;
					}

				if (path.size() > 2) {
					Vector2i vec = path.get(path.size() - 1).tile;
					if (x < vec.getX() << 4)
						xaxis++;
					if (x > vec.getX() << 4)
						xaxis--;
					if (y < vec.getY() << 4)
						yaxis++;
					if (y > vec.getY() << 4)
						yaxis--;
				}
			}
			if (xaxis != 0 || yaxis != 0) {
				if (freezening > 0)
					if (random.nextInt(3) == 0)
						return; // se o mob estiver dificulta o seu movimento

				move(xaxis, yaxis);
				walking = true;
			} else
				walking = false;

		} else {
			if ((getX() == spawnX) || getY() == spawnX)
				return;
			Vector2i start = new Vector2i(getX() >> 4, getY() >> 4);
			Vector2i destination = new Vector2i(spawnX >> 4, spawnY >> 4);
			if (time % 10 == 0)
				path = level.findPath(start, destination);
			if (x < spawnX)
				xaxis++;
			if (x > spawnX)
				xaxis--;
			if (y < spawnY)
				yaxis++;
			if (y > spawnY)
				yaxis--;

			if (xaxis != 0 || yaxis != 0) {
				if (freezening > 0)
					if (random.nextInt(3) == 0)
						return; // se o mob estiver dificulta o seu movimento

				move(xaxis, yaxis);
				walking = true;
			} else
				walking = false;
		}
	}

	public void update() {
		super.update();
		time++;
		findPath();
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
	}

	public void SetFollowing(boolean following) {
		this.following = following;
	}

	@Override
	public void render(Screen screen) {

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
