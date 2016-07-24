package objects;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Turret extends Thing{
	public BufferedImage image, bullet, explosion;
	String filename = "Turret.png";
	String bulletname = "Bullet.png";
	String explosionname = "Explosion.png";
	public int width, height;
	public int rotation = 180;
	public AffineTransform trans = null;
	
	public Turret()
	{
		this.width = 20;
		this.height = 40;
		loadImage(filename);
		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream(bulletname);
			bullet = ImageIO.read(input);	
			input = classLoader.getResourceAsStream(explosionname);
			explosion = ImageIO.read(input);	
		}
		catch(IllegalArgumentException e)
		{
			System.out.println(bulletname + " could not be found in the project.");
		}
		catch(Exception e)
		{
			e.printStackTrace(); //Prints exception
		}
	}
	
	public void rotate(int i)
	{
		rotation+=i;
	}
}
