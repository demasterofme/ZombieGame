package entity;


public class Entity {

	protected float x, y;
	protected float dx, dy;
	protected int r;

	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public boolean update() {
		return false;
	}
	
	public float getx() {
		return x;
	}
	
	public float gety() {
		return y;
	}
	
	public int getr() {
		return r;
	}

}
