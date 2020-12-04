package menu;

import static jogo.util.ColorUtils.getColor;
import static menu.MenuButtonFactory.buttonFactory;

import jogo.graphics.ui.UIButton;
import jogo.graphics.ui.UIPanel;
import jogo.util.Vector2i;

public class MainMenuPanel extends UIPanel {
	private final int widthP = 400;
	private final int heightP = 500;
	private final int x = ((280 * 3 + 240) / 2) - widthP / 2;
	private final int y = 0;

	private MenuController menuController;

	private static final Vector2i DEFAULTSIZE = new Vector2i(300, 60);

	public MainMenuPanel(MenuController menuController) {
		super();
		this.menuController = menuController;
		setPosition(new Vector2i(x, y));
		setSize(new Vector2i(widthP, heightP));
		setColor(getColor(0x00646464));
		createButtons();

	}

	public void createButtons() {
		UIButton b;
		b = buttonFactory(new Vector2i(20, 60), DEFAULTSIZE, "   New Game");
		b.setActinoListener(() -> menuController.newGame());
		addComponent(b);
		
		b = buttonFactory(new Vector2i(20, 120), DEFAULTSIZE, "   load");
		b.setActinoListener(() -> menuController.load());
		addComponent(b);
		b = buttonFactory(new Vector2i(20, 180), DEFAULTSIZE, "   Controls");
		b.setActinoListener(() -> menuController.controls());
		addComponent(b);
		b = buttonFactory(new Vector2i(20, 10), DEFAULTSIZE, "   continue");
		b.setActinoListener(() -> menuController.continueGame());
		addComponent(b);
		
		b = buttonFactory(new Vector2i(20, 240), DEFAULTSIZE, "   Save Game");
		b.setActinoListener(() -> menuController.save());
		addComponent(b);
		addComponent(buttonFactory(new Vector2i(20, 300), DEFAULTSIZE, "   Help"));
		addComponent(buttonFactory(new Vector2i(20, 360), DEFAULTSIZE, "   Credis"));

		b = buttonFactory(new Vector2i(20, 420), DEFAULTSIZE, "   Quit");
		b.setActinoListener(() -> System.exit(0));
		addComponent(b);
	}

}
