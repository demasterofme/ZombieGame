package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import launcher.GamePanel;

public class Settings extends GameState {

	private BufferedImage sourceBackgroundImage;
	private BufferedImage blurredBackgroundImage;
	float[] matrix = { 0.111f, 0.111f, 0.111f, 0.111f, 0.111f, 0.111f, 0.111f,
			0.111f, 0.111f, };

	public Settings() {

		sourceBackgroundImage = GamePanel.image;
		
		BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, matrix), ConvolveOp.EDGE_ZERO_FILL, null);
		blurredBackgroundImage = op.filter(sourceBackgroundImage,
				blurredBackgroundImage);

	}

	public void update() {

	}

	public void render(Graphics2D g) {

		g.drawImage(blurredBackgroundImage, 0, 0, GamePanel.WINDOW_WIDTH,
				GamePanel.WINDOW_HEIGHT, null);

		g.setColor(Color.WHITE);
		g.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		g.drawString("Options", 400, 400);

	}

}
