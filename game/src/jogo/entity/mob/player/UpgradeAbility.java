package jogo.entity.mob.player;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import components.Arrays.intArray;
import components.Objects.DSObject;
import jogo.graphics.ui.UIActionListener;
import jogo.graphics.ui.UIButton;
import jogo.graphics.ui.UIManager;
import jogo.graphics.ui.UIPanel;
import jogo.util.Vector2i;

public class UpgradeAbility {
	private int[] currentLevelAbilities;
	
	private UIManager ui;
	private UIPanel UPAbilitiesPanel;
	private BufferedImage fireUpgradeImage;
	private BufferedImage iceUpgradeImage;
	private BufferedImage poisonUpgradeImage;
	private UIButton poisonAbilityButton;
	private UIButton iceAbilityButton;
	private UIButton FireAbilityButton;
	private int choiceMade = -1;

	private static final int PANELSIZEX = 500;
	private static final int PANELSIZEY = 150;

	public UpgradeAbility(UIManager ui) {
		this.ui = ui;
		currentLevelAbilities = new int[3];

		Vector2i upPosition = new Vector2i(180, 150);
		Vector2i upSize = new Vector2i(PANELSIZEX, PANELSIZEY);
		UPAbilitiesPanel = new UIPanel(upPosition, upSize);
		UPAbilitiesPanel.setColor(0x000000);

		try {
			fireUpgradeImage = ImageIO
					.read(UpgradeAbility.class.getResource("/textures/upgradeAbilities/upgradeFireAbility.png"));
			iceUpgradeImage = ImageIO
					.read(UpgradeAbility.class.getResource("/textures/upgradeAbilities/uogradeIceAbility.png"));
			poisonUpgradeImage = ImageIO
					.read(UpgradeAbility.class.getResource("/textures/upgradeAbilities/ugradePoisonAbility.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Vector2i fireButtonPosition = new Vector2i(0, 0);
		FireAbilityButton = new UIButton(fireButtonPosition, fireUpgradeImage, new UIActionListener() {
			public void perform() {
				choiceMade = 0;
			}
		});

		Vector2i iceButtonPosition = new Vector2i(fireButtonPosition.getX(), fireButtonPosition.getY());
		iceButtonPosition.add(new Vector2i(fireUpgradeImage.getWidth() + 30, 0));
		iceAbilityButton = new UIButton(iceButtonPosition, iceUpgradeImage, new UIActionListener() {
			public void perform() {
				choiceMade = 1;
			}
		});

		Vector2i poisonButtonPosition = new Vector2i(iceButtonPosition.getX(), iceButtonPosition.getY());
		poisonButtonPosition.add(new Vector2i(iceUpgradeImage.getWidth() + 30, 0));
		poisonAbilityButton = new UIButton(poisonButtonPosition, poisonUpgradeImage, new UIActionListener() {
			public void perform() {
				choiceMade = 2;
			}
		});

		UPAbilitiesPanel.addComponent(FireAbilityButton);
		UPAbilitiesPanel.addComponent(iceAbilityButton);
		UPAbilitiesPanel.addComponent(poisonAbilityButton);
	}

	public void upgrade() {
		drawChoser();
	}

	public int GetChoice() {
		return choiceMade;
	}

	public void drawChoser() {
		choiceMade = -1;
		ui.addPanel(UPAbilitiesPanel);

	}
	
	public void save(DSObject o) {
		o.pushArray(new intArray("abilities level", currentLevelAbilities));
	}

	public void removeButton(int button) {
		if (button == 0)
			UPAbilitiesPanel.removeComponent(FireAbilityButton);
		if (button == 1)
			UPAbilitiesPanel.removeComponent(iceAbilityButton);
		if (button == 2)
			UPAbilitiesPanel.removeComponent(poisonAbilityButton);
		ui.removePanel(UPAbilitiesPanel);

	}

}
