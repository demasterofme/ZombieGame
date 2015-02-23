package gameState;

import gfx.Button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;

import launcher.GamePanel;

public class Shop extends GameState {

	private BufferedImage sourceBackgroundImage;
	private BufferedImage blurredBackgroundImage;
	float[] matrix = { 0.111f, 0.111f, 0.111f, 0.111f, 0.111f, 0.111f, 0.111f,
			0.111f, 0.111f, };

	private InGame oldState;

	private ArrayList<Button> buttons;

	private Button button_back;

	public Shop(InGame oldState) {

		this.oldState = oldState;
		sourceBackgroundImage = GamePanel.image;

		BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, matrix),
				ConvolveOp.EDGE_ZERO_FILL, null);
		blurredBackgroundImage = op.filter(sourceBackgroundImage,
				blurredBackgroundImage);

		buttons = new ArrayList<>();

		button_back = new Button(400, 400, "BACK",
				new Font("Century Gothic", Font.PLAIN, 24), new Font(
						"Century Gothic", Font.BOLD, 24));

		buttons.add(button_back);

	}

	public void update() {

		for (Button b : buttons)
			b.update();

		if (button_back.isPressed())
			GamePanel.gameState = oldState;

	}

	public void render(Graphics2D g) {

		g.drawImage(blurredBackgroundImage, 0, 0, GamePanel.WINDOW_WIDTH,
				GamePanel.WINDOW_HEIGHT, null);

		g.setColor(Color.WHITE);
		g.setFont(new Font("Century Gothic", Font.PLAIN, 48));
		g.drawString("SHOP", GamePanel.WINDOW_WIDTH / 2, 150);

		for (Button b : buttons)
			b.draw(g);

	}

	public ArrayList<Button> getButtons() {
		return buttons;
	}

}
