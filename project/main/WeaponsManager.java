package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import objects.Bullet;
import objects.Laser;
import objects.Turret;

public class WeaponsManager {
	static Bullet[] bullets = new Bullet[500]; //Array for the massive amounts of bullets
	private Laser laser = new Laser();
	
	static int number_of_bullets = 0;
	static boolean bulletmode = true;
	static boolean lasermode = false;
	MainClass main;
	public WeaponsManager(MainClass main)
	{
		this.main = main;
	}
	public void shoot(int x, int y, AffineTransform trans)
	{
		if (bulletmode)
		{
			bullets[number_of_bullets] = new Bullet(x, y, trans);
			number_of_bullets+=1;
		}
		/*if (lasermode)
		{
			if (laser.life <= 0) //fires only when last laser has expired back to (0)
			{
				laser.life = 5;
				main.world_health_points--;
			}
		}*/
	}
	public void drawWeapons(Graphics g, int WIDTH, int HEIGHT, Turret turret)
	{
		Graphics2D g2d = (Graphics2D)g;
		if (lasermode) //Makes the laser
		{
			if (InputManager.isSpaceKeyDown())
			{
				laser.x = 10 + turret.width/2-3;
				laser.y = HEIGHT/2+20+turret.height/2-15;
				laser.trans = turret.trans;
				g.setColor(Color.MAGENTA);
				AffineTransform original = g2d.getTransform(); //get original transformation
				g2d.setTransform(laser.trans);					//rotates whole screen by laser.transformation (which is turret.transformation basically)
				g2d.rotate(-90*Math.PI/180, laser.x, laser.y);	//rotates screen by an additional -90 degrees
				g2d.drawImage(laser.getImage(), laser.x-10, laser.y-2, laser.length, 7, null);
				g2d.setTransform(original);					//reset transformation back to normal
			}
		}
		for (int i = 0; i < number_of_bullets; i++)  //draws bullets
		{
			AffineTransform original = g2d.getTransform();	//get original position
			if (bullets[i] != null && (bullets[i].maxlife-bullets[i].life) >= 20)
			{
				g2d.setTransform(bullets[i].trans);
				g2d.rotate(-90*Math.PI/180, bullets[i].x, bullets[i].y);
				g2d.drawImage(turret.bullet, bullets[i].x-2, bullets[i].y-2, 15, 8, null);
				g2d.setTransform(original);
			}
		}
		for (int i = 0; i < number_of_bullets; i++)	//draws bullet's explosions (if its at the end of its life)
		{
			if (bullets[i] != null)
			{
				int life = bullets[i].life;
				int maxlife = bullets[i].maxlife;
				if (life+20 >= maxlife)			//explodes exactly 20 'ticks' before end of it's life
												//explosion scales with remaining life
				{
					AffineTransform original = g2d.getTransform();
					g2d.setTransform(bullets[i].trans);
					g2d.drawImage(turret.explosion, bullets[i].x-(maxlife-life)*5/2, bullets[i].y-10-(maxlife-life)*5/2, (maxlife-life)*5, (maxlife-life)*5, null);
					g2d.setTransform(original);
				}
			}
		}
	}
	public void drawGUI(Graphics g, Image turretexplosion)
	{
		g.setColor(Color.BLACK);
		g.fillRect(150, 5, 52, 52);
		g.fillRect(210, 5, 52, 52);
		g.setColor(Color.ORANGE);
		g.fillRect(151, 6, 50, 50);
		g.setColor(Color.MAGENTA);
		g.fillRect(211, 6, 50, 50);
		g.setColor(Color.WHITE);
		if (bulletmode)		//If in machine gun mode then...
		{
			g.fillRect(156, 11, 40, 40);
			g.setColor(Color.BLACK);
			g.fillRect(216, 11, 40, 40);
		}
		if (lasermode)		//If in laser mode then...
		{
			g.fillRect(216, 11, 40, 40);
			g.setColor(Color.BLACK);
			g.fillRect(156, 11, 40, 40);
		}
		g.drawImage(turretexplosion, 156, 11, 40, 40, null);	//explosion image is stored in Turret.class (not very organized I know)
		g.drawImage(laser.getImage(), 216, 11, 40, 40, null);
		g.setColor(Color.GREEN);
		g.drawString("(1)", 150, 50);
		g.drawString("(2)", 210, 50);
	}
	public void tick()
	{
		for (int i = 0; i < number_of_bullets; i++)	//updates the bullets
		{
			if (bullets[i] != null)	//used to provide program stability. (this program is very asynchronous, so this helps with timing bugs where a bullet gets erased at weird times)
			{
				bullets[i].y--;
				bullets[i].life++;
				if (bullets[i].maxlife - bullets[i].life == 20)
					main.world_health_points--;
				if (bullets[i].life >= bullets[i].maxlife) //erase shot and move others in array
				{
					for (int x = i; x < number_of_bullets-1; x++)
					{
						bullets[x] = bullets[x+1];
					}
					bullets[number_of_bullets-1] = null;
					if (main.world_health_points <= 0 )
					{
						main.gameover = true;
					}
				}
			}
		}
		if (lasermode)
		{
			if (InputManager.isSpaceKeyDown())
			{
				if (laser.life >= 5)	//laser stays on a certain length for exactly 5 'ticks'
				{						//...so basically it strikes the earth 60/5 = 12 times per second
					laser.life = 0;	
					main.world_health_points--;
					laser.length = (int) (Math.random()*100 + 200); //differs the length of the laser to something else
					if (main.world_health_points <= 0 )	//if world is dead....
					{
						main.gameover = true;
					}
				}
				else
				{
					laser.life++;
				}
			}
		}
	}
}
