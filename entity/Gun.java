package entity;

import java.awt.image.BufferedImage;

public class Gun {

	private int rotation;
	private int damage;
	private int fireRate;
	private float reloadSpeed;
	private int currentClip;
	private int clipSize;
	private int maxBullets;
	private int bullets;
	private String name;
	private int price;
	private BufferedImage texture;

	public Gun(String name, int damage, int fireRate, float reloadSpeed,
			int clipSize, int maxBullets, int price, BufferedImage texture) {
		this.name = name;
		this.damage = damage;
		this.fireRate = fireRate;
		this.reloadSpeed = reloadSpeed;
		this.currentClip = clipSize;
		this.clipSize = clipSize;
		this.bullets = maxBullets;
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

	public float getReloadSpeed() {
		return reloadSpeed;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public String getName() {
		return name;
	}

	public int getCurrentClip() {
		return currentClip;
	}

	public void setCurrentClip(int currentClip) {
		this.currentClip = currentClip;
	}
	
	public int getBullets() {
		return bullets;
	}
	
	public void setBullets(int bullets) {
		this.bullets = bullets;
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
