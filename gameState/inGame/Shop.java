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
	private ArrayList<Button> gunButtons;
	private Button button_back;
	private Button button_previous;
	private Button button_next;

	private int page = 1;

	private boolean available;

	private ArrayList<Text> texts;

	public Shop(InGame oldState, boolean available) {

		this.oldState = oldState;

		this.available = available;

		buttons = new ArrayList<>();
		gunButtons = new ArrayList<>();
		texts = new ArrayList<>();

		button_back = new Button(
				(int) (GamePanel.WINDOW_WIDTH - GamePanel.WINDOW_WIDTH * 0.2),
				(int) (GamePanel.WINDOW_HEIGHT - GamePanel.WINDOW_HEIGHT * 0.2),
				"BACK", new Font("Century Gothic", Font.PLAIN, 24));

		button_previous = new Button((int) (GamePanel.WINDOW_WIDTH * 0.11) + 20,
				(int) (GamePanel.WINDOW_HEIGHT * 0.45) + 20, "<<", new Font("Century Gothic",
						Font.PLAIN, 26));

		button_next = new Button((int) (GamePanel.WINDOW_WIDTH * 0.855) + 20,
				(int) (GamePanel.WINDOW_HEIGHT * 0.45) + 20, ">>", new Font("Century Gothic",
						Font.PLAIN, 26));

		buttons.add(button_back);
		buttons.add(button_previous);
		buttons.add(button_next);

		int x = 1, y = 1;

		for (Gun gun : InGame.guns) {
			if (x == 6) {
				x = 1;
				y++;
			}

			Font font = new Font("Century Gothic", Font.PLAIN, 20);
			String text = gun.getName() + ": " + gun.getPrice() + "$";

			gunButtons
					.add(new Button(
							(int) (GamePanel.WINDOW_WIDTH * 0.16 * x++)
									+ (int) (gun.getTexture().getWidth() * 0.1 + 40)
									/ 2
									- GamePanel.g.getFontMetrics(font)
											.stringWidth(text) / 2,
							(int) (GamePanel.WINDOW_HEIGHT * 0.2 * y + gun.getTexture().getHeight() * 0.1)
									+ 45
									+ GamePanel.g.getFontMetrics(font)
											.getHeight(), text, font));

		}

		oldState.getPlayer().resetKeys();

	}

	public void update() {

		oldState.update();

		for (Button b : buttons)
			b.update();

		for (Button b : gunButtons) {
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
					else {
						InGame.player.setMoney(InGame.player.getMoney()
								- gun.getPrice());
						InGame.player.getStats().addSpentMoney(gun.getPrice());
					}
			}
		}

		for (int i = 0; i < texts.size(); i++) {
			if (texts.get(i).update()) {
				texts.remove(i);
				i--;
			}
		}

		if (button_back.isPressed()) {
			GamePanel.changeGameState(oldState);
		}
			
		if (button_next.isPressed())
			page++;

		if (button_previous.isPressed())
			page--;

		if (page > 2)
			page = 1;

		if (page < 1)
			page = 2;

	}

	public void render(Graphics2D g) {

		oldState.render(g);

		g.setColor(new Color(24, 24, 24, 240));

		g.fillRect((int) (GamePanel.WINDOW_WIDTH * 0.1), (int) (GamePanel.WINDOW_HEIGHT * 0.1), (int) (GamePanel.WINDOW_WIDTH - GamePanel.WINDOW_WIDTH * 0.2),
				(int) (GamePanel.WINDOW_HEIGHT - GamePanel.WINDOW_HEIGHT * 0.2));

		g.setColor(Color.WHITE);

		for (Button b : buttons)
			b.draw(g);

		Font font = new Font("Century Gothic", Font.PLAIN, 48);

		g.setFont(font);

		if (page == 1) {

			g.drawString("SHOP - GUNS", GamePanel.WINDOW_WIDTH / 2
					- g.getFontMetrics(font).stringWidth("SHOP - GUNS") / 2,
					150);

			if (!available)
				g.setColor(Color.GRAY);

			for (Button b : gunButtons)
				b.draw(g);

			int x = 1, y = 1;

			for (Gun gun : InGame.guns) {
				if (x == 6) {
					x = 1;
					y++;
				}
				g.drawRenderedImage(gun.getTexture(), GamePanel
						.getAffineTransform(gun.getTexture(),
								(int) (GamePanel.WINDOW_WIDTH * 0.16 * x) + 20,
								(int) (GamePanel.WINDOW_HEIGHT * 0.2 * y) + 20, 0.1, 0));
				g.drawRect((int) (GamePanel.WINDOW_WIDTH * 0.16 * x++), (int) (GamePanel.WINDOW_HEIGHT * 0.2 * y),
						(int) (gun.getTexture().getWidth() * 0.1) + 40,
						(int) (gun.getTexture().getHeight() * 0.1) + 40);
			}

		} else if (page == 2) {
			g.drawString("SHOP - UTILITIES", GamePanel.WINDOW_WIDTH / 2
					- g.getFontMetrics(font).stringWidth("SHOP - UTILITIES") / 2,
					150);

			if (!available)
				g.setColor(Color.GRAY);
			
		}

		for (Text t : texts)
			t.draw(g);

	}

	public ArrayList<Button> getButtons() {
		@SuppressWarnings("unchecked")
		ArrayList<Button> toReturn = (ArrayList<Button>) buttons.clone();
		toReturn.addAll(gunButtons);
		return toReturn;
	}

	public InGame getOldState() {
		return oldState;
	}

}
