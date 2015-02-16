package entity;

import java.awt.image.BufferedImage;

public class Gun {

	private int rotation;
	private int fireRate;
	private int damage;
	private int reloadSpeed;
	private GunType type;

	public static BufferedImage texture_ak47;

	public Gun(GunType type) {
		this.type = type;
		switch(type) {
		case AK47:
			damage = 100;
			fireRate = 100;
			reloadSpeed = 0;
			//enzovoort
		}
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

	public BufferedImage getTexture() {
		switch (type) {
		case AK47:
			return texture_ak47;
		default:
			return null;
		}
	}

	/*public boolean update() {

		x = (int) (GamePanel.player.getx() + 32.3 * Math.cos(Math
				.toRadians(rotation)));
		y = (int) (GamePanel.player.gety() + 32.3 * Math.sin(Math
				.toRadians(rotation)));

		return false;

	}*/

	/*public void draw(Graphics2D g) {

		double scale = 0.1;

		g.drawImage(GamePanel.transformImage(getTexture(), scale, rotation + 90),
				(int) (x - getTexture().getWidth() * scale / 2),
				(int) (y - getTexture().getHeight() * scale / 2), null);

	}*/
}
