package jogo.graphics.ui;

import static jogo.util.StringtoPixels.getWidthStringPixels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import jogo.entity.mob.player.PlayerAbilities;
import jogo.graphics.Sprite;
import jogo.input.Mouse;
import jogo.util.Vector2i;

public class UIButton extends UIComponent {

	public UILabel label;
	private UIButtonListener buttonListener;
	private UIActionListener actionListener;

	private Image image;

	private boolean inside = false;
	private boolean pressed = false;
	private boolean ignorePressed = false;
	private boolean ignoreAction = false;
	
	public static BufferedImage ERRORIMAGE;
	
	static{
		try {
			ERRORIMAGE = ImageIO.read(PlayerAbilities.class.getResource("/buttons/error.png"));
		} catch (IOException e) {
			System.err.println("unable to load errorImage");
			System.exit(0);
		}
	}
	

	public UIButton(Vector2i position, Vector2i size, UIActionListener actionListener) {
		super(position, size);
		this.actionListener = actionListener;
		Vector2i lp = new Vector2i(position);
		lp.x += 4;
		lp.y += size.y - 2;
		label = new UILabel(lp, "");
		label.setColor(0x444444);
		label.active = false;
		init();
	}
	

	public UIButton(Vector2i position, Vector2i size, String title) {
		super(position, size);
		Vector2i lp = new Vector2i(position);
		lp.x += 4;
		lp.y += size.y - 2;
		label = new UILabel(lp, title);
		label.setColor(0x444444);
		label.active = true;
		init();
	}
	
	public UIButton(Vector2i position , String title) {
		this(position, new Vector2i(getWidthStringPixels(title, UIManager.DEFAULTFONT) + 20,50), title);
		
	}
	
	public UIButton(Vector2i position, BufferedImage image, UIActionListener actionListener) {
		super(position, new Vector2i(image.getWidth(), image.getHeight()));
		this.actionListener = actionListener;
		setImage(image);
		init();
	}
	
	public UIButton(Vector2i position, BufferedImage image) {
		super(position, new Vector2i(image.getWidth(), image.getHeight()));
		setImage(image);
		init();
	}
	
	public UIButton (Vector2i position, Sprite sprite) {
			this(position, sprite.getBufferedImage());
	}
	

	
	private void init() {
		setColor(0xaaaaaa);
		buttonListener = new UIButtonListener();
	}

	void init(UIPanel panel) {
		super.init(panel);
		if (label != null)
			panel.addComponent(label);
	}

	public void setButtonListener(UIButtonListener buttonListener) {
		this.buttonListener = buttonListener;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setText(String text) {
		if (text == "")
			label.active = false;
		else
			label.text = text;
	}


	public void performAction() {
		actionListener.perform();
	}

	public void ignoreNextPress() {
		ignoreAction = true;
	}
	
	public void setDropShadow(Boolean b) {
		label.setDropShadow(b);
	}
	
	public void setActinoListener(UIActionListener actionListener) {
		this.actionListener = actionListener;
	}

	public void update() {
		Rectangle rect = new Rectangle(getAbsolutePosition().x, getAbsolutePosition().y, size.x, size.y);
//		Logger.getGlobal().info(rec.toS);
		boolean leftMouseButtonDown = Mouse.getButton() == MouseEvent.BUTTON1;
		if (rect.contains(new Point(Mouse.getX(), Mouse.getY()))) {
			if (!inside) {
				if (leftMouseButtonDown)
					ignorePressed = true;
				else
					ignorePressed = false;
				buttonListener.entered(this);
			}
			inside = true;

			if (!pressed && !ignorePressed && leftMouseButtonDown) {
				buttonListener.pressed(this);
				pressed = true;
			} else if (Mouse.getButton() == MouseEvent.NOBUTTON) {
				if (pressed) {
					buttonListener.released(this);
					if (!ignoreAction) {
						if(actionListener != null) actionListener.perform();
					}
					else
						ignoreAction = false;
					pressed = false;
				}
				ignorePressed = false;
			}
		} else {
			if (inside) {
				buttonListener.exited(this);
				pressed = false;
			}
			inside = false;
		}
	}


	public void render(Graphics g) {
		int x = position.x + offset.x;
		int y = position.y + offset.y;
		if (image != null) {
			g.drawImage(image, x, y, size.getX(), size.getY(),  null);
		} else {
			g.setColor(color);
			g.fillRect(x, y, size.x, size.y);

			if (label != null)
				label.render(g);
		}
	}


	public void setForegroundColor(Color color) {
		label.setForegroundColor(color);	
	}


	public String getText() {
		return label.getText();
	}

}
