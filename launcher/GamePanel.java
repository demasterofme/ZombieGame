package launcher;

import gameState.GameState;
import gameState.TitleScreen.TitleScreen;
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
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	public static int WINDOW_WIDTH;
	public static int WINDOW_HEIGHT;

	private static Thread thread;
	public static boolean running;

	private static GameState gameState;

	// Debug Mode
	public static boolean debugMode = false;
	public static int mouseX;
	public static int mouseY;

	public static BufferedImage image;
	private static BufferedImage lastFrame;
	public static Graphics2D g;

	public static String errorLog;

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
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		addKeyListener(new KeyListener());
		MouseListener mouseListener = new MouseListener();
		addMouseListener(mouseListener);
		addMouseWheelListener(mouseListener);
		addMouseMotionListener(new MouseMotionListener());
	}

	public void run() {

		running = true;

		errorLog = "";

		image = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		gameState = new TitleScreen();

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

			// long start = System.nanoTime();

			gameRender();
			gameDraw();

			// System.out.println((long) 1000 / ((long)((long) System.nanoTime()
			// - start) / 1000000));

		}

		// Game ended

		if (!errorLog.isEmpty()) {

			// The game has crashed, print the error on screen
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Century Gothic", Font.PLAIN, 25));
			g.drawString("The game has crashed. StackTrace: ", 50, 50);
			g.setFont(new Font("Century Gothic", Font.PLAIN, 20));
			int breakIndex = 0;
			int drawY = 100;
			boolean canBreak = true;
			while (canBreak) {
				try {
					g.drawString(
							errorLog.substring(breakIndex, breakIndex + 80),
							50, drawY);
					breakIndex += 50;
					drawY += 30;
				} catch (Exception e) {
					canBreak = false;
					g.drawString(
							errorLog.substring(breakIndex, errorLog.length()),
							50, drawY);
				}
			}

			gameDraw();

		} else {
			Launcher.window.dispose();
		}

	}

	private void gameUpdate() {

		if (!running)
			return;

		gameState.update();

	}

	private void gameRender() {

		if (!running)
			return;

		gameState.render(g);

	}

	private void gameDraw() {
		Graphics g2 = this.getGraphics();
		lastFrame = image;
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}

	public static void changeGameState(GameState gameState) {
		GamePanel.gameState = gameState;
	}

	// This rotates and scales an image
	public static AffineTransform getAffineTransform(BufferedImage image,
			int x, int y, double scale, double rotation) {

		AffineTransform rotateXform = new AffineTransform();
		rotateXform.rotate(rotation, image.getWidth() / 2.0,
				image.getHeight() / 2.0);
		AffineTransform scaleXform = AffineTransform.getTranslateInstance(x, y);
		scaleXform.scale(scale, scale);
		scaleXform.concatenate(rotateXform);

		return scaleXform;

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

	public static BufferedImage getLastFrame() {
		return lastFrame;
	}

	public static GameState getGameState() {
		return gameState;
	}

}
