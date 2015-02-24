package gameState;

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

public class Shop extends GameState {

	private BufferedImage sourceBackgroundImage;
	private BufferedImage blurredBackgroundImage;
	private BufferedImage blurredBackgroundImageEdges;
	float[] matrix;

	private InGame oldState;

	private ArrayList<Button> buttons;
	private Button button_back;
	
	private Graphics2D g;

	public Shop(InGame oldState) {

		this.oldState = oldState;
		
		matrix = new float[400];
		for (int i = 0; i < 400; i++)
			matrix[i] = 1.0f / 400.0f;

		sourceBackgroundImage = new BufferedImage(
				(int) (GamePanel.image.getWidth() * 1.1),
				(int) (GamePanel.image.getHeight() * 1.1),
				BufferedImage.TYPE_INT_RGB);

		Graphics g = sourceBackgroundImage.getGraphics();

		g.drawImage(
				GamePanel.image,
				(sourceBackgroundImage.getWidth() - GamePanel.image.getWidth()) / 2,
				(sourceBackgroundImage.getHeight() - GamePanel.image
						.getHeight()) / 2, GamePanel.image.getWidth(),
				GamePanel.image.getHeight(), null);
		g.dispose();

		BufferedImageOp op = new ConvolveOp(new Kernel(20, 20, matrix),
				ConvolveOp.EDGE_ZERO_FILL, null);
		blurredBackgroundImageEdges = op.filter(sourceBackgroundImage,
				blurredBackgroundImageEdges);
		blurredBackgroundImage = new BufferedImage(GamePanel.WINDOW_WIDTH,
				GamePanel.WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = blurredBackgroundImage.getGraphics();
		g.drawImage(
				blurredBackgroundImageEdges.getSubimage((sourceBackgroundImage
						.getWidth() - GamePanel.image.getWidth()) / 2,
						(sourceBackgroundImage.getHeight() - GamePanel.image
								.getHeight()) / 2, GamePanel.WINDOW_WIDTH,
						GamePanel.WINDOW_HEIGHT), 0, 0, GamePanel.WINDOW_WIDTH,
				GamePanel.WINDOW_HEIGHT, null);
		g.dispose();

		buttons = new ArrayList<>();

		button_back = new Button(400, 400, "BACK", new Font("Century Gothic",
				Font.PLAIN, 24), new Font("Century Gothic", Font.BOLD, 24));

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
