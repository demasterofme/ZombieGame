package input;

import inGame.InGame;

import java.awt.event.KeyEvent;

import launcher.GamePanel;
import launcher.GameState;

public class KeyListener implements java.awt.event.KeyListener {

	public KeyListener() {
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (GamePanel.gameState == null)
			return;
		if (GamePanel.gameState.equals(GameState.TITLE_SCREEN))
			switch (event.getKeyCode()) {
			case KeyEvent.VK_F3:
				GamePanel.debugMode = !GamePanel.debugMode;
				break;
			}
		else if (GamePanel.gameState.equals(GameState.IN_GAME))
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
			}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		if (GamePanel.gameState == null)
			return;
		if (GamePanel.gameState.equals(GameState.IN_GAME))
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
