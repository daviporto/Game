package jogo.graphics.ui;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jogo.Game;
import jogo.input.Mouse;

public class UIManager {
	private int timer = 0;
	private List<UITextandNext> texts = new ArrayList<UITextandNext>();	
	private List<UIPanel> panels = new ArrayList<UIPanel>();	
	private boolean blockShooting = false;
	
	public static Font DEFAULTFONT =  new Font("Helvetica", Font.PLAIN, 32);

	public UIManager() {
		
	}
	public void nextText() {
		if(!texts.isEmpty()) {
			texts.remove(0);
		}
		
	}

	public void addPanel(UIPanel panel) {
		panels.add(panel);
	}
	public void addTextPanel(UITextandNext text) {
		texts.add(text);
	}
	
	public void  addTextPanelFirstPositionAndRemove(UITextandNext text) {
		if(!texts.isEmpty()) {
			texts.add(0, text);
		}else texts.add(text);
		Logger.getGlobal().info("methos called");
	}
	
	
	public void removePanel(UIPanel panelToRemove) {
		for (int i = 0; i < panels.size(); i++) {
			if(panels.get(i) == panelToRemove ) panels.remove(i); 
		}
	}

	
	public void update() {
		timer++;
		 if(timer % Game.FPS == 0) {
			 if(!texts.isEmpty())
				 texts.get(0).updateLifeTime();
		 }
		for (UIPanel panel : panels) {
			panel.update();
		}
		
		for (UITextandNext text : texts) {
			text.update();
		}
		
		if(!texts.isEmpty()) {
			UITextandNext current = texts.get(0);
			current.setButton(true);
			if(current.isDead()) texts.remove(0);
		}
		
		if(!texts.isEmpty()) {
			if(texts.get(0).ContainMouseOnTextPanel(Mouse.getX(), Mouse.getY())) 
				blockShooting = true;
			else blockShooting = false;
				
		}else blockShooting = false;
	}
	
	public boolean blockShooting() {
		return blockShooting;
	}
	
	public  void render(Graphics g) {
		for (UIPanel panel : panels) {
			panel.render(g);
		}
		if(!texts.isEmpty()) texts.get(0).render(g);
	}
	
}