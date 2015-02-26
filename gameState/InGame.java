package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import launcher.GamePanel;
import map.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import entity.Bullet;
import entity.Gun;
import entity.livingEntity.Player;
import entity.livingEntity.Zombie;
import gfx.DeadZombie;
import gfx.MuzzleFlash;

public class InGame extends GameState {

	public static Player player;

	public static Map map;

	public static ArrayList<Zombie> zombies;
	public static ArrayList<DeadZombie> deadZombies;
	public static ArrayList<Bullet> bullets;
	public static ArrayList<Gun> guns;
	public static ArrayList<MuzzleFlash> muzzleFlashes;

	public InGame() {

		if (!loadGuns() || !loadSprites()) {
			GamePanel.running = false;
			return;
		}

		map = new Map();

		player = new Player(1024, 1024);

		zombies = new ArrayList<>();
		deadZombies = new ArrayList<>();
		bullets = new ArrayList<>();
		muzzleFlashes = new ArrayList<>();

		// for debugging
		zombies.add(new Zombie(Zombie.ZombieType.SWARMER, 1024, 1024));

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

	}

	public void render(Graphics2D g) {
		InGame.map.draw(g);

		for (DeadZombie d : deadZombies)
			d.draw(g);

		for (Zombie z : zombies)
			z.draw(g);

		for (MuzzleFlash m : muzzleFlashes)
			m.draw(g);

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
		g.drawString(player.getGun().getName(), 440,
				GamePanel.WINDOW_HEIGHT - 40);
		if (player.isReloading())
			g.setColor(Color.RED);
		g.drawString(player.getGun().getCurrentBullets() + " / "
				+ player.getGun().getMaxBullets(), 440,
				GamePanel.WINDOW_HEIGHT - 20);

		// Debug mode
		if (GamePanel.debugMode) {

			y = 5;

			g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
			g.setColor(Color.WHITE);

			g.drawString("Debug Mode", 10, updateY());
			g.drawString("Player: ", 10, updateY());
			g.drawString("Coordinates: " + player.getx() + ", "
					+ player.gety(), 20, updateY());
			g.drawString("Rotation: " + player.getRotation(), 20,
					updateY());
			g.drawString("Reloading: " + player.isReloading(), 20,
					updateY());
			g.drawString("Gun:", 10, updateY());
			g.drawString("Name: " + player.getGun().getName(), 20,
					updateY());
			g.drawString("Damage: " + player.getGun().getDamage(), 20,
					updateY());
			g.drawString("FireRate: " + player.getGun().getFireRate(),
					20, updateY());
			g.drawString("Reload Speed: "
					+ player.getGun().getReloadSpeed(), 20, updateY());
			g.drawString("Clip Size: " + player.getGun().getClipSize(),
					20, updateY());
			g.drawString("Current Bullets: "
					+ player.getGun().getCurrentBullets(), 20, updateY());
			g.drawString("Max Bullets: "
					+ player.getGun().getMaxBullets(), 20, updateY());
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
			Document document = reader.read(new File(GamePanel.class
					.getResource("/xml/guns.xml").toURI()));
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
				BufferedImage texture = ImageIO.read(GamePanel.class
						.getResource("/sprites/guns/" + name + ".png"));
				guns.add(new Gun(name, damage, fireRate, reloadSpeed, clipSize,
						maxBullets, texture));
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
	
	public Player getPlayer() {
		return player;
	}

}
