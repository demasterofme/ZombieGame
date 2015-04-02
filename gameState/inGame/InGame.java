package gameState.inGame;

import entity.Bullet;
import entity.Gun;
import entity.livingEntity.Player;
import entity.livingEntity.Zombie;
import gameState.GameState;
import gfx.DeadZombie;
import gfx.MuzzleFlash;
import gfx.Text;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
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

		player = new Player(1024, 1024);

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

		player.draw(g);

		// Temp
		// Player health
		g.setColor(Color.WHITE);
		g.fillRect(20, GamePanel.WINDOW_HEIGHT - 60, 400, 40);
		g.setColor(Color.RED);
		g.fillRect(23, GamePanel.WINDOW_HEIGHT - 57,
				394 * player.getHealth() / 20, 34);

		// Draw gun properties
		g.setColor(Color.WHITE);
		g.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		if (player.getInventory().hasGunEquipped())
			g.drawString(player.getInventory().getCurrentGun().getName(), 440,
					GamePanel.WINDOW_HEIGHT - 40);
		if (player.isReloading())
			g.setColor(Color.RED);
		if (player.getInventory().hasGunEquipped())
			g.drawString(player.getInventory().getCurrentGun()
					.getCurrentBullets()
					+ " / "
					+ player.getInventory().getCurrentGun().getMaxBullets(),
					440, GamePanel.WINDOW_HEIGHT - 20);

		// Money
		g.setColor(Color.WHITE);
		g.drawString("Money: " + player.getMoney(),
				(int) (GamePanel.WINDOW_WIDTH - 200),
				(int) (GamePanel.WINDOW_HEIGHT - 20));

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
				for (Vertex v : map.getPathFinding().getVerticesList()) {
					v.draw(g);
				}
			}
		}
	}

	// for debug mode
	private static int y;

	private static int updateY() {
		y += 15;
		return y;
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
				BufferedImage texture = ImageIO.read(GamePanel.class
						.getResource("/sprites/guns/" + name + ".png"));
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

			Player.texture_head = ImageIO.read(GamePanel.class
					.getResource("/sprites/Player-head.png"));
			Player.texture_bottom = ImageIO.read(GamePanel.class
					.getResource("/sprites/Player-bottom.png"));
			MuzzleFlash.texture = ImageIO.read(GamePanel.class
					.getResource("/sprites/MuzzleFlash2.png"));
			Zombie.texture = ImageIO.read(GamePanel.class
					.getResource("/sprites/zombie-swarmer1.png"));
			Map.texture = ImageIO.read(GamePanel.class
					.getResource("/sprites/Map.png"));

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

			GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 4);
			for (Element shapeElement : shapeElements) {
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
