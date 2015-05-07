package stats;

public class Stats {

	private int kills = 0;
	private int earnedMoney = 0;
	private int spentMoney = 0;
	private int damageDealt = 0;
	private int grenadesUsed = 0;
	private int bulletsFired = 0;
	private int time = 0;
	
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
	
	public void addKills(int amount) {
		kills += amount;
	}

	public int getEarnedMoney() {
		return earnedMoney;
	}

	public void setEarnedMoney(int earnedMoney) {
		this.earnedMoney = earnedMoney;
	}
	
	public void addEarnedMoney(int amount) {
		earnedMoney += amount;
	}

	public int getSpentMoney() {
		return spentMoney;
	}

	public void setSpentMoney(int spentMoney) {
		this.spentMoney = spentMoney;
	}
	
	public void addSpentMoney(int amount) {
		spentMoney += amount;
	}

	public int getDamageDealt() {
		return damageDealt;
	}

	public void setDamageDealt(int damageDealt) {
		this.damageDealt = damageDealt;
	}
	
	public void addDamageDealt(int amount) {
		damageDealt += amount;
	}

	public int getGrenadesUsed() {
		return grenadesUsed;
	}

	public void setGrenadesUsed(int grenadesUsed) {
		this.grenadesUsed = grenadesUsed;
	}
	
	public void addGrenadesUsed(int amount) {
		grenadesUsed += amount;
	}

	public int getBulletsFired() {
		return bulletsFired;
	}

	public void setBulletsFired(int bulletsFired) {
		this.bulletsFired = bulletsFired;
	}
	
	public void addBulletsFired(int amount) {
		bulletsFired += amount;
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
