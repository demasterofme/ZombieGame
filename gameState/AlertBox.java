package gameState;

import gameState.inGame.InGame;
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

	private long pauseTimer;
	
	public AlertBox(GameState previousGameState, String message) {

		
		this.pauseTimer = System.nanoTime();

		this.previousGameState = previousGameState;

		this.message = message;
		this.font = new Font("Century Gothic", Font.PLAIN, 24);
		
		this.width = 900;
		this.height = ((int) message.length() / 70) *  GamePanel.g.getFontMetrics(font).getHeight() + 50;
		
		this.x = GamePanel.WINDOW_WIDTH / 2 - width / 2;
		this.y = GamePanel.WINDOW_HEIGHT / 2 - height / 2;
		
		buttons = new ArrayList<>();

		buttonClose = new Button(x + width - 100, y + height - 50, "Close",
				new Font("Century Gothic", Font.PLAIN, 24));
		
		buttons.add(buttonClose);
		
	}

	public void update() {

		for (Button b : buttons)
			b.update();

		if (buttonClose.isPressed()) {

			if (previousGameState instanceof InGame)
				((InGame) previousGameState).getPlayer().resume(this);
			
			GamePanel.changeGameState(previousGameState);
		}

	}

	public long getPauseTimer() {
		return pauseTimer;
	}

	public void render(Graphics2D g) {

		previousGameState.render(g);

		g.setColor(new Color(24, 24, 24, 240));
		g.fillRect(x, y, width, height);

		g.setColor(Color.RED);
		g.drawRect(x + 2, y + 2, width - 4, height - 4);
		g.setColor(Color.WHITE);
		g.drawString("Message:", x + 20, y + 30);
		// g.drawString(message, x + 30, y + 55);
		
		int breakIndex = 0;
		int drawY = y + 55;
		boolean canBreak = true;
		while (canBreak) {
			try {
				g.drawString(
						message.substring(breakIndex, breakIndex + 70),
						x + 30, drawY);
				breakIndex += 50;
				drawY += 20;
			} catch (Exception e) {
				canBreak = false;
				g.drawString(
						message.substring(breakIndex, message.length()),
						x + 30, drawY);
			}
		}

		for (Button b : buttons)
			b.draw(g);

	}

	public ArrayList<Button> getButtons() {
		return buttons;
	}

}
