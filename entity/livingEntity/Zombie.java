package entity.livingEntity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Bullet;
import launcher.GamePanel;

public class Zombie extends LivingEntity {

	private ZombieType type;

	public static BufferedImage texture;

	public Zombie(ZombieType type, int x, int y) {

		super(x, y);
		this.type = type;
		r = 60;
		health = 1000;
	}

	public boolean update() {

		findPath();

		this.x += dx;
		this.y += dy;

		for (int i = 0; i < GamePanel.bullets.size(); i++) {

			Bullet b = GamePanel.bullets.get(i);

			if (Math.sqrt(Math.pow(b.getx() - x, 2) + Math.pow(b.gety() - y, 2)) <= r
					+ b.getr()) {
				damage(b.getDamage());
				GamePanel.bullets.remove(b);
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

		int relativeX = x - GamePanel.map.getxOffset();
		int relativeY = y - GamePanel.map.getyOffset();

		if (relativeX - r > 0 && relativeX + r < GamePanel.WINDOW_WIDTH
				&& relativeY - r > 0 && relativeY + r < GamePanel.WINDOW_HEIGHT) {
			double scale = 0.1;

			g.drawImage(
					GamePanel.transformImage(texture, scale, rotation + 90),
					(int) (relativeX - texture.getWidth() * scale / 2),
					(int) (relativeY - texture.getHeight() * scale / 2), null);

			g.setColor(Color.RED);
			g.drawOval(relativeX - r / 2, relativeY - r / 2, r, r);
		}
	}

}
