package gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import launcher.GamePanel;
import entity.Entity;
import gameState.inGame.InGame;

public class DeadZombie extends Entity {

	private long died;
	private int timer;

	private int rotation;

	public static BufferedImage texture;

	public DeadZombie(double d, double e) {
		super(d, e);
		died = System.nanoTime();
		timer = 30000;

		rotation = new Random().nextInt(360);

	}

	public boolean update() {
		long diedDif = (System.nanoTime() - died) / 1000000;
		if (diedDif > timer)
			return true;
		return false;
	}

	public void draw(Graphics2D g) {

		int relativeX = (int) x - InGame.map.getxOffset();
		int relativeY = (int) y - InGame.map.getyOffset();

		if (relativeX - r + texture.getWidth() > 0
				&& relativeX + r - texture.getWidth() < GamePanel.WINDOW_WIDTH
				&& relativeY - r + texture.getHeight() > 0
				&& relativeY + r - texture.getHeight() < GamePanel.WINDOW_HEIGHT) {

			double horScale = 0.3 * GamePanel.horScale;
			double vertScale = 0.3 * GamePanel.vertScale;

			// Calculate new x and y position
			int x = (int) (relativeX - texture.getWidth() * horScale / 2);
			int y = (int) (relativeY - texture.getHeight() * vertScale / 2);

			g.drawRenderedImage(texture, GamePanel.getAffineTransform(texture,
					x, y, horScale, vertScale, rotation));

			if (GamePanel.debugMode) {
				g.setColor(new Color(1, 0, 0));
				g.drawOval(relativeX - r / 2, relativeY - r / 2, r, r);
			}
		}

	}

}
