import javax.swing.SwingUtilities;


public class Game {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SpaceInvaders();
			}
		});
	}
}
