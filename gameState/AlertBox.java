package gameState;

import gfx.Button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import launcher.GamePanel;

public class AlertBox extends GameState {

	private ArrayList<Button> buttons;

	private GameState previousGameState;

	private int x, y;
	private int width, height;

	private String message;
	private Font font;

	private Button buttonClose;

	public AlertBox(GameState previousGameState, int width, int height,
			String message) {

		this.previousGameState = previousGameState;

		this.width = width;
		this.height = height;

		this.x = GamePanel.WINDOW_WIDTH / 2 - width / 2;
		this.y = GamePanel.WINDOW_HEIGHT / 2 - height / 2;

		this.message = message;
		this.font = new Font("Century Gothic", Font.PLAIN, 24);
		
		buttons = new ArrayList<>();

		buttonClose = new Button(x + width - 100, y + height - 20, "Close",
				new Font("Century Gothic", Font.PLAIN, 24), new Font(
						"Century Gothic", Font.BOLD, 24));
		
		buttons.add(buttonClose);

	}

	public void update() {

		for (Button b : buttons)
			b.update();

	}

	public void render(Graphics2D g) {

		previousGameState.render(g);

		g.setColor(new Color(24, 24, 24, 240));
		g.fillRect(x, y, width, height);
		
		g.setColor(Color.RED);
		g.setColor(Color.WHITE);
		g.drawString("Alert", x + 5, y + 20);
		g.drawString(message, x + 5, y + 40);

		for (Button b : buttons)
			b.draw(g);

	}

	public ArrayList<Button> getButtons() {
		return buttons;
	}

}
