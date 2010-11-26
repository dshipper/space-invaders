import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Mothership extends Alien {

	public Mothership(int x, int y, int velocityX, int velocityY) {
		super(x, y, velocityX, velocityY);
		img = null;
		try {
		    img = ImageIO.read(new File("mothership.jpg"));
		} catch (IOException e) {
		}
	}

	public void accelerate() {
		if (x < 0){
			System.out.println("KIOLLING");
			die();
		}
		else if (x > rightBound){
			System.out.println("KILLING");
			die();
		}
		/*if (y < 0){
			die();
		}
		else if (y > bottomBound){
			die();
		}*/
	}
	
}
