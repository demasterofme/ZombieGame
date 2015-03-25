package gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import launcher.GamePanel;

public class Text {

	private int x, y, time, alpha;
	private String text;
	private long startTime;
	private Font font;
	private Color color;

	private int width, height;

	public Text(String text, int time, Font font, Color color, int x, int y) {

		this.x = x;
		this.y = y;
		this.text = text;
		this.startTime = System.nanoTime();
		this.time = time;
		this.font = font;
		this.color = color;

		this.width = GamePanel.g.getFontMetrics(font).stringWidth(text);
		this.height = GamePanel.g.getFontMetrics(font).getHeight();

	}

	public boolean update() {
		long dif = (System.nanoTime() - startTime) / 1000000;
		if (dif > time)
			return true;
		alpha = (int) (255 * Math.cos((Math.PI / 2) * dif / time));
		if (alpha > 255)
			alpha = 255;
		if (alpha < 0)
			alpha = 0;
		return false;
	}

	public void draw(Graphics2D g) {

		Color color = new Color(this.color.getRed(), this.color.getGreen(),
				this.color.getBlue(), alpha);
		g.setColor(color);

		g.setFont(font);

		g.drawString(text, x - width / 2, y - height / 2);

	}

}
