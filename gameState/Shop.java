package gameState;

import gfx.Button;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import launcher.GamePanel;

public class Shop extends GameState {

	private InGame oldState;

	private ArrayList<Button> buttons;
	private Button button_back;

	public Shop(InGame oldState) {

		this.oldState = oldState;

		buttons = new ArrayList<>();

		button_back = new Button(
				(int) (GamePanel.WINDOW_WIDTH - GamePanel.WINDOW_WIDTH * 0.2),
				(int) (GamePanel.WINDOW_HEIGHT - GamePanel.WINDOW_HEIGHT * 0.2),
				"BACK", new Font("Century Gothic", Font.PLAIN, 24), new Font(
						"Century Gothic", Font.BOLD, 24));

		buttons.add(button_back);
		
		oldState.getPlayer().resetKeys();

	}

	public void update() {

		oldState.update();

		for (Button b : buttons)
			b.update();

		if (button_back.isPressed())
			GamePanel.changeGameState(oldState);

	}

	public void render(Graphics2D g) {

		oldState.render(g);

		g.setColor(Color.WHITE);

		g.setStroke(new BasicStroke(3));
		g.drawRect((int) (GamePanel.WINDOW_WIDTH * 0.1),
				(int) (GamePanel.WINDOW_HEIGHT * 0.1),
				(int) (GamePanel.WINDOW_WIDTH - GamePanel.WINDOW_WIDTH * 0.2),
				(int) (GamePanel.WINDOW_HEIGHT - GamePanel.WINDOW_HEIGHT * 0.2));

		g.setFont(new Font("Century Gothic", Font.PLAIN, 48));
		g.drawString("SHOP", GamePanel.WINDOW_WIDTH / 2, 150);

		for (Button b : buttons)
			b.draw(g);

	}

	public ArrayList<Button> getButtons() {
		return buttons;
	}
	
	public InGame getOldState() {
		return oldState;
	}

}
