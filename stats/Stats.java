package stats;

public class Stats {

	private int kills;
	private int earnedMoney;
	private int spentMoney;
	private int damageDealt;
	private int grenades;
	private int bullets;
	private int time;
	
	private long startTime;
	
	public Stats() {
		startTime = System.nanoTime();
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getEarnedMoney() {
		return earnedMoney;
	}

	public void setEarnedMoney(int earnedMoney) {
		this.earnedMoney = earnedMoney;
	}

	public int getSpentMoney() {
		return spentMoney;
	}

	public void setSpentMoney(int spentMoney) {
		this.spentMoney = spentMoney;
	}

	public int getDamageDealt() {
		return damageDealt;
	}

	public void setDamageDealt(int damageDealt) {
		this.damageDealt = damageDealt;
	}

	public int getGrenades() {
		return grenades;
	}

	public void setGrenades(int grenades) {
		this.grenades = grenades;
	}

	public int getBullets() {
		return bullets;
	}

	public void setBullets(int bullets) {
		this.bullets = bullets;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
}
