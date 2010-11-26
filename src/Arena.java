import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.*;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class Arena extends JPanel implements MouseMotionListener{
	private Alien ball;
	private Tank tank;
	private  LinkedList<Bullet> bullets;
	private LinkedList<Bullet> aiBullets;
	private Invaders invaders;
	private Set<Bunker> bunkers;

	private int interval = 35; // Milliseconds between updates.
	private Timer timer; // Timer fires to animate one step.

	final int COURTWIDTH = 500;
	final int COURTHEIGHT = 480;
	
	final int PADDLE_VEL = 4;
	
	private int score = 0;
	private int bulletCount = 0;
	private int aiBulletCount = 0;
	private int health = 100;
	private int canShoot = 35;
	
	private boolean gameOver = false;
		
	private Clip laserClip;
	
	private int wave;
	
	private Mothership mothership;

	public Arena() {
		setPreferredSize(new Dimension(COURTWIDTH, COURTHEIGHT+20));
		addMouseMotionListener(this);
		reset();
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		timer = new Timer(interval, new TimerAction());
		try {
            // use one of the WAV of Windows installation
            File laser = new File("laser.wav");     
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new FileInputStream(laser));
            AudioFormat audioFormat = audioInputStream
                  .getFormat();
            DataLine.Info dataLineInfo = new DataLine.Info(
                  Clip.class, audioFormat);
            laserClip = (Clip) AudioSystem.getLine(dataLineInfo);
            laserClip.open(audioInputStream);
		}catch(Exception e){System.out.println("Error loading sound: " + e.toString());}

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
			    	if(bulletCount <= 0){
			    		bullets.add(new Bullet(tank.getX()+((Tank.WIDTH/2)-2), tank.getY()-10, Bullet.VELOCITY_Y_FROM_TANK));
			    		try{
			    			if(!laserClip.isActive()){
			    				laserClip.stop();
			    				laserClip.start();
			    			}
			             } catch (Exception eS) {
			                eS.printStackTrace();
			             }

			    	} 
			    }
			}
			
			public void keyReleased(KeyEvent e) {
				tank.setVelocity(0, 0);
			}
		});
	}
	
	public void mouseMoved(MouseEvent e) {
	       tank.setX(e.getX());
	}

	public void mouseDragged(MouseEvent e) {
	       tank.setX(e.getX());
	}


	public void reset() {
		tank = new Tank(COURTWIDTH, COURTHEIGHT);
		bullets = new LinkedList();
		aiBullets = new LinkedList();
		
		invaders = new Invaders(7, 5,1,1);
		bunkers = new HashSet<Bunker>();
		for(int i = 0; i < 4; i++){
			bunkers.add(new Bunker((i*(Bunker.START_WIDTH+80))+60, COURTHEIGHT-130, 0,0));
		}
		score = 0;
		bulletCount = 0;
		aiBulletCount = 0;
		health = 100;
		wave = 0;
		gameOver = false;
		mothership = null;
		grabFocus();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Paint background, border
		if(!gameOver){
			invaders.draw(g);
			tank.draw(g);
			if(mothership != null){
				if(mothership.isAlive())
					mothership.draw(g);
			}
			Iterator bunk = bunkers.iterator();
			while(bunk.hasNext()){
				Bunker b = (Bunker) bunk.next();
				if(b.isAlive())
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
			
			if(aiBullets != null){
				int newBulletCount = 0;
				Iterator itr = aiBullets.iterator();
				while(itr.hasNext()){
					Bullet b = (Bullet) itr.next();
					if(b.isAlive()){
						b.draw(g);
						newBulletCount +=1;
					}
				}
				aiBulletCount = newBulletCount;
			}
			g.drawString("Bullets: " + (bulletCount +aiBulletCount), 150, COURTHEIGHT+10);
			g.drawString("Score: " + score, 50, COURTHEIGHT+10);
			g.drawString("Health: " + health, 250, COURTHEIGHT+10);
		}
		else{
			g.drawString("GAME OVER!", 200, 200);
		}
	}

	public void startTimer() { timer.start(); }
	

	class TimerAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			canShoot += 7;
			if(invaders.getSize() == 0){
				if(wave <= 3){
					wave += 1;
				}
				invaders = new Invaders(7,5,1,20*wave);
			}
			invaders.setBounds(getWidth(), getHeight());
			invaders.move();
			tank.setBounds(getWidth(), getHeight());
			tank.move();
			if(mothership != null){
				if(mothership.isAlive()){
					mothership.setBounds(getWidth(), getHeight());
					mothership.move();
				}
			}
			if(bullets != null){
				Iterator itr = bullets.iterator();
				while(itr.hasNext()){
					Bullet b = (Bullet) itr.next();
					if(b.isAlive()){
						b.setBounds(getWidth(), getHeight());
						b.move();
						score += invaders.checkIntersection(b, true);
						if(mothership != null){
							if(mothership.isAlive()){
								if(mothership.intersects(b) != Intersection.NONE){
									mothership.die();
									b.die();
									score += 20;
								}
							}
						}
						Iterator bunk = bunkers.iterator();
						while(bunk.hasNext()){
							Bunker bunker = (Bunker) bunk.next();
							if(bunker.intersects(b) != Intersection.NONE && b.isAlive() && bunker.isAlive()){
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
			if(aiBullets != null){
				Iterator itr = aiBullets.iterator();
				while(itr.hasNext()){
					Bullet b = (Bullet) itr.next();
					if(b.isAlive()){
						b.setBounds(getWidth(), getHeight());
						b.move();
						if(b.intersects(tank) != Intersection.NONE){
							health -= 25;
							if(health <= 0){
								gameOver = true;
							}
							b.die();
						}
						Iterator bunk = bunkers.iterator();
						while(bunk.hasNext()){
							Bunker bunker = (Bunker) bunk.next();
							if(bunker.intersects(b) != Intersection.NONE && b.isAlive() && bunker.isAlive()){
								bunker.degrade();
								b.die();
							}
						}
					}
					else{
						itr.remove();
					}
				}
				Iterator bunk = bunkers.iterator();
				while(bunk.hasNext()){
					Bunker bunker = (Bunker) bunk.next();
					invaders.checkIntersection(bunker, false);
				}
				
				doAI();
			}
			repaint(); // Repaint indirectly calls paintComponent.
		}

		private void doAI() {
			LinkedList<Alien> aliens = invaders.getAliens();
			Iterator itr = aliens.iterator();
			while(itr.hasNext()){
				Alien a = (Alien) itr.next();
				if((a.getX() <= tank.getX()+1) && (a.getX() >= tank.getX()-1)){
					if(canShoot % 35 == 0){
						aiBullets.add(new Bullet(a.getX(), a.getY(), Bullet.VELOCITY_Y_FROM_ALIEN));
						canShoot+=7;
						if(wave > 0){
							System.out.println("RELEASE THE MOTHERHSIP!");
							if(mothership == null || !mothership.isAlive()){
								mothership = new Mothership(10, 5, 3, 0);
							}
						}
					}
				}
				if((a.getY() >= tank.getY()-20)){
					gameOver = true;
				}
			}
			
		}
	}
	
}
