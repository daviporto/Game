package jogo.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioClip {
	public static AudioClip buttonClick = new AudioClip("res/audio/buttonClick.wav");
	public static AudioClip backgroundTense = new AudioClip("res/audio/tense.wav");
	public static AudioClip fireBool = new AudioClip("res/audio/fireBool.wav");
	

	static {
		backgroundTense.setGain(-25f);
	}
	
	private Clip clip;
	private boolean active = false;
	private FloatControl gain;

	public AudioClip(String path) {
		File audioFile = new File(path);
		if (!audioFile.exists()) {
			Logger.getGlobal().info("deu merda");
		}

		try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile)) {
			Clip audioClip = AudioSystem.getClip();
			audioClip.open(audioStream);
			this.gain = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);

			this.clip = audioClip;
		} catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
			Logger.getGlobal().warning("Error opening audiostream from " + path);

		}
	}

	public void playInLoop() {
		pause();
		clip.setFramePosition(0);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		active = true;
	}

	public void play() {
		pause();
		clip.setFramePosition(0);
		clip.start();
		active = true;

	}

	public void pause() {
		if (clip.isRunning())
			clip.stop();
		active = false;
	}

	public void resume() {
		if (!clip.isRunning()) {
			clip.start();
		}
	}

	public void setGain(float value) {
		gain.setValue(value);
	}
}
