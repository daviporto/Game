package jogo.entity.mob.player;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import jogo.entity.mob.Mob.KindofProjectile;
import jogo.entity.mob.Player;
import jogo.events.types.AbilityChosedEvent;
import jogo.graphics.ui.UIButton;
import jogo.graphics.ui.UIButtonListener;
import jogo.graphics.ui.UIManager;
import jogo.graphics.ui.UIPanel;
import jogo.util.ImageUtils;
import jogo.util.Vector2i;

public class PlayerAbilities {
	private int time = 0;
	private  UIManager ui;
	private final  int sizeX = 220;
	private final  int sizeY = 50;
	private  Color color = new Color(0x4f4f40);
	private  BufferedImage[] fireAbilityImage = new BufferedImage[4];
	private  BufferedImage[] iceAbilityImage = new BufferedImage[4];
	private  BufferedImage[] poisonAbilityImage = new BufferedImage[4];
	private  UIPanel habilitiesPanel;
	public static final int HOWMUCHABILITIES = 3;
	private int[] currentLevelAbilities = new int [HOWMUCHABILITIES];
	private boolean[] MouseOnButton = new boolean [HOWMUCHABILITIES];
    public static final int MASKFIREABILITY = 0;
    public static final int MASKICEABILITY = 1;
    public static final int MASKPOISONABILITY = 2;
    private UIButton[] buttons = new UIButton[HOWMUCHABILITIES]; 
    private Player player;
	
    
	public enum wichAbiliti{
		FIRE, POISON, ICE;
	}
	private wichAbiliti currentAbility = null;
	
	
	
	public PlayerAbilities(Player player) {
		this.player = player;
		ui = player.getUIManager();
		catchImages();
		for(int i = 0; i < HOWMUCHABILITIES; i++) {
			currentLevelAbilities[i] = 0;
			MouseOnButton [i] = false;
		}
	}
	
	
	public void unclockAbility(int wichAility) {
		if(wichAility <= currentLevelAbilities.length && wichAility>= 0) {
			currentLevelAbilities[wichAility] = 1;
			if(wichAility == MASKFIREABILITY)
				fireAbilityImage[3] = fireAbilityImage[2];
			else if (wichAility == MASKICEABILITY)
				iceAbilityImage[3] = iceAbilityImage[2];
			else if(wichAility == MASKPOISONABILITY)
				poisonAbilityImage[3] = poisonAbilityImage[2];
		}
	}
	
	
	public int getCurrentLevelAbiliti(int ability) {
		if(ability < HOWMUCHABILITIES - 1)
		return currentLevelAbilities[ability];
		
		return -1;
	}
	
