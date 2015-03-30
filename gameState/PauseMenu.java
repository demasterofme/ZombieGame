package gameState;

import gameState.TitleScreen.TitleScreen;
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
	private Button button_back;

	public PauseMenu(InGame oldState) {

		this.oldState = oldState;

		pauseTimer = System.nanoTime();

		buttons = new ArrayList<>();

		button_resume = new Button(
				(int) (GamePanel.WINDOW_WIDTH - GamePanel.WINDOW_WIDTH * 0.2),
				(int) (GamePanel.WINDOW_HEIGHT - GamePanel.WINDOW_HEIGHT * 0.2),
				"Resume", new Font("Century Gothic", Font.PLAIN, 24), new Font(
						"Century Gothic", Font.BOLD, 24));

		button_back = new Button(
				(int) (GamePanel.WINDOW_WIDTH * 0.2),
				(int) (GamePanel.WINDOW_HEIGHT - GamePanel.WINDOW_HEIGHT * 0.2),
				"Back to Titlescreen", new Font("Century Gothic", Font.PLAIN,
						24), new Font("Century Gothic", Font.BOLD, 24));

		buttons.add(button_resume);
		buttons.add(button_back);

		oldState.getPlayer().resetKeys();
	}

	public void update() {

		for (Button b : buttons)
			b.update();

		if (button_resume.isPressed()) {

			oldState.getPlayer().resume();
			GamePanel.changeGameState(oldState);

		}

		if (button_back.isPressed()) {

			GamePanel.changeGameState(new TitleScreen());

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
	
	public InGame getOldState() {
		return oldState;
	}

	public long getPauseTimer() {
		return pauseTimer;
	}
}
