package objects;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class World extends Thing{
	public BufferedImage image, mars;
	String filename = "World.png";
	String marsname = "Mars.png";
	public World()
	{
		loadImage(filename);
		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream(marsname);
			mars = ImageIO.read(input);	
		}
		catch(IllegalArgumentException e)
		{
			System.out.println(marsname + " could not be found in the project.");
		}
		catch(Exception e)
		{
			e.printStackTrace(); //Prints exception
		}
	}
}
