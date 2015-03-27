package pathFinding;

import java.util.ArrayList;

public class PathFinding {

	private ArrayList<Vertex> open;
	private ArrayList<Vertex> closed;
	
	private ArrayList<Vertex> path;
	
	private Vertex start;
	private Vertex goal;
	
	public PathFinding(int startX, int startY, int goalX, int goalY) {
		start = new Vertex(startX, startY, null);
		goal = new Vertex(goalX, goalY, null);
		open = new ArrayList<>();
		closed = new ArrayList<>();
		path = new ArrayList<>();
	}
	
	
	
	
	
}
