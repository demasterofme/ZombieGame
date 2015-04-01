package map;

import gameState.inGame.InGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import launcher.GamePanel;
import map.pathFinding.PathFinding;

public class Map {

	private int WIDTH;
	private int HEIGHT;
	
	private int xOffset;
	private int yOffset;

	public static BufferedImage texture;
	
	public static ArrayList<Quad> quadList;
	
	private PathFinding pathFinding;

	public Map() {
		
		WIDTH = texture.getWidth();
		HEIGHT = texture.getHeight();
		
		pathFinding = new PathFinding(quadList);
		
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public void update() {

		xOffset = InGame.player.getx() - GamePanel.WINDOW_WIDTH / 2;
		yOffset = InGame.player.gety() - GamePanel.WINDOW_HEIGHT / 2;

	}

	public int getxOffset() {
		return xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

	public void draw(Graphics2D g) {
		g.drawImage(texture.getSubimage(xOffset, yOffset,
				GamePanel.WINDOW_WIDTH, GamePanel.WINDOW_HEIGHT), 0, 0,
				GamePanel.WINDOW_WIDTH, GamePanel.WINDOW_HEIGHT, null);
		
		// Draw collision rectangles
		if (GamePanel.debugMode) {
			g.setColor(Color.GREEN);
			for (Quad q : quadList) {
				q.draw(g);
			}
		}
	}
}
