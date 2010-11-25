import java.awt.Color;
import java.awt.Graphics;


public class Bullet extends GameObject{
	final static int WIDTH = 5;
	final static int HEIGHT = 15;
	final static int VELOCITY_X = 0;
	public final static int VELOCITY_Y_FROM_TANK = -5;
	public final static int VELOCITY_Y_FROM_ALIEN = 10;
	
	public Bullet(int x, int y, int velocity){
		super(x, y, VELOCITY_X, velocity, WIDTH, HEIGHT);
	}
	
	public void draw(Graphics g){
		g.setColor(Color.RED);
		g.fillRect(x,y, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
	}

	public void accelerate() {
		if (x < 0 || x > rightBound)
			this.isAlive = false;
		if (y < 0 || y > bottomBound)
			this.isAlive = false;
	}

	public boolean isAlive() {
		return isAlive;
	}
	
	
}