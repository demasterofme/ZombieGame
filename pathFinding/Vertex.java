package pathFinding;

public class Vertex {

	private int x, y;
	private int g = 0;
	private Vertex parent;

	public Vertex(int x, int y) {

		this.x = x;
		this.y = y;
		this.parent = parent;

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
	
	public Vertex getParent() {
		return parent;
	}
	
	public void setParent(Vertex parent) {
		this.parent = parent;
	}

}
