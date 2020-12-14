package menu;

import static jogo.util.ColorUtils.getColor;
import static menu.MenuButtonFactory.buttonFactory;

import java.awt.event.KeyEvent;

import components.dataBase.DSDataBase;
import jogo.audio.AudioClip;
import jogo.graphics.ui.UIButton;
import jogo.graphics.ui.UILabel;
import jogo.graphics.ui.UIPanel;
import jogo.input.Keyboard;
import jogo.util.Vector2i;

public class ControlsMenuPanel extends UIPanel {
	private final int widthP = 800;
	private final int heightP = 500;
//	private final int x = ((280 * 3 + 240) / 2) - widthP / 2;// half of screen
	private final int x = 10;
	private final int y = 0;

	private final UIButton foward;
	private final UIButton left;
	private final UIButton backward;
	private final UIButton right;
	
	private final UIButton item1;
	private final UIButton item2;
	private final UIButton item3;
	private final UIButton item4;
	private final UIButton item5;
	private final UIButton item6;

	private final UIButton fireAbility;
	private final UIButton iceAbility;
	private final UIButton poisonAbility;
	
	private final UIButton map;
	private final UIButton abilitiesTree;

	private static final Vector2i DEFAULTSIZE = new Vector2i(300, 60);

	public ControlsMenuPanel(MenuController menuController) {
		super();
		setPosition(new Vector2i(x, y));
		setSize(new Vector2i(widthP, heightP));
		setColor(getColor(0x00646464));

		UILabel title = new UILabel(new Vector2i(250, 40), "to change clik on the button");
		addComponent(title);

		Vector2i bottonHalf = new Vector2i(10, heightP - DEFAULTSIZE.y - 10);
		UIButton voltar = buttonFactory(bottonHalf, DEFAULTSIZE, "MAIN MENU");
		voltar.setActinoListener(() -> menuController.backToMainMenu());
		addComponent(voltar);
		
		Vector2i salvar_position = new Vector2i(400, heightP - DEFAULTSIZE.y - 10);
		UIButton salvar = buttonFactory(salvar_position, DEFAULTSIZE, "SALVAR");
		salvar.setActinoListener(() -> save());
		addComponent(salvar);
		
		
		//first column
		foward = buttonFactory(new Vector2i(0,  DEFAULTSIZE.y * 1), DEFAULTSIZE, "foward = W");
		left = buttonFactory(new Vector2i(0,  DEFAULTSIZE.y * 2), DEFAULTSIZE, "left = A");
		backward = buttonFactory(new Vector2i(0,DEFAULTSIZE.y * 3), DEFAULTSIZE, "backward = S");
		right = buttonFactory(new Vector2i(0,  DEFAULTSIZE.y * 4), DEFAULTSIZE, "right = D");
		item1 = buttonFactory(new Vector2i(0,  DEFAULTSIZE.y * 5), DEFAULTSIZE,
				"item = 1");
		
		//second column
		item2 = buttonFactory(new Vector2i(DEFAULTSIZE.x + 50, DEFAULTSIZE.y * 1), DEFAULTSIZE,
				"item 2 = 2");
		item3 = buttonFactory(new Vector2i(DEFAULTSIZE.x + 50, DEFAULTSIZE.y * 2), DEFAULTSIZE,
				"item 3 = 3");
		item4 = buttonFactory(new Vector2i(DEFAULTSIZE.x + 50, DEFAULTSIZE.y * 3), DEFAULTSIZE,
				"item 4 = 4");
		item5 = buttonFactory(new Vector2i(DEFAULTSIZE.x + 50, DEFAULTSIZE.y * 4), DEFAULTSIZE,
				"item 5 = 5");
		item6 = buttonFactory(new Vector2i(DEFAULTSIZE.x + 50, DEFAULTSIZE.y * 5), DEFAULTSIZE,
				"item 6 = 6");
		
		//third column 
		fireAbility = buttonFactory(new Vector2i(DEFAULTSIZE.x *2  + 50 * 2, DEFAULTSIZE.y * 1), DEFAULTSIZE,
				"ability 1 = 7");
		iceAbility = buttonFactory(new Vector2i(DEFAULTSIZE.x *2  + 50 * 2, DEFAULTSIZE.y * 2), DEFAULTSIZE,
				"ability 2 = 8");
		poisonAbility = buttonFactory(new Vector2i(DEFAULTSIZE.x *2  + 50 * 2, DEFAULTSIZE.y * 3), DEFAULTSIZE,
				"ability 3 = 9");
		map = buttonFactory(new Vector2i(DEFAULTSIZE.x *2  + 50 * 2, DEFAULTSIZE.y * 4), DEFAULTSIZE,
				"map = M");
		abilitiesTree = buttonFactory(new Vector2i(DEFAULTSIZE.x *2  + 50 * 2, DEFAULTSIZE.y * 5), DEFAULTSIZE,
				"abilites tree = T");
		
		

		foward.setActinoListener(() -> menuController.changeKey(foward));
		left.setActinoListener(() -> menuController.changeKey(left));
		backward.setActinoListener(() -> menuController.changeKey(backward));
		right.setActinoListener(() -> menuController.changeKey(right));
		item1.setActinoListener(() -> menuController.changeKey(item1));
		
		item2.setActinoListener(() -> menuController.changeKey(item2));
		item3.setActinoListener(() -> menuController.changeKey(item3));
		item4.setActinoListener(() -> menuController.changeKey(item4));
		item5.setActinoListener(() -> menuController.changeKey(item5));
		item6.setActinoListener(() -> menuController.changeKey(item6));
		
		fireAbility.setActinoListener(() -> menuController.changeKey(fireAbility));
		iceAbility.setActinoListener(() -> menuController.changeKey(iceAbility));
		poisonAbility.setActinoListener(() -> menuController.changeKey(poisonAbility));
		map.setActinoListener(() -> menuController.changeKey(map));
		abilitiesTree.setActinoListener(() -> menuController.changeKey(abilitiesTree));

		addComponent(foward);
		addComponent(left);
		addComponent(backward);
		addComponent(right);
		addComponent(item1);
		
		addComponent(item2);
		addComponent(item3);
		addComponent(item4);
		addComponent(item5);
		addComponent(item6);
		
		addComponent(fireAbility);
		addComponent(iceAbility);
		addComponent(poisonAbility);
		addComponent(map);
		addComponent(abilitiesTree);
	}

