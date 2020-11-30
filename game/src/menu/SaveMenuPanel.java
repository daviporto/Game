package menu;

import jogo.util.Vector2i;

public class SaveMenuPanel {
	private final int widthP = 400;
	private final int heightP = 500;
	private final int x = ((280 * 3 + 240) / 2) - widthP / 2;
	private final int y = 0;

	private MenuController menuController;

	private static final Vector2i DEFAULTSIZE = new Vector2i(300, 60);
	
	public SaveMenuPanel(MenuController menuController) {
		this.menuController = menuController;
	}

}
