package jogo.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import jogo.events.EventListener;
import jogo.events.types.MouseMovedEvent;
import jogo.events.types.MousePressedEvent;
import jogo.events.types.MouseReleasedEvent;


public class Mouse implements MouseListener, MouseMotionListener{

	private static int mousex = -1;
	private static int mousey = -1;
	private static int mouseButton = 0;
	
	private EventListener eventListener;
	
	public Mouse(EventListener listener) {
		eventListener = listener;
	}

	public static int getX() {
		return mousex;
	}
	
	public static int getY() {
		return mousey;
	}
	
	public static int getButton() {
		return mouseButton;
	}
	
	public void mouseDragged(MouseEvent e) {
		mousex = e.getX();
		mousey = e.getY();	
		
		MouseMovedEvent event = new MouseMovedEvent(e.getX(), e.getY(), true);
		eventListener.onEvent(event);
	}

	public void mouseMoved(MouseEvent e) {
		mousex = e.getX();
		mousey = e.getY();
		
		MouseMovedEvent event = new MouseMovedEvent(e.getX(), e.getY(), false);
		eventListener.onEvent(event);
		
	}

	public void mouseClicked(MouseEvent e) {
	
		
	}

	public void mouseEntered(MouseEvent e) {
	
		
	}

	
	public void mouseExited(MouseEvent e) {
		
		
	}

	public void mousePressed(MouseEvent e) {
		mouseButton = e.getButton();
		
		MousePressedEvent event = new MousePressedEvent(e.getButton(), e.getX(), e.getY());
		eventListener.onEvent(event);
		
	}

	public void mouseReleased(MouseEvent e) {
		mouseButton = 0;
		
		MouseReleasedEvent event = new MouseReleasedEvent(e.getButton(), e.getX(), e.getY());
		eventListener.onEvent(event);
		
	}

}
