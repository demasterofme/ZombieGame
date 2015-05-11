package entity;

import gameState.inGame.InGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.GeneralPath;

import launcher.GamePanel;
import map.Map;

public class Bullet extends Entity {

	private double rad;
	private double dx, dy;
	private int damage;

	public Bullet(double x, double y, int angle, int damage) {
		super(x, y);
		rad = Math.toRadians(angle);

		int speed = (int) (10 * GamePanel.horScale);

		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad) * speed;

		this.damage = damage;

		r = (int) (2* GamePanel.horScale);
	}

	public boolean update() {

		x += dx;
		y += dy;

		if (x < -r || x > InGame.map.getWidth() + r || y < -r
				|| y > InGame.map.getHeight() + r || checkCollisions(dx, 0)
				|| checkCollisions(0, dy)) {
			return true;
		}

		return false;
	}

	public int getDamage() {
		return damage;
	}

	public double getdx() {
		return dx;
	}

	public double getdy() {
		return dy;
	}

	public boolean checkCollisions(double dx, double dy) {

		Rectangle movementRect = new Rectangle((int) x + (int) dx - 5, (int) y
				+ (int) dy - 5, 5, 5);

		for (GeneralPath p : Map.pathfindingMap)

			if (p.intersects(movementRect))
				return true;

		return false;
	}

	// For debugging only
	public void draw(Graphics2D g) {

		int relativeX = (int) x - InGame.map.getxOffset();
		int relativeY = (int) y - InGame.map.getyOffset();

		if (relativeX - r > 0 && relativeX + r < GamePanel.WINDOW_WIDTH
				&& relativeY - r > 0 && relativeY + r < GamePanel.WINDOW_HEIGHT) {

			g.setColor(Color.YELLOW);
			g.drawOval(relativeX, relativeY, r, r);
		}

	}
}
