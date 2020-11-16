package jogo.level;

import jogo.util.Vector2i;

public class Node {
	public Vector2i tile;
	public Node parent;
	public double fCost, gCost, hCost;
	
	public Node(Vector2i tile, Node parent, double Gcost, double hCost) {
		this.tile = tile;
		this.parent = parent;
		this.gCost = Gcost;
		this.hCost = hCost;
		this.fCost = this.gCost + this.hCost;
		
	}
}
