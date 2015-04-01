package map;

import java.awt.Point;

public class Quad {

	private Point p1, p2, p3, p4;
	private Vector2D v1, v2, v3, v4;

	public Quad(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {

		p1 = new Point(x1, y1);
		p2 = new Point(x2, y2);
		p3 = new Point(x3, y3);
		p4 = new Point(x4, y4);

		v1 = new Vector2D(p1, p2);
		v2 = new Vector2D(p2, p3);
		v3 = new Vector2D(p3, p4);
		v4 = new Vector2D(p4, p1);

	}

}
