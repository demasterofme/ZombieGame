package map;

import gameState.inGame.InGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Vertex {

	private int x, y;

	private int g = Integer.MAX_VALUE;
	private int h = Integer.MAX_VALUE;

	private Vertex parent;

	public Vertex(int x, int y) {

		this.x = x;
		this.y = y;

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getF() {
		if (!(g == Integer.MAX_VALUE || h == Integer.MAX_VALUE))
			return g + h;
		return 0;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public Vertex getParent() {
		return parent;
	}

	public void setParent(Vertex parent) {
		this.parent = parent;
	}

	public boolean hasLineOfSight(Vertex v1, Vertex v2) {

		Line2D.Double line = new Line2D.Double(v1.getX(), v1.getY(), v2.getX(),
				v2.getY());

		for (GeneralPath g : Map.shapeList) {
			try {
				if (!getIntersections(g, line).isEmpty())
					return false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	public Set<Point2D> getIntersections(final GeneralPath path,
			final Line2D.Double line) throws Exception {

		final PathIterator polyIt = path.getPathIterator(null); // Getting an
																// iterator
																// along the
																// polygon path
		final double[] coords = new double[6]; // Double array with length 6
												// needed by iterator
		final double[] firstCoords = new double[2]; // First point (needed for
													// closing polygon path)
		final double[] lastCoords = new double[2]; // Previously visited point
		final Set<Point2D> intersections = new HashSet<Point2D>(); // List to
																	// hold
																	// found
																	// intersections
		polyIt.currentSegment(firstCoords); // Getting the first coordinate pair
		lastCoords[0] = firstCoords[0]; // Priming the previous coordinate pair
		lastCoords[1] = firstCoords[1];
		polyIt.next();
		while (!polyIt.isDone()) {
			final int type = polyIt.currentSegment(coords);
			switch (type) {
			case PathIterator.SEG_LINETO: {
				final Line2D.Double currentLine = new Line2D.Double(
						lastCoords[0], lastCoords[1], coords[0], coords[1]);
				if (currentLine.intersectsLine(line))
					intersections.add(getIntersection(currentLine, line));
				lastCoords[0] = coords[0];
				lastCoords[1] = coords[1];
				break;
			}
			case PathIterator.SEG_CLOSE: {
				final Line2D.Double currentLine = new Line2D.Double(coords[0],
						coords[1], firstCoords[0], firstCoords[1]);
				if (currentLine.intersectsLine(line))
					intersections.add(getIntersection(currentLine, line));
				break;
			}
			default: {
				throw new Exception("Unsupported PathIterator segment type.");
			}
			}
			polyIt.next();
		}
		return intersections;

	}

	public Point2D getIntersection(final Line2D.Double line1,
			final Line2D.Double line2) {

		final double x1, y1, x2, y2, x3, y3, x4, y4;
		x1 = line1.x1;
		y1 = line1.y1;
		x2 = line1.x2;
		y2 = line1.y2;
		x3 = line2.x1;
		y3 = line2.y1;
		x4 = line2.x2;
		y4 = line2.y2;
		final double x = ((x2 - x1) * (x3 * y4 - x4 * y3) - (x4 - x3)
				* (x1 * y2 - x2 * y1))
				/ ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
		final double y = ((y3 - y4) * (x1 * y2 - x2 * y1) - (y1 - y2)
				* (x3 * y4 - x4 * y3))
				/ ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));

		return new Point2D.Double(x, y);

	}

	public ArrayList<Vertex> getNeighbours() {

		return null;
	}

	public void resetValues() {

		parent = null;
		g = Integer.MAX_VALUE;
		h = Integer.MAX_VALUE;

	}

	public void draw(Graphics2D g) {
		g.setColor(Color.ORANGE);
		g.fillOval(x - 2 - InGame.map.getxOffset(),
				y - 2 - InGame.map.getyOffset(), 4, 4);
	}
}
