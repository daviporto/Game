package jogo.graphics.ui;

import static jogo.util.MathUtils.inTheRange;
import static jogo.util.StringtoPixels.getHeightStringPixels;
import static jogo.util.StringtoPixels.getWidthStringPixels;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import jogo.util.Vector2i;


	public class UITextandNext extends UIPanel{
		private boolean dead = false;
		private UILabel title;
		private int textHeightBynow = 0;
		private int positionOnText = 0;
		private final int MAXLINETWIDTH;
		private static  Font defaultFont = new Font("Verdana", Font.PLAIN, 18);
		private boolean buttonActive = false;
		private int seconds;
		private boolean deadset = false;
		private boolean resizable = false;
		
		
		public static Vector2i defoultTextPosition = new Vector2i(150, 20); 
		public static Vector2i defoultTextsize = new Vector2i(500, 100); 
		    
		    
	public UITextandNext(Vector2i position, Vector2i size, int seconds) {
		super(position, size);
		super.setColor(0x4f4f4f);
		this.seconds = seconds;
		
		
		Vector2i buttonSize = new Vector2i(80, 30);
		Vector2i buttonPosition = new Vector2i(size).subtract(buttonSize);
		MAXLINETWIDTH = size.x - 20;
		
		
		UIButton OKButton = new UIButton(buttonPosition, buttonSize,() -> suicide());
		OKButton.setText("next");
		OKButton.setColor(0xff0000);
		this.addComponent(OKButton);
			
		}
	
	public void resizePanel(UIPanel panel, Vector2i size) {
		super.resize(size);
	}
	
	public UITextandNext(Vector2i position, Vector2i size,  String text, int seconds) {
		this(position, size, seconds);
		
		int textWidth = getWidthStringPixels(text, defaultFont);
		int Xoffset = (size.x - textWidth) >> 1;
		textHeightBynow += getHeightStringPixels(text, defaultFont);
		
		title = new UILabel(new Vector2i(Xoffset,20), text);
		textHeightBynow += 20;
		title.setColor(0xffffff);
		title.setFont(defaultFont);
		super.addComponent(title);
	}
	
	public UITextandNext(Vector2i position, Vector2i size, String titleS, String text, int seconds) {
		this(position, size, seconds);
		
		int textWidth = getWidthStringPixels(titleS, defaultFont);
		int Xoffset = (size.x - textWidth) >> 1;
		textHeightBynow += getHeightStringPixels(titleS, defaultFont);
		
		title = new UILabel(new Vector2i(Xoffset,20), titleS);
		textHeightBynow += 20;
		title.setColor(0xffffff);
		title.setFont(defaultFont);
		super.addComponent(title);
		
		addLine(text);
	}
	
	public UITextandNext(Vector2i position, Vector2i size,  String text, int seconds,  int letterSize) {
		this(position, size, seconds);
		Font font = new Font("Verdana", Font.PLAIN, letterSize);
		
		int textWidth = getWidthStringPixels(text, font);
		int Xoffset = (size.x - textWidth) >> 1;
		textHeightBynow += getHeightStringPixels(text, font);
		
		title = new UILabel(new Vector2i(Xoffset, 20), text);
		textHeightBynow += 20;
		title.setColor(0xffffff);
		title.setFont(font);
		super.addComponent(title);
	}
	
	
	public UITextandNext(String titleS, String text) {
		this(defoultTextPosition, defoultTextsize, 10);
		
		int textWidth = getWidthStringPixels(titleS, defaultFont);
		int Xoffset = (size.x - textWidth) >> 1;
		textHeightBynow += getHeightStringPixels(titleS, defaultFont);
		
		title = new UILabel(new Vector2i(Xoffset,20), titleS);
		textHeightBynow += 20;
		title.setColor(0xffffff);
		title.setFont(defaultFont);
		super.addComponent(title);
		
		addLine(text);
	}
	
	private List<String> GetLine(Font font, String text) {
		List<String> lines = new ArrayList<String>();
		String aux;
		positionOnText = 0;
		int lastWord;
		while (positionOnText < text.length()) {
			aux = text.substring(positionOnText, text.length());
			int textWidth = getWidthStringPixels(aux, font);
			while(textWidth > MAXLINETWIDTH) {
				lastWord = aux.lastIndexOf(" ");
				lastWord += positionOnText;
				aux = text.substring(positionOnText, lastWord);
				textWidth = getWidthStringPixels(aux, font);
			}
			if(aux.indexOf(" ") == 0) { aux = aux.substring(1, aux.length());
				positionOnText += aux.length();
				positionOnText++;
				lines.add(aux);
			}else {
				positionOnText += aux.length();
				lines.add(aux);
			}
		}
			
		return lines;
		}

	
	public void addLine(String text) {
		List<String> lines = new ArrayList<String>();
		int textWidth = getWidthStringPixels(text, defaultFont);
		if(textWidth > MAXLINETWIDTH) {
			lines =	GetLine(defaultFont, text);
			for(int i = 0; i < lines.size(); i++) {
				UILabel line = new UILabel(new Vector2i(10, textHeightBynow), lines.get(i));
				line.setColor(0xffffff);
				line.setFont(defaultFont);
				super.addComponent(line);
			
				
				textHeightBynow += getHeightStringPixels(lines.get(i), defaultFont);
			}
			
		}else {		
			UILabel anotherline = new UILabel(new Vector2i(10, textHeightBynow), text);
			anotherline.setColor(0xffffff);
			anotherline.setFont(defaultFont);
			super.addComponent(anotherline);
			
			textHeightBynow += getHeightStringPixels(text, defaultFont);
		}
	}
	public void addLine(String text, int letterSize) {
		Font font = new Font("Verdana", Font.PLAIN, letterSize);
		UILabel anotherline = new UILabel(new Vector2i(10, textHeightBynow), text);
		anotherline.setColor(0xffffff);
		anotherline.setFont(font);
		super.addComponent(anotherline);
		
		textHeightBynow += getHeightStringPixels(text, font);
	}
	
	
	public void addLine(String text, Font font) {
		UILabel anotherline = new UILabel(new Vector2i(10, textHeightBynow), text);
		anotherline.setColor(0xffffff);
		anotherline.setFont(font);
		super.addComponent(anotherline);
		
		textHeightBynow += getHeightStringPixels(text, font);
		if(resizable && (textHeightBynow + 10) >= size.y)
			this.resizeY(textHeightBynow + 10);
	}
	
	public boolean isDead() {
		return dead;
	}
	public void suicide () {
		if(buttonActive) dead = true;
	}
	
	public boolean ContainMouseOnTextPanel(int x, int y){
		if((inTheRange(x, position.x, position.x + size.x)) &&
				(inTheRange(y,  position.y , position.y + size.y))) {
			return true;
		}
		return false;
	}
	
	public void setButton(boolean active) {
		buttonActive = active;
	}
	
	
	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}
	
	public void updateLifeTime() {
		if(!dead) {
			if(seconds > 0) seconds--;
			else dead = true;
		}
	}

	
}
