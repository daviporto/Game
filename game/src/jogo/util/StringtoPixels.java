package jogo.util;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class StringtoPixels {
	
	
	public StringtoPixels() {
		
	}
	
	public static int getWidthStringPixels(String text, Font font ) {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
		int textwidth = (int)(font.getStringBounds(text, frc).getWidth());
		
		return textwidth;
	}
	
	public static int getHeightStringPixels(String text, Font font ) {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
		int textheight = (int)(font.getStringBounds(text, frc).getHeight());
		
		return textheight;
	}
}
