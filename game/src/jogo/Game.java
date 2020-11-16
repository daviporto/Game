package jogo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;

import components.dataBase.DSDataBase;
import jogo.entity.mob.Player;
import jogo.events.Event;
import jogo.events.messageEvents.MessageEventsManager;
import jogo.graphics.Font;
import jogo.graphics.Screen;
import jogo.graphics.layers.Layer;
import jogo.graphics.ui.UIManager;
import jogo.input.Keyboard;
import jogo.input.Mouse;
import jogo.level.Level;
import jogo.level.TileCoordinate;

public class Game extends Canvas implements Runnable, jogo.events.EventListener {
	private static final long serialVersionUID = 1L;
	private static int width = 280;
	private static int height = 168;
	private static int scale = 3;
	public static String title = "2d";

	private int time;

	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	private Player player;
	private MessageEventsManager messageManager;
	private static UIManager uiManager;
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
		addLayer(level);
		TileCoordinate playerSpawn = new TileCoordinate(16, 16);
		player = new Player("Davi", playerSpawn.x(), playerSpawn.y(), key, messageManager);
		level.add(player);
		font = new Font();
		addKeyListener(key);
		messageManager = new MessageEventsManager(player, uiManager);
		level.addMessageManager(messageManager);
		level.addLocationTrigerredEvents();

		Mouse mouse = new Mouse(this);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);

		player.AddLevelTrigered(messageManager);
	}

	public static int getwindoewidth() {
		return width * scale;
	}

	public static int getwindowheight() {
		return height * scale;
	}

	public static UIManager getUIManager() {
		return uiManager;
	}

	public void addLayer(Layer layer) {
		layerStack.add(layer);
	}

	public synchronized void start() {
		DSDataBase db = level.save();
		db.serializeToFile("save");
		db.enableDebbug();
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
		if (time > 0)
			time--;
		key.update();
		uiManager.update();

		// Update layers here
		for (int i = 0; i < layerStack.size(); i++) {
			layerStack.get(i).update();
		}

		if (time == 0) {
			messageManager.update();
			if (key.enter) uiManager.nextText();
			time = 10;
		}

	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(5);
			return;
		}
		screen.Clear();
		double xScroll = player.getX() - screen.width / 2;
		double yScroll = player.getY() - screen.height / 2;
		level.setScroll((int) xScroll, (int) yScroll);

		// Render layers here
		for (int i = 0; i < layerStack.size(); i++) {
			layerStack.get(i).render(screen);
		}

//		screen.renderSheet(0, 0, SpriteSheet.famaleVampire, false);
//		Logger.getGlobal().info(SpriteSheet.witch.toString());
//		font.render(0, 0,0x000000, "123456789", screen);
//		final Sprite[] sprites = SpriteSheet.witch_down.getSprite();
//		screen.renderSprite(130, 0, Sprite.green_bool, false);
//		screen.renderSprite(160, 0, sprites[2], false);
//		screen.renderSprite(190, 0, sprites[3], false);
		level.RenderCenario(screen);
//		for (int i = 0; i < pixels.length; i++) {
//			pixels[i] = screen.pixels[i];
//		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(new Color(0xff00ff));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, width * scale, height * scale, null);
		uiManager.render(g);

		// g.setColor(Color.white);
		// g.fillRect(Mouse.getX() -32, Mouse.gety() -32, 64, 64);
		// g.draawString("BUtton; " + Mouse.getButon(), Mouse.getX() -32, Mouse.gety()
		// -32);

		g.setColor(Color.white);
		g.drawString("x: " + player.getX() + " y: " + player.getY(), 700, 450);
//		uiManager.render(screen);
		g.dispose();
		bs.show();

	}

	public static void main(String[] args) {
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
		}
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle("first");
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();

	}

}
