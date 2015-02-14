package launcher;

import input.KeyListener;
import input.MouseListener;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entity.Bullet;
import entity.DeadZombie;
import entity.livingEntity.Player;
import entity.livingEntity.Zombie;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

	public static int WINDOW_WIDTH = 800;
	public static int WINDOW_HEIGHT = 600;

	private Thread thread;
	private boolean running;

	private BufferedImage image;
	private Graphics2D g;

	public Player player;

	public static ArrayList<Zombie> zombies;
	public static ArrayList<DeadZombie> deadZombies;
	public static ArrayList<Bullet> bullets;

	private int FPS = 60;

	public GamePanel() {

		super();
		setVisible(true);
		setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();

		addKeyListener(new KeyListener(this));
		addMouseListener(new MouseListener(this));

		try {
			Player.texture = ImageIO.read(GamePanel.class
					.getResource("/Player.png"));
		} catch (IOException e) {

			e.printStackTrace();
		}

		thread = new Thread();
		thread.start();

	}

	@Override
	public void run() {

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

		// bullet-Zombie collision
		for (int i = 0; i < bullets.size(); i++) {

			Bullet b = bullets.get(i);
			double bx = b.getx();
			double by = b.gety();
			double br = b.getr();

			for (int j = 0; j < zombies.size(); j++) {

				Zombie e = zombies.get(j);
				double ex = e.getx();
				double ey = e.gety();
				double er = e.getr();

				double dx = bx - ex;
				double dy = by - ey;
				double dist = Math.sqrt(dx * dx + dy * dy);

				if (dist < br + er) {
					e.damage(b.getDamage());
					bullets.remove(i);
					i--;
					break;
				}

			}

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

	}

	private void gameRender() {

		player.draw(g);
		
		
	}

	private void gameDraw() {
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}

}
