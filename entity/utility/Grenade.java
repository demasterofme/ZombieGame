package entity.utility;

import entity.livingEntity.Zombie;
import gameState.inGame.InGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import launcher.GamePanel;

public class Grenade extends Utility {

	private static int fuse = 5000;

	private long startTime;

	private double speed;

	private int speedTimer = 0;

	private double angle;
	
	public Grenade(BufferedImage texture, BufferedImage textureInHand) {

		super("Grenade", 100, texture, textureInHand);

		r = 200;

	}

	public Grenade deploy(double x, double y, double angdeg) {

		Grenade toReturn = new Grenade(texture, textureInHand);
		toReturn.x = x;
		toReturn.y = y;
		toReturn.angle = Math.toRadians(angdeg);
		toReturn.speed = 2;
		toReturn.dx = Math.cos(angle) * speed;
		toReturn.dy = Math.sin(angle) * speed;
		toReturn.startTime = System.nanoTime();
		return toReturn;

	}

	public boolean update() {

		long dif = (System.nanoTime() - startTime) / 1000000;

		if (dif <= 1000) {
			x += dx;
			y += dy;
			if (dif > 100 * speedTimer) {
				speedTimer++;
				speed -= 0.2;
				dx = Math.cos(angle) * speed;
				dy = Math.sin(angle) * speed;
			}
		}

		if (dif >= fuse) {
			for (Zombie z : InGame.zombies) {
				int dist = (int) Math.sqrt(Math.pow(x - z.getx(), 2)
						+ Math.pow(y - z.gety(), 2));
				if (dist <= r + z.getr())
					z.damage(600);
			}
			return true;
		}

		return false;
	}

	public void draw(Graphics2D g) {

		int relativeX = (int) x - InGame.map.getxOffset();
		int relativeY = (int) y - InGame.map.getyOffset();

		if (relativeX - r + texture.getWidth() > 0
				&& relativeX + r - texture.getWidth() < GamePanel.WINDOW_WIDTH
				&& relativeY - r + texture.getHeight() > 0
				&& relativeY + r - texture.getHeight() < GamePanel.WINDOW_HEIGHT) {

			double scale = 0.07;

			// Calculate new x and y position
			int x = (int) (relativeX - texture.getWidth() * scale / 2);
			int y = (int) (relativeY - texture.getHeight() * scale / 2);

			g.drawRenderedImage(texture,
					GamePanel.getAffineTransform(texture, x, y, scale, 0));

			if (GamePanel.debugMode) {

				g.setColor(Color.MAGENTA);
				g.drawOval(relativeX - r, relativeY - r, r * 2, r * 2);

			}

		}

	}

}