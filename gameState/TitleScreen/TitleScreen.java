package gameState.TitleScreen;

import gameState.AlertBox;
import gameState.GameState;
import gameState.inGame.Endless;
import gfx.Button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import launcher.GamePanel;

public class TitleScreen extends GameState {

	public static ArrayList<Button> buttons;
	private Button button_start;
	private Button button_quit;

	private BufferedImage backgroundImage;

	public TitleScreen() {

		buttons = new ArrayList<>();

		Font font = new Font("Century Gothic", Font.PLAIN, 42);

		// Add buttons to the screen, will be perfected later

		button_start = new Button(true, 250, "Start Game", font);
		button_quit = new Button(true, 350, "Quit", font);

		buttons.add(button_start);
		buttons.add(button_quit);

		// For testing
		try {
			backgroundImage = ImageIO.read(GamePanel.class
					.getResource("/sprites/temp.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {

		for (Button b : buttons)
			b.update();

		if (button_start.isPressed()) {
			Endless endless = new Endless();
			AlertBox alertBox = new AlertBox(
					endless,
					"Welcome to zombie game version 1.0. We hope that you won't encounter any bugs what so ever. But, we are aware that it could happen. Please bare with us");
			GamePanel.changeGameState(alertBox);
		}
		if (button_quit.isPressed()) {
			GamePanel.running = false;
		}
	}

	public void render(Graphics2D g) {
		// g.setColor(Color.BLACK);
		// g.fillRect(0, 0, GamePanel.WINDOW_WIDTH, GamePanel.WINDOW_HEIGHT);

		g.drawImage(backgroundImage, 0, 0, GamePanel.WINDOW_WIDTH,
				GamePanel.WINDOW_HEIGHT, null);

		g.setColor(Color.WHITE);

		for (Button b : buttons)
			b.draw(g);

		// Debug mode
		if (GamePanel.debugMode) {

			y = 5;

			g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
			g.setColor(Color.WHITE);

			g.drawString("Debug Mode", 10, updateY());
			g.drawString("Mouse: ", 10, updateY());
			g.drawString("X: " + GamePanel.mouseX + " Y: " + GamePanel.mouseY,
					20, updateY());
			g.drawString("Button: ", 10, updateY());
			g.drawString("X: " + buttons.get(2).getx() + " Y: "
					+ buttons.get(0).gety(), 20, updateY());
			g.drawString("Width: " + buttons.get(2).getWidth() + " Heigth: "
					+ buttons.get(2).getHeight(), 20, updateY());
			g.drawString("Hover: " + buttons.get(2).isHover(), 20, updateY());
			g.drawString("Pressed: " + buttons.get(2).isPressed(), 20,
					updateY());

		}
	}

	// for debug mode
	private static int y;

	private static int updateY() {
		y += 15;
		return y;
	}

	public ArrayList<Button> getButtons() {
		return buttons;
	}

}
