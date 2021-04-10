package menu.newGame;

import java.awt.Color;
import java.util.logging.Logger;

import jogo.graphics.Sprite;
import jogo.graphics.SpriteSheet;
import jogo.graphics.ui.UIButton;
import jogo.graphics.ui.UILabel;
import jogo.graphics.ui.UILineEntry;
import jogo.graphics.ui.UIPanel;
import jogo.util.Vector2i;
import menu.MenuController;

public class ChoseYourName extends UIPanel {

	private final int widthP = 1000;
	private final int heightP = 250;
	private final int x = 50;
	private final int y = 20;
	private MenuController controller;
	private UILineEntry ln;
	public static final Color BACKGROUNDCOLOR = new Color(0x125252);
	private String lasSavedText;

	public ChoseYourName(MenuController controller) {
		setPosition(new Vector2i(x, y));
		this.controller = controller;
		setSize(new Vector2i(widthP, heightP));
		setColor(0xdddddd);

		UILabel title = new UILabel(new Vector2i(40, 40), "type your name : ");
		title.setForegroundColor(Color.BLACK);
		addComponent(title);
		
		ln = new UILineEntry(new Vector2i(390, 60), new Vector2i(500, 40), "");
		ln.setBackGroundColor(BACKGROUNDCOLOR);
		ln.setForegroundColor(Color.CYAN);
		ln.enableListener();
		addComponent(ln);

		UILabel choose = new UILabel(new Vector2i(40, 80), "chose a character ");
		choose.setForegroundColor(Color.BLACK);
		addComponent(choose);

		UIButton character1 = new UIButton(new Vector2i(40, 120), SpriteSheet.player1[0].getSprites()[0]);
		character1.setActinoListener(()-> triggerAction(1));
		character1.size.multiply(3);
		addComponent(character1);

		UIButton character2 = new UIButton(new Vector2i(40 + character1.size.getX() * 1 + 10 * 1, 120),
				SpriteSheet.player2[0].getSprites()[0]);
		character2.setActinoListener(()-> triggerAction(2));
		character2.size.multiply(3);
		addComponent(character2);

		UIButton character3 = new UIButton(new Vector2i(40 + character1.size.getX() * 2 + 10 * 2, 120),
				SpriteSheet.player3[0].getSprites()[0]);
		character3.setActinoListener(()-> triggerAction(3));
		character3.size.multiply(3);
		addComponent(character3);

		UIButton character4 = new UIButton(new Vector2i(40 + character1.size.getX() * 3 + 10 * 3, 120),
				SpriteSheet.player4[0].getSprites()[0]);
		character4.setActinoListener(()-> triggerAction(4));
		character4.size.multiply(3);
		addComponent(character4);

		UIButton character5 = new UIButton(new Vector2i(40 + character1.size.getX() * 4 + 10 * 4, 120),
				SpriteSheet.player5[0].getSprites()[0]);
		character5.setActinoListener(()-> triggerAction(5));
		character5.size.multiply(3);
		addComponent(character5);

		UIButton character6 = new UIButton(new Vector2i(40 + character1.size.getX() * 5 + 10 * 5, 120),
				SpriteSheet.player6[0].getSprites()[0]);
		character6.setActinoListener(()-> triggerAction(6));
		character6.size.multiply(3);
		addComponent(character6);

		UIButton character7 = new UIButton(new Vector2i(40 + character1.size.getX() * 6+ 10 * 6, 120),
				SpriteSheet.player7[0].getSprites()[0]);
		character7.setActinoListener(()-> triggerAction(7));
		character7.size.multiply(3);
		addComponent(character7);

		UIButton character8 = new UIButton(new Vector2i(40 + character1.size.getX() * 7 + 10 * 7, 120),
				SpriteSheet.player8[0].getSprites()[0]);
		character8.setActinoListener(()-> triggerAction(8));
		character8.size.multiply(3);
		addComponent(character8);

	}
	
	private void triggerAction(int identifier) {
		lasSavedText = ln.getText();
		controller.newGame(identifier, lasSavedText);
	}

}
