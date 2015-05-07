package entity.utility;

import entity.livingEntity.Zombie;
import gameState.inGame.InGame;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import launcher.GamePanel;

public class Grenade extends Utility {

	private static int fuse = 5000;

	private long startTime;
	
	private double speed;
	private double angle;

	public Grenade(BufferedImage texture) {

		super("Grenade", 100, texture);

		r = 60;

	}

	public Grenade deploy(double x, double y, double angle) {
		this.x = x;
		this.y = y;
		this.angle = Math.toRadians(angle);
		speed = 0.1;
		dx = Math.cos(angle) * speed;
		dy = Math.sin(angle) * speed;
		startTime = System.nanoTime();
		return this;
	}

	public boolean update() {

		long dif = (System.nanoTime() - startTime) / 1000000;
		
		if (dif <= 1000) {
			x += dx;
			y += dy;
			if (dif % 100 == 0) {
				speed -= 0.01;
				dx = Math.cos(angle) * speed;
				dy = Math.cos(angle) * speed;
			}
		}

		if (dif >= 5000) {
			for (Zombie z : InGame.zombies) {
				int dist = (int) Math.sqrt(Math.pow(x - z.getx(), 2)
						+ Math.pow(y - z.gety(), 2));
				if (dist <= r + z.getr())
					z.damage(600);
			}
			return false;
		}

		return true;
	}

	public void draw(Graphics2D g) {

		int relativeX = (int) x - InGame.map.getxOffset();
		int relativeY = (int) y - InGame.map.getyOffset();

		if (relativeX + r > 0 && relativeX - r < GamePanel.WINDOW_WIDTH
				&& relativeY + r > 0 && relativeY - r < GamePanel.WINDOW_HEIGHT) {

			double scale = 0.08;

			// Calculate new x and y position
			int x = (int) (relativeX - texture.getWidth() * scale / 2);
			int y = (int) (relativeY - texture.getHeight() * scale / 2);

			g.drawRenderedImage(texture,
					GamePanel.getAffineTransform(texture, x, y, scale, 0));
		}

	}

}