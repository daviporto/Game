package jogo.entity.mob.player;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import jogo.graphics.ui.UIActionListener;

public class Item {
	private int quantity;
	private BufferedImage icon;
	private boolean consumable = false;
	private UIActionListener actionListener;
	
	public Item(String path, boolean consumable, int quantity) {
		this.quantity = quantity;
		try {
			icon = ImageIO.read(this.getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.consumable = consumable;
	}
	
	public Item(String path) {
		this(path, false, 1);
	}
	
	public void setActionListener(UIActionListener actionListener) {
		this.actionListener = actionListener;
	}
	
	public boolean getConsumable() {
		return consumable;
	}
	
	public BufferedImage getIcom() {
		return icon;
	}

	public int getIconWidth() {
		return icon.getWidth();
	}
	
	public int getIconHeight() {
		return icon.getHeight();
	}
	
	public void setIcon(BufferedImage icon) {
		this.icon = icon;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void performAction() {
		actionListener.perform();
	}

	public void used() {
		quantity--;
	}
	
	
}
