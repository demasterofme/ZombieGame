package entity.utility;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Grenade extends Utility {

	private long startTime;
	
	public Grenade(BufferedImage texture) {
		
		super("Grenade", 100, texture);
		
		r = 60;
		
	}
	
	public Grenade deploy(double x, double y, double angle) {
		this.x = x;
		this.y = y;
		double rad = Math.toRadians(angle);
		dx = Math.cos(rad) * 0.1;
		dy = Math.sin(rad) * 0.1;
		startTime = System.nanoTime();
		return this;
	}
	
	public boolean update() {
		
		return true;
	}
	
	public void draw(Graphics2D g) {
		
	}

}
