import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Alien extends GameObject {
	final static int DIAMETER = 42;
	protected BufferedImage img;

	public Alien(int x, int y, int velocityX, int velocityY) {
		super(x, y, velocityX, velocityY, DIAMETER, DIAMETER);
		img = null;
		try {
		    img = ImageIO.read(new File("alien.jpg"));
		} catch (IOException e) {
		}

	}

	
	public int getXVelocity(){
		return velocityX;
	}
	
	public void setXVelocity(int x){
		velocityX = x;
	}
	
	public void accelerate() {
		if (x < 0){
			velocityX =  Math.abs(velocityX);
		}
		else if (x > rightBound){
			velocityX = -Math.abs(velocityX);
		}
		if (y < 0){
			velocityY =  Math.abs(velocityY);
		}
		else if (y > bottomBound){
			velocityY = -Math.abs(velocityY);
		}
	}

	public void move(){
		super.move();
	}
	

	public void draw(Graphics g) {
		if(isAlive){
			//g.fillOval(x, y, DIAMETER, DIAMETER);
			g.drawImage(img, x,y,null);
		}
	}

}
