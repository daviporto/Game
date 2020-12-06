package jogo.events.playerEvents;

import static jogo.util.MathUtils.isInsideArea;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jogo.entity.mob.Player;
import jogo.events.checkPoints.SaveCheckPoint;
import jogo.graphics.ui.UIManager;

public class PlayerEventsManager{
	private Player player;
	private UIManager ui;
	private List<LocationTrigered> locationEvents = new ArrayList<LocationTrigered>(); 
	private List<LevelTrigered> levelEvents = new ArrayList<LevelTrigered>();
	private List<SaveCheckPoint> saveCheckPoints = new ArrayList<SaveCheckPoint>();
	private int playerLastLevel = 0;

	
	public PlayerEventsManager(Player player, UIManager ui) {
		this.player = player;
		this.ui = ui;
	}
	
	
	public void addMessageEvent(messageEvent e) {
		if(e instanceof LocationTrigered) locationEvents.add((LocationTrigered)e);
		else if(e instanceof LevelTrigered) levelEvents.add((LevelTrigered)e);
	}
	
	public void addCheckPointEvent(SaveCheckPoint e) {
		saveCheckPoints.add(e);
	}
	
	
	public void update() {
			if(!locationEvents.isEmpty()) {
				LocationTrigered current;
				Iterator<LocationTrigered> i = locationEvents.iterator();
				while (i.hasNext()) {
					current = i.next();
					if(isInsideArea(current.GetX(), current.GetY(), player.getX(), player.getY())) {
						ui.addTextPanel(current.GetMessage());
						i.remove();
					}
				}
			}
			
			if (!saveCheckPoints.isEmpty()) {
				SaveCheckPoint current;
				Iterator<SaveCheckPoint> i = saveCheckPoints.iterator();
				while (i.hasNext()) {
					current = i.next();
					if(isInsideArea(current.GetX(), current.GetY(), player.getX(), player.getY())) {
						new Thread(current).start();
						i.remove();
					}
				}
			}
				
	}
	
	public void newLevel(int level) {
		if(!levelEvents.isEmpty()) {
			java.util.Iterator<LevelTrigered> i = levelEvents.iterator();
			LevelTrigered t;
			while(i.hasNext()) {
				t = i.next();
				if(t.GetLevel()== playerLastLevel) {
					if(t.GetShowImediately())
						ui.addTextPanelFirstPositionAndRemove(t.GetMessage());
						else ui.addTextPanel(t.GetMessage());
						}
					i.remove();
					}
				}
		}


}
