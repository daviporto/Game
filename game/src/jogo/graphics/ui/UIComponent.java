package jogo.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;

import jogo.util.Vector2i;

public class UIComponent {

	public Vector2i position, size;
	protected Vector2i offset;
	public Color color;
	protected UIPanel panel;
	
	public boolean active = true;
	
	protected UIComponent() {
		offset = new Vector2i();
		
	}
	
	public UIComponent(Vector2i position) {
		this.position = position;
		offset = new Vector2i();
	}
	
	public void setPosition(Vector2i position) {
		this.position = position;
	}
	
	public void setSize(Vector2i size) {
		this.size = size;
	}
	
	public UIComponent(Vector2i position, Vector2i size) {
		this.position = position;
		this.size = size;
		offset = new Vector2i();
	}

	void init(UIPanel panel) {
		this.panel = panel;
	}
	
	public UIComponent setColor(int color) {
		this.color = new Color(color);
		return this;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public void update() {
	}
	
	public void render(Graphics g) {
	}
	
	public Vector2i getAbsolutePosition() {
		return new Vector2i(position).add(offset);
	}
	
	void setOffset(Vector2i offset) {
		this.offset = offset;
	}

	
}
