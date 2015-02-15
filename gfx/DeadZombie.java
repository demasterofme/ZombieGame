package gfx;

import entity.Entity;

public class DeadZombie extends Entity {

	private long died;
	private int alpha;
	
	public DeadZombie(int x, int y) {
		super(x, y);
		this.died = System.nanoTime();
	}
	
	public boolean update() {
		long diedDif = System.nanoTime() - died;
		if (diedDif / 1000000 > 3000)
			return true;
		alpha = (int) (diedDif / 1000000 / 3000 * 255);
		return false;
	}
	
	public void draw() {
		
	}
	
}
