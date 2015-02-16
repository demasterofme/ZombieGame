package entity.livingEntity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Bullet;
import entity.Gun;
import entity.GunType;
import gfx.MuzzleFlash;
import launcher.GamePanel;

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
		gun = new Gun(GunType.AK47);
		speed = 3;
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

		if (x < r)
			x = r;
		if (y < r)
			y = r;
		if (x > GamePanel.WINDOW_WIDTH - r)
			x = GamePanel.WINDOW_WIDTH - r;
		if (y > GamePanel.WINDOW_HEIGHT - r)
			y = GamePanel.WINDOW_HEIGHT - r;

		if (firing) {

			long elapsed = (System.nanoTime() - firingTimer) / 1000000;

			if (elapsed >= gun.getFireRate()) {
				firingTimer = System.nanoTime();
				GamePanel.bullets.add(new Bullet(x, y, rotation, gun
						.getDamage()));
				int x1 = (int) (Math.sin(Math.toRadians(rotation - 90)) * 60.0751);
				int y1 = (int) (Math.cos(Math.toRadians(rotation + 90)) * 60.0751);
				GamePanel.muzzleFlashes.add(new MuzzleFlash(x - x1, y - y1,
						rotation));
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

		BufferedImage temp_image = GamePanel.mergeImages(Player.texture_bottom,
				0, 0, this.getGun().getTexture(), 100, 300);
		BufferedImage temp_player_image = GamePanel.mergeImages(temp_image, 0,
				0, Player.texture_head, 0, 0);

		double scale = 0.1;

		g.drawImage(GamePanel.transformImage(temp_player_image, scale,
				rotation + 90), (int) (x - temp_player_image.getWidth() * scale
				/ 2), (int) (y - temp_player_image.getHeight() * scale / 2),
				null);

	}
}
