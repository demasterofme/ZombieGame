package inGame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import launcher.GamePanel;
import map.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import entity.Bullet;
import entity.Gun;
import entity.livingEntity.Player;
import entity.livingEntity.Zombie;
import gfx.DeadZombie;
import gfx.MuzzleFlash;

public class InGame {

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

}
