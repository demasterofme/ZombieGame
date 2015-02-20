package entity.livingEntity;

import gfx.MuzzleFlash;
import inGame.InGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import launcher.GamePanel;
import entity.Bullet;
import entity.Gun;

public class Player extends LivingEntity {

	private boolean left, right, up, down = false;

	private int score = 0;
	private int money = 0;

	private boolean firing;
	private long firingTimer;

	private boolean recovering;
	private long recoveringTimer;

	private Gun gun;

	public static BufferedImage texture_head;
	public static BufferedImage texture_bottom;

	public Player(int x, int y) {
		super(x, y);
		gun = InGame.guns.get(Gun.GunType.AK47);
		speed = 2;
		r = 30;
	}

	public boolean update() {

		dx = 0;
		dy = 0;

		if (left)
			dx = -speed;
		if (right)
			dx = speed;
		if (up)
			dy = -speed;
		if (down)
			dy = speed;

		x += dx;
		y += dy;

		if (x < GamePanel.WINDOW_WIDTH / 2)
			x = GamePanel.WINDOW_WIDTH / 2;
		if (y < GamePanel.WINDOW_HEIGHT / 2)
			y = GamePanel.WINDOW_HEIGHT / 2;
		if (x > InGame.map.getWidth() - GamePanel.WINDOW_WIDTH / 2)
			x = InGame.map.getWidth() - GamePanel.WINDOW_WIDTH / 2;
		if (y > InGame.map.getHeight() - GamePanel.WINDOW_HEIGHT / 2)
			y = InGame.map.getHeight() - GamePanel.WINDOW_HEIGHT / 2;

		if (firing) {

			long elapsed = (System.nanoTime() - firingTimer) / 1000000;

			if (elapsed >= gun.getFireRate()) {
				// We can now fire
				firingTimer = System.nanoTime();
				int x1 = (int) (Math.sin(Math.toRadians(rotation - 90)) * 60.0751);
				int y1 = (int) (Math.cos(Math.toRadians(rotation + 90)) * 60.0751);
				InGame.muzzleFlashes.add(new MuzzleFlash(GamePanel.WINDOW_WIDTH
						/ 2 - x1, GamePanel.WINDOW_HEIGHT / 2 - y1, rotation));
				InGame.bullets.add(new Bullet(x, y, rotation, gun.getDamage()));

			}

		}

		return false;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public boolean isRecovering() {
		return recovering;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setFiring(boolean firing) {
		this.firing = firing;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
		gun.setRotation(rotation);
	}

	public Gun getGun() {
		return gun;
	}

	public void draw(Graphics2D g) {

		BufferedImage tempImage = GamePanel.mergeImages(Player.texture_bottom,
				0, 0, this.getGun().getTexture(), 100, 300);
		BufferedImage tempPlayerImage = GamePanel.mergeImages(tempImage, 0, 0,
				Player.texture_head, 0, 0);

		double scale = 0.1;

		g.drawImage(
				GamePanel.transformImage(tempPlayerImage, scale, rotation + 90),
				(int) (GamePanel.WINDOW_WIDTH / 2 - tempPlayerImage.getWidth()
						* scale / 2),
				(int) (GamePanel.WINDOW_HEIGHT / 2 - tempPlayerImage
						.getHeight() * scale / 2), null);

		if (GamePanel.debugMode) {
			g.setColor(Color.BLUE);
			g.drawOval(GamePanel.WINDOW_WIDTH / 2 - r, GamePanel.WINDOW_HEIGHT
					/ 2 - r, r * 2, r * 2);
		}

	}
}
