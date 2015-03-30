package entity;

import java.awt.image.BufferedImage;

public class Gun {

	private int rotation;
	private int damage;
	private int fireRate;
	private int reloadSpeed;
	private int currentBullets;
	private int clipSize;
	private int maxBullets;
	private String name;
	private int price;
	private BufferedImage texture;

	public Gun(String name, int damage, int fireRate, int reloadSpeed,
			int clipSize, int maxBullets, int price, BufferedImage texture) {
		this.name = name;
		this.damage = damage;
		this.fireRate = fireRate;
		this.reloadSpeed = reloadSpeed;
		this.currentBullets = clipSize;
		this.clipSize = clipSize;
		this.maxBullets = maxBullets;
		this.price = price;
		this.texture = texture;
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

	public BufferedImage getTexture() {
		return texture;
	}

	public String getName() {
		return name;
	}

	public int getCurrentBullets() {
		return currentBullets;
	}

	public void setCurrentBullets(int currentBullets) {
		this.currentBullets = currentBullets;
	}

	public int getClipSize() {
		return clipSize;
	}

	public int getMaxBullets() {
		return maxBullets;
	}
	
	public int getPrice() {
		return price;
	}

	public void setMaxBullets(int maxBullets) {
		this.maxBullets = maxBullets;
	}

}
