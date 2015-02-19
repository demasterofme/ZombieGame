package input;

import java.awt.event.MouseEvent;

import launcher.GamePanel;

public class MouseListener implements java.awt.event.MouseListener {

	public MouseListener() {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent event) {
		switch (event.getButton()) {
		case 1:
			GamePanel.player.setFiring(true);
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		switch (event.getButton()) {
		case 1:
			GamePanel.player.setFiring(false);
			break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}
}
