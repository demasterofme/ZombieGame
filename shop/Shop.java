package shop;

import java.awt.Color;
import java.awt.Graphics2D;

import launcher.GamePanel;

public class Shop {

	private int width, height;

	public Shop() {

		width = 500;
		height = 500;

	}

	public boolean update() {
		return false;
	}

	public void draw(Graphics2D g) {

		g.setColor(Color.BLACK);
		g.fillRect(GamePanel.WINDOW_WIDTH / 2 - width / 2,
				GamePanel.WINDOW_HEIGHT / 2 - height / 2, width, height);

	}
}
