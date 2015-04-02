package map.pathFinding;

import java.awt.geom.GeneralPath;
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
			float[] coords = new float[2];

			while (!it.isDone()) {
				it.currentSegment(coords);
				vertices.add(new Vertex((int) coords[0], (int) coords[1], this));
				it.next();
			}

		}

		return vertices;
	}

	public ArrayList<Vertex> getVerticesList() {
		return vertices;
	}

	public ArrayList<Vertex> findPath(Vertex startVertex, Vertex goalVertex) {

		// Reset values
		for (Vertex v : vertices) {
			v.resetValues();
		}

		// Initialize
		startVertex.setG(0);
		ArrayList<Vertex> openList = new ArrayList<Vertex>(
				Arrays.asList(startVertex));
		ArrayList<Vertex> closedList = new ArrayList<Vertex>();
		ArrayList<Vertex> path = new ArrayList<Vertex>();
		boolean found = false;

		while (!found && !openList.isEmpty()) {

			// Get the Vertex with the lowest F value
			Vertex lowestVertex = openList.get(0);
			for (Vertex v : vertices) {
				if (v.getF() <= lowestVertex.getF())
					lowestVertex = v;
			}

			// Check if the lowestVertex is the goal vertex, if so, we found a
			// path
			if (lowestVertex == goalVertex) {
				found = true;
				continue;
			}

			// Remove the lowestVertex from the openList and add it to the
			// closedList
			openList.remove(lowestVertex);
			closedList.add(lowestVertex);

			// Update the G, H and F values of the neighbour vertices
			for (Vertex v : lowestVertex.getNeighbours()) {

			}

			return null;
		}

		return null;

	}

}
