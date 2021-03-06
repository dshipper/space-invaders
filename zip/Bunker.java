import java.awt.Color;
import java.awt.Graphics;


public class Bunker extends GameObject{
	
	final static int START_WIDTH = 39;
	final static int START_HEIGHT = 26;
	

	public Bunker(int x, int y, int velocityX, int velocityY) {
		super(x, y, velocityX, velocityY, START_WIDTH, START_HEIGHT);
		width = START_WIDTH;
		height = START_HEIGHT;
	}
	
	public void degrade(){
		width /= 1.1;
		height /= 1.1;
		if(width < 10 || height < 10){
			die();
		}
	}

	@Override
	public void accelerate() {
		//can't accelerate.
	}

	@Override
	public void draw(Graphics g) {
		if(isAlive){
			g.setColor(Color.ORANGE);
			g.fillRect(x, y, width, height);
			g.setColor(Color.BLACK);
		}
	}

}
