package pathFinding;

public class Vertex {

	private int x, y;
	private Vertex parent;

	public Vertex(int x, int y, Vertex parent) {

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
	
	public Vertex getParent() {
		return parent;
	}

}
