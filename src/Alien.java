import java.awt.*;

public class Alien extends GameObject {
	final static int DIAMETER = 21;

	public Alien(int x, int y, int velocityX, int velocityY) {
		super(x, y, velocityX, velocityY, DIAMETER, DIAMETER);
	}

	public void accelerate() {
		if (x < 0)
			velocityX =  Math.abs(velocityX);
		else if (x > rightBound)
			velocityX = -Math.abs(velocityX);
		if (y < 0)
			velocityY =  Math.abs(velocityY);
		else if (y > bottomBound)
			velocityY = -Math.abs(velocityY);
	}

	// Bounce the ball, if necessary
	public void bounce(Intersection i) {
	    switch (i) {
	    	case NONE: break;
	    	case UP: velocityY = -Math.abs(velocityY); break;
	    	case DOWN: velocityY = Math.abs(velocityY); break;
	    	case LEFT: velocityX = -Math.abs(velocityX); break;
	    	case RIGHT: velocityX = Math.abs(velocityX); break;
	    }
	}

	public void draw(Graphics g) {
		g.fillOval(x, y, DIAMETER, DIAMETER);
	}
}
