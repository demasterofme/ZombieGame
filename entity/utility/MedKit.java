package entity.utility;

import java.awt.Graphics2D;

public class MedKit extends Utility {
	
	private int boostPerSecond;
	
	private static int healTime = 20000;
	private long startTime;
	
	public MedKit(int x, int y) {
		
		super();
		
		boostPerSecond = 5;
		startTime = System.nanoTime();
		
	}
	
	public boolean update() {
		
		long timeLeft = (System.nanoTime() - startTime) / 1000000;
		
		return false;
	}
	
	public void draw(Graphics2D g) {
		
		
		
	}

}
