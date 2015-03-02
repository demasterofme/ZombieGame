package map;

import gameState.inGame.InGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import launcher.GamePanel;

public class Map {

	private int WIDTH;
	private int HEIGHT;
	
	private int xOffset, yOffset;

	public static BufferedImage texture;
	
	public static ArrayList<Rectangle> rectangleList;

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
		
		// For debug
		g.setColor(Color.GREEN);
		for (Rectangle r : rectangleList) {
			g.drawRect(r.x - xOffset, r.y - yOffset, r.width, r.height);
		}
	}
}
