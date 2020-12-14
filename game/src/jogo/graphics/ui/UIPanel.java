package jogo.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import jogo.entity.mob.player.Item;
import jogo.util.Vector2i;

public class UIPanel extends UIComponent {

	private List<UIComponent> components = new ArrayList<UIComponent>();


	protected UIPanel() {
		super();
	}

	public UIPanel(Vector2i position, Vector2i size) {
		super(position);
		this.position = position;
		this.size = size;
		color = new Color(0xcacaca);
	}

	public void setBackgroundColor(Color color) {
		this.color = color;
	}

	public void addComponent(UIComponent component) {
		component.init(this);
		components.add(component);
	}

	public void removeComponent(UIComponent componentToRemove) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i) == componentToRemove)
				components.remove(i);
		}
	}

	public void update() {
		for (UIComponent component : components) {
			component.setOffset(position);
			component.update();
		}
	}

	public void resize(Vector2i size) {
		this.size = size;
	}

	public void resizeX(int x) {
		this.size.x = x;
	}

	public void resizeY(int y) {
		this.size.y = y;
	}
	

	public UIComponent getComponentAt(Vector2i point) {
		for (UIComponent component : components) {
			if (component.isInside(point))
				return component;
		}
		return null;
	}
	
	public UIComponent popComponent() {
		if (components.isEmpty())
			return null;
		return components.get(components.size() - 1);
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(position.x, position.y, size.x, size.y);
		try {
			for (UIComponent component : components) {
				component.render(g);
			}	
		}catch (ConcurrentModificationException e) {
			//donsen't make any sense but is java 
			e.printStackTrace();
		}
	}
	
	

}
