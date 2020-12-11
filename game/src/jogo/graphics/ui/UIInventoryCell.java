package jogo.graphics.ui;


import java.awt.Color;
import java.awt.Graphics;

import jogo.entity.mob.player.Item;
import jogo.input.Keyboard;
import jogo.util.Vector2i;

public class UIInventoryCell extends UIComponent{
	public final static Vector2i DEFAULTSIZE = new Vector2i(50,50);
	private Item item;
	private int keyToListen;
	
	public UIInventoryCell(Vector2i position, Vector2i size) {
		super(position, size);
		color = new Color(0xff00ff);
	}
	
	public UIInventoryCell(Vector2i position, Vector2i size, int keyToListen) {
		this(position, size);
		this.keyToListen = keyToListen;
	}
	
	public void update() {
		if (item != null && item.getConsumable() && Keyboard.firstPress(keyToListen)) {
			item.performAction();
			item.used();
		}
		
	}
	
	public void setKeyToObserv(int keyToListen) {
		this.keyToListen = keyToListen;
	}
	
	public void render(Graphics g) {
		int x = position.x + offset.x;
		int y = position.y + offset.y;
		g.setColor(color);
		g.fillRect(x, y, size.x, size.y);
		if(item != null) 
			g.drawImage(item.getIcom(), x, y, size.x, size.y, null);
		
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
}
