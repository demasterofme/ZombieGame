package gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import sfx.Sound;
import launcher.GamePanel;

public class Button {

	private boolean hover;
	private boolean pressed;
	private int x, y;
	private int width, height;
	private String text;
	private Font font;
	
	public static Sound clickSound;
	private boolean playedHoverSound;
	public static Sound hoverSound;

	public Button(int x, int y, String text, Font font) {
		this.x = x;
		this.y = y;
		this.font = font;
		this.text = text;
		this.hover = false;
		this.pressed = false;
		this.width = GamePanel.g.getFontMetrics(font).stringWidth(text) + 20;
		this.height = GamePanel.g.getFontMetrics(font).getHeight() + 10;
		
		playedHoverSound = false;
	}

	public Button(boolean centerX, int y, String text, Font font) {
		this.y = y;
		this.font = font;
		this.text = text;
		this.hover = false;
		this.pressed = false;
		this.width = GamePanel.g.getFontMetrics(font).stringWidth(text) + 20;
		this.height = GamePanel.g.getFontMetrics(font).getHeight() + 10;

		if (centerX)
			x = GamePanel.WINDOW_WIDTH / 2 - width / 2;
		else
			x = 0;
		
		playedHoverSound = false;
	}

	public Button(int x, boolean centerY, String text, Font font) {
		this.x = x;
		this.font = font;
		this.text = text;
		this.hover = false;
		this.pressed = false;
		this.width = GamePanel.g.getFontMetrics(font).stringWidth(text) + 20;
		this.height = GamePanel.g.getFontMetrics(font).getHeight() + 10;
		if (centerY)
			y = GamePanel.WINDOW_HEIGHT / 2 - height / 2;
		else
			y = 0;
		
		playedHoverSound = false;
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
		if (pressed)
			Button.playClickSound();
	}

	public boolean isHover() {
		return hover;
	}

	public void setHover(boolean hover) {
		this.hover = hover;
		if (hover && !playedHoverSound) {
			playedHoverSound = true;
			Button.playHoverSound();
		}
		if (!hover) {
			playedHoverSound = false;
		}
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
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public static void playClickSound() {
		clickSound.play();
	}
	
	public static void playHoverSound() {
		hoverSound.play();
	}

	public void draw(Graphics2D g) {

		Color primary = (hover ? new Color(150, 0, 0, 240) : new Color(24, 24,
				24, 240));
		Color secondary = (hover ? new Color(24, 24, 24, 240) : new Color(150,
				0, 0, 240));

		g.setColor(primary);
		g.fillRect(x, y, width, height);

		g.setColor(secondary);
		g.drawRect(x + 1, y + 1, width - 3, height - 3);

		g.setColor(Color.white);
		g.setFont(font);

		g.drawString(text, x + 10, y
				+ (int) (GamePanel.g.getFontMetrics(font).getHeight() * 0.9));

	}
}
