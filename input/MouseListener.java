package input;

import gameState.InGame;
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
		if (GamePanel.gameState instanceof TitleScreen)
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
		if (GamePanel.gameState instanceof TitleScreen)
			switch (event.getButton()) {
			case 1:
				for (Button b : TitleScreen.buttons) {
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
