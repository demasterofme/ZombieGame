package input;

import java.awt.event.KeyEvent;

import launcher.GamePanel;

public class KeyListener implements java.awt.event.KeyListener {

	private static GamePanel panel;

	public KeyListener(GamePanel panel) {

		this.panel = panel;

	}

	@Override
	public void keyPressed(KeyEvent event) {
		switch (event.getKeyCode()) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			panel.player.setLeft(true);
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			panel.player.setRight(true);
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			panel.player.setUp(true);
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			panel.player.setDown(true);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		switch (event.getKeyCode()) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			panel.player.setLeft(false);
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			panel.player.setRight(false);
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			panel.player.setUp(false);
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			panel.player.setDown(false);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
