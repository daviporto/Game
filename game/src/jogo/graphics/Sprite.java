package jogo.graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import jogo.graphics.ui.UIButton;

public class Sprite {
	
	public final int SIZE;
	private int x, y;
	private int width, height;
	public int[] pixels;
	protected SpriteSheet sheet;
	
	public enum typeParticles{
		NORMAL, BLOOD, FIRE, SMOKE, YELLOW;
	}
	
	
	public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite whiteFlower = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite brownFlower = new Sprite(16, 2, 0, SpriteSheet.tiles);
	public static Sprite trunk = new Sprite(16, 3, 0, SpriteSheet.tiles);
	public static Sprite voidSprite = new Sprite(16, 0x000000);
	
//	spawn level sprites here
	
	public static Sprite spawn_grass = new Sprite(16, 0, 0 , SpriteSheet.spawn_level);
	public static Sprite spawn_browngrass = new Sprite(16, 1, 0 , SpriteSheet.spawn_level);
	public static Sprite spawn_water = new Sprite(16, 2, 0 , SpriteSheet.spawn_level);
	public static Sprite spawn_bricks = new Sprite(16, 0, 1 , SpriteSheet.spawn_level);
	public static Sprite spawn_bluerock = new Sprite(16, 1, 1 , SpriteSheet.spawn_level);
	public static Sprite spawn_purplerock = new Sprite(16, 2, 1 , SpriteSheet.spawn_level);
	public static Sprite spawn_rock = new Sprite(16, 0, 2 , SpriteSheet.spawn_level);
	public static Sprite spawn_halfgrasrock = new Sprite(16, 1, 2 , SpriteSheet.spawn_level);
	
// level 1 sprites here
	public static Sprite Level1_wall_fowar1 = new Sprite(16, 0, 0 , SpriteSheet.level1);
	public static Sprite Level1_wall_fowar2 = new Sprite(16, 1, 0 , SpriteSheet.level1);
	public static Sprite Level1_wall_fowar3 = new Sprite(16, 2, 0 , SpriteSheet.level1);
	public static Sprite Level1_wall_fowar4 = new Sprite(16, 3, 0 , SpriteSheet.level1);
	public static Sprite Level1_wall_rigthside = new Sprite(16, 4, 0 , SpriteSheet.level1);
	public static Sprite Level1_wall_backwards = new Sprite(16, 5, 0 , SpriteSheet.level1);
	public static Sprite level1_wall_leftside = new Sprite(16, 6, 0 , SpriteSheet.level1);
	public static Sprite Level1_floor = new Sprite(16, 7, 0 , SpriteSheet.level1);
	public static Sprite Level1_left_corner = new Sprite(16, 8, 0 , SpriteSheet.level1);
	public static Sprite Level1_right_corner = new Sprite(16, 9, 0 , SpriteSheet.level1);
	public static Sprite Level1_upper_corner = new Sprite(16, 10, 0 , SpriteSheet.level1);
	
	public static Sprite poison_cauldron = new Sprite(42, 0, 0, SpriteSheet.witchStuff);
	public static Sprite water_cauldron = new Sprite(42, 42, 0, SpriteSheet.witchStuff);
	public static Sprite witchTable = new Sprite(90, 60 , 0, 48, SpriteSheet.witchStuff);
	
//	player sprites here
	public static Sprite player_dawn = new Sprite(32, 1 , 4, SpriteSheet.tiles);
	public static Sprite player_dawn1 = new Sprite(32, 0 , 4, SpriteSheet.tiles);
	public static Sprite player_dawn2 = new Sprite(32, 2 , 4, SpriteSheet.tiles);
	
	
	
	public static Sprite player_up = new Sprite(32, 1 , 7, SpriteSheet.tiles);
	public static Sprite player_up1 = new Sprite(32, 0 , 7, SpriteSheet.tiles);
	public static Sprite player_up2 = new Sprite(32, 2 , 7, SpriteSheet.tiles);
	
	
	
	public static Sprite player_left = new Sprite(32, 1 , 5, SpriteSheet.tiles);
	public static Sprite player_left1 = new Sprite(32, 0 , 5, SpriteSheet.tiles);
	public static Sprite player_left2 = new Sprite(32, 2 , 5, SpriteSheet.tiles);
	
	
	
	public static Sprite player_right = new Sprite(32, 1 , 6, SpriteSheet.tiles);
	public static Sprite player_right1 = new Sprite(32, 0 , 6, SpriteSheet.tiles);
	public static Sprite player_right2 = new Sprite(32, 2 , 6, SpriteSheet.tiles);
	
	//projectiles sprites
	
	public static Sprite basic_atk_projectile = new Sprite(11, 0, 0, SpriteSheet.basic_atk_projectile);
	public static Sprite green_bool = new Sprite(11, 0, 0, SpriteSheet.colouredBolls);
	
	public static Sprite dummy = new Sprite(32, 0, 0 , SpriteSheet.Dummy_down);
	public static Sprite blueDummy = new Sprite(32, 0, 0 , SpriteSheet.Dummy_down);
	public static Sprite ghost = new Sprite(32, 0, 0 , SpriteSheet.Ghost_down);

	
	
	// particles
	public static Sprite particle_normal = new Sprite(3, 0x2dffff);
	public static Sprite blood_particle = new Sprite(3, 0x620704);
	public static Sprite yellow_particle = new Sprite(2, 0xffc90e);
	
