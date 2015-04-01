package map;

import gameState.inGame.InGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
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

	public static ArrayList<GeneralPath> shapeList;

	private PathFinding pathFinding;

	public Map() {

		WIDTH = texture.getWidth();
		HEIGHT = texture.getHeight();

		pathFinding = new PathFinding(shapeList);

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
			for (GeneralPath p : shapeList) {
				PathIterator iterator = p.getPathIterator(null);
				int previousX = -1;
				int previousY = -1;
				while (!iterator.isDone()) {
					double[] coords = new double[6];
					iterator.currentSegment(coords);
					iterator.next();
					if (previousX != -1 && previousY != -1) {
						g.drawLine(previousX, previousY, (int) coords[0],
								(int) coords[1]);
					}
					previousX = (int) coords[0];
					previousY = (int) coords[1];
				}
			}
		}
	}
}
