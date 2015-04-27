package gameState.TitleScreen;

import gameState.GameState;
import gfx.Button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;

import launcher.GamePanel;

public class Settings extends GameState {

	private BufferedImage sourceBackgroundImage;
	private BufferedImage blurredBackgroundImageEdges;
	private BufferedImage blurredBackgroundImage;
	float[] matrix;

	private TitleScreen oldState;

	private ArrayList<Button> buttons;

	private Button button_back;

	public Settings(TitleScreen oldState) {

		this.oldState = oldState;

		matrix = new float[400];
		for (int i = 0; i < 400; i++)
			matrix[i] = 1.0f / 400.0f;

		sourceBackgroundImage = new BufferedImage((int) (GamePanel
				.getLastFrame().getWidth() * 1.1), (int) (GamePanel
				.getLastFrame().getHeight() * 1.1), BufferedImage.TYPE_INT_RGB);

		Graphics g = sourceBackgroundImage.getGraphics();

		g.drawImage(GamePanel.getLastFrame(),
				(sourceBackgroundImage.getWidth() - GamePanel.getLastFrame()
						.getWidth()) / 2,
				(sourceBackgroundImage.getHeight() - GamePanel.getLastFrame()
						.getHeight()) / 2, GamePanel.getLastFrame().getWidth(),
				GamePanel.getLastFrame().getHeight(), null);
		g.dispose();

		BufferedImageOp op = new ConvolveOp(new Kernel(20, 20, matrix),
				ConvolveOp.EDGE_ZERO_FILL, null);
		blurredBackgroundImageEdges = op.filter(sourceBackgroundImage,
				blurredBackgroundImageEdges);
		blurredBackgroundImage = new BufferedImage(GamePanel.WINDOW_WIDTH,
				GamePanel.WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = blurredBackgroundImage.getGraphics();
		g.drawImage(blurredBackgroundImageEdges.getSubimage(
				(sourceBackgroundImage.getWidth() - GamePanel.getLastFrame()
						.getWidth()) / 2,
				(sourceBackgroundImage.getHeight() - GamePanel.getLastFrame()
						.getHeight()) / 2, GamePanel.WINDOW_WIDTH,
				GamePanel.WINDOW_HEIGHT), 0, 0, GamePanel.WINDOW_WIDTH,
				GamePanel.WINDOW_HEIGHT, null);
		g.dispose();

		buttons = new ArrayList<>();

		button_back = new Button(true, 300, "Options", new Font(
				"Century Gothic", Font.BOLD, 43));

		buttons.add(button_back);

	}

	public void update() {

		for (Button b : buttons)
			b.update();

		if (button_back.isPressed())
			GamePanel.changeGameState(oldState);

	}

	public void render(Graphics2D g) {

		g.drawImage(blurredBackgroundImage, 0, 0, GamePanel.WINDOW_WIDTH,
				GamePanel.WINDOW_HEIGHT, null);

		g.setColor(Color.WHITE);

		for (Button b : buttons)
			b.draw(g);

	}

	public ArrayList<Button> getButtons() {
		return buttons;
	}

}
