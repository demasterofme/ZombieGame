package entity.livingEntity;

import entity.Entity;

public class LivingEntity extends Entity {

	protected int health;
	protected int rotation;
	protected int speed;
	
	protected boolean dead;
	
	public LivingEntity(int x, int y) {
		super(x, y);
		dead = false;
	}
	
	public int getHealth() {
		return health;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public int getRotation() {
		return rotation;
	}
	
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
}
