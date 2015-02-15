package input;

import java.awt.event.MouseEvent;

import launcher.GamePanel;

public class MouseMotionListener implements java.awt.event.MouseMotionListener {

	GamePanel panel;

	public MouseMotionListener(GamePanel panel) {
		this.panel = panel;
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		int x = panel.player.getx();
		int y = panel.player.gety();
		int mouseX = event.getX();
		int mouseY = event.getY();
		int angle = (int) Math
				.toDegrees(Math.acos((mouseX - x)
						/ (Math.sqrt(Math.pow(mouseX - x, 2)
								+ Math.pow(mouseY - y, 2)))));
		if (mouseY < y)
			angle = 360 - angle;
		panel.player.setRotation(angle);
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		int x = panel.player.getx();
		int y = panel.player.gety();
		int mouseX = event.getX();
		int mouseY = event.getY();
		int angle = (int) Math
				.toDegrees(Math.acos((mouseX - x)
						/ (Math.sqrt(Math.pow(mouseX - x, 2)
								+ Math.pow(mouseY - y, 2)))));
		if (mouseY < y)
			angle = 360 - angle;
		panel.player.setRotation(angle);
	}

}
