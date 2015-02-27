package gameState.inGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import entity.livingEntity.Zombie;
import launcher.GamePanel;

public class Endless extends InGame {

	private int waveNumber = 0;
	private boolean waveInitiating = false;
	private long waveStartTimer;

	public Endless() {
		super();
	}

	public void update() {
		super.update();
		if (zombies.size() == 0 && !waveInitiating) {
			startWave(++waveNumber);
			waveStartTimer = System.nanoTime();
			waveInitiating = true;
			zombies.add(new Zombie(Zombie.ZombieType.SWARMER, 1024, 1024));
		}
	}

	private void startWave(int waveNumber) {

	}

	public void render(Graphics2D g) {
		super.render(g);
		if (waveInitiating) {
			double waveStartDif = (2000 - (double) (System.nanoTime() - waveStartTimer) / 1000000) / 2000;
			g.setFont(new Font("Century Gothic", Font.PLAIN,
					(int) (waveStartDif * 16 + 20)));
			String s = "- W A V E   " + waveNumber + "   -";
			int length = (int) g.getFontMetrics().getStringBounds(s, g)
					.getWidth();
			g.setColor(Color.WHITE);
			g.drawString(s, (int) (waveStartDif)
					* (GamePanel.WINDOW_WIDTH / 2 - length / 2 - 50) + 50,
					(int) (waveStartDif) * (GamePanel.WINDOW_HEIGHT / 2 - 50)
							+ 50);
			if (waveStartDif <= 0)
				waveInitiating = false;
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