	private void save() {
		AudioClip.buttonClick.play();
		DSDataBase keyBindin = new DSDataBase("keyBindin");
		keyBindin.pushObject(Keyboard.save());
		keyBindin.serializeToFile("saves/keys");
	}

	public void updateButtonText() {
		foward.setText(foward.getText().substring(0, foward.getText().lastIndexOf(' ')) +KeyEvent.getKeyText(Keyboard.foward));
		left.setText(left.getText().substring(0, left.getText().lastIndexOf(' ')) + KeyEvent.getKeyText(Keyboard.left));
		right.setText(right.getText().substring(0, right.getText().lastIndexOf(' ')) + KeyEvent.getKeyText(Keyboard.right));
		backward.setText(backward.getText().substring(0, backward.getText().lastIndexOf(' ')) + KeyEvent.getKeyText(Keyboard.backward));
		item1.setText(item1.getText().substring(0, item1.getText().lastIndexOf(' ')) + KeyEvent.getKeyText(Keyboard.item1));
		
		item2.setText(item2.getText().substring(0, item2.getText().lastIndexOf(' ')) + KeyEvent.getKeyText(Keyboard.item2));
		item3.setText(item3.getText().substring(0, item3.getText().lastIndexOf(' ')) + KeyEvent.getKeyText(Keyboard.item3));
		item4.setText(item4.getText().substring(0, item4.getText().lastIndexOf(' ')) + KeyEvent.getKeyText(Keyboard.item4));
		item5.setText(item5.getText().substring(0, item5.getText().lastIndexOf(' ')) + KeyEvent.getKeyText(Keyboard.item5));
		item6.setText(item6.getText().substring(0, item6.getText().lastIndexOf(' ')) + KeyEvent.getKeyText(Keyboard.item6));
		
		
		fireAbility.setText(fireAbility.getText().substring(0, fireAbility.getText().lastIndexOf(' ')) + KeyEvent.getKeyText(Keyboard.fireAbility));
		iceAbility.setText(iceAbility.getText().substring(0, iceAbility.getText().lastIndexOf(' ')) + KeyEvent.getKeyText(Keyboard.iceAbility));
		poisonAbility.setText(poisonAbility.getText().substring(0, poisonAbility.getText().lastIndexOf(' ')) + KeyEvent.getKeyText(Keyboard.poisoneAbility));
		map.setText(map.getText().substring(0, map.getText().lastIndexOf(' ')) + KeyEvent.getKeyText(Keyboard.map));
		abilitiesTree.setText(abilitiesTree.getText().substring(0, abilitiesTree.getText().lastIndexOf(' ')) + KeyEvent.getKeyText(Keyboard.abilitiesTree));
	}

}
