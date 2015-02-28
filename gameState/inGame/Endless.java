package gameState.inGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import launcher.GamePanel;
import entity.livingEntity.Zombie;
import entity.livingEntity.Zombie.ZombieType;

public class Endless extends InGame {

	private int waveNumber = 0;
	private boolean waveInitiating = false;
	private boolean waveText = false;
	private long waveStartTimer;

	private int zombiesAmount;
	private int zombieIndex = 1;

	public Endless() {
		super();
	}

	public void update() {
		super.update();
		if (zombies.size() == 0 && !waveInitiating && !waveText) {
			startWave(++waveNumber);
			waveStartTimer = System.nanoTime();
			waveText = true;
		}

		if (waveInitiating) {
			double difference = (System.nanoTime() - waveStartTimer) / 1000000;

			if (difference / (zombieIndex * 100) > 1
					&& difference / ((zombieIndex + 1) * 100) < 1) {
				zombieIndex++;
				// Temp position;
				zombies.add(new Zombie(ZombieType.SWARMER,
						1024 + zombieIndex * 5, 1024 + zombieIndex * 5));
			}

			if (difference > zombiesAmount * 100) {
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
		if (waveText) {
			double difference = (2000 - (double) (System.nanoTime() - waveStartTimer) / 1000000) / 2000;
			g.setFont(new Font("Century Gothic", Font.PLAIN,
					(int) (difference * 16 + 20)));
			String s = "- W A V E   " + waveNumber + "   -";
			int length = (int) g.getFontMetrics().getStringBounds(s, g)
					.getWidth();
			g.setColor(Color.WHITE);
			g.drawString(s, (int) (difference)
					* (GamePanel.WINDOW_WIDTH / 2 - length / 2 - 50) + 50,
					(int) (difference) * (GamePanel.WINDOW_HEIGHT / 2 - 50)
							+ 50);
			if (difference <= 0) {
				waveStartTimer = System.nanoTime();
				waveInitiating = true;
				waveText = false;
			}
		} else {
			g.setFont(new Font("Century Gothic", Font.PLAIN, 20));
			String s = "- W A V E   " + waveNumber + "   -";
			g.setColor(Color.WHITE);
			g.drawString(s, 50, 50);
		}
	}

	public int getWaveNumber() {
		return waveNumber;
	}

}
