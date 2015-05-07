package entity.livingEntity;

import entity.Entity;

public class LivingEntity extends Entity {

	protected int health;
	protected double rotation;
	protected double speed;

	protected boolean dead;

	public LivingEntity(double x, double y) {
		super(x, y);
		dead = false;
	}

	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isDead() {
		return dead;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	public void damage(int damage) {
		this.health -= damage;
		if (health <= 0)
			this.dead = true;
	}

}
