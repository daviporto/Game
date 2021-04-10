package jogo.graphics.ui;

import java.awt.Color;
import static jogo.util.ColorUtils.getColor;
import java.awt.Font;
import java.awt.Graphics;
import java.util.logging.Logger;

import jogo.util.Vector2i;

public class UILabel extends UIComponent {

	public String text;
	private Font font;
	public boolean dropShadow = false;
	public int dropShadowOffset = 2;
	protected Color foregroundColor;

	public final static Color DEFAULTFOREGROUNDCOLOR = Color.WHITE;
	public final static Color TRANSPARENT = getColor(0x00000000);

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

	public void transparent() {
		setForegroundColor(TRANSPARENT);
	}

	public boolean isTransparent() {
		return foregroundColor == TRANSPARENT;
	}

	public String getText() {
		return text;
	}
	
	public void SetText(String text) {
		this.text = text;
	}
	
	public void appendText(String text) {
		this.text += text;
	}
	
	public int textLenght() {
		return text.length();
	}

	public void blink() {
		if (isTransparent()) 
			foregroundColor = DEFAULTFOREGROUNDCOLOR;
		else 
			foregroundColor =TRANSPARENT;
		
	}

}
