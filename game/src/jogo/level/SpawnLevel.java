package jogo.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpawnLevel extends Level {
	

	public SpawnLevel(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}
	
	protected void loadLevel(String path) {
		
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight(); 
			tiles = new int [w * h];
			image.getRGB(0, 0, w, h, tiles , 0, w);
		}catch(IOException e ) {
			e.printStackTrace();
			System.out.println("Exception! could not load level file!");
		}
		
		for(int i = 0; i < 20 ; i ++) {
//			add(new Dummy(25, 30));	
		}
		
//		add(new witch(1, 1, true, false,Integer.MAX_VALUE,Integer.MAX_VALUE, false ));
	}
	protected void generateLevel() {
	}
}
