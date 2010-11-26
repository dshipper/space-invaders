import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Instructions extends JFrame{
	public Instructions() {
		super("Space Invaders Instructions");
		setLocation(500, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(350,100));
		final JPanel    panel = new JPanel();
		final JTextArea instructions = new JTextArea("Welcome to Space Invaders!\nSpace Invaders is a game played by moving a tank \nusing the arrow keys and shooting invading aliens by \npressing the space bar. Good luck!");
		instructions.setEditable(false);
		panel.add(instructions);
		add(panel, BorderLayout.NORTH);

        pack();

        setVisible(true);
	}
}
