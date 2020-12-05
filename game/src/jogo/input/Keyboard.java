package jogo.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import components.Fields.FieldInt;
import components.Objects.DSObject;
import components.dataBase.DSDataBase;
import jogo.events.EventListener;

public class Keyboard implements KeyListener {

	private static boolean[] keys = new boolean[120];
	private static List<Integer> keysPressed = new ArrayList<Integer>();
	public static boolean waitingForText = false;
	public static List<KeyEvent> keysTyped = new ArrayList<KeyEvent>();
	private EventListener listener;
	
	public static int foward = KeyEvent.VK_W;
	public static int backward = KeyEvent.VK_S;
	public static int left= KeyEvent.VK_A;
	public static int right = KeyEvent.VK_D;
	public static int fireAbility = KeyEvent.VK_1;
	public static int iceAbility = KeyEvent.VK_2;
	public static int poisoneAbility = KeyEvent.VK_3;
	  

	public Keyboard(EventListener listener) {
		this.listener = listener;
	}
	
	public static DSObject save() {
		DSObject keyBindins = new DSObject("keyBindins");
		keyBindins.pushField(new FieldInt("foward", foward));
		keyBindins.pushField(new FieldInt("backward", backward));
		keyBindins.pushField(new FieldInt("left", left));
		keyBindins.pushField(new FieldInt("right", right));
		keyBindins.pushField(new FieldInt("fireAbility", fireAbility));
		keyBindins.pushField(new FieldInt("iceAbility", iceAbility));
		keyBindins.pushField(new FieldInt("poisoneAbility", poisoneAbility));
		return keyBindins;
	}
	
	public static void load() {
		DSDataBase db = DSDataBase.deserializeFromFile("saves/keys");
		DSObject o = db.popObject();
		foward = o.popField().getInt();
		backward = o.popField().getInt();
		left = o.popField().getInt();
		right = o.popField().getInt();
		fireAbility = o.popField().getInt();
		iceAbility = o.popField().getInt();
		poisoneAbility = o.popField().getInt();
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;

	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		if (!keysPressed.isEmpty())
			Unpress(e.getKeyCode());
	}

	public static void Unpress(int KeyCode) {
		if(keysPressed.contains(Integer.valueOf(KeyCode)))
			keysPressed.remove(Integer.valueOf(KeyCode));
	}
	

	public static boolean presed(int keyCode) {
		return keys[keyCode];
	}

	public static boolean firstPress(int KeyCode) {
		if (keys[KeyCode] & !keysPressed.contains(Integer.valueOf(KeyCode))) {
			keysPressed.add(Integer.valueOf(KeyCode));
			return true;
		}
		return false;
	}

	public void keyTyped(KeyEvent e) {
		if(waitingForText) {
			keysTyped.add(e);
		}
	}
	
	public static void clearTypedText() {
		keysTyped.clear();
	}

}
