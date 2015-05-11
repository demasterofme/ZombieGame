package gameState.TitleScreen;

import gameState.AlertBox;
import gameState.GameState;
import gameState.inGame.Endless;
import gfx.Button;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;

import launcher.GamePanel;
import sfx.Sound;

public class TitleScreen extends GameState {

	private ArrayList<Button> buttons;
	private Button button_start;
	private Button button_help;
	private Button button_quit;
	private Button button_sound;

	private BufferedImage backgroundImage;

	private Sound backgroundSound;

	@SuppressWarnings("unchecked")
	public TitleScreen() {

		buttons = new ArrayList<>();

		Font font = new Font("Century Gothic", Font.PLAIN, 42);

		// Add buttons to the screen, will be perfected later

		button_start = new Button(true, (int) (400 * GamePanel.vertScale),
				"Start Game", font);
		button_help = new Button(true, (int) (500 * GamePanel.vertScale),
				"Help", font);
		button_quit = new Button(true, (int) (600 * GamePanel.vertScale),
				"Quit", font);
		button_sound = new Button(GamePanel.WINDOW_WIDTH - 120, 10, "Sounds",
				new Font("Century Gothic", Font.PLAIN, 24));

		buttons.add(button_start);
		buttons.add(button_help);
		buttons.add(button_quit);
		buttons.add(button_sound);

		try {
			backgroundImage = ImageIO.read(GamePanel.class
					.getResource("/sprites/Background.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		BufferedImage newTexture = new BufferedImage(
				(int) (backgroundImage.getWidth() / GamePanel.horScale),
				(int) (backgroundImage.getHeight() / GamePanel.vertScale),
				BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.scale(1 / GamePanel.horScale, 1 / GamePanel.vertScale);
		AffineTransformOp scaleOp = new AffineTransformOp(at,
				AffineTransformOp.TYPE_BILINEAR);
		backgroundImage = scaleOp.filter(backgroundImage, newTexture);

		backgroundSound = new Sound("/sounds/TitleScreen.wav");

		font = new Font("Century Gothic", Font.PLAIN, 24);
		if (Sound.isMuted()) {
			@SuppressWarnings("rawtypes")
			Map attributes = font.getAttributes();
			attributes.put(TextAttribute.STRIKETHROUGH,
					TextAttribute.STRIKETHROUGH_ON);
			font = new Font(attributes);
		} else
			backgroundSound.loop();
		button_sound.setFont(font);

	}

	@SuppressWarnings("unchecked")
	public void update() {

		for (Button b : buttons)
			b.update();

		if (button_start.isPressed()) {
			backgroundSound.stop();
			Endless endless = new Endless();
			String message = "Welcome to Zombie Apocalypse version 1.0. We hope that you won't encounter any bugs what so ever, but please mind that that could happen. We hope that you'll enjoy this game. Here is a quick overview of the controls:	 W, A, S, D = walking  |  R = reload gun  |  SPACE = open shop  |  ESCAPE = pause  |  Mouse = shoot";

			AlertBox alertBox = new AlertBox(endless, message);
			GamePanel.changeGameState(alertBox);
		}

		if (button_help.isPressed()) {
			Desktop desktop = Desktop.isDesktopSupported() ? Desktop
					.getDesktop() : null;
			String errorMessage = "Sorry, we couldn't open the help page :( To view the help page, open a browser and go to: www.github.com/Dacaspex/ZombieGame/blob/master/README.md#the-game ";
			if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
				try {
					URL helpPage = new URL(
							"https://github.com/Dacaspex/ZombieGame/blob/master/README.md#the-game");
					desktop.browse(helpPage.toURI());
				} catch (Exception e) {
					GamePanel.changeGameState(new AlertBox(this, errorMessage));
				}
			} else {
				GamePanel.changeGameState(new AlertBox(this, errorMessage));
			}
		}

		if (button_quit.isPressed()) {
			backgroundSound.stop();
			GamePanel.running = false;
		}

		if (button_sound.isPressed()) {
			Sound.mute();
			Font font = new Font("Century Gothic", Font.PLAIN, 24);
			if (Sound.isMuted()) {
				@SuppressWarnings("rawtypes")
				Map attributes = font.getAttributes();
				attributes.put(TextAttribute.STRIKETHROUGH,
						TextAttribute.STRIKETHROUGH_ON);
				font = new Font(attributes);
				backgroundSound.stop();
			} else
				backgroundSound.loop();
			button_sound.setFont(font);
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
			g.drawString("X: " + buttons.get(1).getx() + " Y: "
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
