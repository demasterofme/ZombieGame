package gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import launcher.GamePanel;

public class MuzzleFlash {

	private int x, y;
	private int rotation;
	private double scale;
	private long startTimer;
	public static BufferedImage texture;

	public MuzzleFlash(int x, int y, int rotation, double scale) {

		this.x = x;
		this.y = y;
		this.rotation = rotation - 90;
		this.scale = scale;
		this.startTimer = System.nanoTime();

	}

	public boolean update() {

		if ((System.nanoTime() - startTimer) / 1000000 >= 50) {

			return true;

		}

		return false;
	}

	public void draw(Graphics2D g) {

		double horScale = 0.2 * GamePanel.horScale * scale;
		double vertScale = 0.2 * GamePanel.vertScale * scale;

		g.drawRenderedImage(texture, GamePanel.getAffineTransform(texture,
				(int) (x - texture.getWidth() * horScale / 2),
				(int) (y - texture.getHeight() * vertScale / 2), horScale,
				vertScale, Math.toRadians(rotation + 90)));
	}
}
