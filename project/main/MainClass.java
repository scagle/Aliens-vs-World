/* This is the main class, there is a lot going on in this program.
 * 
 * (Time manager calls render() as fast as possible, and it calls tick() exactly 60 times a second)
 * 
 * 1.) void render() Renders the program
 * 		a.) it rotates things by using the Graphics2D class and by using the AffineTransform.
 * 			- It basically rotates the entire JFrame by however many degrees, draws something, and then rotates the JFrame back.
 * 		b.) Uses BufferStrategy to avoid 'flickering' affect. (it draws the entire frame before showing it instead of drawing the frame in real time)
 * 		
 * 2.) void tick() 'Ticks', or increments the game 60 times a second
 * 
 */
package main;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;

import graphics.Window;
import objects.World;
import objects.Galaxy;
import objects.Turret;
import objects.TurretBody;

@SuppressWarnings("serial")
public class MainClass extends Canvas 
{	
	int world_health_points = 50;
	
	World world;
	TurretBody turretbody;
	Turret turret;
	Galaxy galaxy;
	
	Window window;
	TimeManager time;
	InputManager input;
	WeaponsManager weapons;
	private int WIDTH, HEIGHT;
	boolean gameover = false;
	int world_rotation = 0; //degrees
	int world_death_timer = 0;
	int world_health_max = world_health_points;
	
	public MainClass()
	{
		turretbody = new TurretBody();
		turret = new Turret();
		world = new World();
		galaxy = new Galaxy();
		window = new Window();
		weapons = new WeaponsManager(this);
		input = new InputManager(this);
		window.addKeyListener(input);		//Added keylisteners to both window and canvas because they were acting funny.
		this.addKeyListener(input);
		window.add(this);
		this.setSize(window.width(), window.height()); //sets size of the canvas
		time = new TimeManager(this);
	}
	public void tick()
	{
		if (gameover && world_death_timer < 250)	//Used to increment the transparency of the "GoodBye World...." screen
		{
			world_death_timer++;
		}
		world_rotation+=1;	//rotates the world by 1 degree
		if (input.isRightKeyDown())		//rotates turret left or right
			if (turret.rotation < 250)
				turret.rotation+=2;
		if (input.isLeftKeyDown())
			if (turret.rotation > 120)
				turret.rotation-=2;
		if (input.isSpaceKeyDown())		//Shoots things
		{
			weapons.shoot(10 + turret.width/2-3, HEIGHT/2+20+turret.height/2-15, turret.trans);
		}
		weapons.tick(); //increments bullets and such

	}
	public void render()
	{
		WIDTH = getWidth();   //width
		HEIGHT = getHeight(); //height

		BufferStrategy bs = getBufferStrategy(); 	// All this makes the rendering much
		if (bs == null)								// cleaner and smoother.
		{											//
			createBufferStrategy(2);				// (pre-draws the image, and 'flips' it, or displays everything at once
			return;									//  to avoid annoying flickering.)
		}											//
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;			// allows for rotations and transformations
		
		if (galaxy != null)				//draws background galaxy
		{
			g.drawImage(galaxy.getImage(), 0, 0, WIDTH, HEIGHT, null); 
		}
///////////////////////Making the pretty gun boxes////////////////////////////
		g.setColor(Color.WHITE);
		g.drawString("'a' and 'd' to rotate", 0, 12);
		g.drawString("'Space Bar' to shoot", 0, 24);
		g.drawString("'r' to reset", 0, 36);
		g.drawString("'1' and '2' for guns", 0, 48);

		weapons.drawGUI(g, turret.explosion);
///////////////////////////////////////////////////////////////////////
		if (world != null)				//draws rotated world
		{
			int x = WIDTH/2-150;
			int y = HEIGHT/2-100;
			int worldwidth = 300;
			int worldheight = 300;
			
			AffineTransform original = g2d.getTransform();	//Saves current transformation
			g2d.rotate(world_rotation*Math.PI/360, x+worldwidth/2, y+worldheight/2);
			if (!gameover)
				g2d.drawImage(world.getImage(), x, y, worldwidth, worldheight, null); //(image, x, y, width, height, ImageObserver)
			else
				g2d.drawImage(world.mars, x+12, y+12, worldwidth-25, worldheight-25, null); //(image, x, y, width, height, ImageObserver)

			g2d.setTransform(original); //resets rotation so that next stuff doesn't get jacked up
		}
		int turretx = 10;
		int turrety = HEIGHT/2+20;
		if (turretbody != null) //draws the UFO/Turret body
		{
			g.drawImage(turretbody.getImage(), turretx-15, turrety-5, 50, 50, null);
		}
		if (turret != null)				//draws rotated turret
		{
			AffineTransform original = g2d.getTransform();
			g2d.rotate(turret.rotation*Math.PI/360, turretx+turret.width/2, turrety+turret.height/2);
			turret.trans = g2d.getTransform();
			g2d.drawImage(turret.getImage(), turretx, turrety, turret.width, turret.height, null); //(image, x, y, width, height, ImageObserver)
			g2d.setTransform(original); //resets rotation so that next stuff doesn't get jacked up
		}
		weapons.drawWeapons(g, WIDTH, HEIGHT, turret);
/////////////////////Draws World's Health Bar////////////////////////////
		g.setColor(Color.DARK_GRAY);
		g.fillRect(WIDTH/2-110, 130, 220, 20);
		g.setColor(Color.gray);
		g.fillRect(WIDTH/2-108, 132, 216, 16);
		g.setColor(Color.RED);
		g.fillRect(WIDTH/2-106, 134, 212, 12);
		g.setColor(Color.GREEN);
		if (world_health_points >= 0)	//Basically fills a green rectangle over a red rectangle (resizing the green rectangle appropriately)
			g.fillRect(WIDTH/2-106, 134, (int)(((double)(212/world_health_max+0.25)*world_health_points)), 12);
/////////////////////////////////////////////////////////////////////////
		//Draws the Hello World
		g2d.setFont(new Font("Arial", 0, 50));
		g2d.setColor(Color.WHITE);
		String str = "Hello World!";
	    FontMetrics fm = g2d.getFontMetrics(); //This stuff gets exact width of the string "Hello World" so that it can be centered on the screen
	    Rectangle2D r = fm.getStringBounds(str, g2d);
	    int x = (this.getWidth() - (int) r.getWidth()) / 2; //gets exact x value to center text.
	    g2d.drawString(str, x, 100);
		if (gameover)	// Draws the Goodbye World screen
		{
			float alpha = world_death_timer/250f;	//sets alpha transparency (increases opacity by 1/250 every tick)
			int type = AlphaComposite.SRC_OVER; 	//Not quite sure what this does
			AlphaComposite composite = 
			  AlphaComposite.getInstance(type, alpha);
			g2d.setComposite(composite);
			g2d.setColor(Color.RED);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			g2d.setColor(new Color(100, 20, 20));
			g2d.setFont(new Font("Arial", 0, 50));
			String stringTime = "Goodbye World....";
		    fm = g2d.getFontMetrics();
		    r = fm.getStringBounds(stringTime, g2d);
		    x = (this.getWidth() - (int) r.getWidth()) / 2;
		    int y = (this.getHeight() - (int) r.getHeight()) / 2 + fm.getAscent();
		    g2d.drawString(stringTime, x, y);
		}
		g.dispose();	//disposes current screen
		bs.show();		//'flips' the pre-drawn buffered image onto current screen
	}

	public static void main(String[] args)
	{
		new MainClass();
	}
}
