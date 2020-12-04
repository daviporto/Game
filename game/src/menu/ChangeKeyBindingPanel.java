package menu;

import java.awt.event.KeyEvent;

import jogo.Game;
import jogo.graphics.ui.UIButton;
import jogo.graphics.ui.UILabel;
import jogo.graphics.ui.UIPanel;
import jogo.input.Keyboard;
import jogo.util.Vector2i;

public class ChangeKeyBindingPanel extends UIPanel {
	private final UILabel change;
	private UIButton button;
	int timer = 0;
	private MenuController menuController;

	public ChangeKeyBindingPanel(UIButton b, MenuController menuController) {
		this.button = b;
		this.menuController = menuController;
		setPosition(new Vector2i(200, 100));
		setColor(MenuController.TRANSPARENT);
		setSize(new Vector2i(400, 200));
		String baseLabelTxt = b.getText();
		baseLabelTxt.substring(0, baseLabelTxt.indexOf(" "));
		addComponent(new UILabel(new Vector2i(10, 10), "digite a nova tecla para" + baseLabelTxt));
		change = new UILabel(new Vector2i(50, 70), baseLabelTxt.substring(0, baseLabelTxt.indexOf(' ')) + " = ");
		addComponent(change);
		Keyboard.clearTypedText();
		Keyboard.waitingForText = true;
	}

	public void update() {
		super.update();
		timer++;
		if(timer % (Game.FPS  / 3) == 0)
			change.blink();
		
		if (!Keyboard.keysTyped.isEmpty()) {
			Keyboard.waitingForText = false;
			KeyEvent e = Keyboard.keysTyped.get(0);
			Keyboard.clearTypedText();
			String buttonText = button.getText();
			button.setText(buttonText.substring(0, buttonText.lastIndexOf(' ')).concat(" " + e.getKeyChar()));
			menuController.KeyBindingDone();
		}
		
	}

}
