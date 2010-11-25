import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tank extends GameObject {
	final static int HEIGHT = 20;
	final static int WIDTH = 50;
	BufferedImage img;

	public Tank(int courtwidth, int courtheight) {
		super((courtwidth - WIDTH) / 2, courtheight - HEIGHT - 20, 0, 0, WIDTH, HEIGHT);
		img = null;
		try {
		    img = ImageIO.read(new File("tank.jpg"));
		} catch (IOException e) {
		}
	}

	public void accelerate() {
		if (x < 0 || x > rightBound)
			velocityX = 0;
		if (y < 0 || y > bottomBound)
			velocityY = 0;
	}

	public void draw(Graphics g) {
		//g.fillRect(x, y, WIDTH, HEIGHT);
		g.drawImage(img, x,y,null);
	}
}
