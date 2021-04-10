package jogo.entity.mob;

import static jogo.util.MathUtils.clamp;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import javax.swing.text.JTextComponent.KeyBinding;

import components.Fields.FieldBoolean;
import components.Fields.FieldByte;
import components.Fields.FieldInt;
import components.Objects.DSObject;
import components.string.DSString;
import jogo.Game;
import jogo.audio.AudioClip;
import jogo.entity.mob.player.Inventory;
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
	private int healing, healingRate;
	private int recoveringMana, recoveringManaRate;
	private int skinIdentifier = 1;
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
	private boolean map = false;

	private KindofProjectile currentAbility = KindofProjectile.NULL;

	private AnimatedSprite down ;
	private AnimatedSprite up;
	private AnimatedSprite left ;
	private AnimatedSprite right;
	private AnimatedSprite animSprite = down;

	private UIManager ui;
	private UIProgressBar uiHealthBar;
	private UIProgressBar uiPlayerLevelBar;
	private UIProgressBar UIManaBar;
	private UILabel PlayerLevelLabel;
	private UIButton button;
	private UILabel manaLabel;
	private UILabel hpLabel;
	private Inventory inventory;
	private UILabel nameLabel;

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
		inventory = new Inventory(ui, this);
		setSking(skinIdentifier);
	}

	public DSObject save() {
		DSObject o = new DSObject("player");
		o.pushString(new DSString("playerName", name));
		super.save(o);
		o.pushField(new FieldInt("skin Identifier", skinIdentifier));
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
		inventory.save(o);
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
		player.setSking(o.popField().getInt());
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
		player.inventory = Inventory.load(o, player.ui, player);
		player.upgradeAbility.load(o);
		return player;
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
		if (fireRate <= 0 && currentMana >= WizardProjectile.MANA_COST && currentAbility != KindofProjectile.NULL) {
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
		
		if ( Keyboard.firstPress(KeyEvent.VK_L)) {
			xp = xp + 100;
		}
		
		if(Keyboard.firstPress(Keyboard.map)) {
			map = !map;
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

		if (Keyboard.presed(Keyboard.fireAbility) && playerAbilities.getAbilityIfUnlocked(0))
			currentAbility = KindofProjectile.FIREBOOL;
		else if (Keyboard.presed(Keyboard.iceAbility) && playerAbilities.getAbilityIfUnlocked(1))
			currentAbility = KindofProjectile.ICEBOOL;
		else if (Keyboard.presed(Keyboard.poisoneAbility) && playerAbilities.getAbilityIfUnlocked(2))
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
			if (healing > 0) {
				addOrRemoveHealth(healingRate);
				healing--;
			}
			
			if (recoveringMana > 0) {
				addOrRemoveMana(recoveringManaRate);
				recoveringMana--;
			}
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
		nameLabel = new UILabel(new Vector2i(40, 30), name);
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
	
		
	}

	public void setInventoryVisibility(boolean visibility) {
		inventory.setVisibility(visibility);
	}
	
	//time in seconds
	//rate will be aplied 1 time per second
	public void Heal(int time, int rate) {
		healing = time;
		healingRate = rate;
	}
	
	//time in seconds
	//rate will be aplied 1 time per second
	public void recoverMana(int time, int rate) {
		recoveringMana = time;
		recoveringManaRate = rate;
	}

	public boolean getMap() {
		return map;
	}
	
	public void setSking(int identifier) {
		switch (identifier) {
		case 1:
			down = new AnimatedSprite(SpriteSheet.player1[0], 32, 32, 3);
			up = new AnimatedSprite(SpriteSheet.player1[1], 32, 32, 3);
			left = new AnimatedSprite(SpriteSheet.player1[2], 32, 32, 3);
			right = new AnimatedSprite(SpriteSheet.player1[3], 32, 32, 3);
			break;
			
		case 2:
			down = new AnimatedSprite(SpriteSheet.player2[0], 32, 32, 3);
			up = new AnimatedSprite(SpriteSheet.player2[1], 32, 32, 3);
			left = new AnimatedSprite(SpriteSheet.player2[2], 32, 32, 3);
			right = new AnimatedSprite(SpriteSheet.player2[3], 32, 32, 3);
			break;
			
		case 3:
			down = new AnimatedSprite(SpriteSheet.player3[0], 32, 32, 3);
			up = new AnimatedSprite(SpriteSheet.player3[1], 32, 32, 3);
			left = new AnimatedSprite(SpriteSheet.player3[2], 32, 32, 3);
			right = new AnimatedSprite(SpriteSheet.player3[3], 32, 32, 3);
			break;
			
		case 4:
			down = new AnimatedSprite(SpriteSheet.player4[0], 32, 32, 3);
			up = new AnimatedSprite(SpriteSheet.player4[1], 32, 32, 3);
			left = new AnimatedSprite(SpriteSheet.player4[2], 32, 32, 3);
			right = new AnimatedSprite(SpriteSheet.player4[3], 32, 32, 3);
			break;
			
		case 5:
			down = new AnimatedSprite(SpriteSheet.player5[0], 32, 32, 3);
			up = new AnimatedSprite(SpriteSheet.player5[1], 32, 32, 3);
			left = new AnimatedSprite(SpriteSheet.player5[2], 32, 32, 3);
			right = new AnimatedSprite(SpriteSheet.player5[3], 32, 32, 3);
			break;
			
		case 6:
			down = new AnimatedSprite(SpriteSheet.player6[0], 32, 32, 3);
			up = new AnimatedSprite(SpriteSheet.player6[1], 32, 32, 3);
			left = new AnimatedSprite(SpriteSheet.player6[2], 32, 32, 3);
			right = new AnimatedSprite(SpriteSheet.player6[3], 32, 32, 3);
			break;
			
		case 7:
			down = new AnimatedSprite(SpriteSheet.player7[0], 32, 32, 3);
			up = new AnimatedSprite(SpriteSheet.player7[1], 32, 32, 3);
			left = new AnimatedSprite(SpriteSheet.player7[2], 32, 32, 3);
			right = new AnimatedSprite(SpriteSheet.player7[3], 32, 32, 3);
			break;
			
		case 8:
			down = new AnimatedSprite(SpriteSheet.player8[0], 32, 32, 3);
			up = new AnimatedSprite(SpriteSheet.player8[1], 32, 32, 3);
			left = new AnimatedSprite(SpriteSheet.player8[2], 32, 32, 3);
			right = new AnimatedSprite(SpriteSheet.player8[3], 32, 32, 3);
			break;

		default:
			break;
		}
		 animSprite = down;
	}
	
	

	public String getNmae() {
		return name;
	}
	
	public void setName(String name) {
		if (name == null || name == "")
			name = "bourdieu";
		Logger.getGlobal().info(name);
		this.name = name;
		nameLabel.SetText(name);
	}

	public void setUIManager(UIManager ui) {
		this.ui = ui;
	}
	
	public Inventory getInventory() {
		return inventory;
	}

	public int getMana() {
		return currentMana;
	}

	public int getManaMaxima() {
		return maxMana;
	}

}
