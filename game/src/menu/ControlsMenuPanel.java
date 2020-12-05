package menu;

import static jogo.util.ColorUtils.getColor;
import static menu.MenuButtonFactory.buttonFactory;

import java.awt.event.KeyEvent;

import components.dataBase.DSDataBase;
import jogo.graphics.ui.UIButton;
import jogo.graphics.ui.UILabel;
import jogo.graphics.ui.UIPanel;
import jogo.input.Keyboard;
import jogo.util.Vector2i;

public class ControlsMenuPanel extends UIPanel {
	private final int widthP = 400;
	private final int heightP = 500;
//	private final int x = ((280 * 3 + 240) / 2) - widthP / 2;// half of screen
	private final int x = 220;
	private final int y = 0;

	private final UIButton foward;
	private final UIButton left;
	private final UIButton backward;
	private final UIButton right;

	private final UIButton fireAbility;
	private final UIButton iceAbility;
	private final UIButton poisonAbility;

	private static final Vector2i DEFAULTSIZE = new Vector2i(300, 60);

	public ControlsMenuPanel(MenuController menuController) {
		super();
		setPosition(new Vector2i(x, y));
		setSize(new Vector2i(widthP, heightP));
		setColor(getColor(0x00646464));

		UILabel title = new UILabel(new Vector2i(70, 70), "to change clik on the button");
		addComponent(title);

		Vector2i bottonHalf = new Vector2i(10, heightP - DEFAULTSIZE.y - 10);
		UIButton voltar = buttonFactory(bottonHalf, DEFAULTSIZE, "MAIN MENU");
		voltar.setActinoListener(() -> menuController.backToMainMenu());
		addComponent(voltar);
		
		Vector2i salvar_position = new Vector2i(400, heightP - DEFAULTSIZE.y - 10);
		UIButton salvar = buttonFactory(salvar_position, DEFAULTSIZE, "SALVAR");
		salvar.setActinoListener(() -> save());
		addComponent(salvar);

		foward = buttonFactory(new Vector2i(0, 20 + DEFAULTSIZE.y * 1), DEFAULTSIZE, "foward = W");
		left = buttonFactory(new Vector2i(0, 20 + DEFAULTSIZE.y * 2), DEFAULTSIZE, "left = A");
		backward = buttonFactory(new Vector2i(0, 20 + DEFAULTSIZE.y * 3), DEFAULTSIZE, "backward = S");
		right = buttonFactory(new Vector2i(0, 20 + DEFAULTSIZE.y * 4), DEFAULTSIZE, "right = D");
		fireAbility = buttonFactory(new Vector2i(DEFAULTSIZE.x + 100, 20 + DEFAULTSIZE.y * 1), DEFAULTSIZE,
				"fire Ability = 1");
		iceAbility = buttonFactory(new Vector2i(DEFAULTSIZE.x + 100, 20 + DEFAULTSIZE.y * 2), DEFAULTSIZE,
				"ice Ability = 2");
		poisonAbility = buttonFactory(new Vector2i(DEFAULTSIZE.x + 100, 20 + DEFAULTSIZE.y * 3), DEFAULTSIZE,
				"poison Ability = 3");

		foward.setActinoListener(() -> menuController.changeKey(foward));
		left.setActinoListener(() -> menuController.changeKey(left));
		backward.setActinoListener(() -> menuController.changeKey(backward));
		right.setActinoListener(() -> menuController.changeKey(right));
		fireAbility.setActinoListener(() -> menuController.changeKey(fireAbility));
		iceAbility.setActinoListener(() -> menuController.changeKey(iceAbility));
		poisonAbility.setActinoListener(() -> menuController.changeKey(poisonAbility));

		addComponent(foward);
		addComponent(left);
		addComponent(backward);
		addComponent(right);
		addComponent(fireAbility);
		addComponent(iceAbility);
		addComponent(poisonAbility);
	}

	private void save() {
		DSDataBase keyBindin = new DSDataBase("keyBindin");
		keyBindin.pushObject(Keyboard.save());
		keyBindin.serializeToFile("saves/keys");
	}

	public void updateButtonText() {
		foward.setText(foward.getText().substring(0, foward.getText().lastIndexOf(' ')) + KeyEvent.getKeyText(Keyboard.foward));
		left.setText(left.getText().substring(0, left.getText().lastIndexOf(' ')) + KeyEvent.getKeyText(Keyboard.left));
		right.setText(right.getText().substring(0, right.getText().lastIndexOf(' ')) + KeyEvent.getKeyText(Keyboard.right));
		backward.setText(backward.getText().substring(0, backward.getText().lastIndexOf(' ')) + KeyEvent.getKeyText(Keyboard.backward));
		fireAbility.setText(fireAbility.getText().substring(0, fireAbility.getText().lastIndexOf(' ')) + KeyEvent.getKeyText(Keyboard.fireAbility));
		iceAbility.setText(iceAbility.getText().substring(0, iceAbility.getText().lastIndexOf(' ')) + KeyEvent.getKeyText(Keyboard.iceAbility));
		poisonAbility.setText(poisonAbility.getText().substring(0, poisonAbility.getText().lastIndexOf(' ')) + KeyEvent.getKeyText(Keyboard.poisoneAbility));
		
	}

}
