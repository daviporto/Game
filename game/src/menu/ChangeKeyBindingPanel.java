package menu;

import java.awt.event.KeyEvent;
import java.util.logging.Logger;

import javax.sound.sampled.ReverbType;
import javax.swing.text.JTextComponent.KeyBinding;

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
		addComponent(new UILabel(new Vector2i(10, 10), "digite a nova tecla para " + baseLabelTxt));
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
			rebind(buttonText.substring(0, buttonText.indexOf('=')).trim(), KeyEvent.getExtendedKeyCodeForChar(e.getKeyChar()));
			button.setText(buttonText.substring(0, buttonText.lastIndexOf('=') - 1).concat(" =" + e.getKeyChar()));
			menuController.KeyBindingDone();
		}
		
	}
	
	private void rebind(String txt, int keycode) {
		if(txt.equals("foward")) Keyboard.foward = keycode;
		else if(txt.equals("backward")) Keyboard.backward = keycode;
		else if(txt.equals("left")) Keyboard.left = keycode;
		else if(txt.equals("right")) Keyboard.right = keycode;
		else if(txt.equals("ice Ability")) Keyboard.iceAbility = keycode;
		else if(txt.equals("fire Ability")) Keyboard.fireAbility = keycode;
		else if(txt.equals("poison Ability")) Keyboard.poisoneAbility = keycode;
		else if(txt.equals("item1")) Keyboard.item1 = keycode;
		else if(txt.equals("item2")) Keyboard.item2 = keycode;
		else if(txt.equals("item3")) Keyboard.item3 = keycode;
		else if(txt.equals("item4")) Keyboard.item4 = keycode;
		else if(txt.equals("item5")) Keyboard.item5 = keycode;
		else if(txt.equals("item6")) Keyboard.item6 = keycode;
		else if(txt.equals("map")) Keyboard.map = keycode;
		else if(txt.equals("abilitiesTree")) Keyboard.abilitiesTree = keycode;
		
	}

}
