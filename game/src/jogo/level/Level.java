package jogo.level;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import components.dataBase.DSDataBase;
import jogo.entity.Entity;
import jogo.entity.mob.Dummy;
import jogo.entity.mob.Mob;
import jogo.entity.mob.Mob.KindofProjectile;
import jogo.entity.mob.Player;
import jogo.entity.particle.Particle;
import jogo.entity.projectile.Projectile;
import jogo.entity.spawner.ParticleSpawner;
import jogo.events.Event;
import jogo.events.messageEvents.MessageEventsManager;
import jogo.graphics.Screen;
import jogo.graphics.layers.Layer;
import jogo.graphics.ui.UIManager;
import jogo.level.tile.Tile;
import jogo.util.Vector2i;

public class Level extends Layer {
	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;
	protected int tile_size;
	protected MessageEventsManager messagesManager;
	protected UIManager ui;

	private int xScroll, yScroll;

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();

	private List<Player> players = new ArrayList<Player>();
	private Player player;
	protected final Random random = new Random();

	private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost)
				return +1;
			if (n1.fCost > n0.fCost)
				return -1;
			return 0;
		}
	};

	public Player getPlayer() {
		return player;
	}

	public void addMessageManager(MessageEventsManager m) {
		messagesManager = m;
	}

	public void RenderCenario(Screen screen) {

	}

	public static Level spawn = new SpawnLevel("/levels/labirinto.png");
	public static Level teste0 = new NivelDeTeste("/levels/firstlevelteste.png");
	public static Level teste = new NivelDeTeste("/levels/firstlevelteste.png");

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		generateLevel();
	}

	public Level(String path) {
		loadLevel(path);
		generateLevel();

	}

	public DSDataBase save() {
		DSDataBase db = new DSDataBase("levelsave");

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).save(db, i);
		}

		return db;
	}

	public int getPlayerx() {
		return player.getX();
	}

	public int getPlayerY() {
		return player.getY();
	}

	protected void loadLevel(String path) {
		
	}
	
	public void setUIManeger(UIManager ui) {
		this.ui = ui;
	}

	protected void generateLevel() {
		for (int Y = 0; Y < 64; Y++) {
			for (int x = 0; x < 64; x++) {
				getTile(x, Y);
			}
		}
		tile_size = 16;
	}

	public void onEvent(Event event) {
		getClientPlayer().onEvent(event);
	}

	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}

		for (int i = 0; i < projectiles.size(); i++) {

			projectiles.get(i).update();
		}

		for (int i = 0; i < particles.size(); i++) {

			particles.get(i).update();
		}

		player.update();

		remove();
	}

	public boolean mobCollision(Mob m, int x, int y) {
		Rectangle mRec = new Rectangle(x, y, 32, 32);
		Iterator<Entity> i = entities.iterator();
		Entity current;
		while (i.hasNext()) {
			current = i.next();
			if (!(current instanceof Mob))
				continue;
			if (current == m)
				continue;
			if (mRec.contains(current.getRectangle().getCenterX(), current.getRectangle().getCenterY())) {
				return true;
			}
		}

		return false;
	}

	public boolean mobCollision(Mob m, double x, double y) {
		return mobCollision(m, (int) x, (int) y);
	}

	public List<Node> findPath(Vector2i start, Vector2i goal) {
		goal = findNonSolid(goal);
		if (goal == null)
			return null;
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, getDistance(start, goal));
		openList.add(current);
		while (openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if (current.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while (current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for (int i = 0; i < 9; i++) {
				if (i == 4)
					continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile at = getTile(x + xi, y + yi);
				if (at == null)
					continue;
				if (at.solid())
					continue;
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + (getDistance(current.tile, a) == 1 ? 1 : 0.95);
				double hCost = getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if (vecInList(closedList, a) && gCost >= node.gCost)
					continue;
				if (!vecInList(openList, a) || gCost < node.gCost)
					openList.add(node);
			}
			if (openList.size() > 150) {
				return null;
			}
		}
		closedList.clear();
		return null;
	}

	private Vector2i findNonSolid(Vector2i v) {
		if (!getTile(v).solid())
			return v;

		for (int i = 0; i < 9; i++) {
			if (i == 4)
				continue;
			int x = v.getX();
			int y = v.getY();
			int xi = (i % 3) - 1;
			int yi = (i / 3) - 1;
			Tile at = getTile(x + xi, y + yi);
			if (at == null)
				continue;
			if (at.solid())
				continue;
			return new Vector2i(x + xi, y + yi);
		}
		return null;
	}

	private boolean vecInList(List<Node> list, Vector2i vector) {
		for (Node n : list) {
			if (n.tile.equals(vector))
				return true;
		}
		return false;
	}

	private double getDistance(Vector2i tile, Vector2i goal) {
		double dx = tile.getX() - goal.getX();
		double dy = tile.getY() - goal.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}

	public List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		int ex = e.getX();
		int ey = e.getY();
		if (entities == null || entities.isEmpty())
			return null;
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity.equals(e))
				continue;
			if (entity instanceof Projectile)
				continue;
			if (entity instanceof ParticleSpawner)
				continue;
			int x = entity.getX();
			int y = entity.getY();

			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius)
				result.add(entity);
		}
		return result;
	}

	public List<Entity> getEntities(int ex, int ey, int radius) {
		List<Entity> result = new ArrayList<Entity>();

		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity.getX() == ex && entity.getY() == ey)
				continue;
			if (entity instanceof Projectile)
				continue;
			if (entity instanceof ParticleSpawner)
				continue;
			int x = entity.getX();
			int y = entity.getY();

			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius)
				result.add(entity);
		}
		return result;
	}

	public List<Player> getPlayers(Entity e, int radius) {
		List<Player> result = new ArrayList<Player>();

		int ex = (int) e.getX();
		int ey = (int) e.getY();

		int x = (int) player.getX();
		int y = (int) player.getY();

		int dx = Math.abs(x - ex);
		int dy = Math.abs(y - ey);
		double distance = Math.sqrt((dx * dx) + (dy * dy));
		if (distance <= radius)
			result.add(player);

		return result;
	}

	private void remove() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved()) {
				Logger.getGlobal().info(entities.get(i).getClass().toString());
				player.addLevel(entities.get(i).getXpAmount());
				entities.remove(i);
			}
		}

		for (int i = 0; i < projectiles.size(); i++) {

			if (projectiles.get(i).isRemoved())
				projectiles.remove(i);
		}

		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved())
				particles.remove(i);

		}
		for (int i = 0; i < players.size(); i++) {
			if (player.isRemoved())
				players.remove(i);

		}
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public boolean tileCollision(int x, int y, int size, int xoffset, int yoffset) {
		boolean solid = false;
		for (int corner = 0; corner < 4; corner++) {
			int xt = (x - corner % 2 * size + xoffset) >> 4;
			int yt = (y - corner / 2 * size + 16 + yoffset) >> 4;
			if (getTile(xt, yt).solid())
				solid = true;
		}
		return solid;
	}

	public boolean entityCollision(Entity e, List<Entity> close) {

		return false;
	}

	public boolean projectileCollision(int x, int y, int size, int xoffset, int yoffset, int damage, List<Entity> e,
			KindofProjectile kind) {
		boolean colided = false;
		if (e == null)
			return false;
		for (int i = 0; i < e.size(); i++) {
			Entity current = e.get(i);
			if (current instanceof Mob) {
				Mob currentMob = (Mob) current;
				if (currentMob.samePosition(x + xoffset, y + yoffset)) {
					if (currentMob.getHealth() != currentMob.UNSIGNEDHEALTH) {
						currentMob.damageTaked(damage, kind);
						colided = true;
					}
				}
			}
		}
		return colided;
	}

	public boolean PLayerProjectileCoolision(int x, int y, int size, int xoffset, int yoffset, int damage) {
		// verify if the player is hited
		boolean colided = false;
		if (getClientPlayer().samePosition(x + xoffset, y + yoffset)) {
			colided = true;
			getClientPlayer().addOrRemoveHealth(-damage);
		}

		return colided;
	}

	public void setScroll(int xScroll, int yScroll) {
		this.xScroll = xScroll;
		this.yScroll = yScroll;
	}

	public void render(Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);// this commands
		}

		for (int i = 0; i < particles.size(); i++) {

			particles.get(i).render(screen);
		}

		player.render(screen);

