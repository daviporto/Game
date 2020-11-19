package menu;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import jogo.entity.mob.MenuMob;
import jogo.entity.mob.Mob;
import jogo.graphics.Screen;
import jogo.level.tile.Tile;

public class MenuFrame {
	public static final Color TRANSPARENT = new Color(0x000A00AA);
	public final int height = 168 * 3 ;
	public final int width = 280 * 3 + 240;
	public final int scale = 3;
	private int imageHeight = height / scale;
	private int imageWidth = width / scale;

	BufferedImage image  = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
	int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private Screen screen;

	private Random random = new Random();

	private Mob[] mobs;

	public MenuFrame(int Nmobs) {

		screen = new Screen(imageWidth, imageHeight, pixels);

		mobs = new Mob[Nmobs];
		for (int i = 0; i < Nmobs; i++) {
			mobs[i] = new MenuMob(random.nextInt(imageWidth - 32), random.nextInt(imageWidth - 32), imageWidth,
					imageHeight);
		}
	}

	public void render() {

		screen.fillWithTile(Tile.grass);

		for (int i = 0; i < mobs.length; i++) {
			mobs[i].render(screen);
		}
	}

	public void update() {
		for (int i = 0; i < mobs.length; i++) {
			mobs[i].update();
		}
	}
	
	public Image getImage() {
		return image;
	}

}
