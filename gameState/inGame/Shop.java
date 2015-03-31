package gameState.inGame;

import entity.Gun;
import gameState.GameState;
import gfx.Button;
import gfx.Text;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import launcher.GamePanel;

public class Shop extends GameState {

	private InGame oldState;

	private ArrayList<Button> buttons;
	private ArrayList<Button> buyButtons;
	private Button button_back;

	private boolean available;

	private ArrayList<Text> texts;

	private static double sx = GamePanel.WINDOW_WIDTH;
	private static double sy = GamePanel.WINDOW_HEIGHT;

	public Shop(InGame oldState, boolean available) {

		this.oldState = oldState;

		this.available = available;

		buttons = new ArrayList<>();
		buyButtons = new ArrayList<>();
		texts = new ArrayList<>();

		button_back = new Button(
				(int) (GamePanel.WINDOW_WIDTH - GamePanel.WINDOW_WIDTH * 0.2),
				(int) (GamePanel.WINDOW_HEIGHT - GamePanel.WINDOW_HEIGHT * 0.2),
				"BACK", new Font("Century Gothic", Font.PLAIN, 24), new Font(
						"Century Gothic", Font.BOLD, 24));

		buttons.add(button_back);

		int x = 1, y = 1;

		for (Gun gun : InGame.guns) {
			if (x == 6) {
				x = 1;
				y++;
			}

			Font font = new Font("Century Gothic", Font.PLAIN, 20);
			String text = gun.getName() + ": " + gun.getPrice() + "$";

			buyButtons
					.add(new Button(
							(int) (sx * 0.16 * x++)
									+ (int) (gun.getTexture().getWidth() * 0.1 + 40)
									/ 2
									- GamePanel.g.getFontMetrics(font)
											.stringWidth(text) / 2,
							(int) (sy * 0.2 * y + gun.getTexture().getHeight() * 0.1)
									+ 45
									+ GamePanel.g.getFontMetrics(font)
											.getHeight(), text, font, new Font(
									"Century Gothic", Font.BOLD, 20)));

		}

		oldState.getPlayer().resetKeys();

	}

	public void update() {

		oldState.update();

		for (Button b : buttons)
			b.update();

		for (Button b : buyButtons) {
			b.update();
			if (b.isPressed()) {
				String name = b.getText().split(":")[0];
				Gun gun = null;
				for (Gun g : InGame.guns)
					if (g.getName().equalsIgnoreCase(name))
						gun = g;
				if (gun != null)
					if (InGame.player.getMoney() < gun.getPrice())
						texts.add(new Text("Unsufficient cash!", 2000,
								new Font("Century Gothic", Font.PLAIN, 36),
								Color.RED, GamePanel.WINDOW_WIDTH / 2,
								GamePanel.WINDOW_HEIGHT / 2));
					else if (!InGame.player.getInventory().addGun(gun))
						texts.add(new Text("Unsufficient room!", 2000,
								new Font("Century Gothic", Font.PLAIN, 36),
								Color.RED, GamePanel.WINDOW_WIDTH / 2,
								GamePanel.WINDOW_HEIGHT / 2));
					else
						InGame.player.setMoney(InGame.player.getMoney()
								- gun.getPrice());
			}
		}

		for (int i = 0; i < texts.size(); i++) {
			if (texts.get(i).update()) {
				texts.remove(i);
				i--;
			}
		}

		if (button_back.isPressed())
			GamePanel.changeGameState(oldState);

	}

	public void render(Graphics2D g) {

		oldState.render(g);

		g.setColor(new Color(24, 24, 24, 240));

		g.fillRect((int) (sx * 0.1), (int) (sy * 0.1), (int) (sx - sx * 0.2),
				(int) (sy - sy * 0.2));

		g.setColor(Color.WHITE);

		Font font = new Font("Century Gothic", Font.PLAIN, 48);

		g.setFont(font);
		g.drawString("SHOP", GamePanel.WINDOW_WIDTH / 2
				- g.getFontMetrics(font).stringWidth("SHOP") / 2, 150);

		for (Button b : buttons)
			b.draw(g);

		for (Button b : buyButtons)
			b.draw(g);

		int x = 1, y = 1;

		for (Gun gun : InGame.guns) {
			if (x == 6) {
				x = 1;
				y++;
			}
			g.drawRenderedImage(gun.getTexture(), GamePanel.getAffineTransform(
					gun.getTexture(), (int) (sx * 0.16 * x) + 20,
					(int) (sy * 0.2 * y) + 20, 0.1, 0));
			g.drawRect((int) (sx * 0.16 * x++), (int) (sy * 0.2 * y),
					(int) (gun.getTexture().getWidth() * 0.1) + 40, (int) (gun
							.getTexture().getHeight() * 0.1) + 40);
		}

		for (Text t : texts)
			t.draw(g);

	}

	public ArrayList<Button> getButtons() {
		@SuppressWarnings("unchecked")
		ArrayList<Button> toReturn = (ArrayList<Button>) buttons.clone();
		toReturn.addAll(buyButtons);
		return toReturn;
	}

	public InGame getOldState() {
		return oldState;
	}

}
