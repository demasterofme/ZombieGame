package gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import launcher.GamePanel;

public class Map {

	private int WIDTH;
	private int HEIGHT;

	private int xOffset, yOffset;

	public static BufferedImage texture;

	public Map() {
		WIDTH = texture.getWidth();
		HEIGHT = texture.getHeight();
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public void update() {

		xOffset = GamePanel.player.getx() - GamePanel.WINDOW_WIDTH / 2;
		yOffset = GamePanel.player.gety() - GamePanel.WINDOW_HEIGHT / 2;
		
	}
	
	public int getxOffset() {
		return xOffset;
	}
	
	public int getyOffset() {
		return yOffset;
	}

	public void draw(Graphics2D g) {
		g.drawImage(texture.getSubimage(xOffset, yOffset, GamePanel.WINDOW_WIDTH,
				GamePanel.WINDOW_HEIGHT), 0, 0, GamePanel.WINDOW_WIDTH,
				GamePanel.WINDOW_HEIGHT, null);
	}
}
