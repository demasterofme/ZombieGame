package gameState.inGame;

import entity.Bullet;
import entity.Gun;
import entity.livingEntity.Player;
import entity.livingEntity.Zombie;
import gameState.GameState;
import gfx.DeadZombie;
import gfx.MuzzleFlash;
import gfx.Text;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import launcher.GamePanel;
import map.Map;
import map.Vertex;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class InGame extends GameState {

	public static Player player;

	public static Map map;

	public static ArrayList<Zombie> zombies;
	public static ArrayList<DeadZombie> deadZombies;
	public static ArrayList<Bullet> bullets;
	public static ArrayList<Gun> guns;
	public static ArrayList<MuzzleFlash> muzzleFlashes;
	public static ArrayList<Text> texts;

	private static ArrayList<GeneralPath> shapeList;

	public InGame() {

		if (!loadGuns() || !loadSprites() || !loadCollisionMap()) {
			GamePanel.running = false;
			return;
		}

		map = new Map();

		player = new Player(1600, 1600);

		zombies = new ArrayList<>();
		deadZombies = new ArrayList<>();
		bullets = new ArrayList<>();
		muzzleFlashes = new ArrayList<>();
		texts = new ArrayList<>();

	}

	public void update() {

		map.update();

		player.update();

		// bullet update
		for (int i = 0; i < bullets.size(); i++) {
			if (bullets.get(i).update()) {
				bullets.remove(i);
				i--;
			}
		}

		// zombie update
		for (int i = 0; i < zombies.size(); i++) {
			zombies.get(i).update();
		}

		// check dead zombie
		for (int i = 0; i < zombies.size(); i++) {
			if (zombies.get(i).isDead()) {
				deadZombies.add(new DeadZombie(zombies.get(i).getx(), zombies
						.get(i).gety()));
				player.setMoney(player.getMoney() + 20);
				zombies.remove(i);
				i--;
			}
		}

		// update deadzombies
		for (int i = 0; i < deadZombies.size(); i++) {
			if (deadZombies.get(i).update()) {
				deadZombies.remove(i);
				i--;
			}
		}

		// update muzzleFlashes
		for (int i = 0; i < muzzleFlashes.size(); i++) {
			if (muzzleFlashes.get(i).update()) {
				muzzleFlashes.remove(i);
				i--;
			}
		}

		// update texts
		for (int i = 0; i < texts.size(); i++) {
			if (texts.get(i).update()) {
				texts.remove(i);
				i--;
			}
		}

	}

	public void render(Graphics2D g) {

		map.draw(g);

		for (DeadZombie d : deadZombies)
			d.draw(g);

		for (Zombie z : zombies)
			z.draw(g);

		for (MuzzleFlash m : muzzleFlashes)
			m.draw(g);

		for (Text t : texts)
			t.draw(g);

		if (GamePanel.debugMode)
			for (Bullet b : bullets)
				b.draw(g);

		g.setFont(new Font("Century Gothic", Font.PLAIN, 20));

		player.draw(g);

		// Draw the inventory, health bar and current selectedItem details
		g.setColor(new Color(24, 24, 24, 100));
		g.fillRect(0, GamePanel.WINDOW_HEIGHT - 150, 500, 150);
		g.setStroke(new BasicStroke(2));
		g.drawRect(-2, GamePanel.WINDOW_HEIGHT - 153, 504, 154);
		g.setStroke(new BasicStroke(1));

		// Inventory
		player.getInventory().draw(g);

		// Player health
		g.setColor(Color.WHITE);
		g.drawString("Health:", 20, GamePanel.WINDOW_HEIGHT - 50);
		g.setColor(new Color(102, 0, 0));
		g.fillRect(20, GamePanel.WINDOW_HEIGHT - 45, 400, 40);
		g.setColor(new Color(150, 0, 0));
		g.fillRect(23, GamePanel.WINDOW_HEIGHT - 42, 394 * player.getHealth()
				/ player.getMaxHealth(), 34);

		// Draw gun properties
		g.setColor(Color.WHITE);

		if (player.getInventory().hasGunEquipped())
			g.drawString(player.getInventory().getCurrentGun().getName(), 370,
					GamePanel.WINDOW_HEIGHT - 110);
		if (player.isReloading())
			g.setColor(Color.RED);
		if (player.getInventory().hasGunEquipped())
			g.drawString(player.getInventory().getCurrentGun()
					.getCurrentBullets()
					+ " / "
					+ player.getInventory().getCurrentGun().getMaxBullets(),
					370, GamePanel.WINDOW_HEIGHT - 90);

		// Money
		g.setColor(Color.WHITE);
		g.drawString("$: " + player.getMoney(), 370, GamePanel.WINDOW_HEIGHT - 70);

		// Debug mode
		if (GamePanel.debugMode) {

			y = 5;

			g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
			g.setColor(Color.WHITE);

			g.drawString("Debug Mode", 10, updateY());
			g.drawString("Player: ", 10, updateY());
			g.drawString(
					"Coordinates: " + player.getx() + ", " + player.gety(), 20,
					updateY());
			g.drawString("Rotation: " + player.getRotation(), 20, updateY());
			g.drawString("Reloading: " + player.isReloading(), 20, updateY());
			g.drawString("Going Right: " + player.getRight(), 20, updateY());
			g.drawString("Going Left: " + player.getLeft(), 20, updateY());
			g.drawString("Going Up: " + player.getUp(), 20, updateY());
			g.drawString("Going Down: " + player.getDown(), 20, updateY());
			g.drawString("Gun:", 10, updateY());
			if (player.getInventory().hasGunEquipped()) {
				g.drawString("Name: "
						+ player.getInventory().getCurrentGun().getName(), 20,
						updateY());
				g.drawString("Damage: "
						+ player.getInventory().getCurrentGun().getDamage(),
						20, updateY());
				g.drawString("FireRate: "
						+ player.getInventory().getCurrentGun().getFireRate(),
						20, updateY());
				g.drawString("Reload Speed: "
						+ player.getInventory().getCurrentGun()
								.getReloadSpeed(), 20, updateY());
				g.drawString("Clip Size: "
						+ player.getInventory().getCurrentGun().getClipSize(),
						20, updateY());
				g.drawString("Current Bullets: "
						+ player.getInventory().getCurrentGun()
								.getCurrentBullets(), 20, updateY());
				g.drawString(
						"Max Bullets: "
								+ player.getInventory().getCurrentGun()
										.getMaxBullets(), 20, updateY());
			}

		}
	}

	// for debug mode
	private static int y;

	private static int updateY() {
		y += 15;
		return y;
	}

	private static GraphicsEnvironment env = GraphicsEnvironment
			.getLocalGraphicsEnvironment();
	private static GraphicsDevice device = env.getDefaultScreenDevice();
	private static GraphicsConfiguration config = device
			.getDefaultConfiguration();

	private static BufferedImage loadCompatibleImage(BufferedImage rawTexture) {
		BufferedImage texture = config.createCompatibleImage(
				rawTexture.getWidth(), rawTexture.getHeight(),
				Transparency.TRANSLUCENT);
		Graphics g = texture.getGraphics();
		g.drawImage(rawTexture, 0, 0, rawTexture.getWidth(),
				rawTexture.getHeight(), null);
		g.dispose();
		return texture;
	}

	private static boolean loadGuns() {
		guns = new ArrayList<>();
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(GamePanel.class
					.getResource("/xml/guns.xml").toURI().toURL());
			Element root = document.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> gunElements = root.elements();
			for (Element gunElement : gunElements) {
				String name = gunElement.getName();
				int damage = Integer.parseInt(gunElement.element("Damage")
						.getText());
				int fireRate = Integer.parseInt(gunElement.element("Firerate")
						.getText());
				int reloadSpeed = Integer.parseInt(gunElement.element(
						"Reloadspeed").getText());
				int clipSize = Integer.parseInt(gunElement.element("Clipsize")
						.getText());
				int maxBullets = Integer.parseInt(gunElement.element(
						"Maxbullets").getText());
				int price = Integer.parseInt(gunElement.element("Price")
						.getText());
				BufferedImage texture = loadCompatibleImage(ImageIO
						.read(GamePanel.class.getResource("/sprites/guns/"
								+ name + ".png")));
				guns.add(new Gun(name, damage, fireRate, reloadSpeed, clipSize,
						maxBullets, price, texture));
			}
		} catch (Exception e) {
			// e.printStackTrace();
			for (StackTraceElement add : e.getStackTrace())
				GamePanel.errorLog += add + " ";
			return false;
		}
		return true;
	}

	private static boolean loadSprites() {

		try {

			Player.texture_head = loadCompatibleImage(ImageIO
					.read(GamePanel.class
							.getResource("/sprites/Player-head.png")));
			Player.texture_bottom = loadCompatibleImage(ImageIO
					.read(GamePanel.class
							.getResource("/sprites/Player-bottom.png")));
			MuzzleFlash.texture = loadCompatibleImage(ImageIO
					.read(GamePanel.class
							.getResource("/sprites/MuzzleFlash2.png")));
			Zombie.texture = loadCompatibleImage(ImageIO.read(GamePanel.class
					.getResource("/sprites/zombie-swarmer1.png")));
			Map.texture = loadCompatibleImage(ImageIO.read(GamePanel.class
					.getResource("/sprites/Map.png")));

		} catch (Exception e) {
			// e.printStackTrace();
			for (StackTraceElement add : e.getStackTrace())
				GamePanel.errorLog += add + " ";
			return false;
		}

		return true;

	}

	private static boolean loadCollisionMap() {
		SAXReader reader = new SAXReader();
		shapeList = new ArrayList<>();
		try {
			Document document = reader.read(GamePanel.class
					.getResource("/xml/colission-map-1.xml").toURI().toURL());
			Element root = document.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> shapeElements = root.elements("shape");

			for (Element shapeElement : shapeElements) {
				GeneralPath polyline = new GeneralPath(
						GeneralPath.WIND_EVEN_ODD, 4);
				int index = 1;
				while (shapeElement.element("x" + index) != null) {
					int x = Integer.parseInt(shapeElement.element("x" + index)
							.getText());
					int y = Integer.parseInt(shapeElement.element("y" + index)
							.getText());
					if (index++ == 1)
						polyline.moveTo(x, y);
					else
						polyline.lineTo(x, y);
				}
				polyline.closePath();

				shapeList.add(polyline);
			}
			Map.shapeList = shapeList;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Player getPlayer() {
		return player;
	}

}
