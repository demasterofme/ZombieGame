package gameState;

import gameState.TitleScreen.TitleScreen;
import gfx.Button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import launcher.GamePanel;
import stats.Stats;

public class StatScreen extends GameState {

	private ArrayList<Button> buttons;
	private Button button_back;

	private long startTime;

	private ArrayList<String> texts;

	private Stats stats;

	private long time;

	private int textTimer = 1;

	public StatScreen(Stats stats) {

		buttons = new ArrayList<>();

		button_back = new Button(true, GamePanel.WINDOW_HEIGHT - 100,
				"Back to titlescreen", new Font("Century Gothic", Font.PLAIN,
						42));

		buttons.add(button_back);

		startTime = System.nanoTime();

		texts = new ArrayList<>();

		this.stats = stats;

		time = (startTime - stats.getStartTime()) / 1000000000;

		stats.setTime(convertTimeString(time));

	}

	public String convertTimeString(long seconds) {
		int hr = (int) (seconds / 3600);
		int rem = (int) (seconds % 3600);
		int mn = rem / 60;
		int sec = rem % 60;

		return ((hr < 10 ? "0" : "") + hr + ":" + (mn < 10 ? "0" : "") + mn
				+ ":" + (sec < 10 ? "0" : "") + sec);
	}

	public void update() {

		for (Button b : buttons)
			b.update();

		if (button_back.isPressed()) {
			GamePanel.changeGameState(new TitleScreen());
		}

		if (textTimer != 9) {

			double dif = (System.nanoTime() - startTime) / 1000000;

			double part = dif / ((double) 1000 * (double) textTimer);

			if (part > 1)
				part = 1;

			if (texts.size() >= textTimer)
				texts.remove(textTimer - 1);

			switch (textTimer) {
			case 1:
				texts.add("Zombies killed: " + (int) (stats.getKills() * part));
				break;
			case 2:
				texts.add("Money earned: "
						+ (int) (stats.getEarnedMoney() * part));
				break;
			case 3:
				texts.add("Money spent: "
						+ (int) (stats.getSpentMoney() * part));
				break;
			case 4:
				texts.add("Damage dealt: "
						+ (int) (stats.getDamageDealt() * part));
				break;
			case 5:
				texts.add("Bullets fired: "
						+ (int) (stats.getBulletsFired() * part));
				break;
			case 6:
				texts.add("Grenades thrown: "
						+ (int) (stats.getGrenadesUsed() * part));
				break;
			case 7:
				texts.add("Time survived: "
						+ convertTimeString((long) (time * part)));
				break;
			case 8:
				texts.add("Waves survived: " + (int) (stats.getWave() * part));
			default:
				break;
			}

			if (dif > 1000 * textTimer)
				textTimer++;

		}

	}

	public void render(Graphics2D g) {

		g.setColor(Color.WHITE);

		g.fillRect(0, 0, GamePanel.WINDOW_WIDTH, GamePanel.WINDOW_HEIGHT);

		Font font = new Font("Century Gothic", Font.PLAIN, 70);
		String text = "Statistics";

		g.setColor(Color.RED);

		g.setFont(font);

		g.drawString(text, GamePanel.WINDOW_WIDTH / 2
				- g.getFontMetrics(font).stringWidth(text) / 2, 100);

		font = new Font("Century Gothic", Font.PLAIN, 40);

		g.setColor(Color.BLACK);

		g.setFont(font);

		for (int yOffset = 1; yOffset < 9; yOffset++) {

			if (texts.size() < yOffset)
				break;

			text = texts.get(yOffset - 1);
			g.drawString(text, (int) (GamePanel.WINDOW_WIDTH
					* (yOffset < 5 ? 0.333 : 0.666) - g.getFontMetrics(font)
					.stringWidth(text) / 2), (yOffset < 5 ? yOffset
					: yOffset - 4) * 160 + 100);

		}

		for (Button b : buttons)
			b.draw(g);

	}

	public ArrayList<Button> getButtons() {
		return buttons;
	}

}
