package titleScreen;

import gfx.Button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import launcher.GamePanel;

public class TitleScreen {

	public static ArrayList<Button> buttons;

	public TitleScreen() {

		buttons = new ArrayList<>();

		Button.font = new Font("Century Gothic", Font.PLAIN, 42);
		Button.g = GamePanel.image.getGraphics();

		// Add buttons to the screen, will be perfected later
		buttons.add(new Button(true, 200, Button.buttonType.START_GAME,
				"Start Game"));
		buttons.add(new Button(true, 300, Button.buttonType.OPTIONS, "Options"));
		buttons.add(new Button(true, 400, Button.buttonType.STOP_GAME, "Quit"));

	}

	public static void update() {

		for (Button b : buttons)
			b.update();

	}

	public static void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WINDOW_WIDTH, GamePanel.WINDOW_HEIGHT);
		g.setColor(Color.WHITE);

		for (Button b : TitleScreen.buttons)
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
			g.drawString("X: " + TitleScreen.buttons.get(2).getx() + " Y: "
					+ TitleScreen.buttons.get(0).gety(), 20, updateY());
			g.drawString("Width: " + TitleScreen.buttons.get(2).getWidth()
					+ " Heigth: " + TitleScreen.buttons.get(2).getHeight(), 20,
					updateY());
			g.drawString("Hover: " + TitleScreen.buttons.get(2).hover, 20,
					updateY());
			g.drawString("Pressed: " + TitleScreen.buttons.get(2).pressed, 20,
					updateY());
		}
	}

	// for debug mode
	private static int y;

	private static int updateY() {
		y += 15;
		return y;
	}

}
