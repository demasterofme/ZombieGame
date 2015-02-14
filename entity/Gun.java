package entity;

public class Gun extends Entity {

	private int rotation;
	private int fireRate;
	private int damage;
	private int reloadSpeed;
	private GunType type;
	
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
	
}
