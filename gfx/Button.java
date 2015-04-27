package gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import launcher.GamePanel;

public class Button {

	private boolean hover;
	private boolean pressed;
	private int x, y;
	private int width, height;
	private String text;
	private Font font;
	private Font font_hover;

	public Button(int x, int y, String text, Font font, Font font_hover) {
		this.x = x;
		this.y = y;
		this.font = font;
		this.font_hover = font_hover;
		this.text = text;
		this.hover = false;
		this.pressed = false;
		this.width = GamePanel.g.getFontMetrics(font).stringWidth(text) + 20;
		this.height = GamePanel.g.getFontMetrics(font).getHeight() + 10;
	}

	public Button(boolean centerX, int y, String text, Font font,
			Font font_hover) {
		this.y = y;
		this.font = font;
		this.font_hover = font_hover;
		this.text = text;
		this.hover = false;
		this.pressed = false;
		this.width = GamePanel.g.getFontMetrics(font).stringWidth(text) + 20;
		this.height = GamePanel.g.getFontMetrics(font).getHeight() + 10;

		if (centerX)
			x = GamePanel.WINDOW_WIDTH / 2 - width / 2;
		else
			x = 0;
	}

	public Button(int x, boolean centerY, String text, Font font,
			Font font_hover) {
		this.x = x;
		this.font = font;
		this.font_hover = font_hover;
		this.text = text;
		this.hover = false;
		this.pressed = false;
		this.width = GamePanel.g.getFontMetrics(font).stringWidth(text) + 20;
		this.height = GamePanel.g.getFontMetrics(font).getHeight() + 10;
		if (centerY)
			y = GamePanel.WINDOW_HEIGHT / 2 - height / 2;
		else
			y = 0;
	}

	// Press prevention, disables the button being pressed for over a tick
	int times = 0;

	public void update() {
		if (pressed)
			times++;
		if (times > 1) {
			times = 0;
			pressed = false;
		}
	}

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

	public boolean isHover() {
		return hover;
	}

	public void setHover(boolean hover) {
		this.hover = hover;
	}

	public int getx() {
		return x;
	}

	public int gety() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getText() {
		return text;
	}

	public void draw(Graphics2D g) {

		if (hover)
			g.setColor(new Color(150, 0, 0, 240));
		else
			g.setColor(new Color(24, 24, 24, 240));
		
		g.fillRect(x, y, width, height);

		g.setColor(new Color(150, 0, 0, 255));
		g.drawRect(x + 2, y + 2, width - 4, height - 4);

		g.setColor(Color.white);
		g.setFont(font);

		g.drawString(text, x + 10, y
				+ (int) (GamePanel.g.getFontMetrics(font).getHeight() * 0.9));

	}

}
