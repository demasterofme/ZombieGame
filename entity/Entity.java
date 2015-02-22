package entity;


public class Entity {

	protected int x, y;
	protected int dx, dy;
	protected int r;

	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean update() {
		return false;
	}
	
	public int getx() {
		return x;
	}
	
	public int gety() {
		return y;
	}
	
	public int getr() {
		return r;
	}

}
