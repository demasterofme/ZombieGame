package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import launcher.GamePanel;

public class Gun extends Entity {

	private int rotation;
	private int fireRate;
	private int damage;
	private int reloadSpeed;
	private GunType type;
	
	public static BufferedImage texture;
	
	public Gun(int x, int y) {
		super(x, y);
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public int getFireRate() {
		return fireRate;
	}

	public int getDamage() {
		return damage;
	}

	public int getReloadSpeed() {
		return reloadSpeed;
	}

	public GunType getType() {
		return type;
	}
	
	public boolean update() {
		
		x = (int) Math.cos(Math.toRadians(rotation)) * 5;
		y = (int) Math.sin(Math.toRadians(rotation)) * 7;
		
		return false;
		
	}
	
	public void draw(Graphics2D g) {
		
		double scale = 1;
		
		g.drawImage(GamePanel.transformImage(texture, scale, rotation + 90),
				(int) (x - texture.getWidth() * scale / 2),
				(int) (y - texture.getHeight() * scale / 2), null);
		
	}
}
