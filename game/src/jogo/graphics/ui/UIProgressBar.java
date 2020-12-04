package jogo.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;
import static jogo.util.MathUtils.clamp;

import org.w3c.dom.ranges.RangeException;

import jogo.util.Vector2i;

public class UIProgressBar extends UIComponent {

	private int progress; // 1 - 100

	private Color foregroundColor;

	public UIProgressBar(Vector2i position, Vector2i size) {
		super(position);
		this.size = size;

		foregroundColor = new Color(0xff00ff);
	}

	public void setProgress(int  atual, int maxima) {		
		progress = (atual * 100) / maxima;
		clamp(progress, 0, 100); // JUST REDUNDANCE
	}

	public void setForegroundColor(int color) {
		this.foregroundColor = new Color(color);
	}

	public double getProgress() {
		return progress;
	}

	public void update() {
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(position.x + offset.x, position.y + offset.y, size.x, size.y);
		
		g.setColor(foregroundColor);
		g.fillRect(position.x + offset.x, position.y + offset.y, (int) ((progress * size.x) / 100), size.y);
	}

}
