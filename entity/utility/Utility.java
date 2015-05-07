package entity.utility;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Entity;

public class Utility extends Entity {

	private String name;
	private int price;
	protected BufferedImage texture;

	public Utility(int x, int y, String name, int price, BufferedImage texture) {
		super(x, y);
		this.name = name;
		this.price = price;
		this.texture = texture;
	}

	public Utility(String name, int price, BufferedImage texture) {
		super(-1, -1);
		this.name = name;
		this.price = price;
		this.texture = texture;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public BufferedImage getTexture() {
		return texture;
	}
	
	public boolean update() {
		return true;
	}
	
	public void draw(Graphics2D g) {
		
	}

}
