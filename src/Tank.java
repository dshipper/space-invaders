import java.awt.*;

public class Tank extends GameObject {
	final static int HEIGHT = 20;
	final static int WIDTH = 50;

	public Tank(int courtwidth, int courtheight) {
		super((courtwidth - WIDTH) / 2, courtheight - HEIGHT - 20, 0, 0, WIDTH, HEIGHT);
	}

	public void accelerate() {
		if (x < 0 || x > rightBound)
			velocityX = 0;
		if (y < 0 || y > bottomBound)
			velocityY = 0;
	}

	public void draw(Graphics g) {
		g.fillRect(x, y, WIDTH, HEIGHT);
	}
}
