package entity.livingEntity;

import entity.Bullet;
import entity.Gun;
import entity.utility.Grenade;
import entity.utility.MedKit;
import entity.utility.Utility;
import gameState.inGame.InGame;
import gfx.MuzzleFlash;
import gui.Inventory;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import launcher.GamePanel;
import map.Map;
import sfx.Sound;
import stats.Stats;

public class Player extends LivingEntity {

	private boolean left, right, up, down = false;

	private int money = 0;

	private boolean firing;
	private long firingTimer;

	private boolean reloading;
	private long reloadTimer;

	private boolean recovering;
	private long recoveringTimer;

	private Inventory inventory;
	private Gun gun;

	private int maxHealth;

	private Stats stats;

	public static BufferedImage texture_bottom;
	public static BufferedImage texture_head;

	private static int clipNumber = -1;
	public static Sound walk_sound1;
	public static Sound walk_sound2;
	public static Sound walk_sound3;
	public static Sound walk_sound4;

	public Player(float x, float y) {
		super(x, y);
		speed = 1 * GamePanel.horScale;
		r = (int) (30 * GamePanel.horScale);
		health = 100;
		inventory = new Inventory(InGame.guns.get(0));
		gun = inventory.getCurrentGun();

		// Temp, will be done by XML later
		maxHealth = 100;
		stats = new Stats();
	}

	public boolean update() {

		dx = 0;
		dy = 0;

		gun = inventory.getCurrentGun();

		if (gun != null)
			speed = 1 / (gun.getWeight() * 0.0007);
		else
			speed = 1;

		if (left)
			dx = -speed * (up || down ? Math.sqrt(0.5) : 1);
		if (right)
			dx = speed * (up || down ? Math.sqrt(0.5) : 1);
		if (up)
			dy = -speed * (left || right ? Math.sqrt(0.5) : 1);
		if (down)
			dy = speed * (left || right ? Math.sqrt(0.5) : 1);

		if (checkCollisions(dx, 0))
			dx = 0;
		if (checkCollisions(0, dy))
			dy = 0;

		if (dx != 0 || dy != 0) {
			if (clipNumber != -1) {
				if (!getSoundByNumber(clipNumber).isRunning()) {
					clipNumber = new Random().nextInt(4) + 1;
					getSoundByNumber(clipNumber).play();
				}
			} else {
				clipNumber = new Random().nextInt(4) + 1;
				getSoundByNumber(clipNumber).play();
			}
		}

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

		if (reloading
				&& getInventory().hasGunEquipped()
				&& (System.nanoTime() - reloadTimer) / 1000000000 >= gun
						.getReloadSpeed()) {
			reloading = false;

			if (gun.getBullets() >= gun.getClipSize()) {
				gun.setBullets(gun.getBullets() - gun.getClipSize()
						+ gun.getCurrentClip());
				gun.setCurrentClip(gun.getClipSize());
			} else {
				gun.setCurrentClip(gun.getBullets());
				gun.setMaxBullets(0);
			}
		}

		if ((System.nanoTime() - recoveringTimer) / 1000000 > 100)
			recovering = false;

		if (firing) {

			if (getInventory().hasGunEquipped()) {

				long elapsed = (System.nanoTime() - firingTimer) / 1000000;

				if (elapsed >= 60000 / gun.getFireRate() && !reloading
						&& gun.getCurrentClip() > 0) {
					firingTimer = System.nanoTime();
					int x1 = (int) (Math.sin(Math.toRadians(rotation - 90)) * (gun
							.getName().equals("G17") ? 78 / GamePanel.horScale : 102.4 / GamePanel.horScale));
					int y1 = (int) (Math.cos(Math.toRadians(rotation + 90)) * (gun
							.getName().equals("G17") ? 78 / GamePanel.vertScale : 102.4 / GamePanel.vertScale));
					InGame.muzzleFlashes.add(new MuzzleFlash(
							GamePanel.WINDOW_WIDTH / 2 - x1,
							GamePanel.WINDOW_HEIGHT / 2 - y1, (int) rotation));
					InGame.bullets.add(new Bullet(x, y, (int) rotation, gun
							.getDamage()));
					stats.addBulletsFired(1);

					gun.setCurrentClip(gun.getCurrentClip() - 1);

					// Reloading
					if (gun.getCurrentClip() == 0) {
						reloading = true;
						reloadTimer = System.nanoTime();
					}

				}

			} else if (getInventory().hasUtilityEquipped()) {

				long elapsed = (System.nanoTime() - firingTimer) / 1000000;

				if (elapsed >= 1000 && !reloading) {

					firingTimer = System.nanoTime();

					Utility current = Inventory.getFirstKey(getInventory()
							.getCurrentUtility());

					if (current instanceof MedKit) {

						MedKit toDeploy = ((MedKit) Inventory
								.getFirstKey(inventory.getCurrentUtility()))
								.deploy(x, y);

						InGame.deployedUtilities.add(toDeploy);

					} else if (current instanceof Grenade) {

						Grenade toDeploy = ((Grenade) Inventory
								.getFirstKey(inventory.getCurrentUtility()))
								.deploy(x, y, (int) rotation);

						InGame.deployedUtilities.add(toDeploy);

					}

					getInventory().removeItem(getInventory().getSelectedSlot());

				}

			}

		}

		return false;
	}

