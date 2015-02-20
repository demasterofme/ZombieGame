package entity.livingEntity;

import inGame.InGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import launcher.GamePanel;
import entity.Bullet;

public class Zombie extends LivingEntity {

	private ZombieType type;

	public static BufferedImage texture;

	public Zombie(ZombieType type, int x, int y) {

		super(x, y);
		this.type = type;
		r = 30;
		health = 1000;
		speed = 1;
	}

	public boolean update() {

		findPath();

		this.x += dx;
		this.y += dy;

		for (int i = 0; i < InGame.bullets.size(); i++) {

			Bullet b = InGame.bullets.get(i);

			if (Math.sqrt(Math.pow(b.getx() - x, 2) + Math.pow(b.gety() - y, 2)) <= r
					+ b.getr()) {
				damage(b.getDamage());
				InGame.bullets.remove(b);
				i--;
			}

		}

		return false;
	}

	public void findPath() {

	}

	public void damage(int damage) {

		health -= damage;
		if (health <= 0)
			dead = true;

	}

	public void draw(Graphics2D g) {

		int relativeX = x - InGame.map.getxOffset();
		int relativeY = y - InGame.map.getyOffset();

		if (relativeX + r > 0 && relativeX - r < GamePanel.WINDOW_WIDTH
				&& relativeY + r > 0 && relativeY - r < GamePanel.WINDOW_HEIGHT) {
			double scale = 0.1;

			g.drawImage(
					GamePanel.transformImage(texture, scale, rotation + 90),
					(int) (relativeX - texture.getWidth() * scale / 2),
					(int) (relativeY - texture.getHeight() * scale / 2), null);
			if (GamePanel.debugMode) {
				g.setColor(Color.RED);
				g.drawOval(relativeX - r, relativeY - r, r * 2, r * 2);
			}
		}
	}
	
	public enum ZombieType {

		SWARMER,
		STALKER,
		CHOKER;
		
	}

}
