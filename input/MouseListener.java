package input;

import gfx.Button;
import inGame.InGame;

import java.awt.event.MouseEvent;

import launcher.GamePanel;
import launcher.GameState;
import titleScreen.TitleScreen;

public class MouseListener implements java.awt.event.MouseListener {

	public MouseListener() {
	}

	@Override
	public void mousePressed(MouseEvent event) {
		if (GamePanel.gameState == null)
			return;
		if (GamePanel.gameState.equals(GameState.TITLE_SCREEN))
			switch (event.getButton()) {
			case 1:
				for (Button b : TitleScreen.buttons) {
					if (event.getX() >= b.getx()
							&& event.getX() <= b.getx() + b.getWidth()
							&& event.getY() >= b.gety() - b.getHeight()
							&& event.getY() <= b.gety())
						b.setPressed(true);
				}
			}
		else if (GamePanel.gameState.equals(GameState.IN_GAME))
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
		if (GamePanel.gameState.equals(GameState.TITLE_SCREEN))
			switch (event.getButton()) {
			case 1:
				for (Button b : TitleScreen.buttons) {
					if (b.isPressed())
						b.setPressed(false);
				}
			}
		else if (GamePanel.gameState.equals(GameState.IN_GAME))
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
