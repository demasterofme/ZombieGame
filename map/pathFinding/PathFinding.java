package map.pathFinding;

import gameState.inGame.InGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Arrays;

import map.Vertex;

public class PathFinding {

	private ArrayList<GeneralPath> colissionMap;
	private ArrayList<Vertex> vertices;

	public PathFinding(ArrayList<GeneralPath> colissionMap) {

		this.colissionMap = colissionMap;
		vertices = makeVerticesList();

	}

	public ArrayList<GeneralPath> getColissionMap() {
		return colissionMap;
	}

	private ArrayList<Vertex> makeVerticesList() {

		ArrayList<Vertex> vertices = new ArrayList<Vertex>();

		for (GeneralPath p : colissionMap) {

			PathIterator it = p.getPathIterator(null);
			double[] coords = new double[2];

			while (!it.isDone()) {
				it.currentSegment(coords);
				vertices.add(new Vertex(coords[0], coords[1], this));
				it.next();
			}

			vertices.remove(vertices.size() - 1);

		}

		return vertices;
	}

	public ArrayList<Vertex> getVerticesList() {
		return vertices;
	}

	int z = 0;
	ArrayList<Line2D> lineOfSight = new ArrayList<Line2D>();
	ArrayList<Line2D> noLineOfSight = new ArrayList<Line2D>();

	public ArrayList<Vertex> findPath(Vertex startVertex, Vertex goalVertex) {

		@SuppressWarnings("unchecked")
		ArrayList<Vertex> vertices = (ArrayList<Vertex>) this.vertices.clone();
		vertices.add(startVertex);
		vertices.add(goalVertex);

		// Reset values
		for (Vertex v : vertices) {
			v.resetValues();
		}

		// Initialize
		startVertex.setG(0);
		startVertex.setH(0);
		ArrayList<Vertex> openList = new ArrayList<Vertex>(
				Arrays.asList(startVertex));
		ArrayList<Vertex> closedList = new ArrayList<Vertex>();
		@SuppressWarnings("unchecked")
		ArrayList<Vertex> localVerticesList = (ArrayList<Vertex>) getVerticesList().clone();
		localVerticesList.add(goalVertex);
		boolean found = false;

		while (!found && !openList.isEmpty()) {

			// Get the Vertex with the lowest F value
			Vertex cheapestVertex = openList.get(0);

			for (Vertex v : openList) {
				if (v.getF() <= cheapestVertex.getF())
					cheapestVertex = v;
			}

			// Check if the lowestVertex is the goal vertex, if so, we found a
			// path
			if (cheapestVertex.equalsVertex(goalVertex)) {
				found = true;
				continue;
			}

			// Remove the lowestVertex from the openList and add it to the
			// closedList
			openList.remove(cheapestVertex);
			closedList.add(cheapestVertex);

			//System.out.println("New neighbors check");

			// Update the G, H and F values of the neighbour vertices
			for (Vertex v : cheapestVertex.getNeighbours(goalVertex)) {

				if (!closedList.contains(v)) {

					// G value (From cheapestVertex to neighbor)
					int newG = (int) Math.sqrt(Math.pow(cheapestVertex.getX()
							- v.getX(), 2)
							+ Math.pow(cheapestVertex.getY() - v.getY(), 2))
							+ cheapestVertex.getG();

					// H value (From neighbor vertex to goalVertex)
					int newH = (int) Math.sqrt(Math.pow(
							v.getX() - goalVertex.getX(), 2)
							+ Math.pow(v.getY() - goalVertex.getY(), 2));

					if (!openList.contains(v)) {
						openList.add(v);
						v.setParent(cheapestVertex);
						v.setG(newG);
						v.setH(newH);
					} else {
						if (newG < v.getG()) {
							v.setG(newG);
							v.setParent(cheapestVertex);
						}
					}
				}
			}
		}

		if (found) {
			Vertex currentVertex = goalVertex;
			ArrayList<Vertex> path = new ArrayList<Vertex>();

			while (currentVertex != startVertex) {

				path.add(currentVertex);
				currentVertex = currentVertex.getParent();

			}

			path.add(startVertex);
			//System.out.println("Path found");
			return path;
		} else {
			//System.out.println("No path");
			return null;
		}

	}

}
