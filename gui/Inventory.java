package gui;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import entity.Gun;
import entity.utility.Grenade;
import entity.utility.MedKit;
import entity.utility.Utility;

public class Inventory {

	private Gun slot1;
	private Gun slot2;
	private Gun slot3;

	private HashMap<Utility, Integer> slot4;
	private HashMap<Utility, Integer> slot5;

	public Inventory(Gun startGun) {

		this.slot1 = startGun;

	}

	public void update() {

	}

	public boolean addGun(Gun gun) {

		if (slot2 == null) {
			slot2 = gun;
			return true;
		}

		if (slot3 == null) {
			slot3 = gun;
			return true;
		}

		return false;
	}

	public boolean addUtility(Utility utility) {

		if (slot4.isEmpty()) {
			slot4.put(utility, 1);
			return true;
		}

		if (slot5.isEmpty()) {
			slot5.put(utility, 1);
			return true;
		}

		if (slot4.keySet().iterator().next() instanceof Grenade
				&& utility instanceof Grenade && slot4.get(0) < 3) {
			int amount = slot4.get(0);
			slot4.clear();
			slot4.put(utility, amount++);
			return true;
		}

		if (slot5.keySet().iterator().next() instanceof Grenade
				&& utility instanceof Grenade && slot5.get(0) < 3) {
			int amount = slot5.get(0);
			slot5.clear();
			slot5.put(utility, amount++);
			return true;
		}

		return false;
	}

	public boolean removeItem(int slot) {

		switch (slot) {
		case 1:
			return false;
		case 2:
			if (slot2 != null) {
				slot2 = null;
				return true;
			} else {
				return false;
			}
		case 3:
			if (slot3 != null) {
				slot3 = null;
				return true;
			} else {
				return false;
			}
		case 4:
			if (!slot4.isEmpty() && slot4.get(0) > 1) {
				int currentAmount = slot4.get(0);
				Utility currentUtility = slot4.keySet().iterator().next();
				slot4.clear();
				slot4.put(currentUtility, currentAmount--);
				return true;
			} else if (!slot4.isEmpty()) {
				slot4.clear();
				return true;
			}
			return false;
		case 5:
			if (!slot5.isEmpty() && slot5.get(0) > 1) {
				int currentAmount = slot5.get(0);
				Utility currentUtility = slot5.keySet().iterator().next();
				slot5.clear();
				slot5.put(currentUtility, currentAmount--);
				return true;
			} else if (!slot5.isEmpty()) {
				slot5.clear();
				return true;
			}
			return false;
		default:
			return false;
		}

	}

	public void draw(Graphics2D g) {

	}

}
