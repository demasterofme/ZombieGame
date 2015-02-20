package launcher;

import entity.Bullet;
import entity.livingEntity.Zombie;
import gfx.Button;
import gfx.DeadZombie;
import gfx.MuzzleFlash;
import inGame.InGame;
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

import javax.swing.JPanel;

import titleScreen.TitleScreen;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	public static int WINDOW_WIDTH;
	public static int WINDOW_HEIGHT;

	private static Thread thread;
	private static boolean running;

	public static GameState gameState;

	// Debug Mode
	public static boolean debugMode = false;
	public static int mouseX;
	public static int mouseY;

	public static BufferedImage image;
	private static Graphics2D g;

	private static boolean can_run = true;

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

		image = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		new TitleScreen();
		gameState = GameState.TITLE_SCREEN;

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

	private void gameUpdate() {

		if (gameState.equals(GameState.TITLE_SCREEN)) {

		} else if (gameState.equals(GameState.PRE_GAME)) {

		} else if (gameState.equals(GameState.IN_GAME)) {

			InGame.map.update();

			InGame.player.update();

			// bullet update
			for (int i = 0; i < InGame.bullets.size(); i++) {
				if (InGame.bullets.get(i).update()) {
					InGame.bullets.remove(i);
					i--;
				}
			}

			// zombie update
			for (int i = 0; i < InGame.zombies.size(); i++) {
				InGame.zombies.get(i).update();
			}

			// check dead zombie
			for (int i = 0; i < InGame.zombies.size(); i++) {
				if (InGame.zombies.get(i).isDead()) {
					InGame.deadZombies.add(new DeadZombie(InGame.zombies.get(i)
							.getx(), InGame.zombies.get(i).gety()));
					InGame.zombies.remove(i);
					i--;
				}
			}

			// update deadzombies
			for (int i = 0; i < InGame.deadZombies.size(); i++) {
				if (InGame.deadZombies.get(i).update()) {
					InGame.deadZombies.remove(i);
					i--;
				}
			}

			// update muzzleFlashes
			for (int i = 0; i < InGame.muzzleFlashes.size(); i++) {
				if (InGame.muzzleFlashes.get(i).update()) {
					InGame.muzzleFlashes.remove(i);
					i--;
				}
			}

		} else if (gameState.equals(GameState.POST_GAME)) {

		}

	}

	private void gameRender() {

		if (gameState.equals(GameState.TITLE_SCREEN)) {

			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
			g.setColor(Color.WHITE);

			for (Button b : TitleScreen.buttons)
				b.draw(g);

			// Debug mode
			if (debugMode) {

				y = 5;

				g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
				g.setColor(Color.WHITE);

				g.drawString("Debug Mode", 10, updateY());
				g.drawString("Mouse: ", 10, updateY());
				g.drawString("X: " + mouseX + " Y: " + mouseY, 20, updateY());
				g.drawString("Button: ", 10, updateY());
				g.drawString("X: " + TitleScreen.buttons.get(0).getx() + " Y: "
						+ TitleScreen.buttons.get(0).gety(), 20, updateY());
				g.drawString("Width: " + TitleScreen.buttons.get(0).getWidth()
						+ " Heigth: " + TitleScreen.buttons.get(0).getHeight(),
						20, updateY());
				g.drawString("Hover: " + TitleScreen.buttons.get(0).hover, 20,
						updateY());
				g.drawString("Pressed: " + TitleScreen.buttons.get(0).pressed, 20,
						updateY());
			}

		} else if (gameState.equals(GameState.PRE_GAME)) {

		} else if (gameState.equals(GameState.IN_GAME)) {

			InGame.map.draw(g);

			for (DeadZombie d : InGame.deadZombies)
				d.draw(g);

			for (Zombie z : InGame.zombies)
				z.draw(g);

			for (MuzzleFlash m : InGame.muzzleFlashes)
				m.draw(g);

			if (debugMode)
				for (Bullet b : InGame.bullets)
					b.draw(g);

			InGame.player.draw(g);

			// Debug mode
			if (debugMode) {

				y = 5;

				g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
				g.setColor(Color.WHITE);

				g.drawString("Debug Mode", 10, updateY());
				g.drawString("Player: ", 10, updateY());
				g.drawString("Coordinates: " + InGame.player.getx() + ", "
						+ InGame.player.gety(), 20, updateY());
				g.drawString("Rotation: " + InGame.player.getRotation(), 20,
						updateY());
				g.drawString("Gun:", 10, updateY());
				g.drawString("Type: " + InGame.player.getGun().getType(), 20,
						updateY());
				g.drawString("Damage: " + InGame.player.getGun().getDamage(),
						20, updateY());
				g.drawString("FireRate: "
						+ InGame.player.getGun().getFireRate(), 20, updateY());
				g.drawString("Reload Speed: "
						+ InGame.player.getGun().getReloadSpeed(), 20,
						updateY());
			}
		} else if (gameState.equals(GameState.POST_GAME)) {

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
