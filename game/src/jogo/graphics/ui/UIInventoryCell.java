package jogo.graphics.ui;


import java.awt.Color;
import java.awt.Graphics;

import jogo.util.Vector2i;

public class UIInventoryCell extends UIComponent{
	public final static Vector2i DEFAULTSIZE = new Vector2i(50,50);
	public UIInventoryCell(Vector2i position, Vector2i size) {
		super(position, size);
		color = new Color(0xff00ff);
	}
	
	public UIInventoryCell(Vector2i position, Vector2i size, String path) {
		this(position, size);
		
	}
	
	public void render(Graphics g) {
		int x = position.x + offset.x;
		int y = position.y + offset.y;
		g.setColor(color);
		g.fillRect(x, y, size.x, size.y);
	}
}
