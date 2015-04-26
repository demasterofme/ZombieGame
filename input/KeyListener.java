package input;

import gameState.AlertBox;
import gameState.PauseMenu;
import gameState.TitleScreen.TitleScreen;
import gameState.inGame.InGame;
import gameState.inGame.Shop;

import java.awt.event.KeyEvent;

import launcher.GamePanel;

public class KeyListener implements java.awt.event.KeyListener {

	public KeyListener() {
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (GamePanel.getGameState() == null)
			return;
		if (GamePanel.getGameState() instanceof TitleScreen)
			switch (event.getKeyCode()) {
			case KeyEvent.VK_F3:
				GamePanel.debugMode = !GamePanel.debugMode;
				break;
			case KeyEvent.VK_ESCAPE:
				GamePanel.running = false;
				break;
			}
		else if (GamePanel.getGameState() instanceof InGame)
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
			case KeyEvent.VK_E:
				InGame.player.getInventory().cycleSelected(true);
				break;
			case KeyEvent.VK_Q:
				InGame.player.getInventory().cycleSelected(false);
				break;
			case KeyEvent.VK_ESCAPE:
				GamePanel.changeGameState(new PauseMenu((InGame) GamePanel
						.getGameState()));
				break;
			case KeyEvent.VK_SPACE:
				GamePanel.changeGameState(new Shop((InGame) GamePanel
						.getGameState(), false));
				break;
			case KeyEvent.VK_U:
				GamePanel.changeGameState(new AlertBox((InGame) GamePanel.getGameState(), 900, 400, "Hello world"));
				break;
			}
		else if (GamePanel.getGameState() instanceof Shop)
			switch (event.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
			case KeyEvent.VK_SPACE:
				GamePanel.changeGameState(((Shop) GamePanel.getGameState())
						.getOldState());
				break;
			}
		else if (GamePanel.getGameState() instanceof PauseMenu)
			switch (event.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				((PauseMenu) GamePanel.getGameState()).getOldState()
						.getPlayer().resume();
				GamePanel
						.changeGameState(((PauseMenu) GamePanel.getGameState())
								.getOldState());
				break;
			}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		if (GamePanel.getGameState() == null)
			return;
		if (GamePanel.getGameState() instanceof InGame)
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
