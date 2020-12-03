package jogo.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.KeyStroke;

import jogo.entity.mob.player.PlayerAbilities;
import jogo.events.EventListener;
import jogo.events.types.AbilityChosedEvent;

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
