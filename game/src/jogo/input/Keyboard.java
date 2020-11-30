package jogo.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import jogo.entity.mob.player.PlayerAbilities;
import jogo.events.EventListener;
import jogo.events.types.AbilityChosedEvent;

public class Keyboard implements KeyListener {

	private static boolean[] keys = new boolean[120];
	private static List<Integer> keysPressed = new ArrayList<Integer>();
	public boolean up, down, left, right, enter, L;
	private EventListener listener;

	public Keyboard(EventListener listener) {
		this.listener = listener;
	}

	public void update() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		if (keys[KeyEvent.VK_1] || keys[KeyEvent.VK_NUMPAD1])
			listener.onEvent(new AbilityChosedEvent(PlayerAbilities.wichAbiliti.FIRE));

		if (keys[KeyEvent.VK_2] || keys[KeyEvent.VK_NUMPAD2])
			listener.onEvent(new AbilityChosedEvent(PlayerAbilities.wichAbiliti.ICE));
		if (keys[KeyEvent.VK_3] || keys[KeyEvent.VK_NUMPAD3])
			listener.onEvent(new AbilityChosedEvent(PlayerAbilities.wichAbiliti.POISON));

		enter = keys[KeyEvent.VK_ENTER];
		L = keys[KeyEvent.VK_L];
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
//		System.out.println(keys[KeyEvent.VK_L]);
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

	}

}
