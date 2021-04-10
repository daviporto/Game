package menu;

import static jogo.util.ColorUtils.getColor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import components.Arrays.booleanArray;
import jogo.Game;
import jogo.audio.AudioClip;
import jogo.graphics.ui.UIButton;
import jogo.graphics.ui.UIManager;
import jogo.graphics.ui.UIPanel;
import jogo.util.Vector2i;
import menu.newGame.ChoseYourName;

public class MenuController {
	private MenuFrame menuFrame;
	private UIManager uiManager;
	private Game game;

	private final MainMenuPanel mainMenuPanel;
	private final ControlsMenuPanel controlsMenuPanel;
	private final ChoseYourName choseYourName;
	private UIPanel currentPanel;
	private final DeadMenuPanel deadMenuPanel;

	public final static Color DEFAULTBTNCOLOR = getColor(0x05101010);
	public final static Color TRANSPARENT = getColor(0x00000000);
	public static final Vector2i DEFAULTSIZE = new Vector2i(300, 60);

	public MenuController(Game game) {
		this.game = game;
		uiManager = new UIManager();
		mainMenuPanel = new MainMenuPanel(this);
		controlsMenuPanel = new ControlsMenuPanel(this);
		deadMenuPanel = new DeadMenuPanel(this);
		choseYourName = new ChoseYourName(this);
		menuFrame = new MenuFrame(70);
		currentPanel = mainMenuPanel;
//		currentPanel = choseYourName;
//		currentPanel = deadMenuPanel;
		addPanel(currentPanel);
	}

	public void updateButtonText() {
		controlsMenuPanel.updateButtonText();
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
		uiManager.removePanel(currentPanel);
		currentPanel = choseYourName;
		addPanel(currentPanel);
//		AudioClip.buttonClick.play();
	}

	public void newGame(int skinIdentifier, String name) {
		if (name == null || name.isEmpty()) name = "bourdieu";
		uiManager.removePanel(currentPanel);
		currentPanel = mainMenuPanel;
		addPanel(currentPanel);
		game.newGame(skinIdentifier, name);
//		AudioClip.buttonClick.play();
	}
	public void load() {
		AudioClip.buttonClick.play();
		game.load();
	}

	public void save() {
		AudioClip.buttonClick.play();
		game.save();

	}

	public void resetLevel() {
		AudioClip.buttonClick.play();
		game.resetLevel();
	}

	public void lastCheckPoint() {
		AudioClip.buttonClick.play();
		game.lastCheckPoint();
	}

	public void continueGame() {
		AudioClip.buttonClick.play();
		game.continueGame();
	}

	public void controls() {
		uiManager.removePanel(currentPanel);
		currentPanel = controlsMenuPanel;
		uiManager.addPanel(currentPanel);
		AudioClip.buttonClick.play();
	}

	public void changeKey(UIButton b) {
		AudioClip.buttonClick.play();
		removePanel(currentPanel);
		currentPanel = new ChangeKeyBindingPanel(b, this);
		addPanel(currentPanel);
	}

	public void help() {
		AudioClip.buttonClick.play();

	}

	public void credits() {
		AudioClip.buttonClick.play();

	}

	public void backToMainMenu() {
		AudioClip.buttonClick.play();
		removePanel(currentPanel);
		currentPanel = mainMenuPanel;
		addPanel(currentPanel);
	}

	// called after a new keybinding is done
	public void KeyBindingDone() {
		AudioClip.buttonClick.play();
		removePanel(currentPanel);
		currentPanel = controlsMenuPanel;
		addPanel(currentPanel);
	}

	public void playerDied() {
		game.pause();
		removePanel(currentPanel);
		currentPanel = deadMenuPanel;
		addPanel(currentPanel);
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
