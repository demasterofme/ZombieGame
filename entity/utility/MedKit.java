package entity.utility;

import gameState.inGame.InGame;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import launcher.GamePanel;

public class MedKit extends Utility {

	private static int boostPerSecond = 5;
	private static int healTime = 15000;

	private long startTime;
	
	private int healTimer = 0;

	public MedKit(BufferedImage texture) {

		super("Medkit", 100, texture);

		r = 60;

	}

	public MedKit deploy(double x, double y) {
		this.x = x;
		this.y = y;
		startTime = System.nanoTime();
		return this;
	}

	public boolean update() {

		long dif = (System.nanoTime() - startTime) / 1000000;

		if (dif >= healTime)
			return true;

		int distanceToPlayer = (int) Math.sqrt(Math.pow(
				x - InGame.player.getx(), 2)
				+ Math.pow(y - InGame.player.gety(), 2));

		if (distanceToPlayer < r + InGame.player.getr()) {
			if (dif > 1000 * healTimer) {
				healTimer++;
				InGame.player.setHealth(InGame.player.getHealth()
							+ boostPerSecond);
				if (InGame.player.getHealth() > InGame.player.getMaxHealth())
					InGame.player.setHealth(InGame.player.getMaxHealth());
			}
		}
		return false;
	}

	public void draw(Graphics2D g) {

		int relativeX = (int) x - InGame.map.getxOffset();
		int relativeY = (int) y - InGame.map.getyOffset();

		if (relativeX + r > 0 && relativeX - r < GamePanel.WINDOW_WIDTH
				&& relativeY + r > 0 && relativeY - r < GamePanel.WINDOW_HEIGHT) {

			double scale = 0.1;

			// Calculate new x and y position
			int x = (int) (relativeX - texture.getWidth() * scale / 2);
			int y = (int) (relativeY - texture.getHeight() * scale / 2);

			g.drawRenderedImage(texture,
					GamePanel.getAffineTransform(texture, x, y, scale, 0));
		}

	}

}
