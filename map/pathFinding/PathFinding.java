package map.pathFinding;

import java.util.ArrayList;
import java.util.Arrays;

import map.Quad;
import map.Vertex;

public class PathFinding {

	private ArrayList<Quad> colissionMap;
	private ArrayList<Vertex> vertices;

	public PathFinding(ArrayList<Quad> colissionMap) {

		this.colissionMap = colissionMap;
		this.vertices = getVertices();

	}

	public ArrayList<Quad> getColissionMap() {
		return colissionMap;
	}

	public ArrayList<Vertex> getVertices() {

		ArrayList<Vertex> vertices = new ArrayList<Vertex>();

		for (Quad q : colissionMap) {
			for (Vertex v : q.getVerticies()) {
				vertices.add(v);
			}
		}

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
