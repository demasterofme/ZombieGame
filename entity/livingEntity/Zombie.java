package entity.livingEntity;

import entity.Bullet;
import gameState.inGame.InGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import launcher.GamePanel;
import map.Vertex;

public class Zombie extends LivingEntity {

	private ZombieType type;

	public static BufferedImage texture;
	
	private int attackStrength;
	private boolean canAttack;
	private long canAttackTimer;

	private ArrayList<Vertex> path;
	private int findPathTimer = 0;

	public Zombie(ZombieType type, double x, double y) {

		super(x, y);
		this.type = type;
		r = 30;
		health = 1000;
		speed = 1;
		
		canAttack = true;
		attackStrength = 10;

	}

	public boolean update() {

		findPathTimer++;

		int distanceToPlayer = (int) Math.sqrt(Math.pow(
				x - InGame.player.getx(), 2)
				+ Math.pow(y - InGame.player.gety(), 2));

		// int shortestDistanceToOtherZombie = Integer.MAX_VALUE;
		// for (Zombie z : InGame.zombies) {
		// if (z.equals(this))
		// continue;
		// int distanceToZombie = (int) Math.sqrt(Math.pow(x + dx - z.getx(),
		// 2) + Math.pow(y + dy - z.gety(), 2));
		// if (distanceToZombie < shortestDistanceToOtherZombie) {
		// shortestDistanceToOtherZombie = distanceToZombie;
		// }
		// }

		// if ((r + InGame.player.getr()) * 0.8 < distanceToPlayer
		// && shortestDistanceToOtherZombie > 2 * r * 0.7) {

		if ((r + InGame.player.getr()) * 0.8 < distanceToPlayer) {

			if (findPathTimer > 30) {
				path = findPath();
				findPathTimer = 0;
			}

			if (path != null) {

				double vertexX = path.get(path.size() - 2).getX();
				double vertexY = path.get(path.size() - 2).getY();

				double angle = Math.acos((vertexX - x)
						/ (Math.sqrt(Math.pow(vertexX - x, 2)
								+ Math.pow(vertexY - y, 2))));
				if (vertexY < y)
					angle = 2 * Math.PI - angle;

				double rotation = Math.toDegrees(angle) + 90;
				if (rotation >= 360)
					rotation -= 360;

				this.rotation = rotation;

				dx = Math.cos(angle) * speed;
				dy = Math.sin(angle) * speed;

			}

		} else {
			
			if (!canAttack && (System.nanoTime() - canAttackTimer) / 1000000 > 3000)
				canAttack = true;
			
			if (canAttack) {
				InGame.player.damage(attackStrength);
				canAttack = false;
				canAttackTimer = System.nanoTime();
			}
			
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

	public ArrayList<Vertex> findPath() {

		if (InGame.map.getPathFinding() != null)
			return InGame.map.getPathFinding().findPath(
					new Vertex(x, y, InGame.map.getPathFinding()),
					new Vertex(InGame.player.getx(), InGame.player.gety(),
							InGame.map.getPathFinding()));
		return null;
	}

	public void draw(Graphics2D g) {

		int relativeX = (int) x - InGame.map.getxOffset();
		int relativeY = (int) y - InGame.map.getyOffset();

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

			g.setStroke(new BasicStroke(1));
			if (path != null) {
				for (int i = 0; i < path.size() - 1; i++) {
					g.setColor(Color.ORANGE);
					g.drawLine(
							(int) path.get(i).getX() - InGame.map.getxOffset(),
							(int) path.get(i).getY() - InGame.map.getyOffset(),
							(int) path.get(i + 1).getX()
									- InGame.map.getxOffset(),
							(int) path.get(i + 1).getY()
									- InGame.map.getyOffset());
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
