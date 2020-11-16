package jogo.entity.mob;

import static jogo.util.MathUtils.clamp;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import jogo.Game;
import jogo.entity.mob.player.PlayerAbilities;
import jogo.entity.mob.player.PlayerNextLevelChoice;
import jogo.entity.mob.player.UpgradeAbility;
import jogo.entity.projectile.BasicProjectile;
import jogo.entity.projectile.Projectile;
import jogo.entity.projectile.WizardProjectile;
import jogo.events.Event;
import jogo.events.EventDispatcher;
import jogo.events.EventListener;
import jogo.events.messageEvents.LevelTrigered;
import jogo.events.messageEvents.MessageEventsManager;
import jogo.events.types.AbilityChosedEvent;
import jogo.events.types.MousePressedEvent;
import jogo.events.types.MouseReleasedEvent;
import jogo.graphics.AnimatedSprite;
import jogo.graphics.Screen;
import jogo.graphics.Sprite;
import jogo.graphics.SpriteSheet;
import jogo.graphics.ui.UIActionListener;
import jogo.graphics.ui.UIButton;
import jogo.graphics.ui.UIButtonListener;
import jogo.graphics.ui.UILabel;
import jogo.graphics.ui.UIManager;
import jogo.graphics.ui.UIPanel;
import jogo.graphics.ui.UIProgressBar;
import jogo.graphics.ui.UITextandNext;
import jogo.input.Keyboard;
import jogo.input.Mouse;
import jogo.util.ImageUtils;
import jogo.util.Vector2i;

public class Player extends Mob implements EventListener {
	private String name;
	private Keyboard imput;
	private Sprite sprite;
	private int anim = 0;
	private boolean walking = false;
	private int PLayerLevel;
	private int xp;
	private int currentMana, maxMana;
	private int xpToNextLevel;
	private int time;
	private int manaRecoverPerSecond, lifeRecoverPerSecond;
	private boolean shooting = false;
	private boolean choosingNextLevel = false;
	private boolean choosingNewAbility = false;
	private boolean blockShooting = false;
	private int fireRate = 0;
	private PlayerNextLevelChoice.choice choice;
	private PlayerAbilities playerAbilities;
	private UpgradeAbility upgradeAbility;
	private PlayerNextLevelChoice playerNextLevelChoice;
	private MessageEventsManager messageManager;
	private String whichLevel;
	private String textAndLevel;