	public void damage(int damage) {
		if (!recovering) {
			this.health -= damage;
			recovering = true;
			recoveringTimer = System.nanoTime();
			if (health <= 0)
				this.dead = true;
		}
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

	private Sound getSoundByNumber(int number) {
		if (number == 1)
			return walk_sound1;
		if (number == 2)
			return walk_sound2;
		if (number == 3)
			return walk_sound3;
		if (number == 4)
			return walk_sound4;
		return null;
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

	public int getMaxHealth() {
		return maxHealth;
	}

	public Stats getStats() {
		return stats;
	}

	public void resume(long pauseTimer) {

		reloadTimer += System.nanoTime() - pauseTimer;

		resetKeys();
	}

	public boolean checkCollisions(double dx, double dy) {

		Rectangle2D.Double movementRect = new Rectangle2D.Double(x + dx - 5, y
				+ dy - 5, 5, 5);

		for (GeneralPath p : Map.colissionMap)

			if (p.intersects(movementRect))
				return true;

		return false;
	}

	public void draw(Graphics2D g) {

		// Combine the player with the gun
		BufferedImage mergedImage;
		BufferedImage bottom;
		if (inventory.getCurrentGun() != null) {
			BufferedImage gunTexture = inventory.getCurrentGun().getTexture();
			bottom = GamePanel.mergeImages(texture_bottom, 0, 0, gunTexture, 0,
					256);
		} else if (!inventory.getCurrentUtility().isEmpty()) {
			Utility utility = Inventory.getFirstKey(inventory
					.getCurrentUtility());
			bottom = GamePanel.mergeImages(texture_bottom, 0, 0,
					utility.textureInHand, 0, 180);
		} else
			bottom = Player.texture_bottom;

		mergedImage = GamePanel.mergeImages(bottom, 0, 0, texture_head, 0, 0);

		double horScale = 0.2 / GamePanel.horScale;
		double vertScale = 0.2 / GamePanel.vertScale;

		g.drawRenderedImage(mergedImage, GamePanel.getAffineTransform(
				mergedImage,
				(int) (GamePanel.WINDOW_WIDTH / 2 - mergedImage.getWidth()
						* horScale / 2),
				(int) (GamePanel.WINDOW_HEIGHT / 2 - mergedImage.getHeight()
						* vertScale / 2), horScale, vertScale, Math.toRadians(rotation + 90)));

		if ((System.nanoTime() - recoveringTimer) / 1000000 <= 500) {

			int alpha = (int) (128 * Math.cos((Math.PI / 2)
					* ((System.nanoTime() - recoveringTimer) / 1000000) / 500));
			if (alpha > 128)
				alpha = 128;
			if (alpha < 0)
				alpha = 0;

			g.setColor(new Color(255, 0, 0, alpha));
			g.fillRect(0, 0, GamePanel.WINDOW_WIDTH, GamePanel.WINDOW_HEIGHT);

		}

		if (GamePanel.debugMode) {
			g.setColor(Color.RED);
			g.drawOval(GamePanel.WINDOW_WIDTH / 2 - r, GamePanel.WINDOW_HEIGHT
					/ 2 - r, r * 2, r * 2);
			// g.setColor(Color.YELLOW);
			// g.drawRect((int) x - InGame.map.getxOffset() - r, (int) y
			// - InGame.map.getyOffset() - r, 2 * r, 2 * r);
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
