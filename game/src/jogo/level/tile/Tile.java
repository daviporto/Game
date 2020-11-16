package jogo.level.tile;

import jogo.graphics.Screen;
import jogo.graphics.Sprite;
import jogo.level.tile.level1.Level1floor;
import jogo.level.tile.level1.Level1wall;
import jogo.level.tile.spawn_level.SpawnBricksTile;
import jogo.level.tile.spawn_level.SpawnGrassTile;
import jogo.level.tile.spawn_level.SpawnHalfGassRockTile;
import jogo.level.tile.spawn_level.SpawnRockTile;
import jogo.level.tile.spawn_level.SpawnWaterTile;

public class Tile {
	public int x, y;
	public Sprite sprite;
	
	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile whiteFlower = new WhiteFlowerTile(Sprite.whiteFlower);
	public static Tile brownFlower = new BrownFlower(Sprite.brownFlower);
	public static Tile trunk = new trunk(Sprite.trunk);
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);
	
	public static Tile spawn_grass= new SpawnGrassTile(Sprite.spawn_grass);
	public static Tile spawn_browngrass= new SpawnGrassTile(Sprite.spawn_browngrass);
	public static Tile spawn_water= new SpawnWaterTile(Sprite.spawn_water);
	public static Tile spawn_brickss= new SpawnBricksTile(Sprite.spawn_bricks);
	public static Tile spawn_bluerock= new SpawnRockTile(Sprite.spawn_bluerock);
	public static Tile spawn_purplerock= new SpawnRockTile(Sprite.spawn_purplerock);
	public static Tile spawn_rock= new SpawnRockTile(Sprite.spawn_rock);
	public static Tile spawn_halfgrasrock = new SpawnHalfGassRockTile(Sprite.spawn_halfgrasrock);
	
	public static Tile level1_wall1 = new Level1wall(Sprite.Level1_wall_fowar1);
	public static Tile level1_wall2 = new Level1wall(Sprite.Level1_wall_fowar2);
	public static Tile level1_wall3 = new Level1wall(Sprite.Level1_wall_fowar3);
	public static Tile level1_wall4 = new Level1wall(Sprite.Level1_wall_fowar4);
	public static Tile level1_wall_left = new Level1wall(Sprite.level1_wall_leftside);
	public static Tile level1_wall_back = new Level1wall(Sprite.Level1_wall_backwards);
	public static Tile level1_wall1_right = new Level1wall(Sprite.Level1_wall_rigthside);
	public static Tile level1_floor = new Level1floor(Sprite.Level1_floor);
	public static Tile level1_left_corner = new Level1wall(Sprite.Level1_left_corner);
	public static Tile level1_right_corner = new Level1wall(Sprite.Level1_right_corner);
	public static Tile level1_upper_corner = new Level1wall(Sprite.Level1_upper_corner);
	
	public final static int colour_spawn_water = 0xffb8ed83;
	public final static int colour_spawn_grass = 0xff9ccda4;
	public final static int colour_spawn_rock = 0xff7d8a7f;
	public final static int colour_spawn_blue_rock = 0xff370d9f;
	public final static int colour_spawn_purple_rock = 0xff7c0886;
	public final static int colour_spawn_bricks = 0xfff51212;
	
	public final static int colour_level1_floor = 0xff64086c;
	public final static int colour_level1_wall1 = 0xff24062e;
	public final static int colour_level1_wall2 = 0xff2a272b;
	public final static int colour_level1_wall3 = 0xff020e49;
	public final static int colour_level1_wall4 = 0xffa36fbb;
	public final static int colour_level1_wall_right = 0xff009f0b;
	public final static int colour_level1_wall_back = 0xff6b916e;
	public final static int colour_level1_left = 0xff24010a;
	public final static int colour_level1_left_corner = 0xff3b2412;
	public final static int colour_level1_right_corner = 0xff944b11;
	public final static int colour_level1_upper_corner = 0xff305835;
	
	

	
	
	
	public Tile (Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen) {
		
	}
	
	public boolean solid() {
		return false;
	}
	
}
