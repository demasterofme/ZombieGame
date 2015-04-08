package entity.livingEntity;

import entity.Bullet;
import gameState.inGame.InGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import launcher.GamePanel;
import map.Vertex;

public class Zombie extends LivingEntity {

	private ZombieType type;

	public static BufferedImage texture;

	public Zombie(ZombieType type, int x, int y) {

		super(x, y);
		this.type = type;
		r = 30;
		health = 1000;
		speed = 1;

	}

	public boolean update() {

		findPath();

//		int vertexX = next.getX();
//		int vertexY = next.getY();
//		double angle = Math.acos((vertexX - x)
//				/ (Math.sqrt(Math.pow(vertexX - x, 2)
//						+ Math.pow(vertexY - y, 2))));
//		if (vertexY < y)
//			angle =  2 *Math.PI - angle;
//		
//		dx = (int) Math.round(Math.cos(angle) * speed);
//		dy = (int) Math.round(Math.sin(angle) * speed);

		x += dx;
		y += dy;
		
		//System.out.println("Angle: " + angle + " dx: " + dx + " dy: " + dy + " x: " + x + " y: " + y + " vertexX: " + vertexX + " vertexY: " + vertexY + " playerX: " + InGame.player.getx() + " playerY: " + InGame.player.gety());

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

	public void findPath() {

		Vertex vertex;
		
		// Zombie vertex
		Vertex start = new Vertex(x, y, InGame.map.getPathFinding());
		
		vertex = InGame.map.getPathFinding().findPath(
				start,
				new Vertex(InGame.player.getx(), InGame.player.gety(),
						InGame.map.getPathFinding()));
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
