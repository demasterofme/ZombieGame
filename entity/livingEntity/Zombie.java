package entity.livingEntity;

public class Zombie extends LivingEntity {

	private ZombieType type;
	
	public Zombie(ZombieType type, int x, int y) {
		super(x, y);
		this.type = type;
	}
	
	public void damage(int damage) {
		health -= damage;
		if (health <= 0)
			dead = true;
	}
	
}
