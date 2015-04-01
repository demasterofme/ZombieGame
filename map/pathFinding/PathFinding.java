package map.pathFinding;

import java.awt.Rectangle;
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
	
	public  ArrayList<Vertex> findPath(Vertex start, Vertex goal) {
		
		return null;
		
	}
	
}
