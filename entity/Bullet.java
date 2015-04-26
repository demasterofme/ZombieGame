package entity;

import gameState.inGame.InGame;

import java.awt.Color;
import java.awt.Graphics2D;

import launcher.GamePanel;

public class Bullet extends Entity {

	private double rad;
	private double dx, dy;
	private int damage;

	public Bullet(float x, float y, int angle, int damage) {
		super(x, y);
		rad = Math.toRadians(angle);

		int speed = 10;

		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad) * speed;

		this.damage = damage;

		r = 2;
	}

	public boolean update() {

		x += dx;
		y += dy;

		if (x < -r || x > InGame.map.getWidth() + r || y < -r
				|| y > InGame.map.getHeight() + r) {
			return true;
		}

		return false;
	}

	public int getDamage() {
		return damage;
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
