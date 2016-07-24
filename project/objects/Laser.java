package objects;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Laser extends Thing{
	BufferedImage image;
	String filename = "Laser.png";
	public int x, y, length;
	public AffineTransform trans;
	public int life = 0;
	public int realX, realY;
	public boolean on = false;
	public Laser()
	{
		this.x = 0;
		this.y = 0;
		this.length = (int) (Math.random() * 100 + 100);
		this.trans = null;
		loadImage(filename);
	}
}
