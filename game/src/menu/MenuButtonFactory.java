package menu;

import java.awt.Color;

import jogo.graphics.ui.UIButton;
import jogo.util.Vector2i;

public class MenuButtonFactory {

	public static UIButton buttonFactory(Vector2i position, Vector2i size, String label) {
		UIButton b = new UIButton(position, size, label);
		b.setColor(MenuController.DEFAULTBTNCOLOR);
		b.setForegroundColor(Color.white);
		b.setDropShadow(true);
		b.setButtonListener(new DefaultButtonListener());
		return b;
	}
	
}
