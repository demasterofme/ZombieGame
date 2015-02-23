package input;

import gameState.InGame;
import gameState.Shop;
import gameState.TitleScreen;

import java.awt.event.KeyEvent;

import launcher.GamePanel;

public class KeyListener implements java.awt.event.KeyListener {

	public KeyListener() {
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (GamePanel.gameState == null)
			return;
		if (GamePanel.gameState instanceof TitleScreen)
			switch (event.getKeyCode()) {
			case KeyEvent.VK_F3:
				GamePanel.debugMode = !GamePanel.debugMode;
				break;
			case KeyEvent.VK_ESCAPE:
				GamePanel.running = false;
				break;
			}
		else if (GamePanel.gameState instanceof InGame)
			switch (event.getKeyCode()) {
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				InGame.player.setLeft(true);
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				InGame.player.setRight(true);
				break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				InGame.player.setUp(true);
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				InGame.player.setDown(true);
				break;
			case KeyEvent.VK_F3:
				GamePanel.debugMode = !GamePanel.debugMode;
				break;
			case KeyEvent.VK_R:
				InGame.player.setReloadTimer(System.nanoTime());
				InGame.player.setReloading(true);
				break;
			case KeyEvent.VK_ESCAPE:
				GamePanel.gameState = new Shop((InGame) GamePanel.gameState);
				break;
			}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		if (GamePanel.gameState == null)
			return;
		if (GamePanel.gameState instanceof InGame)
			switch (event.getKeyCode()) {
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				InGame.player.setLeft(false);
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				InGame.player.setRight(false);
				break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				InGame.player.setUp(false);
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				InGame.player.setDown(false);
				break;
			}
	}

	public void keyTyped(KeyEvent arg0) {
	}

}
