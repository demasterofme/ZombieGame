package gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import launcher.GamePanel;

public class MuzzleFlash {

	private int x, y;
	private int rotation;
	private long startTimer;
	public static BufferedImage texture;
	
	public MuzzleFlash(int x, int y, int rotation) {
		
		this.x = x;
		this.y = y;
		this.rotation = rotation - 90;
		this.startTimer = System.nanoTime();
		
	}
	
	public boolean update() {
		
		if ((System.nanoTime() - startTimer) / 1000000 >= 100) {
		
			return true;
			
		}
		
		return false;
	}
	
	public void draw(Graphics2D g) {
		
		double scale = 0.1;
		
		g.drawImage(GamePanel.transformImage(texture, scale, rotation + 90),
				(int) (x - texture.getWidth() * scale / 2),
				(int) (y - texture.getHeight() * scale / 2), null);
	}
	
}
