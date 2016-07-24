package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import objects.Bullet;

public class InputManager implements KeyListener{

	MainClass main;
	
	private static boolean leftDown = false, rightDown = false, spaceDown = false;

	public InputManager(MainClass main)
	{
		this.main = main;
	}
	public void keyReleased(KeyEvent e) 
	{
		switch (e.getKeyChar())
		{
		case 'a':
			leftDown = false;
			break;
		case 'd':
			rightDown = false;
			break;
		case ' ':
			spaceDown = false;
			break;
		}
	}
	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyChar())
		{
		case 'a':
			leftDown = true;
			rightDown = false;
			break;
		case 'd':
			rightDown = true;
			leftDown = false;
			break;
		case ' ':
			spaceDown = true;
			break;
		case '1':
			WeaponsManager.bulletmode = true;
			WeaponsManager.lasermode = false;
			break;
		case '2':
			WeaponsManager.lasermode = true;
			WeaponsManager.bulletmode = false;
			break;
		case 'r':
			main.gameover = false;
			main.world_health_points = main.world_health_max;
			WeaponsManager.number_of_bullets = 0;
			WeaponsManager.bullets = new Bullet[500];
			main.world_death_timer = 0;
			break;
		}
	}
	public static boolean isRightKeyDown()
	{
		return rightDown;
	}
	public static boolean isLeftKeyDown()
	{
		return leftDown;
	}
	public static boolean isSpaceKeyDown()
	{
		return spaceDown;
	}

	public void keyTyped(KeyEvent e){}
	
}
