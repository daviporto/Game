package menu;

import java.awt.Color;
import java.util.logging.Logger;

import jogo.Game;
import jogo.graphics.ui.UIButton;
import jogo.graphics.ui.UILabel;
import jogo.graphics.ui.UIPanel;
import jogo.util.Vector2i;
import static jogo.util.ColorUtils.getColor;

public class ChangeKeyBindingPanel extends UIPanel {
	private final UILabel change;
	int timer = 0;

	public ChangeKeyBindingPanel(UIButton b) {
		setPosition(new Vector2i(200, 100));
		setColor(MenuController.TRANSPARENT);
		setSize(new Vector2i(400, 200));
		String baseLabelTxt = b.getText();
		baseLabelTxt.substring(0, baseLabelTxt.indexOf(" "));
		addComponent(new UILabel(new Vector2i(10, 10), "digite a nova tecla para" + baseLabelTxt));
		change = new UILabel(new Vector2i(50, 70), baseLabelTxt.substring(0, baseLabelTxt.indexOf(' ')) + " = ");
		addComponent(change);
	}

	public void update() {
		super.update();
		timer++;
		if(timer % (Game.FPS  / 3) == 0)
			change.blink();
	}
	

}
