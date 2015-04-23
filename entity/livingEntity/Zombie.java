package entity.livingEntity;

import entity.Bullet;
import gameState.inGame.InGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import launcher.GamePanel;
import map.Vertex;

public class Zombie extends LivingEntity {

	private ZombieType type;

	public static BufferedImage texture;
	
	private int findPathTimer = 0;

	public Zombie(ZombieType type, int x, int y) {

		super(x, y);
		this.type = type;
		r = 30;
		health = 1000;
		speed = 1;

		// for (Vertex v : v2.getNeighbours(v1)) {
		// System.out.println(v.getX() + ", " + v.getY());
		// }

	}

	public boolean update() {

		findPathTimer++;
		
		if (!(x == InGame.player.getx() && y == InGame.player.gety())) {
			if (findPathTimer > 30) {
				findPath();
				findPathTimer = 0;
			}
		} else {
			dx = 0; 
			dy = 0;
		}

		x += dx;
		y += dy;

		for (int i = 0; i < InGame.bullets.size(); i++) {

			Bullet b = InGame.bullets.get(i);

			if (Math.sqrt(Math.pow(b.getx() - x, 2) + Math.pow(b.gety() - y, 2)) <= r
					+ b.getr()) {
				damage(b.getDamage());
				InGame.bullets.remove(b);
				i--;
			}

		}

		return false;
	}

	private ArrayList<Vertex> path;

	public void findPath() {

		if (InGame.map.getPathFinding() != null)
			path = InGame.map.getPathFinding().findPath(
					new Vertex(x, y, InGame.map.getPathFinding()),
					new Vertex(InGame.player.getx(), InGame.player.gety(),
							InGame.map.getPathFinding()));

		if (path != null) {

			int vertexX = path.get(path.size() - 2).getX();
			int vertexY = path.get(path.size() - 2).getY();

			double angle = Math.acos((vertexX - x)
					/ (Math.sqrt(Math.pow(vertexX - x, 2)
							+ Math.pow(vertexY - y, 2))));
			if (vertexY < y)
				angle = 2 * Math.PI - angle;

			this.rotation = (int) Math.toDegrees(angle) + 90;

			dx = (int) Math.round(Math.cos(angle) * speed);
			dy = (int) Math.round(Math.sin(angle) * speed);
		}
	}

	public void damage(int damage) {

		health -= damage;
		if (health <= 0)
			dead = true;

	}

	public void draw(Graphics2D g) {

		int relativeX = x - InGame.map.getxOffset();
		int relativeY = y - InGame.map.getyOffset();

		if (relativeX + r > 0 && relativeX - r < GamePanel.WINDOW_WIDTH
				&& relativeY + r > 0 && relativeY - r < GamePanel.WINDOW_HEIGHT) {

			double scale = 0.1;

			// Calculate new x and y position
			int x = (int) (relativeX - texture.getWidth() * scale / 2);
			int y = (int) (relativeY - texture.getHeight() * scale / 2);

			g.drawRenderedImage(
					texture,
					GamePanel.getAffineTransform(texture, x, y, scale,
							Math.toRadians(rotation)));

			if (path != null) {
				for (int i = 0; i < path.size() - 1; i++) {
					g.setColor(Color.ORANGE);
					g.drawLine(path.get(i).getX() - InGame.map.getxOffset(), path
							.get(i).getY() - InGame.map.getyOffset(),
							path.get(i + 1).getX() - InGame.map.getxOffset(), path
									.get(i + 1).getY() - InGame.map.getyOffset());
				}
		}

			if (GamePanel.debugMode) {
				g.setColor(Color.RED);
				g.drawOval(relativeX - r, relativeY - r, r * 2, r * 2);
			}
		}
	}

	public enum ZombieType {

		SWARMER, STALKER, CHOKER;

	}

}
