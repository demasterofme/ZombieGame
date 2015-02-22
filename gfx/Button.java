package gfx;

import gameState.InGame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import launcher.GamePanel;

public class Button {

	public boolean hover;
	public boolean pressed;
	private int x, y;
	private int width, height;
	private String text;
	private buttonType type;
	public static Font font;
	public static Graphics g;

	public Button(int x, int y, buttonType type, String text) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.text = text;
		this.hover = false;
		this.pressed = false;
		this.width = g.getFontMetrics(font).stringWidth(text);
		this.height = g.getFontMetrics(font).getHeight();
	}

	public Button(boolean centerX, int y, buttonType type, String text) {
		this.y = y;
		this.type = type;
		this.text = text;
		this.hover = false;
		this.pressed = false;
		this.width = g.getFontMetrics(font).stringWidth(text);
		this.height = g.getFontMetrics(font).getHeight();
		if (centerX)
			x = GamePanel.WINDOW_WIDTH / 2 - width / 2;
		else
			x = 0;
	}

	public Button(int x, boolean centerY, buttonType type, String text) {
		this.x = x;
		this.type = type;
		this.text = text;
		this.hover = false;
		this.pressed = false;
		this.width = g.getFontMetrics(font).stringWidth(text);
		this.height = g.getFontMetrics(font).getHeight();
		if (centerY)
			y = GamePanel.WINDOW_HEIGHT / 2 - height / 2;
		else
			y = 0;
	}

	public void update() {

		if (pressed)
			switch (type) {
			case START_GAME:
				GamePanel.gameState = new InGame();
				break;
			case OPTIONS:
				break;
			case STOP_GAME:
				GamePanel.running = false;
				break;
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
			g.setFont(new Font("Century Gothic", Font.BOLD, 42));
		else
			g.setFont(new Font("Century Gothic", Font.PLAIN, 42));
		g.drawString(text, x, y);
	}

	public enum buttonType {
		START_GAME, OPTIONS, STOP_GAME;
	}
}
