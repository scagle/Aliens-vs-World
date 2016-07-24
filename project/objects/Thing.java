//Abstract class that helps reduce clutter on the various Bullet/Turret/World/etc.class's

package objects;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

public abstract class Thing {
	
	BufferedImage image;
	
	public BufferedImage getImage()
	{
		return image;
	}
	public void loadImage(String filename)
	{
		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream(filename);
			image = ImageIO.read(input);				
		}
		catch(IllegalArgumentException e)
		{
			System.out.println(filename + " could not be found in the project.");
		}
		catch(Exception e)
		{
			e.printStackTrace(); //Prints exception
		}
	}
}
