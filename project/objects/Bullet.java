package objects;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends Thing{
	BufferedImage image;
	String filename = "Turret.png";
	public int x, y;
	public AffineTransform trans;
	public int maxlife = (int) (Math.random() * 150)+101, life = 0;
	public int realX, realY;
	
	public Bullet(int x, int y, AffineTransform trans)
	{
		this.x = x;
		this.y = y;
		this.trans = trans;
		loadImage(filename);
	}
	public void map() 
	{
		//System.out.println(trans.getTranslateX() + " ,  " + trans.getTranslateY());

	}
}
