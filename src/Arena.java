import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Arena extends JPanel {
	private Alien ball;
	private Tank tank;
	private  LinkedList<Bullet> bullets;
	private Invaders invaders;
	private Set<Bunker> bunkers;

	private int interval = 35; // Milliseconds between updates.
	private Timer timer; // Timer fires to animate one step.

	final int COURTWIDTH = 640;
	final int COURTHEIGHT = 480;
	
	final int PADDLE_VEL = 4;
	
	private int score = 0;
	private int bulletCount = 0;

	public Arena() {
		setPreferredSize(new Dimension(COURTWIDTH, COURTHEIGHT+20));
		reset();
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		timer = new Timer(interval, new TimerAction());

		setFocusable(true);
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
			    if (e.getKeyCode() == KeyEvent.VK_LEFT)
			        tank.setVelocity(-PADDLE_VEL, 0);
			    else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			        tank.setVelocity(PADDLE_VEL, 0);
			    else if (e.getKeyCode() == KeyEvent.VK_R)
			        reset();
			    else if (e.getKeyCode() == KeyEvent.VK_SPACE){
			    	if(bulletCount <= 3)
			    		bullets.add(new Bullet(tank.getX()+((Tank.WIDTH/2)-2), tank.getY()-10, Bullet.VELOCITY_Y_FROM_TANK));
			    }
			}
			
			public void keyReleased(KeyEvent e) {
				tank.setVelocity(0, 0);
			}
		});
	}

	public void reset() {
		tank = new Tank(COURTWIDTH, COURTHEIGHT);
		bullets = new LinkedList();
		invaders = new Invaders(7, 6,1,1);
		bunkers = new HashSet<Bunker>();
		for(int i = 0; i < 4; i++){
			bunkers.add(new Bunker((i*(Bunker.START_WIDTH+80))+60, COURTHEIGHT-130, 0,0));
		}
		score = 0;
		bulletCount = 0;
		grabFocus();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Paint background, border
		invaders.draw(g);
		tank.draw(g);
		Iterator bunk = bunkers.iterator();
		while(bunk.hasNext()){
			Bunker b = (Bunker) bunk.next();
			b.draw(g);
		}
		
		if(bullets != null){
			int newBulletCount = 0;
			Iterator itr = bullets.iterator();
			while(itr.hasNext()){
				Bullet b = (Bullet) itr.next();
				if(b.isAlive()){
					b.draw(g);
					newBulletCount +=1;
				}
			}
			bulletCount = newBulletCount;
		}
		g.drawString("Bullets: " + bulletCount, 150, COURTHEIGHT+10);
		g.drawString("Score: " + score, 50, COURTHEIGHT+10);
		
	}

	public void startTimer() { timer.start(); }
	

	class TimerAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			invaders.setBounds(getWidth(), getHeight());
			invaders.move();
			tank.setBounds(getWidth(), getHeight());
			tank.move();
			if(bullets != null){
				Iterator itr = bullets.iterator();
				while(itr.hasNext()){
					Bullet b = (Bullet) itr.next();
					if(b.isAlive()){
						b.setBounds(getWidth(), getHeight());
						b.move();
						score += invaders.checkIntersection(b);
						Iterator bunk = bunkers.iterator();
						while(bunk.hasNext()){
							Bunker bunker = (Bunker) bunk.next();
							if(bunker.intersects(b) != Intersection.NONE && b.isAlive()){
								bunker.degrade();
								b.die();
							}
						}
					}
					else{
						itr.remove();
					}
				}
			}
			repaint(); // Repaint indirectly calls paintComponent.
		}
	}
}
