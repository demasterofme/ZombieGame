package entity.livingEntity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Bullet;
import launcher.GamePanel;

public class Zombie extends LivingEntity {

	private ZombieType type;
	
	public static BufferedImage texture;
	
	public Zombie(ZombieType type, int x, int y) {
		
		super(x, y);
		this.type = type;
		r = 60;
	}
	
	public boolean update() {
		
		findPath();
		
		this.x += dx;
		this.y += dy;
		
		for (Bullet b : GamePanel.bullets) {
			
			if (Math.sqrt(Math.pow(b.getx() - x, 2) + Math.pow(b.gety() - y, 2)) <= r)
				health -= b.getDamage();
			
		}
		
		if (health <= 0)
			return true;
		
		return false;
	}
	
	public void findPath() {
		
	}
	
	public void damage(int damage) {
		
		health -= damage;
		if (health <= 0)
			dead = true;
		
	}
	
	public void draw(Graphics2D g) {
		
		double scale = 0.1;
		
		g.drawImage(GamePanel.transformImage(texture, scale, rotation + 90),
				(int) (x - texture.getWidth() * scale / 2),
				(int) (y - texture.getHeight() * scale / 2), null);
		g.setColor(Color.RED);
		g.drawOval(x - r / 2, y - r / 2, r, r);
	}
	
}
