package jogo.events.playerEvents;

import jogo.graphics.ui.UITextandNext;

public class messageEvent {
	protected UITextandNext message;
	
	public messageEvent(UITextandNext message){
		this.message = message;
	}
	
	public UITextandNext GetMessage() {
		return message;
	}
}
