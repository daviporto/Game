package jogo.graphics.ui;

import static jogo.util.ColorUtils.getColor;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import jogo.entity.mob.player.Item;
import jogo.input.Keyboard;
import jogo.util.Vector2i;

public class UIInventoryCell extends UIComponent implements Cloneable {
	public final static Vector2i DEFAULTSIZE = new Vector2i(50, 50);
	
	private int id;
	private Item item;
	private int keyToListen;
	private UILabel numberToBeRendered = null;
	public static final Font QuantityFont = new Font("sans serif", Font.PLAIN, 15);

	public UIInventoryCell(Vector2i position, Vector2i size, int id) {
		super(position, size);
		color = new Color(0xff00ff);
		this.id = id;
	}

	public UIInventoryCell(Vector2i position, Vector2i size,int id,  Item item ) {
		super(position, size);
		this.item = item;
		color = new Color(0xff00ff);
		this.id = id;
	}

	public UIInventoryCell(Vector2i position, Vector2i size, int id, int keyToListen) {
		this(position, size, id);
		this.keyToListen = keyToListen;
	}

	public void update() {

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
		return new UIInventoryCell(position.sumAndNew(this.offset), DEFAULTSIZE,this.id,  this.item);
	}

	public void setKeyToObserv(int keyToListen) {
		this.keyToListen = keyToListen;
	}

	public void render(Graphics g) {
		int x = position.x + offset.x;
		int y = position.y + offset.y;
		g.setColor(color);
		g.fillRect(x, y, size.x, size.y);
		
		if (item != null) {
			g.drawImage(item.getIcom(), x, y, size.x, size.y, null);
			g.setFont(QuantityFont);
			g.setColor(Color.BLACK);
			g.drawString("" + item.getQuantity(), x + size.x - 20 , y + 13);			
		}
		
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

	public int getKeyToListen() {
		return keyToListen;
	}
	
	public int getId() {
		return id;
	}
}
