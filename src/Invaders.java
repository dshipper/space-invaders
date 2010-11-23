import java.awt.Graphics;
import java.util.*;

public class Invaders {
	LinkedList<Alien> aliens;
	private int rows;
	private int columns;
	
	private int xBound;
	private int yBound;
	
	public Invaders(int row, int column, int startX, int startY){
		this.rows = row;
		this.columns = column;
		aliens = new LinkedList();
		for(int i = 0; i < column; i++){
			for(int k = 0; k < row; k++){
				int newStartX = startX+(k*(Alien.DIAMETER + 5));
				int newStartY = startY+(i*(Alien.DIAMETER+5));
				aliens.add(new Alien(newStartX, newStartY, -1, 0));
			}
		}
	}
	public void draw(Graphics g) {
		Iterator itr = aliens.iterator();
		while(itr.hasNext()){
			Alien a = (Alien) itr.next();
			a.draw(g);
		}
	}
	public void setBounds(int width, int height) {
		Iterator itr = aliens.iterator();
		while(itr.hasNext()){
			Alien a = (Alien) itr.next();
			a.setBounds(width, height);
		}
		xBound = width;
		yBound = height;
		
	}
	public void move() {
		boolean changeY = false;
		Iterator itr = aliens.iterator();
		while(itr.hasNext()){
			Alien a = (Alien) itr.next();
			a.move();
			if(a.getX() == 0 || a.getX() >= xBound-Alien.DIAMETER){
				System.out.println("YOOOO");
				changeY = true;
			}
		}
		if(changeY){
			itr = aliens.iterator();
			while(itr.hasNext()){
				Alien a = (Alien) itr.next();
				a.setY(a.getY() + 3);
				a.setXVelocity(-a.getXVelocity());
			}
		}
		
	}
	public void checkIntersection(Bullet b) {
		Iterator itr = aliens.iterator();
		while(itr.hasNext()){
			Alien a = (Alien) itr.next();
			if(b.intersects(a) != Intersection.NONE && b.isAlive() && a.isAlive()){
				b.die();
				a.die();
			}
		}
	}
	
	
}