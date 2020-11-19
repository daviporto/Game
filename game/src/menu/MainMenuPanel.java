package menu;

import static jogo.util.ColorUtils.getColor;

import java.awt.Color;

import jogo.graphics.ui.UIButton;
import jogo.graphics.ui.UIButtonListener;
import jogo.graphics.ui.UIPanel;
import jogo.util.Vector2i;

public class MainMenuPanel extends UIPanel {
	private final int widthP = 400;
	private final int heightP = 500;
	private final int x = ((280 * 3 +240) / 2 ) - widthP / 2;
	private final int y = 0;
	
	private MenuController menuController;

	private static final Vector2i DEFAULTSIZE = new Vector2i(300, 60); 
	private static final Color DEFAULTBTNCOLOR = getColor(0x05101010);
	private Btnlistener defaultbtnListener = new Btnlistener();


	public MainMenuPanel(MenuController menuController) {
		super();
		this.menuController = menuController;
		setPosition(new Vector2i(x, y));
		setSize(new Vector2i(widthP, heightP));
		setColor(getColor(0x00646464));
		createButtons();
	}

	public void createButtons() {
		UIButton b;
		b = buttonFactory(new Vector2i(20, 50), DEFAULTSIZE, "   New Game");
		b.setActinoListener(() -> menuController.newGame());
		
		addComponent(b);
		addComponent(buttonFactory(new Vector2i(20, 110), DEFAULTSIZE, "   Controls"));
		addComponent(buttonFactory(new Vector2i(20, 170), DEFAULTSIZE, "   Load Game"));
		addComponent(buttonFactory(new Vector2i(20, 230), DEFAULTSIZE, "   Save Game"));
		addComponent(buttonFactory(new Vector2i(20, 290), DEFAULTSIZE, "   Help"));
		addComponent(buttonFactory(new Vector2i(20, 350), DEFAULTSIZE, "   Credis"));
	}
	
	public UIButton buttonFactory(Vector2i position, Vector2i size, String label) {
		UIButton b  = new UIButton(position, size, label);
		b.setColor(DEFAULTBTNCOLOR);
		b.setForegroundColor(Color.white);
		b.setDropShadow(true);
		b.setButtonListener(defaultbtnListener);
		return b;
	}
	
	class Btnlistener extends UIButtonListener{
		public void entered(UIButton button) {
			button.setColor(getColor(0x80505050));
		}
		
		public void exited(UIButton button) {
			button.setColor(DEFAULTBTNCOLOR);
		}
		
		public void pressed(UIButton button) {
			button.setColor(0xff222222);
		}
		
		public void released(UIButton button) {
			button.setColor(0xffffffff);
		}

	}
	
}
