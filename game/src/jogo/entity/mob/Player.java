package jogo.entity.mob;

import static jogo.util.MathUtils.clamp;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import components.Fields.FieldBoolean;
import components.Fields.FieldByte;
import components.Fields.FieldInt;
import components.Objects.DSObject;
import components.string.DSString;
import jogo.Game;
import jogo.audio.AudioClip;
import jogo.entity.mob.player.PlayerAbilities;
import jogo.entity.mob.player.PlayerNextLevelChoice;
import jogo.entity.mob.player.UpgradeAbility;
import jogo.entity.projectile.BasicProjectile;
import jogo.entity.projectile.Projectile;
import jogo.entity.projectile.WizardProjectile;
import jogo.events.Event;
import jogo.events.EventDispatcher;
import jogo.events.EventListener;
import jogo.events.playerEvents.LevelTrigered;
import jogo.events.playerEvents.PlayerEventsManager;
import jogo.events.types.MousePressedEvent;
import jogo.events.types.MouseReleasedEvent;
import jogo.graphics.AnimatedSprite;
import jogo.graphics.Screen;
import jogo.graphics.Sprite;
import jogo.graphics.SpriteSheet;
import jogo.graphics.ui.UIButton;
import jogo.graphics.ui.UILabel;
import jogo.graphics.ui.UIManager;
import jogo.graphics.ui.UIPanel;
import jogo.graphics.ui.UIProgressBar;
import jogo.graphics.ui.UITextandNext;
import jogo.input.Keyboard;
import jogo.input.Mouse;
import jogo.level.Level;
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
	private PlayerEventsManager messageManager;
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

	private Player() {

	}

	@Deprecated
	public Player(String name, Keyboard imput) {
		this.name = name;
		this.imput = imput;
		sprite = Sprite.player_dawn;

	}

	public Player(String name, int x, int y, Keyboard imput, PlayerEventsManager messageManager, UIManager ui) {
		this.messageManager = messageManager;
		this.name = name;
		this.x = x;
		this.y = y;
		this.imput = imput;

		// Player default attributes
		health = maxMana = currentMana = MaxHealth = 100;
		PLayerLevel = xp = 0;
		xpToNextLevel = 100;
		lifeRecoverPerSecond = manaRecoverPerSecond = 1;
		maxMana = currentMana = 100;

		this.ui = ui;
		createUIConponents();

		updateHealth();
		updateMana();
		updateLevel();

		playerNextLevelChoice = new PlayerNextLevelChoice(ui);
		playerAbilities = new PlayerAbilities(ui, currentAbility);
		playerAbilities.drawHablities();
		upgradeAbility = new UpgradeAbility(ui);
	}

	public DSObject save() {
		DSObject o = new DSObject("player");
		o.pushString(new DSString("playerName", name));
		super.save(o);
		o.pushField(new FieldInt("PLayerLevel", PLayerLevel));
		o.pushField(new FieldInt("xp", xp));
		o.pushField(new FieldByte("currentAbility", KindofProjectile.getByte(currentAbility)));
		o.pushField(new FieldInt("currentMana", currentMana));
		o.pushField(new FieldInt("xpToNextLevel", xpToNextLevel));
		o.pushField(new FieldInt("manaRecoverPerSecond", manaRecoverPerSecond));
		o.pushField(new FieldInt("lifeRecoverPerSecond", lifeRecoverPerSecond));
		o.pushField(new FieldInt("maxMana", maxMana));
		o.pushField(new FieldBoolean("shooting", shooting));
		o.pushField(new FieldBoolean("choosingNextLevel", choosingNextLevel));
		o.pushField(new FieldBoolean("choosingNewAbility", choosingNewAbility));
		o.pushField(new FieldBoolean("blockShooting", blockShooting));
		upgradeAbility.save(o);
		return o;
	}

	public static Player load(DSObject o, Level level, UIManager ui) {
		Player player = new Player();
		player.setLevel(level);
		player.name = o.popString().getString();
		player.x = o.popField().getInt();
		player.y = o.popField().getInt();
		player.health = o.popField().getInt();
		player.MaxHealth = o.popField().getInt();
		player.burning = o.popField().getInt();
		player.freezening = o.popField().getInt();
		player.poisoned = o.popField().getInt();
		player.PLayerLevel = o.popField().getInt();
		player.xp = o.popField().getInt();
		player.currentAbility = KindofProjectile.getKind(o.popField().getByte());
		player.currentMana = o.popField().getInt();
		player.xpToNextLevel = o.popField().getInt();
		player.manaRecoverPerSecond = o.popField().getInt();
		player.lifeRecoverPerSecond = o.popField().getInt();
		player.maxMana = o.popField().getInt();
		player.shooting = o.popField().getBoolean();
		player.choosingNextLevel = o.popField().getBoolean();
		player.choosingNewAbility = o.popField().getBoolean();
		player.blockShooting = o.popField().getBoolean();
		player.setUIManager(ui);
		player.createUIConponents();

		player.playerNextLevelChoice = new PlayerNextLevelChoice(player.ui);
		player.playerAbilities = new PlayerAbilities(player.ui, player.currentAbility);
		player.upgradeAbility = new UpgradeAbility(player.ui);
		player.playerAbilities.drawHablities();
		
		return player;
	}

	public String getNmae() {
		return name;
	}

	public void setUIManager(UIManager ui) {
		this.ui = ui;
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
		this.health = clamp(this.health, 0, MaxHealth);// Redundance
		updateHealth();
	}

	public void addOrRemoveHealth(int health) {
		this.health += health;
		this.health = clamp(this.health, 0, MaxHealth);
		updateHealth();
	}

	public void addLevel(int amount) {
		xp += amount;
		updateLevel();
	}

	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePressed((MousePressedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseReleased((MouseReleasedEvent) e));
	}

	public boolean Abilitychosed(KindofProjectile k) {
//		System.out.println(currentAbility);
		if (k == KindofProjectile.FIREBOOL
				&& playerAbilities.getCurrentLevelAbiliti(PlayerAbilities.MASKFIREABILITY) > 0)
			currentAbility = KindofProjectile.FIREBOOL;

		else if (k == KindofProjectile.ICEBOOL
				&& playerAbilities.getCurrentLevelAbiliti(PlayerAbilities.MASKICEABILITY) > 0)
			currentAbility = KindofProjectile.ICEBOOL;

		else if (k == KindofProjectile.POISONBOOL
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
			AudioClip.fireBool.play();
			shoot(x, y, direction, currentAbility);
			fireRate = WizardProjectile.FIRE_RATE;
			addOrRemoveMana(-WizardProjectile.MANA_COST);
		}
	}

	public void update() {
		time++;
		
		if (health <= 0) 
			removed = true;
		
		if (time % 1 == 0 & Keyboard.firstPress(KeyEvent.VK_L)) {
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

		if (Keyboard.presed(Keyboard.fireAbility))
			currentAbility = KindofProjectile.FIREBOOL;
		else if (Keyboard.presed(Keyboard.iceAbility))
			currentAbility = KindofProjectile.ICEBOOL;
		else if (Keyboard.presed(Keyboard.poisoneAbility))
			currentAbility = KindofProjectile.POISONBOOL;

		if (Keyboard.presed(Keyboard.foward)) {
			animSprite = up;
			yaxis -= speed;

		}
		if (Keyboard.presed(Keyboard.backward)) {
			animSprite = down;
			yaxis += speed;
		}
		if (Keyboard.presed(Keyboard.left)) {
			animSprite = left;
			xaxis -= speed;

		}
		if (Keyboard.presed(Keyboard.right)) {
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

			if (health <= MaxHealth) {
				if (health != MaxHealth)
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
		String valueOfMaxHealth = Integer.toString(MaxHealth);
		String textHelthBar = "HP: " + howMuchhealth + "/" + valueOfMaxHealth;
		hpLabel.update(textHelthBar);

		uiHealthBar.setProgress(health, MaxHealth);
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
				MaxHealth += 10;
			if (choice == PlayerNextLevelChoice.choice.MANA)
				maxMana += 10;
			addOrRemoveHealth(MaxHealth);
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

	public void AddLevelTrigered(PlayerEventsManager m) {
		m.addMessageEvent(
				new LevelTrigered(1, true,
						new UITextandNext(UITextandNext.defoultTextPosition, UITextandNext.defoultTextsize, "level up",
								"voce subiu de nivel, escolha uma"
										+ "abilidade para desbloquear e um atributo para avançar" + "vida ou mana.",
								20)));
		m.addMessageEvent(new LevelTrigered(1,
				new UITextandNext(UITextandNext.defoultTextPosition, UITextandNext.defoultTextsize, "using ability",
						"Voce desbloqueou uma nova abilidade"
								+ "para usala clique no botao correspondete a ela, ou aperte 1 para fogo"
								+ ", 2 gele , ou 3 veneno",
						18)));
		m.addMessageEvent(
				new LevelTrigered(1,
						new UITextandNext(UITextandNext.defoultTextPosition, UITextandNext.defoultTextsize, "",
								"abilidades tem efeitos especial a de gelo diminui a velocidade"
										+ ",a de fogo da dano com o tempo, a de venono diminui o dano do oponente",
								18)));
	}

	public UIManager getUIManager() {
		return ui;
	}

	public void createUIConponents() {
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
		String valueOfMaxHealth = Integer.toString(MaxHealth);
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

//		button = new UIButton(new Vector2i(10, 350), new Vector2i(100, 30), new UIActionListener() {
//			public void perform() {
////				System.out.println("Action Performed!");
//			}
//		});
//		button.setText("Hello");
//		panel.addComponent(button);

//		try {
//			image = ImageIO.read(Player.class.getResource("/textures/home.png"));
//			System.out.println(image.getType());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		UIButton imageButton = new UIButton(new Vector2i(10, 360), image, new UIActionListener() {
//			public void perform() {
//				System.exit(0);
//			}
//		});
//		imageButton.setButtonListener(new UIButtonListener() {
//			public void entered(UIButton button) {
//				button.setImage(ImageUtils.changeBrightness(image, -50));
//			}
//
//			public void exited(UIButton button) {
//				button.setImage(image);
//			}

//			blockShooting = ui.blockShooting();
//			
//			if(time% 5 == 0) {
//				updateLevel();
//			
//				if(imput.number1) playerAbilities.setCurrentAbility(1);
//				else if(imput.number2) playerAbilities.setCurrentAbility(2);
//				else if(imput.number3) playerAbilities.setCurrentAbility(3);
//			}
//			public void pressed(UIButton button) {
//				button.setImage(ImageUtils.changeBrightness(image, 50));
//			}
//
//			public void released(UIButton button) {
//				button.setImage(image);
//			}
//		});
//		panel.addComponent(imageButton);
	}
}