	public static Sprite[] FireParticles = {
			  new Sprite(3, 0xe43b44) ,
			  new Sprite(3, 0xf77622) , 
			  new Sprite(3, 0xfee761),  
			  new Sprite(3, 0xeffffff) 
			};
	
	public static Sprite[] iceParticles= {
			  new Sprite(3, 0x2ce8f5) ,
			  new Sprite(3, 0x0099db) , 
			  new Sprite(3, 0x124e89),  
			  new Sprite(3, 0xeffffff) 
			};
	
	public static Sprite[] poisonParticles  = {
			  new Sprite(3, 0x265c42) ,
			  new Sprite(3, 0x3e8948) , 
			  new Sprite(3, 0x63c74d),  
			  new Sprite(3, 0xffffff) 
			};
	
	public static Sprite[] smokeParticles  = {
			  new Sprite(3, 0xfafafa) ,
			  new Sprite(3, 0x8f8f8f) , 
			  new Sprite(3, 0x575757),  
			  new Sprite(3, 0x3d3d3d) 
			};
	
	
	
	
	protected Sprite(SpriteSheet sheet, int width, int height) {
		
		SIZE = (width == height) ? width : -1;
		this.width = width; 
		this.height = height;
		this.sheet = sheet;
		
	} 
	
	
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	public Sprite(int sizeX, int sizeY, int x, int y, SpriteSheet sheet) {
		this.width = sizeX;
		this.height = sizeY;
		SIZE = width * height;
		pixels = new int[SIZE];
		this.x = x;
		this.y = y;
		this.sheet = sheet;
		load();
	}
	
	public Sprite(int width, int height, int colour) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width*height];
		setColour(colour);
	}
	
	public Sprite(int size, int colour ) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		setColour(colour);
	}

	public Sprite(int[] pixels, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.pixels = new int[pixels.length];
		System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
		
	}
	public Sprite(int[] pixels, int width, int height, SpriteSheet sheet) {
		this(pixels, width, height);
		this.sheet = sheet;
	}
	


	private void setColour(int colour) {
		for(int i = 0 ; i < width * height; i++) {
			pixels[i] = colour;
		}
	}
	
	public Sprite rotate(Sprite sprite, double angle) {
		return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height);
	}
	
	private static int[] rotate(int[] pixels, int width, int height, double angle) {
		int[] result = new int[width * height];
		
		double nx_x = rot_x(-angle, 1.0, 0.0);
		double nx_y = rot_y(-angle, 1.0, 0.0);
		double ny_x = rot_x(-angle, 0.0, 1.0);
		double ny_y = rot_y(-angle, 0.0, 1.0);
		
		double x0 = rot_x(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
		double y0 = rot_y(-angle, -width / 2.0, -height / 2.0) + height / 2.0;
		
		for (int y = 0; y < height; y++) {
			double x1 = x0;
			double y1 = y0;
			for (int x = 0; x < width; x++) {
				int xx = (int) x1;
				int yy = (int) y1;
				int col = 0;
				if (xx < 0 || xx >= width || yy < 0 || yy >= height) col = 0xffff00ff;
				else col = pixels[xx + yy * width];
				result[x + y * width] = col;
				x1 += nx_x;
				y1 += nx_y;
			}			
			x0 += ny_x;
			y0 += ny_y;
		}
		
		return result;
	}
	
	private static double rot_x(double angle, double x, double y) {
		double cos = Math.cos(angle - Math.PI / 2);
		double sin = Math.sin(angle - Math.PI / 2);
		return x * cos + y * -sin;
	}

	private static double rot_y(double angle, double x, double y) {
		double cos = Math.cos(angle - Math.PI / 2);
		double sin = Math.sin(angle - Math.PI / 2);
		return x * sin + y * cos;
	}
		
	public int getWidth() {
		return width;
		
	}
	
	public int getHeight() {
		return height;
		
	}
	
	public String getPath() {
		return sheet.getPath();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	private void load() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SPRITE_WIDTH];
			}
		}
	}

	public static Sprite[] split(SpriteSheet sheet) {
		int amount = (sheet.getWidth() * sheet.getHeight()) / (sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT);
		Sprite[] sprites = new Sprite[amount];
		int current = 0;
		int[] pixels = new int[sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT];
		for (int yp = 0; yp < sheet.getHeight() / sheet.SPRITE_HEIGHT; yp++) {
			for (int xp = 0; xp < sheet.getWidth() / sheet.SPRITE_WIDTH; xp++) {

				for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
					for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
						int xo = x + xp * sheet.SPRITE_WIDTH;
						int yo = y + yp * sheet.SPRITE_HEIGHT;
						pixels[x + y * sheet.SPRITE_WIDTH] = sheet.getPixels()[xo + yo * sheet.getWidth()];
					}
				}

				sprites[current++] = new Sprite(pixels, sheet.SPRITE_WIDTH, sheet.SPRITE_HEIGHT);
			}
		}

		return sprites;
	}
	
	public BufferedImage getBufferedImage() {
		BufferedImage image =  new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[] _pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(pixels, 0, _pixels, 0, width * height);
		return image.getSubimage(getX(), getY(), getWidth(), getHeight());
		
	}

	
}
