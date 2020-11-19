package menu;

import java.awt.Graphics;
import java.awt.Image;

import jogo.Game;
import jogo.graphics.ui.UIManager;

public class MenuController {
	private MenuFrame menuFrame;
	private MainMenuPanel mainMenuPanel;
	private UIManager uiManager;
	private Game game;

	public MenuController(Game game) {
		this.game = game;
		uiManager = new UIManager();
		mainMenuPanel = new MainMenuPanel(this);
		menuFrame = new MenuFrame(70);
		uiManager.addPanel(mainMenuPanel);
	}
	
	public void render(Graphics g) {
		menuFrame.render();
		uiManager.render(g);
	}
	
	public void update() {
		menuFrame.update();
		uiManager.update();
		
	}

	public void newGame() {
		game.unpause();
	}

	public void load() {

	}

	public void lontrols() {

	}

	public void help() {

	}

	public void credits() {

	}


	public Image getImage() {
		return menuFrame.getImage();
	}

	
}
