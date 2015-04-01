package pathFinding;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

public class PathFinding {

	private ArrayList<Rectangle> colissionMap;
	private ArrayList<Vertex> vertexes;
	
	public PathFinding(ArrayList<Rectangle> colissionMap) {

		this.colissionMap = colissionMap;
		this.vertexes = getVertexes();
		
	}
	
	public ArrayList<Rectangle> getColissionMap() {
		return colissionMap;
	}
	
	public ArrayList<Vertex> getVertexes() {
		
		ArrayList<Vertex> vertexes = new ArrayList<Vertex>();
		
		for (Rectangle r : colissionMap) {
			vertexes.add(new Vertex(r.x, r.y));
			vertexes.add(new Vertex(r.x, r.y + r.height));
			vertexes.add(new Vertex(r.x + r.width, r.y + r.height));
			vertexes.add(new Vertex(r.x + r.width, r.y));
		}
		
		return vertexes;
	}
	
	public  ArrayList<Vertex> findPath(Vertex start, Vertex goal) {
		
		return null;
		
	}
	
}
