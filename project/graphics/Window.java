package graphics;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame{
	
	public Window()
	{
		setTitle("Hello World!");     //Sets title of window
		setDefaultCloseOperation(3);  //Exits automatically when closed
		setSize(500, 500);
		setLocationRelativeTo(null);  //Centers the window in the exact middle of the screen
		setVisible(true);
	}
	public int width()
	{
		return getContentPane().getWidth();
	}
	public int height()
	{
		return getContentPane().getHeight();
	}
}
