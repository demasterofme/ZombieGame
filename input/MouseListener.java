package input;

import gameState.InGame;
import gameState.PauseMenu;
import gameState.Settings;
import gameState.Shop;
import gameState.TitleScreen;
import gfx.Button;

import java.awt.event.MouseEvent;

import launcher.GamePanel;

public class MouseListener implements java.awt.event.MouseListener {

	public MouseListener() {
	}

	@Override
	public void mousePressed(MouseEvent event) {
		if (GamePanel.gameState == null)
			return;
		if (GamePanel.gameState instanceof TitleScreen
				|| GamePanel.gameState instanceof Settings
				|| GamePanel.gameState instanceof Shop
				|| GamePanel.gameState instanceof PauseMenu)
			switch (event.getButton()) {
			case 1:
				for (Button b : GamePanel.gameState.getButtons()) {
					if (event.getX() >= b.getx()
							&& event.getX() <= b.getx() + b.getWidth()
							&& event.getY() >= b.gety() - b.getHeight()
							&& event.getY() <= b.gety())
						b.setPressed(true);
				}
			}
		else if (GamePanel.gameState instanceof InGame && InGame.player != null)
			switch (event.getButton()) {
			case 1:
				InGame.player.setFiring(true);
				break;
			}
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if (GamePanel.gameState == null)
			return;
		if (GamePanel.gameState instanceof TitleScreen
				|| GamePanel.gameState instanceof Settings
				|| GamePanel.gameState instanceof Shop)
			switch (event.getButton()) {
			case 1:
				for (Button b : GamePanel.gameState.getButtons()) {
					if (b.isPressed())
						b.setPressed(false);
				}
			}
		else if (GamePanel.gameState instanceof InGame && InGame.player != null)
			switch (event.getButton()) {
			case 1:
				InGame.player.setFiring(false);
				break;
			}
	}

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

}
