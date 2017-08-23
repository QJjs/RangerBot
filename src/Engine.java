/*
 * Made by Peder Johnson
 * First Created: 8/17/2017
 * Last Edited: 8/17/2017
 * Dedicated to the Jesuit College Preparatory School of Dallas
 */

import com.sun.istack.internal.Nullable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Engine extends JFrame implements Runnable
{
	private Canvas canvas = new Canvas();
	private RenderHandler renderer;
	private BufferedImage robotBmp;
	
	private int frame = 0;
	
	private int botX;
	private int botY;
	private int botDir;
	private int botBeepers;
	
	public int getBotX()
	{
		return botX;
	}
	public int getBotY()
	{
		return botY;
	}
	public int getBotDir()
	{
		return botDir;
	}
	public int getBotBeepers()
	{
		return botBeepers;
	}
	
	private Engine()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1000, 1000);
		setLocationRelativeTo(null);
		
		add(canvas);
		setVisible(true);
		canvas.createBufferStrategy(2);
		
		renderer = new RenderHandler(getWidth(), getHeight());
		robotBmp = Engine.loadImage("robot.bmp");
		
	}
	
	private void update()
	{
	
	}
	
	@Nullable
	public static BufferedImage loadImage(String path)
	{
		try {
			BufferedImage loadedImage = ImageIO.read(Engine.class.getResource(path));
			BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);
			return formattedImage;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void render()
	{
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		super.paint(graphics);
		
		renderer.clear(0xffffff);
		renderer.renderImage(robotBmp, 0, 0);
		renderer.drawLine(0, frame % getWidth(), 0, frame % getWidth(), getHeight());
		renderer.render(graphics);
		frame++;
		graphics.dispose();
		bufferStrategy.show();
	}
	
	@Override
	public void run()
	{
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		int i = 0;
		int x = 0;
		
		long lastTime = System.nanoTime();
		double nanoSecondConversion = 1000000000.0 / 60;
		double changeInSeconds = 0;
		
		while(true) {
			long now = System.nanoTime();
			changeInSeconds += (now - lastTime);
			while(changeInSeconds >= 1){
				update();
				changeInSeconds = 0;
			}
			render();
			lastTime = now;
		}
	}
	
	public static void main(String[] args)
	{
		Engine engine = new Engine();
		Thread engineThread = new Thread(engine);
		engineThread.start();
	}
}
