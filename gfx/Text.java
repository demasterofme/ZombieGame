package gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import launcher.GamePanel;

public class Text {

	private int x, y, time, alpha;
	private ArrayList<String> messageList;
	private long startTime;
	private Font font;
	private Color color;

	private int width, height;

	private int breakIndex;

	public Text(String text, int time, Font font, Color color, int x, int y) {

		this.x = x;
		this.y = y;
		this.startTime = System.nanoTime();
		this.time = time;
		this.font = font;
		this.color = color;

		messageList = new ArrayList<String>();
		breakIndex = 35;

		splitText(text);
		width = calculateMaxWidth(messageList);
		height = calculateMaxHeight(messageList);

	}

	public Text(String text, int time, Font font, Color color) {

		this.startTime = System.nanoTime();
		this.time = time;
		this.font = font;
		this.color = color;

		messageList = new ArrayList<String>();
		breakIndex = 35;

		splitText(text);
		width = calculateMaxWidth(messageList);
		height = calculateMaxHeight(messageList);

		x = GamePanel.WINDOW_WIDTH / 2 - width / 2;
		y = GamePanel.WINDOW_HEIGHT / 2 - height / 2;

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

	public void splitText(String message) {

		int currentIndex = 0;

		for (int i = 0; i < message.length(); i++) {

			if (i == 0)
				continue;

			if (i % breakIndex == 0) {

				if (currentIndex + breakIndex <= message.length() - 1) {
					messageList.add(message.substring(currentIndex, i));
				} else
					messageList.add(message.substring(currentIndex,
							message.length() - 1));

				currentIndex += breakIndex;

			}
		}

		int totalSize = 0;
		for (String s : messageList)
			totalSize += s.length();

		if (totalSize != message.length())
			messageList.add(message.substring(currentIndex,
					message.length()));

	}

	public int calculateMaxWidth(ArrayList<String> message) {
		return GamePanel.g.getFontMetrics(font).stringWidth(messageList.get(0));
	}

	public int calculateMaxHeight(ArrayList<String> message) {

		int lineHeight = GamePanel.g.getFontMetrics(font).getHeight();
		int lineCount = message.size();
		
		return (int) (lineHeight * lineCount);

	}

	public void draw(Graphics2D g) {

		Color color = new Color(this.color.getRed(), this.color.getGreen(),
				this.color.getBlue(), alpha);
		g.setColor(color);

		g.setFont(font);

		int drawY = y;

		for (String textLine : messageList) {
			g.drawString(textLine, x, drawY);
			drawY += GamePanel.g.getFontMetrics(font).getHeight();
		}

	}

}
