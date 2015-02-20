package entity;

import java.awt.image.BufferedImage;

public class Gun {

	private int rotation;
	private int damage;
	private int fireRate;
	private int reloadSpeed;
	private GunType type;
	private BufferedImage texture;

	public Gun(GunType type, int damage, int fireRate, int reloadSpeed, BufferedImage texture) {
		this.type = type;
		this.damage = damage;
		this.fireRate = fireRate;
		this.reloadSpeed = reloadSpeed;
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
	public GunType getType() {
		return type;
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
