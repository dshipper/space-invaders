import java.awt.*;

public class Alien extends GameObject {
	final static int DIAMETER = 21;

	public Alien(int x, int y, int velocityX, int velocityY) {
		super(x, y, velocityX, velocityY, DIAMETER, DIAMETER);
	}

	public void setY(int y){
		this.y = y;
	}
	
	public int getY(){
		return y;
	}
	
	public int getX(){
		return x;
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
			g.fillOval(x, y, DIAMETER, DIAMETER);
		}
	}

	public boolean isAlive() {
		return isAlive;
	}
}
