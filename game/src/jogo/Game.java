package jogo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import components.Fields.FieldShort;
import components.Objects.DSObject;
import components.dataBase.DSDataBase;
import jogo.entity.mob.Player;
import jogo.events.Event;
import jogo.events.playerEvents.PlayerEventsManager;
import jogo.graphics.Font;
import jogo.graphics.Screen;
import jogo.graphics.layers.Layer;
import jogo.graphics.ui.UIManager;
import jogo.input.Keyboard;
import jogo.input.Mouse;
import jogo.level.Level;
import jogo.level.Level.Levels;
import jogo.level.TileCoordinate;
import menu.MenuController;

public class Game extends Canvas implements Runnable, jogo.events.EventListener {
	private static final long serialVersionUID = 1L;
	private static int width = 280;
	private static int height = 168;
	private static int scale = 3;
	public static final Color RecColor = new Color(0xff00ff);
	public static String title = "2d";
	private boolean pause = true;

	private int time;

	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	private Player player;
	private PlayerEventsManager messageManager;
	private boolean inventoryActive = false;
	private UIManager uiManager;
	private MenuController menuController;
	private boolean running = false;
	public static int FPS = 60;

	private Screen screen;
	@SuppressWarnings("unused")
	private Font font;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private List<Layer> layerStack = new ArrayList<Layer>();
	
	

	public Game() {
		Dimension size = new Dimension(width * scale + 240, height * scale);
		setPreferredSize(size);

		screen = new Screen(width, height, pixels);
		uiManager = new UIManager();
		key = new Keyboard(this);
		frame = new JFrame();
		level = Level.teste;
		level.setUIManeger(uiManager);
		addLayer(level);
		font = new Font();

		reset();

		Mouse mouse = new Mouse(this);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);

		player.AddLevelTrigered(messageManager);

