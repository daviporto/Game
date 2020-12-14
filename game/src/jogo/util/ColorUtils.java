package jogo.util;

import static jogo.util.MathUtils.clamp;

import java.awt.Color;

public class ColorUtils {

	public static Color changeBrightness(int color, int amount) {
		int a = (color >> 24) & 0x000000ff;
		int r = (color >> 16) & 0x000000ff;
		int g = (color >> 8) & 0x000000ff;
		int b = (color >> 1) & 0x000000ff;

		r = clamp(r + amount, 0, 255);
		g = clamp(g + amount, 0, 255);
		b = clamp(b + amount, 0, 255);

		return new Color(r, g, b, a);
	}
	
	public static Color getColor(int color) {
		int a = (color >> 24) & 0x000000ff;
		int r = (color >> 16) & 0x000000ff;
		int g = (color >> 8) & 0x000000ff;
		int b = (color >> 1) & 0x000000ff;
		return new Color(r, g, b, a);
	}
}
