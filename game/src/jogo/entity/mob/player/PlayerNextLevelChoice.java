package jogo.entity.mob.player;

import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


import jogo.Game;
import jogo.graphics.ui.UIActionListener;
import jogo.graphics.ui.UIButton;
import jogo.graphics.ui.UIButtonListener;
import jogo.graphics.ui.UILabel;
import jogo.graphics.ui.UIManager;
import jogo.graphics.ui.UIPanel;
import jogo.util.ImageUtils;
import jogo.util.Vector2i;

public class PlayerNextLevelChoice {
	UIPanel panel_Player_stats;
	private UIManager ui;
	private final int sizeX = 300;
	private final int sizeY = 210;
	private final int positionX = Game.getwindoewidth()/2  - sizeX/2;
	private final int positionY = Game.getwindowheight()/2 - sizeY/2;
	private BufferedImage manaButtonImage;
	private BufferedImage healthButtonImage;
	private choice made;
	
	public enum choice{
		HP, MANA;
	}
	
	public choice getChoice(){
		return made;
	}
	
	public void removeLayer() {
		ui.removePanel(panel_Player_stats);
	}
	
	
	public PlayerNextLevelChoice(UIManager ui) {
		Vector2i LabelPositionn= new Vector2i(positionX ,positionY);
		Vector2i Labelsize= new Vector2i(sizeX, sizeY);
		panel_Player_stats = (UIPanel) new UIPanel(LabelPositionn, Labelsize);
		panel_Player_stats.setColor(0x4f4f4f);
		this.ui = ui;
		constructPanel();
	}
	
	public void constructPanel() {
		made = null;
		String textOnLelUp_0 = "you leveled up!";
		Vector2i textPosition_0= new Vector2i(panel_Player_stats.position).add(new Vector2i(-sizeX + 100,  -sizeY + 90));
		UILabel textLevelUp_0 = new UILabel(textPosition_0, textOnLelUp_0);
		textLevelUp_0.setColor(0xffffff);
		textLevelUp_0.setFont(new Font("Verdana", Font.PLAIN, 18));
		panel_Player_stats.addComponent(textLevelUp_0);
		
		String textOnLelUp_1 = "chose one atibute to advance.";
		Vector2i textPosition_1 = new Vector2i(panel_Player_stats.position).add(new Vector2i(-sizeX + 50 , -sizeY + 110));
		UILabel textLevelUp_1= new UILabel(textPosition_1, textOnLelUp_1);
		textLevelUp_1.setColor(0xffffff);
		textLevelUp_1.setFont(new Font("Verdana", Font.PLAIN, 18));
		panel_Player_stats.addComponent(textLevelUp_1);
		
		
	try {
		manaButtonImage = ImageIO.read(PlayerNextLevelChoice.class.getResource("/buttons/manaPotionButton.png"));
	}catch(IOException e) {
		e.printStackTrace();
	}
	Vector2i manaButtonPosition = new Vector2i(panel_Player_stats.position).add(new Vector2i(-sizeX +70 ,  -sizeY + 120));
	
	UIButton manaImageButton= new UIButton(manaButtonPosition, manaButtonImage, new UIActionListener() {
		public void perform() {
			made = made.MANA;
		}
	});
	
	manaImageButton.setButtonListener(new UIButtonListener() {
		public void entered(UIButton button) {
			button.setImage(ImageUtils.changeBrightness(manaButtonImage, -50));
		}
		
		public void exited(UIButton button) {
			button.setImage(manaButtonImage);
		}
		
		public void pressed(UIButton button) {
			button.setImage(ImageUtils.changeBrightness(manaButtonImage, 50));
			
//			System.out.println(made);
		}
		
		public void released(UIButton button) {
			button.setImage(manaButtonImage);
		}
	});
	
	
	try {
		healthButtonImage = ImageIO.read(PlayerNextLevelChoice.class.getResource("/buttons/healthPotionButton.png"));
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	Vector2i heathButtonPosition = new Vector2i(manaImageButton.position).add(new Vector2i(120, 0));
	UIButton healthImageButton = new UIButton(heathButtonPosition, healthButtonImage, new UIActionListener() {
		public void perform() {
			made = made.HP;
		}
	});
	
	healthImageButton.setButtonListener(new UIButtonListener() {
			public void entered(UIButton button) {
				button.setImage(ImageUtils.changeBrightness(healthButtonImage, -50));
			}
			
			public void exited(UIButton button) {
				button.setImage(healthButtonImage);
				
			}
			
			public void pressed(UIButton button) {
				button.setImage(ImageUtils.changeBrightness(healthButtonImage, 50));

			}
			public void released(UIButton button) {
				button.setImage(healthButtonImage);
			}
		});
	
	
	
	panel_Player_stats.addComponent(manaImageButton);
	panel_Player_stats.addComponent(healthImageButton);
	}
	
	public void makeBuffchoice() {
		made = null;
		ui.addPanel(panel_Player_stats);
	}	
	

		
}	
