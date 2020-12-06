package jogo.events.playerEvents;

import jogo.graphics.ui.UITextandNext;
import jogo.util.Vector2i;

public class LocationTrigered extends messageEvent{
	private Vector2i x;
	private Vector2i y;
	
	public LocationTrigered(Vector2i x, Vector2i y, UITextandNext message) {
		super(message);
		this.x = x;
		this.y = y;
	}
	
	public Vector2i GetX() {
		return x;
	}
	
	public Vector2i GetY() {
		return y;
	}
	
}
