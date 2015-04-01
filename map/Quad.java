package map;

import gameState.inGame.InGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

import com.sun.javafx.geom.Line2D;

public class Quad {

	private Vertex p1, p2, p3, p4;
	private Line2D v1, v2, v3, v4;

	private ArrayList<Vertex> vertices;
	private ArrayList<Line2D> vectors;

	public Quad(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {

		p1 = new Vertex(x1, y1);
		p2 = new Vertex(x2, y2);
		p3 = new Vertex(x3, y3);
		p4 = new Vertex(x4, y4);

		v1 = new Line2D();
		v1.setLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		v2 = new Line2D();
		v2.setLine(p2.getX(), p2.getY(), p3.getX(), p3.getY());
		v3 = new Line2D();
		v3.setLine(p3.getX(), p3.getY(), p4.getX(), p4.getY());
		v4 = new Line2D();
		v4.setLine(p4.getX(), p4.getY(), p1.getX(), p1.getY());

		vertices = new ArrayList<Vertex>(Arrays.asList(p1, p2, p3, p4));
		vectors = new ArrayList<Line2D>(Arrays.asList(v1, v2, v3, v4));

	}

	public ArrayList<Vertex> getVerticies() {
		return vertices;
	}

	public ArrayList<Line2D> getVectors() {
		return vectors;
	}

	// For debugging
	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.drawLine(p1.getX() - InGame.map.getxOffset(),
				p1.getY() - InGame.map.getyOffset(),
				p2.getX() - InGame.map.getxOffset(),
				p2.getY() - InGame.map.getyOffset());
		g.drawLine(p2.getX() - InGame.map.getxOffset(),
				p2.getY() - InGame.map.getyOffset(),
				p3.getX() - InGame.map.getxOffset(),
				p3.getY() - InGame.map.getyOffset());
		g.drawLine(p3.getX() - InGame.map.getxOffset(),
				p3.getY() - InGame.map.getyOffset(),
				p4.getX() - InGame.map.getxOffset(),
				p4.getY() - InGame.map.getyOffset());
		g.drawLine(p4.getX() - InGame.map.getxOffset(),
				p4.getY() - InGame.map.getyOffset(),
				p1.getX() - InGame.map.getxOffset(),
				p1.getY() - InGame.map.getyOffset());
	}
}
