package entity;


public class Entity {

	protected double x, y;
	protected double dx, dy;
	protected int r;

	public Entity(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public boolean update() {
		return false;
	}
	
	public double getx() {
		return x;
	}
	
	public double gety() {
		return y;
	}
	
	public int getr() {
		return r;
	}

}
