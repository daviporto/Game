package menu;

import static jogo.util.ColorUtils.getColor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import jogo.Game;
import jogo.graphics.ui.UIButton;
import jogo.graphics.ui.UIManager;
import jogo.graphics.ui.UIPanel;

public class MenuController {
	private MenuFrame menuFrame;
	private UIManager uiManager;
	private Game game;

	private final MainMenuPanel mainMenuPanel;
	private final ControlsMenuPanel controlsMenuPanel;
	private UIPanel currentPanel;

	public  final static  Color DEFAULTBTNCOLOR = getColor(0x05101010);
	public final static  Color TRANSPARENT = getColor(0x00000000);

	public MenuController(Game game) {
		this.game = game;
		uiManager = new UIManager();
		mainMenuPanel = new MainMenuPanel(this);
		controlsMenuPanel = new ControlsMenuPanel(this);
		menuFrame = new MenuFrame(70);
		uiManager.addPanel(mainMenuPanel);
		currentPanel = mainMenuPanel;
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
		game.load();
	}

	public void save() {
		game.save();

	}

	public void controls() {
		uiManager.removePanel(currentPanel);
		currentPanel = controlsMenuPanel;
		uiManager.addPanel(currentPanel);
	}

	public void changeKey(UIButton b) {
		removePanel(currentPanel);
		currentPanel = new ChangeKeyBindingPanel(b);
		addPanel(currentPanel);
	}

	public void help() {

	}

	public void credits() {

	}

	public void removePanel(UIPanel p) {
		uiManager.removePanel(p);
	}

	public void addPanel(UIPanel p) {
		uiManager.addPanel(p);
	}

	public Image getImage() {
		return menuFrame.getImage();
	}

}
