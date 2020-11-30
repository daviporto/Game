package menu;

import static jogo.util.ColorUtils.getColor;

import jogo.graphics.ui.UIButton;
import jogo.graphics.ui.UIButtonListener;

public class DefaultButtonListener extends UIButtonListener {

	public void entered(UIButton button) {
		button.setColor(getColor(0x80505050));
	}

	public void exited(UIButton button) {
		button.setColor(MenuController.DEFAULTBTNCOLOR);
	}

	public void pressed(UIButton button) {
		button.setColor(0xff222222);
	}

	public void released(UIButton button) {
		button.setColor(0xffffffff);
	}
}
