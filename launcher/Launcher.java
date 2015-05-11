package launcher;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class Launcher {

	public static JFrame window;

	public static void main(String[] args) {

		window = new JFrame("Zombie Apocalypse");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setUndecorated(true);
		window.setContentPane(new GamePanel());
		window.setResizable(false);

		window.pack();
		window.setVisible(true);

		if (GamePanel.screen != 0) {
			GraphicsEnvironment ge = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			GraphicsDevice[] gs = ge.getScreenDevices();
			gs[GamePanel.screen].setFullScreenWindow(window);
		}

	}

}
