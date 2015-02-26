package input;

import gameState.InGame;
import gameState.Settings;
import gameState.Shop;
import gameState.TitleScreen;
import gfx.Button;

import java.awt.event.MouseEvent;

import launcher.GamePanel;

public class MouseMotionListener implements java.awt.event.MouseMotionListener {

	public MouseMotionListener() {
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		if (GamePanel.getGameState() == null)
			return;
		if (GamePanel.getGameState() instanceof TitleScreen
				|| GamePanel.getGameState() instanceof Settings
				|| GamePanel.getGameState() instanceof Shop)
			for (Button b : GamePanel.getGameState().getButtons()) {
				if (event.getX() >= b.getx()
						&& event.getX() <= b.getx() + b.getWidth()
						&& event.getY() >= b.gety() - b.getHeight()
						&& event.getY() <= b.gety())
					b.setHover(true);
				else
					b.setHover(false);
			}
		else if (GamePanel.getGameState() instanceof InGame && InGame.player != null) {
			int x = GamePanel.WINDOW_WIDTH / 2;
			int y = GamePanel.WINDOW_HEIGHT / 2;
			int mouseX = event.getX();
			int mouseY = event.getY();
			int angle = (int) Math.toDegrees(Math.acos((mouseX - x)
					/ (Math.sqrt(Math.pow(mouseX - x, 2)
							+ Math.pow(mouseY - y, 2)))));
			if (mouseY < y)
				angle = 360 - angle;
			InGame.player.setRotation(angle);
		}
		if (GamePanel.debugMode) {
			GamePanel.mouseX = event.getX();
			GamePanel.mouseY = event.getY();
		}
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		if (GamePanel.getGameState() == null)
			return;
		if (GamePanel.getGameState() instanceof TitleScreen
				|| GamePanel.getGameState() instanceof Settings
				|| GamePanel.getGameState() instanceof Shop)
			for (Button b : GamePanel.getGameState().getButtons()) {
				if (event.getX() >= b.getx()
						&& event.getX() <= b.getx() + b.getWidth()
						&& event.getY() >= b.gety() - b.getHeight()
						&& event.getY() <= b.gety())
					b.setHover(true);
				else
					b.setHover(false);
			}
		else if (GamePanel.getGameState() instanceof InGame && InGame.player != null) {
			int x = GamePanel.WINDOW_WIDTH / 2;
			int y = GamePanel.WINDOW_HEIGHT / 2;
			int mouseX = event.getX();
			int mouseY = event.getY();
			int angle = (int) Math.toDegrees(Math.acos((mouseX - x)
					/ (Math.sqrt(Math.pow(mouseX - x, 2)
							+ Math.pow(mouseY - y, 2)))));
			if (mouseY < y)
				angle = 360 - angle;
			InGame.player.setRotation(angle);
		}
		if (GamePanel.debugMode) {
			GamePanel.mouseX = event.getX();
			GamePanel.mouseY = event.getY();
		}
	}

}
