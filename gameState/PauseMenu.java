package gameState;

import gameState.inGame.InGame;
import gfx.Button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import launcher.GamePanel;

public class PauseMenu extends GameState {

	private long pauseTimer;

	private InGame oldState;

	private ArrayList<Button> buttons;
	private Button button_resume;

	public PauseMenu(InGame oldState) {

		this.oldState = oldState;

		pauseTimer = System.nanoTime();

		buttons = new ArrayList<>();

		button_resume = new Button(400, 400, "Resume", new Font(
				"Century Gothic", Font.PLAIN, 24), new Font("Century Gothic",
				Font.BOLD, 24));

		buttons.add(button_resume);

		oldState.getPlayer().resetKeys();
	}

	public void update() {

		for (Button b : buttons)
			b.update();

		if (button_resume.isPressed()) {

			oldState.getPlayer().resume();
			GamePanel.changeGameState(oldState);

		}

	}

	public void render(Graphics2D g) {

		oldState.render(g);

		g.setColor(Color.WHITE);
		g.setFont(new Font("Century Gothic", Font.PLAIN, 48));
		g.drawString("Pause", GamePanel.WINDOW_WIDTH / 2, 150);

		for (Button b : buttons)
			b.draw(g);

	}

	public ArrayList<Button> getButtons() {
		return buttons;
	}

	public long getPauseTimer() {
		return pauseTimer;
	}
}
