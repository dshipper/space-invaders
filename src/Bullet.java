import java.awt.Graphics;


public class Bullet extends GameObject{
	final static int WIDTH = 5;
	final static int HEIGHT = 15;
	final static int VELOCITY_X = 0;
	final static int VELOCITY_Y_FROM_TANK = -1;
	final static int VELOCITY_Y_FROM_ALIEN = 1;
	private boolean isAlive;
	
	public Bullet(int x, int y, int velocity){
		super(x, y, VELOCITY_X, velocity, WIDTH, HEIGHT);
		this.isAlive = true;
	}
	
	public void draw(Graphics g){
		System.out.println("Position: " + x + "," + y);
		g.fillRect(x,y, WIDTH, HEIGHT);
	}

	public void accelerate() {
		if (x < 0 || x > rightBound)
			this.isAlive = false;
		if (y < 0 || y > bottomBound)
			this.isAlive = false;
	}
}