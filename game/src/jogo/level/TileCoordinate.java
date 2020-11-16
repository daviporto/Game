package jogo.level;

public class TileCoordinate {
	
	private int x, y ;
	private final int tileSize = 16;
	
	public TileCoordinate (int x, int y) {
		this.x = x * tileSize;
		this.y = y * tileSize ;
	}
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}
	
	public int[] xy (){
		int [] r = new int [2];
		r[0] = x;
		r[1] = y;
		return r;
	}
}
