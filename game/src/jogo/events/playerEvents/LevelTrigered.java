package jogo.events.playerEvents;

import jogo.graphics.ui.UITextandNext;

public class LevelTrigered extends messageEvent{
	private UITextandNext message;
	private final int level;
	private boolean showImediately;
	
	public LevelTrigered(int level,boolean showImediately,  UITextandNext message) {
		super(message);
		this.level = level;
		this.showImediately = false;
	}
	
	public LevelTrigered(int level, UITextandNext message) {
		//showImediately false;
		this(level, false, message);
	}
	
	public int GetLevel() {
		return level;
	}
	
	public boolean GetShowImediately() {
		return showImediately;
	}
	
	public UITextandNext getMessage(){
		return message;
	}

}
