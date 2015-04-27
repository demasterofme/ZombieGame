package launcher;

import javax.swing.JFrame;

public class Launcher {

	public static JFrame window;
	
	public static void main(String[] args) {
		
		window = new JFrame("Zombie Game");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setUndecorated(true);
		window.setContentPane(new GamePanel());
		window.setResizable(false);
		
		window.pack();
		window.setVisible(true);

	}

}
