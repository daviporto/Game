package jogo.graphics;

import java.util.Random;

import jogo.entity.mob.Mob;
import jogo.entity.projectile.Projectile;
import jogo.level.tile.Tile;

public class Screen {
	public int width;
	public int height;
	public int[] pixels;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	public int tiles[] = new int[MAP_SIZE * MAP_SIZE];
	public int xOffset, yOffset;

	private final int ALPHA_COL = 0xffff00ff;

	public Random random = new Random();

	public Screen(int width, int height, int[] pixels) {
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}

	public void Clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void fill(int color) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = color;
		}
	}

	public void renderTextCharacter(int xp, int yp, Sprite sprite, int color, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					continue;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if (col != ALPHA_COL && col != 0xff7f007f)
					pixels[xa + ya * width] = color;
			}
		}
	}

	public void renderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
			int ya = y + yp;
			for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					continue;
				pixels[xa + ya * width] = sheet.pixels[x + y * sheet.SPRITE_WIDTH];
			}
		}
	}

	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					continue;
				int colour = sprite.pixels[x + y * sprite.getWidth()];
				if (colour != 0xffff00ff)
					pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getWidth()];
			}
		}
	}

	public void renderTile(int xp, int yp, Tile tile) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < tile.sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < tile.sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					continue;
				pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
			}
		}
	}

	public void fillWithTile(Tile tile) {
		for (int y = 0; y < height; y += 16) {
			for (int x = 0; x < width; x += 16) {
				renderTile(x, y, tile);
			}
		}
	}

	public void renderprojecTile(int xp, int yp, Projectile p) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < p.getSpriteSize(); y++) {
			int ya = y + yp;
			for (int x = 0; x < p.getSpriteSize(); x++) {
				int xa = x + xp;
				if (xa < -p.getSpriteSize() || xa >= width || ya < 0 || ya >= height)
					continue;
				int colour = p.getSprite().pixels[x + y * p.getSpriteSize()];
				if (colour != 0xffff00ff)
					pixels[xa + ya * width] = colour;
			}
		}
	}

	public void renderMob(int xp, int yp, Mob mob) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < mob.getSprite().getHeight(); y++) {
			int ya = y + yp;
			int ys = y;
			for (int x = 0; x < mob.getSprite().getWidth(); x++) {
				int xa = x + xp;
				int xs = x;
				if (xa < -32 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int colour = mob.getSprite().pixels[xs + ys * mob.getSprite().getWidth()];
				if (colour != 0xFFFF00FF && colour != 0xffff5f60) {
					pixels[xa + ya * width] = colour;

				}

			}
		}
	}

	public void renderMob(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < 32; y++) {
			int ya = y + yp;
			for (int x = 0; x < 32; x++) {
				int xa = x + xp;
				if (xa < -32 || xa >= width || ya < 0 || ya >= width)
					break;
				if (xa < 0)
					xa = 0;
				int colour = sprite.pixels[x + y * 32];
				if (colour != 0xFFFF00FF)
					pixels[xa + ya * width] = colour;

			}
		}
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void drawRectangle(int xp, int yp, int width, int height, int color, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int x = xp; x < xp + width; x++) {
			if (yp >= this.height || x < 0 || x >= this.width)
				continue;
			if (yp > 0)
				pixels[x + yp * this.width] = color;
			if (yp + height >= this.height)
				continue;
			if (yp + height > 0)
				pixels[x + (yp + height) * this.width] = color;
		}

		for (int y = yp; y < yp + height; y++) {
			if (xp >= this.width || y <= 0 || y >= this.height)
				continue;
			if (xp > 0)
				pixels[xp + y * this.width] = color;
			if (xp + width >= this.width)
				continue;
			if (xp + width > 0)
				pixels[(xp + width) + y * this.width] = color;

		}

	}

	public void drawRectangle(int xp, int yp, int width, int height, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		int color = 0xff000000;
		for (int x = xp; x < xp + width; x++) {
			if (yp >= this.height || x < 0 || x >= this.width)
				continue;
			if (yp > 0)
				pixels[x + yp * this.width] = color;
			if (yp + height >= this.height)
				continue;
			if (yp + height > 0)
				pixels[x + (yp + height) * this.width] = color;
		}

		for (int y = yp; y < yp + height; y++) {
			if (xp >= this.width || y <= 0 || y >= this.height)
				continue;
			if (xp > 0)
				pixels[xp + y * this.width] = color;
			if (xp + width >= this.width)
				continue;
			if (xp + width > 0)
				pixels[(xp + width) + y * this.width] = color;

		}
	}
}