		frame.setResizable(false);
		frame.setTitle("first");
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		messageManager.newLevel(0);// add events triggered at level 0
//		menuController.addContinueButton();
		Keyboard.load();
		menuController.updateButtonText();
		level.saveCheckPoint("begin");
		start();
	}

	private void reset() {
		TileCoordinate playerSpawn = new TileCoordinate(16, 16);
		player = new Player("Davi", playerSpawn.x(), playerSpawn.y(), key, messageManager, uiManager);
		level.add(player);
		addKeyListener(key);
		messageManager = new PlayerEventsManager(player, uiManager);
		level.addMessageManager(messageManager);
		level.addLocationTrigerredEvents();
		level.addCheckPoints();
		menuController = new MenuController(this);
		level.setMenuController(menuController);
	}

	public void newGame() {
		reset();
		pause = false;
	}

	public void continueGame() {
		pause = false;
	}

	public void resetLevel() {
		level.loadCheckPonint("begin");
		player = level.getPlayer();
		pause = false;
	}

	public void lastCheckPoint() {
		level.loadCheckPonint("lastCheckPoint");
		player = level.getPlayer();
		pause = false;
	}

	public static int getwindoewidth() {
		return width * scale;
	}

	public static int getwindowheight() {
		return height * scale;
	}

	public UIManager getUIManager() {
		return uiManager;
	}

	public void addLayer(Layer layer) {
		layerStack.add(layer);
	}

	public void save() {
		DSDataBase db = level.save();
		DSObject level = new DSObject("level");
		Level.Levels witchLevel = this.level.getLevel();
		short levelID = -1;
		if (witchLevel == Levels.Teste1)
			levelID = 0;

		level.pushField(new FieldShort("levelID", levelID));
		db.pushObject(level);
//		db.enableDebbug();
		db.serializeToFile("saves/save");
	}

	public void load() {
		DSDataBase db = DSDataBase.deserializeFromFile("saves/save");
//		db.enableDebbug();
		DSObject o = db.getAndRemoveObject("level");
		short levelID = o.popField().getShort();
		if (levelID == 0)
			level = Level.teste;
		player = Player.load(db.getAndRemoveObject("player"), level, uiManager);
		level.setPlayer(player);
		level.load(db);
		pause = false;
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;

		requestFocus();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
//				frame.setTitle(title + "      ups: " + updates + "  fps: " + frames);
				System.out.println(title + "      ups: " + updates + "  fps: " + frames);
				updates = frames = 0;
			}
		}
		stop();
	}

	public void onEvent(Event event) {
		for (int i = layerStack.size() - 1; i >= 0; i--) {
			layerStack.get(i).onEvent(event);
		}
	}

	private void update() {

		if (Keyboard.firstPress(KeyEvent.VK_ESCAPE)) {
			if(!pause) {
				inventoryActive = false;
				player.getInventory().setVisibility(false);
				pause = true;
			}else {
				pause = !pause;
			}
		}
		
		if(Keyboard.firstPress(KeyEvent.VK_I)) {			
			if (!inventoryActive){
				layerStack.add(player.getInventory());
				player.setInventoryVisibility(true);
				inventoryActive = true;
			}else {
				layerStack.remove(player.getInventory());
				player.setInventoryVisibility(false);
				inventoryActive = false;
			}
		}

		if (!pause) {
			if (time > 0)
				time--;

			uiManager.update();
			// Update layers here
			for (int i = 0; i < layerStack.size(); i++) {
				layerStack.get(i).update();
			}

			if (time == 0) {
				messageManager.update();
				if (Keyboard.presed(KeyEvent.VK_ENTER))
					uiManager.nextText();
				time = 10;
			}
		} else {
			menuController.update();
		}

	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.Clear();
		double xScroll = player.getX() - screen.width / 2;
		double yScroll = player.getY() - screen.height / 2;
		level.setScroll((int) xScroll, (int) yScroll);

		Graphics g = bs.getDrawGraphics();
		// Render layers here
//		screen.renderSheet(0, 0, SpriteSheet.famaleVampire, false);
//		Logger.getGlobal().info(SpriteSheet.witch.toString());
//		font.render(0, 0,0x000000, "123456789", screen);
//		final Sprite[] sprites = SpriteSheet.witch_down.getSprite();
//		screen.renderSprite(130, 0, Sprite.green_bool, false);
//		screen.renderSprite(160, 0, sprites[2], false);
//		screen.renderSprite(190, 0, sprites[3], false);
//		for (int i = 0; i < pixels.length; i++) {
//			pixels[i] = screen.pixels[i];
//		}

		g.setColor(RecColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		if (!pause) {
			level.RenderCenario(screen);
			for (int i = 0; i < layerStack.size(); i++) {
				layerStack.get(i).render(screen);
			}
			g.drawImage(image, 0, 0, width * scale, height * scale, null);
			uiManager.render(g);
			g.drawImage(level.getMiniMapImage(), width * scale, height * scale - 200, 240, 200, null);
		} else {
			g.drawImage(menuController.getImage(), 0, 0, frame.getWidth(), frame.getHeight(), null);
			menuController.render(g);
		}

		// g.setColor(Color.white);
		// g.fillRect(Mouse.getX() -32, Mouse.gety() -32, 64, 64);
		// g.draawString("BUtton; " + Mouse.getButon(), Mouse.getX() -32, Mouse.gety()
		// -32);
		
		g.setColor(Color.white);
		g.drawString("x: " + player.getX() + " y: " + player.getY(), 700, 450);
		
		if(player.getMap()) {
			g.drawImage(level.getMap(), 0,0,frame.getWidth(), frame.getHeight(), null);
//			g.fillRect((player.getX()>>4) + 65, (player.getY() >> 4) + 75 , 4, 4);
		}
		
		g.dispose();
		bs.show();

	}

	public void unpause() {
		pause = false;
	}
	
	public void pause() {
		pause =  true;
	}

}
