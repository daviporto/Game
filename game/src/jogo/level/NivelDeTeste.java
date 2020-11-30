package jogo.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import jogo.entity.mob.Dummy;
import jogo.entity.mob.Mob.KindofProjectile;
import jogo.entity.mob.PumpkinHead;
import jogo.entity.mob.Shooter;
import jogo.entity.mob.usingstar.Mage;
import jogo.entity.mob.usingstar.Vampire;
import jogo.entity.mob.usingstar.Witch;
import jogo.events.messageEvents.LevelTrigered;
import jogo.events.messageEvents.LocationTrigered;
import jogo.graphics.Screen;
import jogo.graphics.ui.UITextandNext;
import jogo.util.Vector2i;

public class NivelDeTeste extends Level {

	UITextandNext chegadoSala1;

	public NivelDeTeste(String path) {
		super(path);
	}

	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception! could not load level file!");
		}

		add(new Dummy(450, 492, 350, 580, false));
		add(new Dummy(233, 689, 350, 580, false));
		add(new Dummy(399, 491, 350, 580, false));
		add(new Dummy(399, 686, 350, 580, false));
//		
//		for(int i = 0; i < 500; i++) add(new Dummy(399, 686, 350, 580, false));
//
		add(new Shooter(643, 759, 781, 603, false, true));
		add(new Shooter(913, 783, 781, 603, false, true));
		add(new Shooter(643, 448, 781, 603, false, true));
		add(new Shooter(913, 448, 781, 603, false, true));

		add(new PumpkinHead(1167, 432, false, true));
		add(new PumpkinHead(1167, 587, false, true));
		add(new PumpkinHead(1167, 714, false, true));
		add(new PumpkinHead(1167, 831, false, true));

		add(new Witch(1356, 454, false, 70, 170, true));
		add(new Witch(1356, 613, false, 70, 170, true));
		add(new Witch(1356, 780, false, 70, 170, true));

		add(new Vampire(2085, 646, false, 90, 250, true, Vampire.Gender.FAMELE));
		add(new Vampire(2109, 974, false, 90, 250, true, Vampire.Gender.MALE));

		Mage FM = new Mage(2812, 692, false, true, 250, 120);
		FM.SetKindOfProjectile(KindofProjectile.FIREBOOL);
		add(FM);

		Mage IM = new Mage(2812, 920, false, true, 250, 120);
		IM.SetKindOfProjectile(KindofProjectile.ICEBOOL);
		add(IM);

		Mage PM = new Mage(2941, 812, false, true, 250, 120);
		PM.SetKindOfProjectile(KindofProjectile.POISONBOOL);
		add(PM);

	}
	
	public Level.Levels getLevel(){
		return Levels.Teste1;
	}

	public void addLocationTrigerredEvents() {
		UITextandNext basicatkPanel = new UITextandNext(new Vector2i(150, 20), new Vector2i(500, 100),
				"bem vindo ao tutorial ", 10);
		basicatkPanel.addLine("pressione o botão esquerdo do mause para executar ataques básicos");
		messagesManager.AddEvent(new LevelTrigered(0, basicatkPanel));

		UITextandNext movingPanel = new UITextandNext(new Vector2i(150, 20), new Vector2i(500, 100), "como andar", 10);
		movingPanel.addLine("para se mover use W A S D ou as setas, voce pode atirar" + " enquanto se move");
		messagesManager.AddEvent(new LevelTrigered(0, movingPanel));

		UITextandNext proximaSala = new UITextandNext(new Vector2i(150, 20), new Vector2i(500, 100), "primeiros passos",
				10);
		proximaSala.addLine("ande em direção a sala a sua frente");
		messagesManager.AddEvent(new LevelTrigered(0, proximaSala));

		messagesManager.AddEvent(new LocationTrigered(new Vector2i(500, 600), new Vector2i(576, 620),
				new UITextandNext(UITextandNext.defoultTextPosition, UITextandNext.defoultTextsize, "fantasmas",
						"esses fantasmas vagam pelo mapa aleatoriamente,"
								+ "eles não te perseguem mas caso voce entre no range deles, esse irão te atacar",
						10)));

		messagesManager.AddEvent(new LocationTrigered(new Vector2i(950, 1000), new Vector2i(528, 851),
				new UITextandNext(UITextandNext.defoultTextPosition, UITextandNext.defoultTextsize,
						"cabeças de abobora", "essa cabeças de abobora são semelhantes aos fantas"
								+ "mas so te atacarão caso entre no range deles",
						10)));

		messagesManager.AddEvent(new LocationTrigered(new Vector2i(1238, 1283), new Vector2i(496, 783),
				new UITextandNext(UITextandNext.defoultTextPosition, UITextandNext.defoultTextsize, "bruxas",
						"as bruxas são imunes a veneno"
								+ "e podem te envenenar cuidado!!!, elas diferente dos outros inimigos podem te perseguir"
								+ "sendo capaz de desviar ate mesmo de paredes",
						10)));

		messagesManager.AddEvent(new LocationTrigered(new Vector2i(1803, 1875), new Vector2i(784, 834),
				new UITextandNext("magos trigemeos",
						"a frente voce encontrara 3 magos, cada um com um tipo de abilidade"
								+ "(fogo, gelo e veneno), eles que nem as bruxar irão te perseguir")));
	}

	protected void generateLevel() {

	}

	public void RenderCenario(Screen screen) {
//		screen.renderSprite(1015, 432, Sprite.poison_cauldron, true);
	}

}
