package launcher;

import gfx.DeadZombie;
import gfx.MuzzleFlash;
import input.KeyListener;
import input.MouseListener;
import input.MouseMotionListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entity.Bullet;
import entity.Gun;
import entity.livingEntity.Player;
import entity.livingEntity.Zombie;
import entity.livingEntity.ZombieType;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	public static int WINDOW_WIDTH;
	public static int WINDOW_HEIGHT;

	private Thread thread;
	private boolean running;

	private BufferedImage image;
	private Graphics2D g;

	public static Player player;

	public static ArrayList<Zombie> zombies;
	public static ArrayList<DeadZombie> deadZombies;
	public static ArrayList<Bullet> bullets;
	public static ArrayList<MuzzleFlash> muzzleFlashes;

	private int FPS = 60;

	private boolean can_run = false;

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
		if (loadSprites())
			can_run = true;

	}

	public void addNotify() {
		super.addNotify();
		if (thread == null && can_run) {
			thread = new Thread(this);
			thread.start();
		}
		addKeyListener(new KeyListener(this));
		addMouseListener(new MouseListener(this));
		addMouseMotionListener(new MouseMotionListener(this));
	}

	public void run() {

		running = true;

		image = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		player = new Player(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2);

		zombies = new ArrayList<>();
		deadZombies = new ArrayList<>();
		bullets = new ArrayList<>();
		muzzleFlashes = new ArrayList<>();

		// for debugging
		zombies.add(new Zombie(ZombieType.SWARMER, 300, 500));

		long startTime;
		long URDTimeMillis;
		long waitTime;

		int frameCount = 0;
		int maxFrameCount = 60;

		long targetTime = 1000 / FPS;

		while (running) {
			startTime = System.nanoTime();

			gameUpdate();
			gameRender();
			gameDraw();

			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;

			waitTime = targetTime - URDTimeMillis;

			try {
				Thread.sleep(waitTime);
			} catch (Exception e) {
			}

			frameCount++;
			if (frameCount == maxFrameCount) {
				frameCount = 0;
			}
		}

		// Game ended

	}

	private boolean loadSprites() {

		try {

			Player.texture_head = ImageIO.read(GamePanel.class
					.getResource("/sprites/Player-head.png"));
			Player.texture_bottom = ImageIO.read(GamePanel.class
					.getResource("/sprites/Player-bottom.png"));
			MuzzleFlash.texture = ImageIO.read(GamePanel.class
					.getResource("/sprites/MuzzleFlash2.png"));
			Gun.texture_ak47 = ImageIO.read(GamePanel.class
					.getResource("/sprites/Gun2.png"));
			Zombie.texture = ImageIO.read(GamePanel.class
					.getResource("/sprites/zombie-swarmer1.png"));

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}

		return true;

	}

	private void gameUpdate() {

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

		g.setColor(new Color(0, 100, 255));
		g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

		for (DeadZombie d : deadZombies) {
			d.draw();
		}
		
		for (Zombie z : zombies) {
			z.draw(g);
		}

		for (MuzzleFlash m : muzzleFlashes) {
			m.draw(g);
		}

		player.draw(g);

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
