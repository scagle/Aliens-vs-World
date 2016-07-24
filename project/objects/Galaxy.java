package objects;

import java.awt.image.BufferedImage;

public class Galaxy extends Thing{
	BufferedImage image;
	String filename = "Galaxy.jpg";
	public Galaxy()
	{
		loadImage(filename);
	}
}
