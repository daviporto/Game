package jogo.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	int gambiarra;
	private String path;
	public final int SIZE;
	public final int SPRITE_WIDTH, SPRITE_HEIGHT;//sprites
	private int width, height;//pixels
	public int [] pixels;

	private Sprite[] sprites;
	public static SpriteSheet witchStuff = new SpriteSheet("/textures/sheets/witchStuff.png", 256);
	
	public static SpriteSheet tiles = new SpriteSheet("/textures/sheets/spritesheet.png", 256);
	public static SpriteSheet spawn_level = new SpriteSheet("/textures/sheets/spawnlevel.png", 48);
	public static SpriteSheet level1 = new SpriteSheet("/textures/sheets/level1.png", 176, 16);
	public static SpriteSheet projectile_wizard = new SpriteSheet("/textures/sheets/projectiles/teste.png", 48);
	
	public static SpriteSheet bool_projectile_spritesheet = new SpriteSheet("/textures/projectiles/boolsProjectiles.png", 101, 56);
//	public static SpriteSheet bools_projecitles_sprites = new SpriteSheet(bool_projectile_spritesheet,0, 0, 7, 4, 15);
	
	public static SpriteSheet firebool_projectile_spritesheet = new SpriteSheet("/textures/projectiles/firebolProjectile.png", 220, 22);
	public static SpriteSheet firebools_projecitles_sprites = new SpriteSheet(firebool_projectile_spritesheet,0, 0, 10, 1, 22);
	
	public static SpriteSheet poisonbool_projectile_spritesheet = new SpriteSheet("/textures/projectiles/poisonProjectile.png", 220, 22);
	public static SpriteSheet poisonbools_projecitles_sprites = new SpriteSheet(poisonbool_projectile_spritesheet,0, 0, 10, 1, 22);
	
	public static SpriteSheet icebool_projectile_spritesheet = new SpriteSheet("/textures/projectiles/iceProjectile.png", 230, 23);
	public static SpriteSheet icebools_projecitles_sprites = new SpriteSheet(icebool_projectile_spritesheet,0, 0, 10, 1, 23);
	
	public static SpriteSheet basic_atk_projectile = new SpriteSheet("/textures/projectiles/basicAtk.png",11);
	public static SpriteSheet colouredBolls = new SpriteSheet("/textures/projectiles/boolsProjectiles.png", 101, 56);

	
	public static SpriteSheet Player = new SpriteSheet("/textures/entities/player.png", 96, 128);
	public static SpriteSheet player_down = new SpriteSheet(Player,0, 0, 3, 1, 32 );
	public static SpriteSheet player_up = new SpriteSheet(Player,0, 3, 3, 1, 32 );
	public static SpriteSheet player_left = new SpriteSheet(Player,0, 1, 3, 1, 32 );
	public static SpriteSheet player_right = new SpriteSheet(Player,0, 2, 3, 1, 32 );
	
	public static SpriteSheet Dummy  = new SpriteSheet("/textures/entities/Dummy.png", 96, 128);
	public static SpriteSheet Dummy_down = new SpriteSheet(Dummy,0, 0, 3, 1, 32 );
	public static SpriteSheet Dummy_up = new SpriteSheet(Dummy,0, 3, 3, 1, 32 );
	public static SpriteSheet Dummy_left = new SpriteSheet(Dummy,0, 1, 3, 1, 32 );
	public static SpriteSheet Dummy_right = new SpriteSheet(Dummy,0, 2, 3, 1, 32 );
	
	
	public static SpriteSheet Ghost = new SpriteSheet("/textures/entities/Ghost.png", 96, 128);
	public static SpriteSheet Ghost_down = new SpriteSheet(Ghost,0, 0, 3, 1, 32 );
	public static SpriteSheet Ghost_up = new SpriteSheet(Ghost,0, 3, 3, 1, 32 );
	public static SpriteSheet Ghost_left = new SpriteSheet(Ghost,0, 1, 3, 1, 32 );
	public static SpriteSheet Ghost_right = new SpriteSheet(Ghost,0, 2, 3, 1, 32 );
	
	public static SpriteSheet blueDummy = new SpriteSheet("/textures/entities/blueDummy.png", 96, 128);
	public static SpriteSheet blue_down = new SpriteSheet(blueDummy,0, 0, 3, 1, 32 );
	public static SpriteSheet blue_up = new SpriteSheet(blueDummy,0, 3, 3, 1, 32 );
	public static SpriteSheet blue_left = new SpriteSheet(blueDummy,0, 1, 3, 1, 32 );
	public static SpriteSheet blue_right = new SpriteSheet(blueDummy,0, 2, 3, 1, 32 );
	
	public static SpriteSheet pumpkinHead = new SpriteSheet("/textures/entities/pumpkinHead.png", 96, 128);
	public static SpriteSheet pumpkinHead_down = new SpriteSheet(pumpkinHead,0, 0, 3, 1, 32 );
	public static SpriteSheet pumpkinHead_up = new SpriteSheet(pumpkinHead,0, 3, 3, 1, 32 );
	public static SpriteSheet pumpkinHead_left = new SpriteSheet(pumpkinHead,0, 1, 3, 1, 32 );
	public static SpriteSheet pumpkinHead_right = new SpriteSheet(pumpkinHead,0, 2, 3, 1, 32 );
	
	
	public static SpriteSheet witch = new SpriteSheet("/textures/entities/witch.png", 78, 144);
	public static SpriteSheet witch_down = new SpriteSheet(witch,0, 0, 3, 1, 78/3, 144 >> 2 );
	public static SpriteSheet witch_up = new SpriteSheet(witch,0, 3, 3, 1,  78/3, 144>> 2 );
	public static SpriteSheet witch_left = new SpriteSheet(witch,0, 1, 3, 1,  78/3, 144>> 2  );
	public static SpriteSheet witch_right = new SpriteSheet(witch,0, 2, 3, 1, 78/3,144>> 2 );
	
	public static SpriteSheet maleVampire = new SpriteSheet("/textures/entities/maleVampire.png", 72, 140);
	public static SpriteSheet maleVampire_down = new SpriteSheet(maleVampire,0, 0, 3, 1, 72/3, 140 >> 2 );
	public static SpriteSheet maleVampire_up = new SpriteSheet(maleVampire,0, 3, 3, 1,  72/3, 140>> 2 );
	public static SpriteSheet maleVampire_left = new SpriteSheet(maleVampire,0, 1, 3, 1,  72/3, 140>> 2  );
	public static SpriteSheet maleVampire_right = new SpriteSheet(maleVampire,0, 2, 3, 1, 72/3, 140>> 2 );
	
	public static SpriteSheet famaleVampire = new SpriteSheet("/textures/entities/famaleVampire.png", 72, 140);
	public static SpriteSheet famaleVampire_down = new SpriteSheet(famaleVampire,0, 0, 3, 1, 72/3, 140 >> 2);
	public static SpriteSheet famaleVampire_up = new SpriteSheet(famaleVampire,0, 3, 3, 1,  72/3, 140>> 2);
	public static SpriteSheet famaleVampire_left = new SpriteSheet(famaleVampire,0, 1, 3, 1,  72/3, 140>> 2);
	public static SpriteSheet famaleVampire_right = new SpriteSheet(famaleVampire,0, 2, 3, 1, 72/3, 140>> 2 );
	
	public static SpriteSheet bat = new SpriteSheet("/textures/entities/bat.png", 71, 133);
	public static SpriteSheet bat_down = new SpriteSheet(bat,0, 0, 3, 1, 71/3, 133 >> 2);
	public static SpriteSheet bat_up = new SpriteSheet(bat,0, 3, 3, 1,  71/3, 133>> 2);
	public static SpriteSheet bat_left = new SpriteSheet(bat,0, 1, 3, 1,  71/3, 133>> 2);
	public static SpriteSheet bat_right = new SpriteSheet(bat,0, 2, 3, 1, 71/3, 133>> 2 );
	
	public static SpriteSheet blueMage = new SpriteSheet("/textures/entities/blueMage.png", 96, 128);
	public static SpriteSheet blueMage_down = new SpriteSheet(blueMage,0, 0, 3, 1, 32 );
	public static SpriteSheet blueMage_up = new SpriteSheet(blueMage,0, 3, 3, 1, 32 );
	public static SpriteSheet blueMage_left = new SpriteSheet(blueMage,0, 1, 3, 1, 32 );
	public static SpriteSheet blueMage_right = new SpriteSheet(blueMage,0, 2, 3, 1, 32 );
	
	
	

	

	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {
		int xx = x * spriteSize;
		int yy = y * spriteSize;
		int w = width * spriteSize;
		int h = height * spriteSize;
		
		if(width == height) SIZE = width;
		else SIZE = -1;
		SPRITE_WIDTH = w;
		SPRITE_HEIGHT = h;
		
		pixels = new int[w * h];
		for(int y0 = 0; y0 < h; y0++) {
			int yposition = yy + y0;
			for(int x0 = 0; x0 < w; x0++) {
				int xposition = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xposition + yposition * sheet.SPRITE_WIDTH];
			}
			
		}
		// xa and ya are in sprite precision
		// x0 and y0 are in pixel precision
		int frame = 0;
		sprites = new Sprite[width * height];
		for(int ya = 0; ya < height; ya++) {
			for(int xa = 0; xa < width; xa++) {
				int [] spritePixels = new int[spriteSize * spriteSize];
				for(int y0 = 0; y0 <spriteSize; y0++) {
					for(int x0 = 0; x0 < spriteSize; x0++) {
						spritePixels[x0 + y0 *spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * SPRITE_WIDTH];
					
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
				sprites[frame++] = sprite;
			}
		}
		 
	}
	
	
	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSizeX, int spriteSizeY) {
		int xx = x * spriteSizeX;
		int yy = y * spriteSizeY;
		int w = width * spriteSizeX;
		int h = height * spriteSizeY;
		
		if(width == height) SIZE = width;
		else SIZE = -1;
		SPRITE_WIDTH = w;
		SPRITE_HEIGHT = h;
		
		pixels = new int[w * h];
		for(int y0 = 0; y0 < h; y0++) {
			int yposition = yy + y0;
			for(int x0 = 0; x0 < w; x0++) {
				int xposition = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xposition + yposition * sheet.SPRITE_WIDTH];
			}
			
		}
		// xa and ya are in sprite precision
		// x0 and y0 are in pixel precision
		int frame = 0;
		sprites = new Sprite[width * height];
		for(int ya = 0; ya < height; ya++) {
			for(int xa = 0; xa < width; xa++) {
				int [] spritePixels = new int[spriteSizeX * spriteSizeY];
				for(int y0 = 0; y0 <spriteSizeY; y0++) {
					for(int x0 = 0; x0 < spriteSizeX; x0++) {
						spritePixels[x0 + y0 *spriteSizeX] = pixels[(x0 + xa * spriteSizeX) + (y0 + ya * spriteSizeX) * SPRITE_WIDTH];
					
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSizeX, spriteSizeY);
				sprites[frame++] = sprite;
			}
		}
		 
	}
	
	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize,double angle) {
		int xx = x * spriteSize;
		int yy = y * spriteSize;
		int w = width * spriteSize;
		int h = height * spriteSize;
		
		if(width == height) SIZE = width;
		else SIZE = -1;
		SPRITE_WIDTH = w;
		SPRITE_HEIGHT = h;
		
		pixels = new int[w * h];
		for(int y0 = 0; y0 < h; y0++) {
			int yposition = yy + y0;
			for(int x0 = 0; x0 < w; x0++) {
				int xposition = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xposition + yposition * sheet.SPRITE_WIDTH];
			}
			
		}
		// xa and ya are in sprite precision
		// x0 and y0 are in pixel precision
		int frame = 0;
		sprites = new Sprite[width * height];
		for(int ya = 0; ya < height; ya++) {
			for(int xa = 0; xa < width; xa++) {
				int [] spritePixels = new int[spriteSize * spriteSize];
				for(int y0 = 0; y0 <spriteSize; y0++) {
					for(int x0 = 0; x0 < spriteSize; x0++) {
						spritePixels[x0 + y0 *spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * SPRITE_WIDTH];
					
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
				sprites[frame++] = sprite;
			}
		}
		
		 
	}
	
	public SpriteSheet(String path, int size) {
		this.path = path;
		SIZE = size;
		SPRITE_WIDTH = size;
		SPRITE_HEIGHT = size;
		load();
	}

	public SpriteSheet(String path, int width, int height) {
		this.path = path;
		SIZE = -1;
		SPRITE_WIDTH = width;
		SPRITE_HEIGHT = height;
		pixels = new int[SPRITE_WIDTH * SPRITE_HEIGHT];
		load();
	}
	
	
	public Sprite[] getSprite() {
		return sprites;	
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int[] getPixels() {
		return pixels;
	}
	
	
	private void load() {
		try {
			System.out.print("Trying to load: " + path + "...");
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			System.out.println(" succeeded!");
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println(" failed!");
		}

	}

}