	private KindofProjectile currentAbility = null;

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);
	private AnimatedSprite animSprite = down;

	private UIManager ui;
	private UIProgressBar uiHealthBar;
	private UIProgressBar uiPlayerLevelBar;
	private UIProgressBar UIManaBar;
	private UILabel PlayerLevelLabel;
	private UIButton button;
	private UILabel manaLabel;
	private UILabel hpLabel;

	private BufferedImage image;
	UIPanel panel = (UIPanel) new UIPanel(new Vector2i((280) * 3, 0), new Vector2i(80 * 3, 168 * 3)).setColor(0x4f4f4f);

	@Deprecated
	public Player(String name, Keyboard imput) {
		this.name = name;
		this.imput = imput;
		sprite = Sprite.player_dawn;

	}

	public Player(String name, int x, int y, Keyboard imput, MessageEventsManager messageManager) {
		this.messageManager = messageManager;
		this.name = name;
		this.x = x;
		this.y = y;
		this.imput = imput;

		// Player default attributes
		health = maxMana = currentMana = MaxHelath = 100;
		PLayerLevel = xp = 0;
		xpToNextLevel = 100;
		lifeRecoverPerSecond = manaRecoverPerSecond = 1;
		maxMana = currentMana = 100;

		ui = Game.getUIManager();
		ui.addPanel(panel);
		UILabel nameLabel = new UILabel(new Vector2i(40, 30), name);
		nameLabel.setColor(0xbbbbbb);
		nameLabel.setFont(new Font("Verdana", Font.PLAIN, 24));
		nameLabel.dropShadow = true;
		panel.addComponent(nameLabel);

		uiHealthBar = new UIProgressBar(new Vector2i(10, 215), new Vector2i(80 * 3 - 20, 20));
		uiHealthBar.setColor(0x6a6a6a);
		uiHealthBar.setForegroundColor(0xee3030);
		panel.addComponent(uiHealthBar);

		String howMuchhealth = Integer.toString(health);
		String valueOfMaxHealth = Integer.toString(MaxHelath);
		String textHelthBar = "HP: " + howMuchhealth + "/" + valueOfMaxHealth;
		Vector2i HealthBarTextPosition = new Vector2i(uiHealthBar.position).add(new Vector2i(2, 16));
		hpLabel = new UILabel(HealthBarTextPosition, textHelthBar);
		hpLabel.setColor(0xffffff);
		hpLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		panel.addComponent(hpLabel);

		uiPlayerLevelBar = new UIProgressBar(new Vector2i(10, 245), new Vector2i(80 * 3 - 20, 20));
		uiPlayerLevelBar.setColor(0x6a6a6a);
		uiPlayerLevelBar.setForegroundColor(0x13ed13);
		panel.addComponent(uiPlayerLevelBar);

		String witchLevel = Integer.toString(PLayerLevel);
		String textAndLevel = "LVL: " + witchLevel;
		Vector2i lvlPosition = new Vector2i(uiPlayerLevelBar.position).add(new Vector2i(2, 16));
		PlayerLevelLabel = new UILabel(lvlPosition, textAndLevel);
		PlayerLevelLabel.setColor(0xffffff);
		PlayerLevelLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		panel.addComponent(PlayerLevelLabel);

		UIManaBar = new UIProgressBar(new Vector2i(10, 275), new Vector2i(80 * 3 - 20, 20));
		UIManaBar.setColor(0x6a6a6a);
		UIManaBar.setForegroundColor(0x3085e8);
		panel.addComponent(UIManaBar);

		String howMuchMana = Integer.toString(currentMana);
		String ValueOfMaxMana = Integer.toString(maxMana);
		String textManaBar = "MANA: " + howMuchMana + "/" + ValueOfMaxMana;
		Vector2i manaPosition = new Vector2i(UIManaBar.position).add(new Vector2i(2, 16));
		manaLabel = new UILabel(manaPosition, textManaBar);
		manaLabel.setColor(0xffffff);
		manaLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		panel.addComponent(manaLabel);

		button = new UIButton(new Vector2i(10, 350), new Vector2i(100, 30), new UIActionListener() {
			public void perform() {
//				System.out.println("Action Performed!");
			}
		});
		button.setText("Hello");
		panel.addComponent(button);

		try {
			image = ImageIO.read(Player.class.getResource("/textures/home.png"));
			System.out.println(image.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}

		UIButton imageButton = new UIButton(new Vector2i(10, 360), image, new UIActionListener() {
			public void perform() {
				System.exit(0);
			}
		});
		imageButton.setButtonListener(new UIButtonListener() {
			public void entered(UIButton button) {
				button.setImage(ImageUtils.changeBrightness(image, -50));
			}

			public void exited(UIButton button) {
				button.setImage(image);
			}

//			blockShooting = ui.blockShooting();
//			
//			if(time% 5 == 0) {
//				updateLevel();
//			
//				if(imput.number1) playerAbilities.setCurrentAbility(1);
//				else if(imput.number2) playerAbilities.setCurrentAbility(2);
//				else if(imput.number3) playerAbilities.setCurrentAbility(3);
//			}
			public void pressed(UIButton button) {
				button.setImage(ImageUtils.changeBrightness(image, 50));
			}

			public void released(UIButton button) {
				button.setImage(image);
			}
		});
		panel.addComponent(imageButton);

		updateHealth();
		updateMana();
		updateLevel();

		playerNextLevelChoice = new PlayerNextLevelChoice();
		playerAbilities = new PlayerAbilities(this);
		playerAbilities.drawHablities();
		upgradeAbility = new UpgradeAbility();
	}

	public String getNmae() {
		return name;
	}

	public int getMana() {
		return currentMana;
	}

	public int getManaMaxima() {
		return maxMana;
	}

	public void setMana(int mana) {
		currentMana = mana;
		currentMana = clamp(currentMana, 0, maxMana);// Redundance
		uiPlayerLevelBar.setProgress(xp, xpToNextLevel);
		updateMana();
	}

	public void addOrRemoveMana(int mana) {
		currentMana += mana;
		currentMana = clamp(currentMana, 0, maxMana);
		updateMana();
	}

	public void setHealth(int health) {
		this.health += health;
		this.health = clamp(this.health, 0, MaxHelath);// Redundance
		updateHealth();
	}

	public void addOrRemoveHealth(int health) {
		this.health += health;
		this.health = clamp(this.health, 0, MaxHelath);
		updateHealth();
	}

	public void addLevel(int amount) {
		Logger.getGlobal().info("came here amount = " + Integer.toString(amount));
		xp += amount;
	}

	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePressed((MousePressedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseReleased((MouseReleasedEvent) e));
		dispatcher.dispatch(Event.Type.ABILITY_CHOSED, (Event e) -> Abilitychosed((AbilityChosedEvent) e));
	}

	public boolean Abilitychosed(AbilityChosedEvent e) {
//		System.out.println(currentAbility);
		if (e.getWichAbility() == PlayerAbilities.wichAbiliti.FIRE
				&& playerAbilities.getCurrentLevelAbiliti(PlayerAbilities.MASKFIREABILITY) > 0)
			currentAbility = KindofProjectile.FIREBOOL;

		else if (e.getWichAbility() == PlayerAbilities.wichAbiliti.ICE
				&& playerAbilities.getCurrentLevelAbiliti(PlayerAbilities.MASKICEABILITY) > 0)
			currentAbility = KindofProjectile.ICEBOOL;

		else if (e.getWichAbility() == PlayerAbilities.wichAbiliti.POISON
				&& playerAbilities.getCurrentLevelAbiliti(PlayerAbilities.MASKPOISONABILITY) > 0)
			currentAbility = KindofProjectile.POISONBOOL;

		return true;
	}

	public boolean onMousePressed(MousePressedEvent e) {
		if (Mouse.getX() > 280 * 3)
			return false;

		if (e.getButton() == MouseEvent.BUTTON1) {
			shooting = true;
			return true;
		}

		if (e.getButton() == MouseEvent.BUTTON3) {
			abilityShooting();
			return true;
		}

		return false;
	}

	public boolean onMouseReleased(MouseReleasedEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			shooting = false;
			return true;
		}
		return false;
	}

	private void abilityShooting() {
		if (fireRate <= 0 && currentMana >= WizardProjectile.MANA_COST && currentAbility != null) {
			double dx = Mouse.getX() - Game.getwindoewidth() / 2;
			double dy = Mouse.getY() - Game.getwindowheight() / 2;
			double direction = Math.atan2(dy, dx);
			shoot(x, y, direction, currentAbility);
			fireRate = WizardProjectile.FIRE_RATE;
			addOrRemoveMana(-WizardProjectile.MANA_COST);
		}
	}

	public void update() {
		time++;
		if (time % 1 == 0 & Keyboard.firstPress(KeyEvent.VK_L)) {
			System.out.println("here");
			xp = xp + 100;
		}

		if (walking)
			animSprite.update();
		else
			animSprite.setFrame(0);

		if (fireRate > 0)
			fireRate--;
		double xaxis = 0, yaxis = 0;
		double speed = 3;

		if (anim < 7500)
			anim++;
		else
			anim = 0;

		if (imput.up) {
			animSprite = up;
			yaxis -= speed;

		}
		if (imput.down) {
			animSprite = down;
			yaxis += speed;
		}
		if (imput.left) {
			animSprite = left;
			xaxis -= speed;

		}
		if (imput.right) {
			animSprite = right;
			xaxis += speed;
		}

		if (xaxis != 0 || yaxis != 0) {
			move(xaxis, yaxis);
			walking = true;
		} else {

			walking = false;
		}

		clear();
		updateShooting();

		if (xp >= xpToNextLevel) {
			nextLevel();
		}

		if (time % 60 == 0) {
			if (currentMana <= maxMana) {
				if (currentMana != maxMana)
					addOrRemoveMana(manaRecoverPerSecond);

				updateMana();
			}

			if (health <= MaxHelath) {
				if (health != MaxHelath)
					addOrRemoveHealth(lifeRecoverPerSecond);
				updateHealth();
			}
		}

		if (choosingNextLevel) {
			if (playerNextLevelChoice.getChoice() != null) {
				choice = playerNextLevelChoice.getChoice();
				playerNextLevelChoice.removeLayer();
				choosingNextLevel = false;

				if (PLayerLevel <= 3) {
					upgradeAbility.upgrade();
					choosingNewAbility = true;
				}

				updateLevel();
			}
		}

		if (choosingNewAbility) {
			if (upgradeAbility.GetChoice() != -1) {
				playerAbilities.unclockAbility(upgradeAbility.GetChoice());
				upgradeAbility.removeButton(upgradeAbility.GetChoice());
				choosingNewAbility = false;
				blockShooting = false;
			}
		}

	}

	private void updateMana() {
		String howMuchMana = Integer.toString(currentMana);
		String ValueOfMaxMana = Integer.toString(maxMana);
		String textManaBar = "MANA: " + howMuchMana + "/" + ValueOfMaxMana;
		manaLabel.update(textManaBar);

		UIManaBar.setProgress(currentMana, maxMana);
	}

	private void updateHealth() {
		String howMuchhealth = Integer.toString(health);
		String valueOfMaxHealth = Integer.toString(MaxHelath);
		String textHelthBar = "HP: " + howMuchhealth + "/" + valueOfMaxHealth;
		hpLabel.update(textHelthBar);

		uiHealthBar.setProgress(health, MaxHelath);
	}

	private void nextLevel() {
		PLayerLevel++;
//		messageManager.newLevel(PLayerLevel);
		xpToNextLevel = (int) (xpToNextLevel * 1.5);
		xp = 0;

		playerNextLevelChoice.makeBuffchoice();
		choosingNextLevel = true;
		blockShooting = true;
	}

	private void updateLevel() {
		uiPlayerLevelBar.setProgress(xp, xpToNextLevel);
		if (choice != null) {
			if (choice == PlayerNextLevelChoice.choice.HP)
				MaxHelath += 10;
			if (choice == PlayerNextLevelChoice.choice.MANA)
				maxMana += 10;
			addOrRemoveHealth(MaxHelath);
			addOrRemoveMana(maxMana);
			choice = null;
			whichLevel = Integer.toString(PLayerLevel);
			textAndLevel = "LVL: " + whichLevel;
			PlayerLevelLabel.update(textAndLevel);
		}

	}

	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved())
				level.getProjectiles().remove(i);
		}

	}

	private void updateShooting() {
		if (!shooting || fireRate > 0)
			return;
		double dx = Mouse.getX() - Game.getwindoewidth() / 2;
		double dy = Mouse.getY() - Game.getwindowheight() / 2;
		double direction = Math.atan2(dy, dx);
		shoot(x + 3, y + 16, direction, KindofProjectile.BASIC);
		fireRate = BasicProjectile.FIRE_RATE;

	}

	public void render(Screen screen) {

		sprite = animSprite.getSprite();
		screen.renderMob(x, y, sprite);
	}

	public int getLevel() {
		return PLayerLevel;
	}

	@Override
	public void Burning() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Freezening() {
		// TODO Auto-generated method stub

	}

	@Override
	public void poisoned() {
		// TODO Auto-generated method stub

	}

	public void AddLevelTrigered(MessageEventsManager m) {
		m.AddEvent(
				new LevelTrigered(1, true,
						new UITextandNext(UITextandNext.defoultTextPosition, UITextandNext.defoultTextsize, "level up",
								"voce subiu de nivel, escolha uma"
										+ "abilidade para desbloquear e um atributo para avançar" + "vida ou mana.",
								20)));
		m.AddEvent(new LevelTrigered(1,
				new UITextandNext(UITextandNext.defoultTextPosition, UITextandNext.defoultTextsize, "using ability",
						"Voce desbloqueou uma nova abilidade"
								+ "para usala clique no botao correspondete a ela, ou aperte 1 para fogo"
								+ ", 2 gele , ou 3 veneno",
						18)));
		m.AddEvent(
				new LevelTrigered(1,
						new UITextandNext(UITextandNext.defoultTextPosition, UITextandNext.defoultTextsize, "",
								"abilidades tem efeitos especial a de gelo diminui a velocidade"
										+ ",a de fogo da dano com o tempo, a de venono diminui o dano do oponente",
								18)));
	}
}
