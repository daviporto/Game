package jogo.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;

import jogo.input.Keyboard;
import jogo.util.Vector2i;

public class UILineEntry extends UIComponent{
	private UILabel lbl;
	public UILineEntry(Vector2i position, Vector2i size, String text) {
		super(position, size);
		lbl = new UILabel(position, text);
		
	}
	
	public void setBackGroundColor(Color color) {
		lbl.setColor(color);
	}
	
	public void setForegroundColor(Color color) {
		lbl.setForegroundColor(color);
	}
	
	private void switchTrailingSimble(UILabel lbl) {
		if(lbl.getText().charAt(lbl.textLenght() - 1) == '|')
			lbl.SetText(lbl.getText().substring(0, lbl.textLenght()));
		else 
			lbl.appendText("|");
	}
	
	
	public void update() {
		Keyboard.backSpace();
		lbl.SetText(Keyboard.getText());
		lbl.update();
		
	}
	
	public void enableListener() {
		Keyboard.BACKSLASHON = true;
		Keyboard.waitingForText = true;
	}
	
	public void disableListener() {
		Keyboard.BACKSLASHON = false;
		Keyboard.waitingForText = false;
	}
	
	
	public void render(Graphics g) {
		lbl.render(g);
	}

	public String getText() {
		String name = Keyboard.getText();
		Keyboard.waitingForText = false;
		Keyboard.keysTyped.clear();
		return name;
	}
	
}
