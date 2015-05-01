package gameState.inGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import launcher.GamePanel;
import entity.livingEntity.Zombie;
import entity.livingEntity.Zombie.ZombieType;
import gfx.Text;

public class Endless extends InGame {

	private int waveNumber = 0;
	private boolean waveInitiating = false;
	private boolean waveText = false;
	private long waveStartTimer;

	private int zombiesAmount;
	private int zombieIndex = 1;
	private int spawnIndex = 0;

	public Endless() {
		super();

	}

	public void update() {
		super.update();

		if (zombies.size() == 0 && !waveInitiating) {
			startWave(++waveNumber);
			waveStartTimer = System.nanoTime();
			if (waveNumber != 1) {
				Font font = new Font("Century Gothic", Font.PLAIN, 36);
				String text = "- W A V E   " + (waveNumber - 1)
						+ "   C O M P L E T E D -";
				texts.add(new Text(text, 2000, font, Color.WHITE,
						GamePanel.WINDOW_WIDTH
								/ 2
								- GamePanel.g.getFontMetrics(font).stringWidth(
										text) / 2, GamePanel.WINDOW_HEIGHT / 2));
			}
			waveText = false;
			waveInitiating = true;
		}

		if (waveInitiating) {

			int interWaveTime = 15000;
			if (waveNumber == 1)
				interWaveTime = 4000;

			int timeBetweenZombies = 1000;

			double difference = (System.nanoTime() - waveStartTimer) / 1000000;

			if ((difference - interWaveTime)
					/ (zombieIndex * timeBetweenZombies) > 1
					&& (difference - interWaveTime)
							/ ((zombieIndex + 1) * timeBetweenZombies) < 1) {
				if (!waveText) {
					Font font = new Font("Century Gothic", Font.PLAIN, 36);
					String text = "- W A V E   " + waveNumber + "   -";
					texts.add(new Text(text, 3000, font, Color.WHITE,
							GamePanel.WINDOW_WIDTH
									/ 2
									- GamePanel.g.getFontMetrics(font)
											.stringWidth(text) / 2,
							GamePanel.WINDOW_HEIGHT / 2));
					waveText = true;
				}
				zombieIndex++;
				// Temp position;
				zombies.add(new Zombie(ZombieType.SWARMER, spawnLocations.get(
						spawnIndex).getX(), spawnLocations.get(spawnIndex)
						.getY()));
				spawnIndex++;
				if (spawnIndex >= spawnLocations.size())
					spawnIndex = 0;
			}

			if (difference - interWaveTime > zombiesAmount * timeBetweenZombies) {
				zombieIndex = 0;
				waveInitiating = false;
			}
		}

	}

	private void startWave(int waveNumber) {
		zombiesAmount = (int) (Math.pow(3, waveNumber) + 20);
	}

	public void render(Graphics2D g) {

		super.render(g);

		// if (waveText) {
		//
		// double difference = (2000 - (double) (System.nanoTime() -
		// waveStartTimer) / 1000000) / 2000;
		// if (difference <= 0) {
		// waveStartTimer = System.nanoTime();
		// waveInitiating = true;
		// waveText = false;
		// }
		//
		// } else {
		// g.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		// String s = "- W A V E   " + waveNumber + "   -";
		// g.setColor(Color.WHITE);
		// // g.drawString(s, 50, 50);
		// }
	}

	public int getWaveNumber() {
		return waveNumber;
	}

}
