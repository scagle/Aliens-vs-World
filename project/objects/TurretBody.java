package objects;

import java.awt.image.BufferedImage;

public class TurretBody extends Thing{
	BufferedImage image;
	String filename = "UFO.png";
	public TurretBody()
	{
		loadImage(filename);
	}
}
