package jogo.graphics.ui;

import static jogo.util.ColorUtils.getColor;
import java.awt.Color;
import java.awt.Graphics;

import jogo.entity.mob.player.Item;
import jogo.input.Keyboard;
import jogo.util.Vector2i;

public class UIInventoryCell extends UIComponent implements Cloneable {
	public final static Vector2i DEFAULTSIZE = new Vector2i(50, 50);
	private Item item;
	private int keyToListen;
	private UILabel numberToBeRendered = null;

	public UIInventoryCell(Vector2i position, Vector2i size) {
		super(position, size);
		color = new Color(0xff00ff);
	}

	public UIInventoryCell(Vector2i position, Vector2i size, Item item) {
		super(position, size);
		this.item = item;
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

	public void setNumberToBeRendered(int numberToBeRendered) {
		this.numberToBeRendered = new UILabel(position, Integer.toString(numberToBeRendered));
		this.numberToBeRendered.setForegroundColor(getColor(0x80ffffff));
	}

	public void setNumberToBeRendered(int numberToBeRendered, Vector2i offset) {
		setNumberToBeRendered(numberToBeRendered);
		this.numberToBeRendered.setOffset(offset);
	}

	public UIInventoryCell clone() {
		return new UIInventoryCell(position.sumAndNew(this.offset), DEFAULTSIZE, this.item);
	}

	public void setKeyToObserv(int keyToListen) {
		this.keyToListen = keyToListen;
	}

	public void render(Graphics g) {
		int x = position.x + offset.x;
		int y = position.y + offset.y;
		g.setColor(color);
		g.fillRect(x, y, size.x, size.y);
		
		if (item != null)
			g.drawImage(item.getIcom(), x, y, size.x, size.y, null);
		
		if (numberToBeRendered != null)
			numberToBeRendered.render(g);
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void deleteOffset() {
		offset.set(0, 0);
	}
	
	public UILabel getNumberToBeRendered() {
		return numberToBeRendered;
	}
}
