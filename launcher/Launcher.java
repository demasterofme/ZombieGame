package launcher;

import javax.swing.JFrame;

public class Launcher {

	public static void main(String[] args) {
		
		JFrame window = new JFrame("First Game");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setUndecorated(true);
		window.setContentPane(new GamePanel());
		
		window.pack();
		window.setVisible(true);

	}

}
