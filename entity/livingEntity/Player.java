package entity.livingEntity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import launcher.GamePanel;
import map.Map;
import entity.Bullet;
import entity.Gun;
import gameState.PauseMenu;
import gameState.inGame.InGame;
import gfx.MuzzleFlash;
import gui.Inventory;

public class Player extends LivingEntity {

	private boolean left, right, up, down = false;

	private int score = 0;
	private int money = 0;

	private boolean firing;
	private long firingTimer;

	private boolean reloading;
	private long reloadTimer;

	private boolean recovering;
	private long recoveringTimer;

	private Inventory inventory;

	private int maxHealth;

	public static BufferedImage texture_head;
	public static BufferedImage texture_bottom;

	public Player(int x, int y) {
		super(x, y);
		speed = 2;
		r = 30;
		health = 20;
		inventory = new Inventory(InGame.guns.get(0));

		// Temp, will be done by XML later
		maxHealth = 20;
	}

	public boolean update() {

		dx = 0;
		dy = 0;

		if (left)
			dx = (int) (-speed * (up || down ? 0.6 : 1));
		if (right)
			dx = (int) (speed * (up || down ? 0.6 : 1));
		if (up)
			dy = (int) (-speed * (left || right ? 0.6 : 1));
		if (down)
			dy = (int) (speed * (left || right ? 0.6 : 1));

		if (checkCollisions(dx, 0))
			dx = 0;
		if (checkCollisions(0, dy))
			dy = 0;

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

		Gun gun = inventory.getCurrentGun();

		if (reloading
				&& getInventory().hasGunEquipped()
				&& (System.nanoTime() - reloadTimer) / 1000000000 >= gun
						.getReloadSpeed()) {
			reloading = false;

			if (gun.getMaxBullets() >= gun.getClipSize()) {
				gun.setMaxBullets(gun.getMaxBullets() - gun.getClipSize()
						+ gun.getCurrentBullets());
				gun.setCurrentBullets(gun.getClipSize());
			} else {
				gun.setCurrentBullets(gun.getMaxBullets());
				gun.setMaxBullets(0);
			}
		}

		if (firing && getInventory().hasGunEquipped()) {

			long elapsed = (System.nanoTime() - firingTimer) / 1000000;

			if (elapsed >= gun.getFireRate() && !reloading
					&& gun.getCurrentBullets() > 0) {
				// We can now fire
				firingTimer = System.nanoTime();
				int x1 = (int) (Math.sin(Math.toRadians(rotation - 90)) * 60.0751);
				int y1 = (int) (Math.cos(Math.toRadians(rotation + 90)) * 60.0751);
				InGame.muzzleFlashes.add(new MuzzleFlash(GamePanel.WINDOW_WIDTH
						/ 2 - x1, GamePanel.WINDOW_HEIGHT / 2 - y1, rotation));
				InGame.bullets.add(new Bullet(x, y, rotation, gun.getDamage()));

				gun.setCurrentBullets(gun.getCurrentBullets() - 1);

				// Reloading
				if (gun.getCurrentBullets() == 0) {
					reloading = true;
					reloadTimer = System.nanoTime();
				}

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

	public boolean getLeft() {
		return left;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean getRight() {
		return right;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean getUp() {
		return up;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean getDown() {
		return down;
	}

	public void setFiring(boolean firing) {
		this.firing = firing;
	}

	public void setReloading(boolean reloading) {
		this.reloading = reloading;
	}

	public void setReloadTimer(long reloadTimer) {
		this.reloadTimer = reloadTimer;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
		if (inventory.getCurrentGun() != null)
			inventory.getCurrentGun().setRotation(rotation);
	}

	public boolean isReloading() {
		return reloading;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void resume() {
		reloadTimer += System.nanoTime()
				- ((PauseMenu) GamePanel.getGameState()).getPauseTimer();
		reloadTimer += System.nanoTime()
				- ((PauseMenu) GamePanel.getGameState()).getPauseTimer();
		right = false;
		left = false;
		up = false;
		down = false;
	}

	public boolean checkCollisions(int dx, int dy) {

		Rectangle movementRect = new Rectangle(x + dx - 5, y + dy - 5, 5, 5);

		for (Rectangle r : Map.rectangleList)

			if (movementRect.intersects(r))
				// We are coliding with that rectangle
				return true;

		return false;
	}

	public void draw(Graphics2D g) {

		// Combine the player with the gun
		BufferedImage tempImage;
		if (inventory.getCurrentGun() != null)
			tempImage = GamePanel.mergeImages(Player.texture_bottom, 0, 0,
					inventory.getCurrentGun().getTexture(), 100, 300);
		else
			tempImage = Player.texture_bottom;
		BufferedImage tempPlayerImage = GamePanel.mergeImages(tempImage, 0, 0,
				Player.texture_head, 0, 0);

		double scale = 0.1;

		g.drawRenderedImage(tempPlayerImage, GamePanel.getAffineTransform(
				tempPlayerImage,
				(int) (GamePanel.WINDOW_WIDTH / 2 - tempPlayerImage.getWidth()
						* scale / 2),
				(int) (GamePanel.WINDOW_HEIGHT / 2 - tempPlayerImage
						.getHeight() * scale / 2), scale,
				Math.toRadians(rotation + 90)));

		if (GamePanel.debugMode) {
			g.setColor(Color.RED);
			g.drawOval(GamePanel.WINDOW_WIDTH / 2 - r, GamePanel.WINDOW_HEIGHT
					/ 2 - r, r * 2, r * 2);
			g.setColor(Color.YELLOW);
			g.drawRect(x - InGame.map.getxOffset() - r,
					y - InGame.map.getyOffset() - r, 2 * r, 2 * r);
		}
	}

	public void resetKeys() {
		down = false;
		up = false;
		left = false;
		right = false;
		firing = false;
	}

}