	private  void catchImages() {
		try {
			fireAbilityImage[0] = ImageIO.read(PlayerAbilities.class.getResource("/buttons/fireAbilityButton.png"));
			fireAbilityImage[1] = ImageUtils.changeBrightness(fireAbilityImage[0], -100);
			fireAbilityImage[2] = ImageUtils.changeBrightness(fireAbilityImage[0], +100);
			fireAbilityImage[3] = fireAbilityImage[1];
			
			iceAbilityImage[0] = ImageIO.read(PlayerAbilities.class.getResource("/buttons/iceAbilityButton.png"));
			iceAbilityImage[1] = ImageUtils.changeBrightness(iceAbilityImage[0], -100);
			iceAbilityImage[2] = ImageUtils.changeBrightness(iceAbilityImage[0], +100);
			iceAbilityImage[3] = iceAbilityImage[1];
			
			poisonAbilityImage[0] = ImageIO.read(PlayerAbilities.class.getResource("/buttons/poisonAbilityButton.png"));
			poisonAbilityImage[1] = ImageUtils.changeBrightness(poisonAbilityImage[0], -100);
			poisonAbilityImage[2] = ImageUtils.changeBrightness(poisonAbilityImage[0], +100);
			poisonAbilityImage[3] = poisonAbilityImage[1];
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	public KindofProjectile getCurrentAbility() {
		if(currentAbility == wichAbiliti.FIRE) return KindofProjectile.FIREBOOL;
		else if(currentAbility == wichAbiliti.ICE) return KindofProjectile.ICEBOOL;
		else if(currentAbility == wichAbiliti.POISON) return KindofProjectile.POISONBOOL;
		else return null;
	}
	
	public  void drawHablities() {
		Vector2i habilitiesPosition = new Vector2i(280 * 3 + 10, 155); 
		Vector2i habilitiesSize = new Vector2i(sizeX, sizeY); 
		habilitiesPanel = new UIPanel(habilitiesPosition, habilitiesSize);
		habilitiesPanel.setBackgroundColor(color);
		

		UIButton fireABilityImageButton = new UIButton(new Vector2i(0, 0), fireAbilityImage[3],()-> {
			player.Abilitychosed(new AbilityChosedEvent(wichAbiliti.FIRE));
		});

		
		fireABilityImageButton.setButtonListener(new UIButtonListener() {

			public void entered(UIButton button) {
				button.setImage(ImageUtils.changeBrightness(fireAbilityImage[0], -50));
				MouseOnButton[MASKFIREABILITY] = true;
			}
			
			public void exited(UIButton button) {
				button.setImage(fireAbilityImage[0]);
				MouseOnButton[MASKFIREABILITY] = false;
				
			}
			
			public void pressed(UIButton button) {
				button.setImage(ImageUtils.changeBrightness(fireAbilityImage[0], 50));
				
			}
			
			public void released(UIButton button) {
				button.setImage(fireAbilityImage[3]);
			}

		});
		
		UIButton iceABilityImageButton = new UIButton(new Vector2i(fireAbilityImage[0].getWidth() + 10, 0),
				iceAbilityImage[3],()-> player.Abilitychosed(new AbilityChosedEvent(wichAbiliti.ICE)));
											
		
		iceABilityImageButton.setButtonListener(new UIButtonListener() {

			public void entered(UIButton button) {
				button.setImage(ImageUtils.changeBrightness(iceAbilityImage[0], -50));
				MouseOnButton[MASKICEABILITY] = true;
			}
			
			public void exited(UIButton button) {
				button.setImage(iceAbilityImage[0]);
				MouseOnButton[MASKICEABILITY] = false;
			}
			
			public void pressed(UIButton button) {
				
				
			}
			
			public void released(UIButton button) {
				button.setImage(iceAbilityImage[3]);
			}

		});
		
		final int positionX = fireAbilityImage[0].getWidth() + iceAbilityImage[0].getWidth() + 20;
		UIButton poisonABilityImageButton = new UIButton(new Vector2i(positionX, 0), poisonAbilityImage[3],
								() ->player.Abilitychosed(new AbilityChosedEvent(wichAbiliti.POISON)));
		
		poisonABilityImageButton.setButtonListener(new UIButtonListener() {

			public void entered(UIButton button) {
				button.setImage(ImageUtils.changeBrightness(poisonAbilityImage[0], -50));
				MouseOnButton[MASKPOISONABILITY] = true;
			}
			
			public void exited(UIButton button) {
				button.setImage(poisonAbilityImage[0]);
				MouseOnButton[MASKPOISONABILITY] = false;
				
			}
			
			public void pressed(UIButton button) {
				
				
				
			}
			
			public void released(UIButton button) {
				button.setImage(poisonAbilityImage[3]);
			}

		});
		
		
		buttons[0] = fireABilityImageButton;
		buttons[1] = iceABilityImageButton;
		buttons[2] = poisonABilityImageButton;
		habilitiesPanel.addComponent(fireABilityImageButton);
		habilitiesPanel.addComponent(iceABilityImageButton);
		habilitiesPanel.addComponent(poisonABilityImageButton);
		ui.addPanel(habilitiesPanel);
	
	}
	public void makeAbilityChoice() {
		
	}

	public void update() {
		time++;
		if(time % 15 == 0) {
			for(int i = 0; i < buttons.length; i++) {
				if(i == MASKFIREABILITY & !MouseOnButton[MASKFIREABILITY])
					buttons[i].setImage(fireAbilityImage[3]);
				else if(i == MASKICEABILITY & !MouseOnButton[MASKICEABILITY])
					buttons[i].setImage(iceAbilityImage[3]);
				else if(i == MASKPOISONABILITY & !MouseOnButton[MASKPOISONABILITY])
					buttons[i].setImage(poisonAbilityImage[3]);
			}
		}

	}	
	
}
