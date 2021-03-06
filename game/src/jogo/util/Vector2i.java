package jogo.util;

import java.awt.event.MouseEvent;

import jogo.events.types.MousePressedEvent;

public class Vector2i {

	public int x, y;

	public Vector2i() {
		set(0, 0);
	}

	public Vector2i(Vector2i vector) {
		set(vector.x, vector.y);
	}

	public Vector2i(MousePressedEvent e) {
		set(e.getX(), e.getY());
	}

	public Vector2i(int x, int y) {
		set(x, y);
	}

	public Vector2i sumAndNew(Vector2i b) {
		Vector2i result = new Vector2i(this);
		return result.add(b);
	}

	public Vector2i subtractAndNew(Vector2i b) {
		Vector2i result = new Vector2i(this);
		return result.subtract(b);
	}

	public void multiply(int scale) {
		this.x *= scale;
		this.y *= scale;
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Vector2i add(Vector2i vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}

	public Vector2i add(int value) {
		this.x += value;
		this.y += value;
		return this;
	}

	public Vector2i subtract(Vector2i vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		return this;
	}

	public Vector2i setX(int x) {
		this.x = x;
		return this;
	}

	public Vector2i setY(int y) {
		this.y = y;
		return this;
	}

	public static double getDistance(Vector2i v0, Vector2i v1) {
		double x = v0.getX() - v1.getX();
		double y = v0.getY() - v1.getY();
		return Math.sqrt(x * x + y * y);
	}

	public boolean equals(Object object) {
		if (!(object instanceof Vector2i))
			return false;
		Vector2i vec = (Vector2i) object;
		if (vec.getX() == this.getX() && vec.getY() == this.getY())
			return true;
		return false;
	}
}
