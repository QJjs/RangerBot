/*
 * Made by Peder Johnson
 * First Created: 8/17/2017
 * Last Edited: 8/17/2017
 * Dedicated to the Jesuit College Preparatory School of Dallas
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;

public class RenderHandler
{
	private BufferedImage view;
	private int[] pixels;
	Graphics g;
	
	public RenderHandler(int width, int height)
	{
		view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
		g = view.getGraphics();
	}
	
	public void render(Graphics graphics)
	{
		graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
	}
	
	public void renderImage(BufferedImage image, int X, int Y)
	{
		g.drawImage(image, X, Y, new ImgObserver());
	}
	
	public void drawLine(int color, int x1, int y1, int x2, int y2)
	{
		g.setColor(new Color(color));
		g.drawLine(x1, y1, x2, y2);
	}
	
	public void setPixel(int pixel, int x, int y)
	{
		int index = x + y * view.getWidth();
		if(pixels.length <= index) {
			pixels[index] = pixel;
		}
	}
	
	public void clear(int color)
	{
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = color;
		}
	}
	
	public class ImgObserver implements ImageObserver
	{
		@Override
		public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height)
		{
			return false;
		}
	}
}
