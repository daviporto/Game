package jogo.graphics.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import jogo.util.Vector2i;

public class UILabel extends UIComponent {

	public String text;
	private Font font;
	public boolean dropShadow = false;
	public int dropShadowOffset = 2;
	protected Color foregroundColor;
	
	private final static Color DEFAULTFOREGROUNDCOLOR = Color.BLACK;

	public UILabel(Vector2i position, String text) {
		super(position);
		font = new Font("Helvetica", Font.PLAIN, 32);
		this.text = text;
		color = new Color(0xff00ff);
		foregroundColor = DEFAULTFOREGROUNDCOLOR;
	}
	

	public UILabel setFont(Font font) {
		this.font = font;
		return this;
	}
	
	public Font getFont() {
		return font;
	}
	
	
	public void setForegroundColor(Color color) {
		foregroundColor = color;
	}

	
	public void update(String text) {
		this.text = text;
	}
	
	public void setDropShadow(boolean b) {
		dropShadow = b;
	}

	public void render(Graphics g) {
		if (dropShadow) {
			g.setFont(font);
			g.setColor(Color.BLACK);
			g.drawString(text, position.x + offset.x + dropShadowOffset, position.y + offset.y + dropShadowOffset);
		}

		g.setColor(foregroundColor);
		g.setFont(font);
		g.drawString(text, position.x + offset.x, position.y + offset.y);
	}

}
