package jogo.util;

import java.awt.Color;

public class ColorUtils {

	public static Color getColor(int color) {
		int a = (color >> 24) & 0x000000ff;
		int r = (color >> 16) & 0x000000ff;
		int g = (color >> 8 ) & 0x000000ff;
		int b = (color >> 1 ) & 0x000000ff;
		return new Color(r, g, b, a);
	}
}
