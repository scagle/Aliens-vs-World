package main;

public class TimeManager implements Runnable{
	boolean running = true;
	MainClass main;
	
	public TimeManager(MainClass main)
	{
		this.main = main;
		Thread t = new Thread(this);	//creates a new thread
		t.run();
	}
	public void run() 
	{
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				main.tick();	//calls this exactly (amountOfTicks) per second (60 times per second)
				updates++;
				delta--;
			}
			main.render();	//calls this as many times as possible
			frames++;
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: "+frames+" TICKS: "+updates);
				frames = 0;
				updates = 0;
			}
		}
	}
}
