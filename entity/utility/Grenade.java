package entity.utility;

import java.awt.image.BufferedImage;

public class Grenade extends Utility {

	private long startTime;
	
	public static BufferedImage texture;
	
	public Grenade(int x, int y) {
		super(x, y);
		startTime = System.nanoTime();
	}

}
