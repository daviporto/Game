package jogo.entity.spawner;

import java.util.Random;

import jogo.entity.particle.Particle;
import jogo.graphics.Sprite;
import jogo.graphics.Sprite.typeParticles;
import jogo.level.Level;

public class ParticleSpawner extends Spawner{
 static Random random = new Random();
	
	public ParticleSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		
		for(int i = 0; i < amount; i++) {
				level.add(new Particle(x, y, life));
		}
	}

	public ParticleSpawner(int x, int y, int life, int amount, Level level, typeParticles Type_particle) {
		super(x, y, Type.PARTICLE, amount, level);
		
		for(int i = 0; i < amount; i++) {
			level.add(new Particle(x, y, life, Type_particle));
	}
	}
	
	public ParticleSpawner(int x, int y, int life, int amount, Level level, Sprite[] sprites) {
		super(x, y, Type.PARTICLE, amount, level);
		
		for(int i = 0; i < amount; i++) {
			int index = random.nextInt(sprites.length );
			level.add(new Particle(x, y, life, sprites[index]));
		}
	}

	public ParticleSpawner(int x, int y, int life, int amount, Level level, Sprite[] particles, int randomRange) {
		this( (x+= random.nextInt(randomRange) - 8), (y+=random.nextInt(randomRange) - 8),
				life, amount, level, particles);

	}
	
	


}
