package map;

import gameState.inGame.InGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import map.pathFinding.PathFinding;

public class Vertex {

	private int x, y;

	private int g = 100000;
	private int h = 100000;

	private Vertex parent;

	private PathFinding pathFinding;

	public Vertex(int x, int y, PathFinding pathFinding) {

		this.x = x;
		this.y = y;

		this.pathFinding = pathFinding;

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
		return g + h;
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

	public boolean hasLineOfSight(Vertex v) {

		Line2D.Double line = new Line2D.Double(getX(), getY(), v.getX(),
				v.getY());

		for (GeneralPath g : Map.shapeList) {
			try {
				if (intersects(g, line, v))
					return false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	public boolean hasLineOfSight(Vertex v, boolean output) {

		Line2D.Double line = new Line2D.Double(getX(), getY(), v.getX(),
				v.getY());

		for (GeneralPath g : Map.shapeList) {
			try {
				if (intersects(g, line, v))
					return false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	// public boolean intersects(GeneralPath path, Line2D.Double line) {
	// int[] xPoints = { (int) line.getX1(), (int) line.getX2() };
	// int[] yPoints = { (int) line.getY1(), (int) line.getY2() };
	// Polygon linePolygon = new Polygon(xPoints, yPoints, 2);
	// PathIterator pathIt = path.getPathIterator(null);
	// double[] coords = new double[6];
	// pathIt.currentSegment(coords);
	// xPoints = new int[1000];
	// yPoints = new int[1000];
	// int xPointsIndex = 0;
	// int yPointsIndex = 0;
	// while (!pathIt.isDone()) {
	// pathIt.currentSegment(coords);
	// xPoints[xPointsIndex++] = (int) coords[0];
	// yPoints[yPointsIndex++] = (int) coords[1];
	// pathIt.next();
	// }
	// Polygon pathPolygon = new Polygon(xPoints, yPoints, xPoints.length);
	// Area pathArea = new Area(pathPolygon);
	// pathArea.intersect(new Area(linePolygon));
	// System.out.println("Empty: " + new Area(linePolygon).isEmpty());
	// return !pathArea.isEmpty();
	// }

	public boolean intersects(GeneralPath path, Line2D.Double line, Vertex v)
			throws Exception {

		PathIterator pathIt = path.getPathIterator(null); // Getting an
															// iterator
															// along the
															// polygon path
		double[] coords = new double[6]; // Double array with length 6
											// needed by iterator
		double[] firstCoords = new double[2]; // First point (needed for
												// closing polygon path)
		double[] lastCoords = new double[2]; // Previously visited point
		pathIt.currentSegment(firstCoords); // Getting the first coordinate pair
		lastCoords[0] = firstCoords[0]; // Priming the previous coordinate pair
		lastCoords[1] = firstCoords[1];
		pathIt.next();

		boolean intersects = false;
		int endLineIntersections = 0;

		while (!pathIt.isDone()) {
			int type = pathIt.currentSegment(coords);
			switch (type) {
			case PathIterator.SEG_LINETO: {
				Line2D.Double currentLine = new Line2D.Double(lastCoords[0],
						lastCoords[1], coords[0], coords[1]);
				System.out.println("Line check");
				if (currentLine.intersectsLine(line)) {

					double currentLineSlope = (((double) Math.abs(currentLine
							.getX2() - currentLine.getX1())) / ((double) Math
							.abs(currentLine.getY2() - currentLine.getY1())));

					double lineSlope = (((double) Math.abs(line.getX2()
							- line.getX1())) / ((double) Math.abs(line.getY2()
							- line.getY1())));

					Point2D.Double beginCurrentLine = new Point2D.Double(
							currentLine.getX1(), currentLine.getY1());
					Point2D.Double endCurrentLine = new Point2D.Double(
							currentLine.getX2(), currentLine.getY2());
					Point2D.Double beginLine = new Point2D.Double(line.getX1(),
							line.getY1());
					Point2D.Double endLine = new Point2D.Double(line.getX2(),
							line.getY2());
					Point2D.Double intersection = (Point2D.Double) getIntersection(
							currentLine, line);

					System.out.println("Currentline: " + currentLine.getX1()
							+ ";" + currentLine.getY1() + ", "
							+ currentLine.getX2() + ";" + currentLine.getY2()
							+ " Line: " + line.getX1() + ";" + line.getY1()
							+ ", " + line.getX2() + ";" + line.getY2());

					if (currentLineSlope == lineSlope
							|| (Double.isInfinite(currentLineSlope) && Double
									.isNaN(lineSlope))
							|| (Double.isInfinite(lineSlope) && Double
									.isNaN(currentLineSlope))) {
						System.out.println("No intersection slope case");
						return false;
					}

					if (!(intersection.equals(endCurrentLine) || intersection
							.equals(beginCurrentLine))) {
						intersects = true;
						if (intersection.equals(beginLine)
								|| intersection.equals(endLine))
							if (!(++endLineIntersections > 1))
								intersects = false;
					}

				}
				lastCoords[0] = coords[0];
				lastCoords[1] = coords[1];
				break;
			}
			case PathIterator.SEG_CLOSE: {
				Line2D.Double currentLine = new Line2D.Double(coords[0],
						coords[1], firstCoords[0], firstCoords[1]);
				System.out.println("Line check");
				if (currentLine.intersectsLine(line)) {

					double currentLineSlope = (((double) Math.abs(currentLine
							.getX2() - currentLine.getX1())) / ((double) Math
							.abs(currentLine.getY2() - currentLine.getY1())));

					double lineSlope = (((double) Math.abs(line.getX2()
							- line.getX1())) / ((double) Math.abs(line.getY2()
							- line.getY1())));

					Point2D.Double beginCurrentLine = new Point2D.Double(
							currentLine.getX1(), currentLine.getY1());
					Point2D.Double endCurrentLine = new Point2D.Double(
							currentLine.getX2(), currentLine.getY2());
					Point2D.Double beginLine = new Point2D.Double(line.getX1(),
							line.getY1());
					Point2D.Double endLine = new Point2D.Double(line.getX2(),
							line.getY2());
					Point2D.Double intersection = (Point2D.Double) getIntersection(
							currentLine, line);

					System.out.println("Currentline: " + currentLine.getX1()
							+ ";" + currentLine.getY1() + ", "
							+ currentLine.getX2() + ";" + currentLine.getY2()
							+ " Line: " + line.getX1() + ";" + line.getY1()
							+ ", " + line.getX2() + ";" + line.getY2());

					if (currentLineSlope == lineSlope
							|| (Double.isInfinite(currentLineSlope) && Double
									.isNaN(lineSlope))
							|| (Double.isInfinite(lineSlope) && Double
									.isNaN(currentLineSlope))) {
						System.out.println("No intersection slope case");
						return false;
					}

					if (!(intersection.equals(endCurrentLine) || intersection
							.equals(beginCurrentLine))) {
						intersects = true;
						if (intersection.equals(beginLine)
								|| intersection.equals(endLine))
							if (!(++endLineIntersections > 1))
								intersects = false;
					}

				}
				break;
			}
			default: {
				throw new Exception("Unsupported PathIterator segment type.");
			}
			}
			pathIt.next();
		}
		System.out.println((intersects ? "" : "No ") + "Intersection");
		return intersects;
	}

	public Point2D getIntersection(Line2D.Double line1, Line2D.Double line2) {

		double x1 = line1.x1, y1 = line1.y1, x2 = line1.x2, y2 = line1.y2, x3 = line2.x1, y3 = line2.y1, x4 = line2.x2, y4 = line2.y2;
		double x = ((x2 - x1) * (x3 * y4 - x4 * y3) - (x4 - x3)
				* (x1 * y2 - x2 * y1))
				/ ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
		double y = ((y3 - y4) * (x1 * y2 - x2 * y1) - (y1 - y2)
				* (x3 * y4 - x4 * y3))
				/ ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));

		return new Point2D.Double(x, y);

	}

	public ArrayList<Vertex> getNeighbours(Vertex goalVertex) {

		ArrayList<Vertex> neighbours = new ArrayList<Vertex>();

		@SuppressWarnings("unchecked")
		ArrayList<Vertex> verticesList = (ArrayList<Vertex>) pathFinding
				.getVerticesList().clone();

		if (!goalVertex.equalsVertex(new Vertex(InGame.player.getx(),
				InGame.player.gety(), pathFinding))) {
			verticesList.add(new Vertex(InGame.player.getx(), InGame.player
					.gety(), pathFinding));
		} else {
			verticesList.add(goalVertex);
		}

		for (Vertex v : verticesList) {

			if (this.hasLineOfSight(v))
				neighbours.add(v);

		}

		return neighbours;
	}

	public void resetValues() {

		parent = null;
		g = 100000;
		h = 100000;

	}

	public boolean equalsVertex(Vertex v) {
		if (this.getX() == v.getX() && this.getY() == v.getY())
			return true;
		return false;
	}

	public Point2D toPoint() {
		return new Point2D.Double(x, y);
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.ORANGE);
		g.fillOval(x - 2 - InGame.map.getxOffset(),
				y - 2 - InGame.map.getyOffset(), 4, 4);
	}
}
