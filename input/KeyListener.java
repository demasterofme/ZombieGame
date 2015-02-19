package input;

import java.awt.event.KeyEvent;

import launcher.GamePanel;

public class KeyListener implements java.awt.event.KeyListener {


	public KeyListener() {
	}

	@Override
	public void keyPressed(KeyEvent event) {
		switch (event.getKeyCode()) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			GamePanel.player.setLeft(true);
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			GamePanel.player.setRight(true);
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			GamePanel.player.setUp(true);
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			GamePanel.player.setDown(true);
			break;
		case KeyEvent.VK_Z:
			System.out.println(GamePanel.player.getx() + " " + GamePanel.player.gety());
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		switch (event.getKeyCode()) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			GamePanel.player.setLeft(false);
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			GamePanel.player.setRight(false);
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			GamePanel.player.setUp(false);
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			GamePanel.player.setDown(false);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
