package menu;

import static jogo.util.ColorUtils.getColor;
import static menu.MenuButtonFactory.buttonFactory;

import jogo.graphics.ui.UIButton;
import jogo.graphics.ui.UILabel;
import jogo.graphics.ui.UIPanel;
import jogo.util.Vector2i;

public class DeadMenuPanel extends UIPanel {
	private final int widthP = 400;
	private final int heightP = 500;
	private final int x = ((280 * 3 + 240) / 2) - widthP / 2;// half of screen
	private final int y = 0;

	public DeadMenuPanel(MenuController menuController) {
		super();
		setPosition(new Vector2i(x, y));
		setSize(new Vector2i(widthP, heightP));
		setColor(getColor(0x00646464));

		UILabel title = new UILabel(new Vector2i(120, 150), "you died");
		addComponent(title);

		UIButton b = buttonFactory(new Vector2i(50, 200), new Vector2i(400, MenuController.DEFAULTSIZE.y),
				"load last check point");
		b.setActinoListener(() -> menuController.lastCheckPoint());
		addComponent(b);

		b = buttonFactory(new Vector2i(115, 200 + MenuController.DEFAULTSIZE.y),
				new Vector2i(250, MenuController.DEFAULTSIZE.y), "restar level");
		b.setActinoListener(() -> menuController.resetLevel());
		addComponent(b);
		
		b = buttonFactory(new Vector2i(110, 320),new Vector2i(250, MenuController.DEFAULTSIZE.y) , "     quit     ");
		b.setActinoListener(() -> System.exit(0));
		addComponent(b);

	}

}
