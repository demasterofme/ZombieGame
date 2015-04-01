package map;

import java.util.ArrayList;

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
	
	public void setG(int g)	 {
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
	
	public ArrayList<Vertex> getNeighbours() {
		
		// Return vertices that are in LineOfSight
		
		return null;
	}

}