//	screen.renderSheet(0, 0, SpriteSheet.laser_projectiles_spritesheet, false);
//		screen.renderSprite(100, 100, SpriteSheet.laser_green_sprites.getSprite()[0], false);

	}

	public void add(Entity e) {
		e.initialize(this);
		if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if (e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else if (e instanceof Player) {
			player = ((Player) e);
		} else {
			entities.add(e);
		}

	}

	public List<Player> getPlayes() {
		return players;
	}

	@Deprecated
	public Player getPlayerAt(int index) {
		return player;
	}

	public Player getClientPlayer() {
		return player;
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Tile.voidTile;
		if (tiles[x + y * width] == Tile.colour_spawn_water)
			return Tile.spawn_water;
		if (tiles[x + y * width] == Tile.colour_spawn_blue_rock)
			return Tile.spawn_bluerock;
		if (tiles[x + y * width] == Tile.colour_spawn_bricks)
			return Tile.spawn_brickss;
		if (tiles[x + y * width] == Tile.colour_spawn_grass)
			return Tile.spawn_grass;
		if (tiles[x + y * width] == Tile.colour_spawn_purple_rock)
			return Tile.spawn_purplerock;
		if (tiles[x + y * width] == Tile.colour_spawn_rock)
			return Tile.spawn_rock;
		if (tiles[x + y * width] == Tile.colour_spawn_water)
			return Tile.spawn_water;

		if (tiles[x + y * width] == Tile.colour_level1_floor)
			return Tile.level1_floor;
		if (tiles[x + y * width] == Tile.colour_level1_wall1)
			return Tile.level1_wall1;
		if (tiles[x + y * width] == Tile.colour_level1_wall2)
			return Tile.level1_wall2;
		if (tiles[x + y * width] == Tile.colour_level1_wall3)
			return Tile.level1_wall3;
		if (tiles[x + y * width] == Tile.colour_level1_wall4)
			return Tile.level1_wall4;
		if (tiles[x + y * width] == Tile.colour_level1_wall_back)
			return Tile.level1_wall_back;
		if (tiles[x + y * width] == Tile.colour_level1_left)
			return Tile.level1_wall_left;
		if (tiles[x + y * width] == Tile.colour_level1_wall_right)
			return Tile.level1_wall1_right;
		if (tiles[x + y * width] == Tile.colour_level1_left_corner)
			return Tile.level1_left_corner;
		if (tiles[x + y * width] == Tile.colour_level1_right_corner)
			return Tile.level1_right_corner;
		if (tiles[x + y * width] == Tile.colour_level1_upper_corner)
			return Tile.level1_upper_corner;

		return Tile.voidTile;

	}

	public Tile getTile(Vector2i v) {
		return getTile(v.getX(), v.getY());
	}

	public void addLocationTrigerredEvents() {

	}

}
