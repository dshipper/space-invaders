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

	private int interval = 35; // Milliseconds between updates.
	private Timer timer; // Timer fires to animate one step.

	final int COURTWIDTH = 300;
	final int COURTHEIGHT = 200;
	final int VELOCITY_FROM_TANK = -1;
	final int VELOCTIY_FROM_ALIEN = 1;
	
	final int PADDLE_VEL = 4;

	public Arena() {
		setPreferredSize(new Dimension(COURTWIDTH, COURTHEIGHT));
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
			    	bullets.add(new Bullet(tank.getX(), tank.getY(), VELOCITY_FROM_TANK));
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
		invaders = new Invaders(5,5,1,1);
		grabFocus();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Paint background, border
		invaders.draw(g);
		tank.draw(g);
		if(bullets != null){
			Iterator itr = bullets.iterator();
			while(itr.hasNext()){
				Bullet b = (Bullet) itr.next();
				if(b.isAlive()){
					b.draw(g);
				}
			}
		}
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
					b.setBounds(getWidth(), getHeight());
					b.move();
					invaders.checkIntersection(b);
				}
			}
			repaint(); // Repaint indirectly calls paintComponent.
		}
	}
}
