package input;

import java.awt.event.MouseEvent;

import launcher.GamePanel;

public class MouseMotionListener implements java.awt.event.MouseMotionListener {

	public MouseMotionListener() {
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		int x = GamePanel.WINDOW_WIDTH / 2;
		int y = GamePanel.WINDOW_HEIGHT / 2;
		int mouseX = event.getX();
		int mouseY = event.getY();
		int angle = (int) Math
				.toDegrees(Math.acos((mouseX - x)
						/ (Math.sqrt(Math.pow(mouseX - x, 2)
								+ Math.pow(mouseY - y, 2)))));
		if (mouseY < y)
			angle = 360 - angle;
		GamePanel.player.setRotation(angle);
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		int x = GamePanel.WINDOW_WIDTH / 2;
		int y = GamePanel.WINDOW_HEIGHT / 2;
		int mouseX = event.getX();
		int mouseY = event.getY();
		int angle = (int) Math
				.toDegrees(Math.acos((mouseX - x)
						/ (Math.sqrt(Math.pow(mouseX - x, 2)
								+ Math.pow(mouseY - y, 2)))));
		if (mouseY < y)
			angle = 360 - angle;
		GamePanel.player.setRotation(angle);
	}

}
