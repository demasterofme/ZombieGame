package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import entity.livingEntity.Zombie;
import launcher.GamePanel;

public class Bullet extends Entity {

	private double rad;
	private double dx, dy;
	private int damage;
	
	public Bullet(int x, int y, int angle, int damage) {
		super(x, y);
		rad = Math.toRadians(angle);
		
		int speed = 10;
		
		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad) * speed;
		
		this.damage = damage;
	}
	
	public boolean update() {

		x += dx;
		y += dy;

		if (x < -r || x > GamePanel.WINDOW_WIDTH + r || y < -r
				|| y > GamePanel.WINDOW_HEIGHT + r) {
			return true;
		}

		return false;
	}
	
	public int getDamage() {
		return damage;
	}
	
}
