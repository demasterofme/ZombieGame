package map;

import gameState.inGame.InGame;

import java.awt.BasicStroke;
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
		System.out.println("LoS Check: " + new Vertex(1024, 1024, pathFinding).hasLineOfSight(new Vertex(1600, 1600, pathFinding)));

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
	
	public PathFinding getPathFinding() {
		return pathFinding;
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
			g.setStroke(new BasicStroke(1));
			g.setColor(Color.GREEN);
			for (GeneralPath p : shapeList) {
				PathIterator iterator = p.getPathIterator(null);
				int previousX = -1;
				int previousY = -1;
				int startX = -1;
				int startY = -1;
				while (!iterator.isDone()) {
					double[] coords = new double[6];
					iterator.currentSegment(coords);
					if (previousX != -1 && previousY != -1) {
						g.drawLine(previousX - InGame.map.getxOffset(),
								previousY - InGame.map.getyOffset(),
								(int) (coords[0] == 0 ? startX : coords[0]) - InGame.map.getxOffset(),
								(int)  (coords[1] == 0 ? startY : coords[1]) - InGame.map.getyOffset());
					} else {
						startX = (int) coords[0];
						startY = (int) coords[1];
					}
					previousX = (int) coords[0];
					previousY = (int) coords[1];
					iterator.next();
				}
			}
		}
	}
}
