package map.pathFinding;

import java.util.ArrayList;

import map.Vertex;

public class Path {

	private ArrayList<Vertex> vertices;
	private int index;
	
	public Path(ArrayList<Vertex> vertices) {
		
		this.vertices = vertices;
		
	}
	
	public Path() {
		
		vertices = new ArrayList<Vertex>();
		
	}
	
	public Vertex next() {
		return vertices.get(index++);
	}
	
	public void reset() {
		index = 0;
	}
	
	public void add(Vertex vertex) {
		vertices.add(vertex);
	}
	
	public void remove(Vertex vertex) {
		vertices.remove(vertex);
	}
	
}
