import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class SpaceInvaders extends JFrame {

	private SpaceInvaders() {
		super("Space Invaders");
		setLocation(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JPanel    panel = new JPanel();
		final JButton   reset = new JButton("Reset");
		final Arena court = new Arena();

		panel.add(reset);
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.reset();
			}
		});

		add(panel, BorderLayout.NORTH);
		add(court, BorderLayout.CENTER);

        pack();

        setVisible(true);
		court.startTimer();
		court.grabFocus();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SpaceInvaders();
			}
		});
	}

}
