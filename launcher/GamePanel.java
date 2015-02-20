package launcher;

import input.KeyListener;
import input.MouseListener;
import input.MouseMotionListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import map.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import entity.Bullet;
import entity.Gun;
import entity.GunType;
import entity.livingEntity.Player;
import entity.livingEntity.Zombie;
import entity.livingEntity.ZombieType;
import gfx.DeadZombie;
import gfx.MuzzleFlash;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	public static int WINDOW_WIDTH;
	public static int WINDOW_HEIGHT;

	private static Thread thread;
	private static boolean running;

	public static boolean debugMode = false;

	private static BufferedImage image;
	private static Graphics2D g;

	public static Player player;

	public static Map map;

	public static ArrayList<Zombie> zombies;
	public static ArrayList<DeadZombie> deadZombies;
	public static ArrayList<Bullet> bullets;
	public static HashMap<GunType, Gun> guns;
	public static ArrayList<MuzzleFlash> muzzleFlashes;

	private static boolean can_run = true;

	private int framecount;
	private long startGameTime;
	private int gameFrames = 0;

	public GamePanel() {

		super();
		setVisible(true);
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		Rectangle maximumWindowBounds = graphicsEnvironment
				.getMaximumWindowBounds();
		setBounds(maximumWindowBounds);
		WINDOW_WIDTH = (int) maximumWindowBounds.getWidth();
		WINDOW_HEIGHT = (int) maximumWindowBounds.getHeight();
		setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		setFocusable(true);
		requestFocus();
		if (!loadSprites() || !loadGuns())
			can_run = false;

	}

	public void addNotify() {
		super.addNotify();
		if (thread == null && can_run) {
			thread = new Thread(this);
			thread.start();
		}
		addKeyListener(new KeyListener());
		addMouseListener(new MouseListener());
		addMouseMotionListener(new MouseMotionListener());
	}

	public void run() {

		running = true;

		map = new Map();

		image = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		player = new Player(1024, 1024);

		zombies = new ArrayList<>();
		deadZombies = new ArrayList<>();
		bullets = new ArrayList<>();
		muzzleFlashes = new ArrayList<>();

		// for debugging
		zombies.add(new Zombie(ZombieType.SWARMER, 1024, 1024));

		framecount = 0;
		int ticksPerSecond = 25;
		int skipTicks = 1000 / ticksPerSecond;
		int maxFrameSkips = 5;

		long nextGameTick = System.nanoTime();

		int loops;

		while (running) {

			loops = 0;

			while (System.nanoTime() > nextGameTick && loops < maxFrameSkips) {
				double deltaTime = (System.currentTimeMillis() + skipTicks - nextGameTick
						/ (double) skipTicks);
				gameUpdate();
				nextGameTick += skipTicks;
				loops++;

			}

			gameRender();
			gameDraw();

		}

		// Game ended

	}

	private static boolean loadGuns() {
		guns = new HashMap<>();
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File(GamePanel.class
					.getResource("/xml/guns.xml").toURI()));
			Element root = document.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> gunElements = root.elements();
			for (Element gunElement : gunElements) {
				GunType type = GunType.valueOf(gunElement.getName());
				int damage = Integer.parseInt(gunElement.element("Damage")
						.getText());
				int fireRate = Integer.parseInt(gunElement.element("Firerate")
						.getText());
				int reloadSpeed = Integer.parseInt(gunElement.element(
						"Reloadspeed").getText());
				BufferedImage texture = ImageIO.read(GamePanel.class
						.getResource("/sprites/guns/AK47.png"));
				guns.put(type, new Gun(type, damage, fireRate, reloadSpeed,
						texture));
			}
		} catch (DocumentException | URISyntaxException | IOException e) {
			e.printStackTrace();
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

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	private void gameUpdate() {

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

	private void gameRender() {

		map.draw(g);

		for (DeadZombie d : deadZombies)
			d.draw(g);

		for (Zombie z : zombies)
			z.draw(g);

		for (MuzzleFlash m : muzzleFlashes)
			m.draw(g);

		if (debugMode)
			for (Bullet b : bullets)
				b.draw(g);

		player.draw(g);

		// Debug mode
		if (debugMode) {

			y = 5;

			g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
			g.setColor(Color.WHITE);

			g.drawString("Debug Mode", 10, updateY());
			g.drawString("Player: ", 10, updateY());
			g.drawString(
					"Coordinates: " + player.getx() + ", " + player.gety(), 20,
					updateY());
			g.drawString("Rotation: " + player.getRotation(), 20, updateY());
			g.drawString("Gun:", 10, updateY());
			g.drawString("Type: " + player.getGun().getType(), 20, updateY());
			g.drawString("Damage: " + player.getGun().getDamage(), 20,
					updateY());
			g.drawString("FireRate: " + player.getGun().getFireRate(), 20,
					updateY());
			g.drawString("Reload Speed: " + player.getGun().getReloadSpeed(),
					20, updateY());
		}

	}

	// for debug mode
	private static int y;

	private int updateY() {
		y += 15;
		return y;
	}

	private void gameDraw() {
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}

	public static BufferedImage transformImage(BufferedImage image,
			double scale, int rotation) {
		int scaledWidth = (int) (scale * image.getWidth());
		int scaledHeight = (int) (scale * image.getHeight());
		AffineTransform transform = AffineTransform.getRotateInstance(
				Math.toRadians(rotation), scaledWidth / 2, scaledHeight / 2);
		transform.scale(scale, scale);
		AffineTransformOp operation = new AffineTransformOp(transform,
				AffineTransformOp.TYPE_BILINEAR);
		return operation.filter(image, null);
	}

	public static BufferedImage mergeImages(BufferedImage image1, int xOffset1,
			int yOffset1, BufferedImage image2, int xOffset2, int yOffset2) {
		BufferedImage combined = new BufferedImage(1024, 1024,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = combined.getGraphics();
		g.drawImage(image1, xOffset1 + 512 - image1.getWidth() / 2, -yOffset1
				+ 512 - image1.getHeight() / 2, null);
		g.drawImage(image2, xOffset2 + 512 - image2.getWidth() / 2, -yOffset2
				+ 512 - image2.getHeight() / 2, null);
		return combined;
	}
}
