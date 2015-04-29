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

		button_resume = new Button(GamePanel.WINDOW_WIDTH / 2 - 58,
				GamePanel.WINDOW_HEIGHT / 2 + 50, "Resume", new Font(
						"Century Gothic", Font.PLAIN, 24));

		button_back = new Button(GamePanel.WINDOW_WIDTH / 2 - 120,
				GamePanel.WINDOW_HEIGHT / 2 + 100, "Back to Titlescreen",
				new Font("Century Gothic", Font.PLAIN, 24));
		
		System.out.println(button_resume.getWidth());

		buttons.add(button_resume);
		buttons.add(button_back);

		oldState.getPlayer().resetKeys();
	}

	public void update() {

		for (Button b : buttons)
			b.update();

		if (button_resume.isPressed()) {

			oldState.getPlayer().resume(this);
			GamePanel.changeGameState(oldState);

		}

		if (button_back.isPressed()) {

			GamePanel.changeGameState(new TitleScreen());

		}

	}

	public void render(Graphics2D g) {

		oldState.render(g);

		g.setColor(new Color(24, 24, 24, 240));

		g.fillRect(GamePanel.WINDOW_WIDTH / 2 - 300,
				GamePanel.WINDOW_HEIGHT / 2 - 150, 600, 300);

		g.setColor(Color.WHITE);

		Font font = new Font("Century Gothic", Font.PLAIN, 48);

		g.setFont(font);
		g.drawString("PAUSE",
				GamePanel.WINDOW_WIDTH / 2
						- g.getFontMetrics(font).stringWidth("PAUSE") / 2,
				GamePanel.WINDOW_HEIGHT / 2 - 100);

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
