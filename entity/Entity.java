package entity;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Entity {

	protected int x, y;
	protected int dx, dy;
	protected int r;

	public static BufferedImage texture;

	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean update() {
		return false;
	}
	
	public int getx() {
		return x;
	}
	
	public int gety() {
		return y;
	}
	
	public int getr() {
		return r;
	}
	
	protected static BufferedImage transformImage(BufferedImage image,
			double scale, int rotation) {
		int scaledWidth = (int) (scale * image.getWidth());
		int scaledHeight = (int) (scale * image.getHeight());
		AffineTransform transform;
		if (rotation % 180 == 0) {
			transform = AffineTransform
					.getRotateInstance(Math.toRadians(rotation),
							scaledWidth / 2, scaledHeight / 2);
			transform.scale(scale, scale);
		} else {
			transform = AffineTransform.getTranslateInstance(
					(scaledHeight - scaledWidth) / 2,
					(scaledWidth - scaledHeight) / 2);
			transform.rotate(Math.toRadians(rotation), scaledWidth / 2,
					scaledHeight / 2);
			transform.scale(scale, scale);
		}
		AffineTransformOp operation = new AffineTransformOp(transform,
				AffineTransformOp.TYPE_BILINEAR);
		return operation.filter(image, null);
	}

}
